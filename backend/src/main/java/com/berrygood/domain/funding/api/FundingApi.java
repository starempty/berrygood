package com.berrygood.domain.funding.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.berrygood.domain.funding.api.response.FundingResponse;
import com.berrygood.domain.funding.application.FundingImgService;
import com.berrygood.domain.funding.application.FundingParticipantService;
import com.berrygood.domain.funding.entity.FundingImg;
import com.berrygood.domain.funding.entity.FundingParticipant;
import com.berrygood.domain.user.application.UserService;
import com.berrygood.domain.user.entity.User;
import com.berrygood.global.common.ParticipantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.berrygood.domain.comp.application.CompService;
import com.berrygood.domain.comp.entity.Comp;
import com.berrygood.domain.donate.entity.Donate;
import com.berrygood.domain.donate.entity.DonateParticipant;
import com.berrygood.domain.funding.api.request.FundingInfo;
import com.berrygood.domain.funding.application.FundingService;
import com.berrygood.domain.funding.entity.Funding;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "펀딩 API", tags = {"Funding"})
@RestController
@RequestMapping("api/funding")
public class FundingApi {
	@Autowired
	private FundingService fundingService;

	@Autowired
	private FundingImgService fundingImgService;

	@Autowired
	private FundingParticipantService fundingParticipantService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CompService compService;

	final static String basePath = "images" + File.separator + "funding" + File.separator;
	
	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	@ApiOperation(value = "펀딩 등록", notes = "<strong>펀딩 등록</strong>")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<String> register(@RequestParam(required = false) String email,
										   @RequestParam(required = false) String name,
										   @RequestParam(required = false) String content,
										   @RequestParam(required = false) int amount,
										   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date due,
										   @RequestPart(value="files", required=false) List<MultipartFile> files
										   )throws IOException {
		if(compService.getCompByEmail(email).isPresent()) {
			Comp comp = compService.getCompByEmail(email).get();
			Funding funding = Funding.builder()
					.comp(comp)
					.name(name)
					.content(content)
					.amount(amount)
					.due(due)
					.time(new Date(System.currentTimeMillis()))
					.calc(0)
					.build();
			fundingService.registerFunding(funding);

			for(MultipartFile file : files) {
				String fundingPath = basePath + funding.getNo() + File.separator;

				File baseFile = new File(fundingPath);
				if(!baseFile.exists()) {
					baseFile.mkdirs();
				}
				String filePath = fundingPath + file.getOriginalFilename();
				Path path = Paths.get(filePath).toAbsolutePath();

				file.transferTo(path.toFile());
				FundingImg fundingImg = FundingImg.builder()
						.funding(funding)
						.imgPath(filePath)
						.build();
				fundingImgService.registerFundingImg(fundingImg);
			}
		}
		return ResponseEntity.status(200).body("Success");
	}

	@PostMapping("/{fundingNo}")
	@ApiOperation(value = "펀딩 정보 수정", notes = "<strong>funding_no</strong>로 펀딩 정보 수정")
	public ResponseEntity<String> update(@PathVariable("fundingNo") Long no,
										 @RequestParam(required = false) String email,
										 @RequestParam(required = false) String name,
										 @RequestParam(required = false) String content,
										 @RequestParam(required = false) int amount,
										 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date due,
										 @RequestPart(value="files", required=false) List<MultipartFile> files
	)throws IOException {
		if(compService.getCompByEmail(email).isPresent()) {
			Comp comp = compService.getCompByEmail(email).get();
			Funding fundingInfo = Funding.builder()
					.comp(comp)
					.name(name)
					.content(content)
					.amount(amount)
					.due(due)
					.build();
			
			if(fundingService.updateFunding(no, fundingInfo) == 1) {
				if(fundingService.selectFunding(no).isEmpty()) {
					return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
				}
				Funding funding = fundingService.selectFunding(no).get();
				fundingImgService.deleteFundingImg(funding);

				for(MultipartFile file : files) {
					String fundingPath = basePath + funding.getNo() + File.separator;

					File baseFile = new File(fundingPath);
					if(!baseFile.exists()) {
						baseFile.mkdirs();
					}
					String filePath = fundingPath + file.getOriginalFilename();
					Path path = Paths.get(filePath).toAbsolutePath();

					file.transferTo(path.toFile());
					FundingImg fundingImg = FundingImg.builder()
							.funding(funding)
							.imgPath(filePath)
							.build();
					fundingImgService.registerFundingImg(fundingImg);
				}

				return ResponseEntity.status(200).body("Success");
			}
		}
		return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
	}

