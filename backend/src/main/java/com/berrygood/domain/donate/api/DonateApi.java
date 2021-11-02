package com.berrygood.domain.donate.api;
import com.berrygood.domain.donate.api.request.Participant;
import com.berrygood.domain.donate.api.response.DonateInfo;
import com.berrygood.domain.donate.application.DonateImgService;
import com.berrygood.domain.donate.application.Impl.DonateParticipantServiceImpl;
import com.berrygood.domain.donate.entity.Donate;
import com.berrygood.domain.donate.entity.DonateImg;
import com.berrygood.domain.donate.entity.DonateParticipant;
import com.berrygood.domain.funding.entity.Funding;
import com.berrygood.domain.user.application.UserService;
import com.berrygood.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.berrygood.domain.comp.application.CompService;
import com.berrygood.domain.comp.entity.Comp;
import com.berrygood.domain.donate.application.DonateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Api(value = "기부 API", tags = {"Donate"})
@RestController
@RequestMapping("api/donate")
public class DonateApi {
    
    @Autowired
    private DonateService donateService;

    @Autowired
    private DonateImgService donateImgService;

    @Autowired
    private UserService userService;

    @Autowired
    private CompService compService;

    @Autowired
    private DonateParticipantServiceImpl donateParticipantService;

    final static String basePath = "images" + File.separator + "donate" + File.separator;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "기부 등록", notes = "<strong>기부 등록</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> register(@RequestParam(required = false) String email,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String content,
                                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date due,
                                           @RequestPart(value="files", required=false) List<MultipartFile> files) throws IOException {
        if(compService.getCompByEmail(email).isPresent()) {
            Comp comp = compService.getCompByEmail(email).get();
            Donate donate = Donate.builder()
                    .comp(comp)
                    .due(due)
                    .content(content)
                    .name(name)
                    .time(new Date(System.currentTimeMillis()))
                    .build();
            donateService.registerDonate(donate);

            if(Objects.nonNull(files)) {
                for(MultipartFile file : files) {
                    String donatePath = basePath + donate.getNo() + File.separator;

                    File baseFile = new File(donatePath);
                    if(!baseFile.exists()) {
                        baseFile.mkdirs();
                    }
                    String filePath = donatePath + file.getOriginalFilename();
                    Path path = Paths.get(filePath).toAbsolutePath();

                    file.transferTo(path.toFile());
                    DonateImg donateImg = DonateImg.builder()
                            .donate(donate)
                            .imgPath(filePath)
                            .build();
                    donateImgService.registerDonateImg(donateImg);
                }
            }

            return ResponseEntity.status(201).body("Success");
        }
        return ResponseEntity.status(404).body(null);
    }
    
    @PostMapping(value = "/{donateNo}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "기부 정보 수정", notes = "<strong>donate_no</strong>로 기부 정보 수정")
    public ResponseEntity<?> update(@PathVariable("donateNo") Long no,
                                    @RequestParam(required = false) String email,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String content,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date due,
                                    @RequestPart(value="files", required=false) List<MultipartFile> files
    ) throws IOException {
        if(compService.getCompByEmail(email).isPresent()) {
            Comp comp = compService.getCompByEmail(email).get();
            Donate donateInfo = Donate
                    .builder()
                    .comp(comp)
                    .due(due)
                    .content(content)
                    .name(name)
                    .build();

            if(donateService.updateDonate(no, donateInfo) == 1) {
                if(donateService.selectDonate(no).isEmpty()) {
                    return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
                }
                Donate donate = donateService.selectDonate(no).get();
                donateImgService.deleteDonateImg(donate);

                for(MultipartFile file : files) {
                    String volunteerPath = basePath + no + File.separator;

                    File baseFile = new File(volunteerPath);
                    if(!baseFile.exists()) {
                        baseFile.mkdirs();
                    }
                    String filePath = volunteerPath + file.getOriginalFilename();
                    Path path = Paths.get(filePath).toAbsolutePath();

                    file.transferTo(path.toFile());
                    DonateImg donateImg = DonateImg.builder()
                            .donate(donate)
                            .imgPath(filePath)
                            .build();
                    donateImgService.registerDonateImg(donateImg);
                }

                return ResponseEntity.status(200).body("Success");
            }
        }

        return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
    }
    
    @GetMapping()
    @ApiOperation(value = "기부 전체 조회", notes = "전체 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<DonateInfo>> getAllDonate() {
        List<DonateInfo> donateInfos = new ArrayList<>();
        if(donateService.selectAllDonate().isPresent()) {
            List<Donate> donates = donateService.selectAllDonate().get();
            for(Donate donate : donates) {
                if(donateImgService.selectDonateImg(donate).isPresent()
                        && donateParticipantService.getDonateParticipant(donate).isPresent()) {
                    List<DonateImg> donateImgs = donateImgService.selectDonateImg(donate).get();

                    List<String> paths = new ArrayList<>();
                    for(DonateImg donateImg : donateImgs) {
                        paths.add(donateImg.getImgPath());
                    }

                    List<DonateParticipant> donateParticipants = donateParticipantService.getDonateParticipant(donate).get();
                    Long sum = 0L;
                    for(DonateParticipant donateParticipant : donateParticipants) {
                        sum += donateParticipant.getAmount();
                    }

                    donateInfos.add(DonateInfo.builder()
                            .donate(donate)
                            .imgPath(paths)
                            .totalCnt(donateParticipants.size())
                            .totalAmount(sum)
                            .build());
                }
            }
            return ResponseEntity.status(200).body(donateInfos);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(donateInfos);
        }
    }
    @GetMapping("/donate/{email}")
    @ApiOperation(value = "유저 기부 전체 조회", notes = "유저 기부 조회")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "성공"),
    	@ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<DonateParticipant>> getAllDonateByUser(@PathVariable String email) {
    	if(userService.getUserByEmail(email).isPresent()) {
    		User user = userService.getUserByEmail(email).get();
    		if(donateParticipantService.getDonateParticipantByUser(user).isPresent()) {
    			List<DonateParticipant> participants = donateParticipantService.getDonateParticipantByUser(user).get();
    			return ResponseEntity.status(200).body(participants);
    		}
    	}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    @GetMapping("/donate/comp/{email}")
    @ApiOperation(value = "기업이 등록한 기부 전체 조회", notes = "기업 기부 조회")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "성공"),
    	@ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<Donate>> getAllDonateByComp(@PathVariable String email) {
    	if(compService.getCompByEmail(email).isPresent()) {
    		Comp comp = compService.getCompByEmail(email).get();
    		if(donateService.searchDonateByComp(comp).isPresent()) {
    			List<Donate> donates = donateService.searchDonateByComp(comp).get();
    			return ResponseEntity.status(200).body(donates);
    		}
    	}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    @GetMapping("/comp/{no}")
    @ApiOperation(value = "기업이 등록한 기부에 참여한 내역 전체 조회", notes = "기업의 기부에 참여한 내역 조회")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "성공"),
    	@ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<DonateParticipant>> getAllDonateParticipantByDonate(@PathVariable Long no) {
    	if(donateService.selectDonate(no).isPresent()) {
    		Donate donate = donateService.selectDonate(no).get();
    		if(donateParticipantService.getDonateParticipant(donate).isPresent()) {
    			List<DonateParticipant> participants = donateParticipantService.getDonateParticipant(donate).get();
    			return ResponseEntity.status(200).body(participants);
    		}
    	}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @GetMapping("/{donate_id}")
    @ApiOperation(value = "기부 상세 조회", notes = "기부 상세 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<DonateInfo> getDonate(@PathVariable Long donate_id) {
        if(donateService.selectDonate(donate_id).isPresent()) {
            Donate donate = donateService.selectDonate(donate_id).get();
            if(donateImgService.selectDonateImg(donate).isPresent()
                    && donateParticipantService.getDonateParticipant(donate).isPresent()) {
                List<DonateImg> donateImgs = donateImgService.selectDonateImg(donate).get();

                List<String> paths = new ArrayList<>();
                for(DonateImg donateImg : donateImgs) {
                    paths.add(donateImg.getImgPath());
                }

                List<DonateParticipant> donateParticipants = donateParticipantService.getDonateParticipant(donate).get();
                Long sum = 0L;
                List<String> messages = new ArrayList<>(); 
                for(DonateParticipant donateParticipant : donateParticipants) {
                    sum += donateParticipant.getAmount();
                    messages.add(donateParticipant.getMessage());
                }

                DonateInfo donateInfo = DonateInfo.builder()
                        .donate(donate)
                        .imgPath(paths)
                        .totalCnt(donateParticipants.size())
                        .totalAmount(sum)
                        .messages(messages)
                        .build();
                return ResponseEntity.status(200).body(donateInfo);
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    @DeleteMapping("/{no}")
    @ApiOperation(value = "기부 정보 삭제", notes = "<strong>no값</strong>를 통해 기부 정보 삭제")
    @ApiResponses({
            @ApiResponse(code = 204, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> delete(@PathVariable Long no) {
        try {
            if(donateService.selectDonate(no).isPresent()) {
                Donate donate = donateService.selectDonate(no).get();

                donateParticipantService.deleteAllDonateParticipant(donate);
                donateImgService.deleteDonateImg(donate);

                int response = donateService.deleteDonate(no);
                return ResponseEntity.status(204).body("Success");
            }
            return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
        }
    }

    @PostMapping("/participant")
    @ApiOperation(value = "기부 참가자 등록", notes = "<strong>기부 참가자</strong>등록")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "중복"),
            @ApiResponse(code = 500, message = "실패")
    })
    public ResponseEntity<?> donateParticipant(@RequestBody Participant participantRequest) {
        try {
            if(userService.getUserByEmail(participantRequest.getUserEmail()).isEmpty()
            || donateService.selectDonate(participantRequest.getProjectId()).isEmpty()) {
                return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
            }
            User user = userService.getUserByEmail(participantRequest.getUserEmail()).get();
            Donate donate = donateService.selectDonate(participantRequest.getProjectId()).get();

//            if(donateParticipantService.getDonateParticipant(user, donate).isEmpty()) {
                DonateParticipant donateParticipant = DonateParticipant.builder()
                        .user(user)
                        .donate(donate)
                        .amount(participantRequest.getAmount())
                        .message(participantRequest.getMessage())
                        .joinDate(new Date(System.currentTimeMillis()))
                        .build();
                donateParticipantService.registerDonateParticipant(donateParticipant);

                return ResponseEntity.status(200).body("Success");
//            }

//            return ResponseEntity.status(400).body("Bad Request");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
    }

    @DeleteMapping("/participant")
    @ApiOperation(value = "기부 참가자 삭제", notes = "<strong>참가자</strong>를 삭제")
    @ApiResponses({
            @ApiResponse(code = 204, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> deleteDonateParticipant(@RequestBody Participant participantRequest) {
        try {
            if(userService.getUserByEmail(participantRequest.getUserEmail()).isEmpty()
                    || donateService.selectDonate(participantRequest.getProjectId()).isEmpty()) {
                return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
            }
            User user = userService.getUserByEmail(participantRequest.getUserEmail()).get();
            Donate donate = donateService.selectDonate(participantRequest.getProjectId()).get();
            if(donateParticipantService.getDonateParticipant(user, donate).isPresent()) {
                DonateParticipant donateParticipant = donateParticipantService.getDonateParticipant(user, donate).get();
                donateParticipantService.deleteDonateParticipant(donateParticipant);
                return ResponseEntity.status(204).body("Success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
    }
    
}