	@GetMapping()
	@ApiOperation(value = "펀딩 전체 조회", notes = "전체 조회")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<List<FundingResponse>> getFunding() {
		List<FundingResponse> fundingResponses = new ArrayList<>();
		if(fundingService.selectAllFunding().isPresent()) {
			List<Funding> fundings = fundingService.selectAllFunding().get();
			for(Funding funding : fundings) {
				if(fundingImgService.selectFundingImg(funding).isPresent()
						&& fundingParticipantService.getFundingParticipant(funding).isPresent()) {
					List<FundingImg> fundingImgs = fundingImgService.selectFundingImg(funding).get();

					List<String> paths = new ArrayList<>();
					for(FundingImg fundingImg : fundingImgs) {
						paths.add(fundingImg.getImgPath());
					}

					List<FundingParticipant> fundingParticipants =
							fundingParticipantService.getFundingParticipant(funding).get();

					Long sum = 0L;
					for(FundingParticipant fundingParticipant : fundingParticipants) {
						sum += fundingParticipant.getAmount();
					}

					fundingResponses.add(FundingResponse
							.builder()
							.funding(funding)
							.imgPath(paths)
							.totalCnt(fundingParticipants.size())
							.totalAmount(sum)
							.build());
				}
			}
			return ResponseEntity.status(200).body(fundingResponses);
		}
		else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@GetMapping("/funding/{email}")
    @ApiOperation(value = "유저 펀딩 전체 조회", notes = "유저 펀딩 조회")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "성공"),
    	@ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<FundingParticipant>> getAllFundingByUser(@PathVariable String email) {
    	if(userService.getUserByEmail(email).isPresent()) {
    		User user = userService.getUserByEmail(email).get();
    		if(fundingParticipantService.getFundingParticipantByUser(user).isPresent()) {
    			List<FundingParticipant> participants = fundingParticipantService.getFundingParticipantByUser(user).get();
    			return ResponseEntity.status(200).body(participants);
    		}
    	}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
	
	@GetMapping("/funding/comp/{email}")
    @ApiOperation(value = "기업이 등록한 펀딩 전체 조회", notes = "기업 펀딩 조회")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "성공"),
    	@ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<Funding>> getAllFundingByComp(@PathVariable String email) {
    	if(compService.getCompByEmail(email).isPresent()) {
    		Comp comp = compService.getCompByEmail(email).get();
    		if(fundingService.searchFunding(comp).isPresent()) {
    			List<Funding> fundings = fundingService.searchFunding(comp).get();
    			return ResponseEntity.status(200).body(fundings);
    		}
    	}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
	
	@GetMapping("/comp/{no}")
    @ApiOperation(value = "기업이 등록한 펀딩에 참여한 내역 전체 조회", notes = "기업의 펀딩에 참여한 내역 조회")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "성공"),
    	@ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<FundingParticipant>> getAllFundingParticipantByDonate(@PathVariable Long no) {
    	if(fundingService.selectFunding(no).isPresent()) {
    		Funding funding = fundingService.selectFunding(no).get();
    		if(fundingParticipantService.getFundingParticipant(funding).isPresent()) {
    			List<FundingParticipant> participants = fundingParticipantService.getFundingParticipant(funding).get();
    			return ResponseEntity.status(200).body(participants);
    		}
    	}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
	
	@GetMapping("/{no}")
	@ApiOperation(value="펀딩 상세 조회", notes = "펀딩 상세 조회")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<FundingResponse> getFunding(@PathVariable Long no) {
		if(fundingService.selectFunding(no).isPresent()) {
			Funding funding = fundingService.selectFunding(no).get();

			if(fundingImgService.selectFundingImg(funding).isPresent()
					&& fundingParticipantService.getFundingParticipant(funding).isPresent()) {
				List<FundingImg> fundingImgs = fundingImgService.selectFundingImg(funding).get();

				List<String> paths = new ArrayList<>();
				for(FundingImg fundingImg : fundingImgs) {
					paths.add(fundingImg.getImgPath());
				}

				List<FundingParticipant> fundingParticipants =
						fundingParticipantService.getFundingParticipant(funding).get();

				Long sum = 0L;
				for(FundingParticipant fundingParticipant : fundingParticipants) {
					sum += fundingParticipant.getAmount();
				}

				FundingResponse fundingResponse = FundingResponse
						.builder()
						.funding(funding)
						.imgPath(paths)
						.totalCnt(fundingParticipants.size())
						.totalAmount(sum)
						.build();

				return ResponseEntity.status(200).body(fundingResponse);
			}
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	@DeleteMapping("/{no}")
	@ApiOperation(value = "펀딩 정보 삭제", notes = "<strong>펀딩게시글번호</strong>를 통해 펀딩 정보 삭제")
	public ResponseEntity<?> delete(@PathVariable Long no) {
		try {
			if(fundingService.selectFunding(no).isPresent()) {
				Funding funding = fundingService.selectFunding(no).get();

				fundingParticipantService.deleteAllFundingParticipant(funding);
				fundingImgService.deleteFundingImg(funding);
				int response = fundingService.deleteFunding(no);
				return ResponseEntity.status(200).body("Success");
			}
			return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
		}
	}

	@PostMapping("/participant")
	@ApiOperation(value = "펀딩 참가자 등록", notes = "<strong>펀딩 참가자</strong>등록")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 400, message = "중복"),
			@ApiResponse(code = 500, message = "실패")
	})
	public ResponseEntity<?> fundingParticipant(@RequestBody ParticipantRequest participantRequest) {
		try {
			if(userService.getUserByEmail(participantRequest.getUserEmail()).isEmpty()
					|| fundingService.selectFunding(participantRequest.getProjectId()).isEmpty()) {
				return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
			}
			User user = userService.getUserByEmail(participantRequest.getUserEmail()).get();
			Funding funding = fundingService.selectFunding(participantRequest.getProjectId()).get();

			if(fundingParticipantService.getFundingParticipant(user, funding).isEmpty()) {
				FundingParticipant fundingParticipant = FundingParticipant.builder()
						.user(user)
						.funding(funding)
						.amount(participantRequest.getAmount())
						.joinDate(new Date(System.currentTimeMillis()))
						.build();
				fundingParticipantService.registerFundingParticipant(fundingParticipant);

				return ResponseEntity.status(200).body("Success");
			}
			return ResponseEntity.status(400).body("Bad Request");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
	}

	@DeleteMapping("/participant")
	@ApiOperation(value = "펀딩 참가자 삭제", notes = "<strong>참가자</strong>를 삭제")
	@ApiResponses({
			@ApiResponse(code = 204, message = "성공"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<?> deleteFundingParticipant(@RequestBody ParticipantRequest participantRequest) {
		try {
			if(userService.getUserByEmail(participantRequest.getUserEmail()).isEmpty()
					|| fundingService.selectFunding(participantRequest.getProjectId()).isEmpty()) {
				return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
			}

			User user = userService.getUserByEmail(participantRequest.getUserEmail()).get();
			Funding funding = fundingService.selectFunding(participantRequest.getProjectId()).get();
			if(fundingParticipantService.getFundingParticipant(user, funding).isPresent()) {
				FundingParticipant fundingParticipant = fundingParticipantService.getFundingParticipant(user, funding).get();
				fundingParticipantService.deleteFundingParticipant(fundingParticipant);
				return ResponseEntity.status(204).body("Success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
	}
}
