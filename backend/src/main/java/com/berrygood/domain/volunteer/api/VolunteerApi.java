package com.berrygood.domain.volunteer.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.berrygood.domain.volunteer.api.response.VolunteerResponse;
import com.berrygood.domain.volunteer.application.VolunteerImgService;
import com.berrygood.domain.volunteer.application.VolunteerParticipantService;
import com.berrygood.domain.volunteer.entity.VolunteerImg;
import com.berrygood.domain.volunteer.entity.VolunteerParticipant;
import com.berrygood.global.common.ParticipantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.berrygood.domain.funding.entity.FundingParticipant;
import com.berrygood.domain.user.application.UserService;
import com.berrygood.domain.user.entity.User;
import com.berrygood.domain.volunteer.api.request.VolunteerInfo;
import com.berrygood.domain.volunteer.application.VolunteerService;
import com.berrygood.domain.volunteer.entity.Volunteer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "봉사활동 API", tags = {"Volunteer"})
@RestController
@RequestMapping("api/volunteer")
public class VolunteerApi {

	@Autowired
	private VolunteerService volunteerService;

	@Autowired
	private VolunteerImgService volunteerImgService;

	@Autowired
	private VolunteerParticipantService volunteerParticipantService;
	
	@Autowired
	private UserService userService;

	final static String basePath = "images" + File.separator + "volunteer" + File.separator;
	
	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	@ApiOperation(value = "봉사활동 등록", notes = "<strong>봉사활동 등록</strong>")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<String> register(@RequestParam(required = false) String email,
										   @RequestParam(required = false) String name,
										   @RequestParam(required = false) String content,
										   @RequestParam(required = false) int num,
										   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date date,
										   @RequestParam(required = false) String type,
										   @RequestParam(required = false) String time,
										   @RequestPart(value="files", required=false) List<MultipartFile> files
	)throws IOException {
		if(userService.getUserByEmail(email).isPresent()) {
			User user = userService.getUserByEmail(email).get();
			Volunteer volunteer = Volunteer.builder()
					.user(user)
					.name(name)
					.content(content)
					.num(num)
					.date(date)
					.type(type)
					.time(time)
					.build();
			volunteerService.registerVolunteer(volunteer);

			for(MultipartFile file : files) {
				String volunteerPath = basePath + volunteer.getNo() + File.separator;

				File baseFile = new File(volunteerPath);
				if(!baseFile.exists()) {
					baseFile.mkdirs();
				}
				String filePath = volunteerPath + file.getOriginalFilename();
				Path path = Paths.get(filePath).toAbsolutePath();

				file.transferTo(path.toFile());
				VolunteerImg volunteerImg = VolunteerImg
						.builder()
						.volunteer(volunteer)
						.imgPath(filePath)
						.build();
				volunteerImgService.registerVolunteerImg(volunteerImg);
			}
		}
		return ResponseEntity.status(200).body("Success");
	}

	@PostMapping(value = "/{volunteerNo}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	@ApiOperation(value = "봉사활동 정보 수정", notes = "<strong>volunteerNo</strong>로 봉사활동 정보 수정")
	public ResponseEntity<String> update(@PathVariable("volunteerNo") Long no,
										 @RequestParam(required = false) String email,
										 @RequestParam(required = false) String name,
										 @RequestParam(required = false) String content,
										 @RequestParam(required = false) int num,
										 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date date,
										 @RequestParam(required = false) String type,
										 @RequestParam(required = false) String time,
										 @RequestPart(value="files", required=false) List<MultipartFile> files
	) throws IOException {
		if(userService.getUserByEmail(email).isPresent()) {
			User user = userService.getUserByEmail(email).get();
			Volunteer volunteerInfo = Volunteer
					.builder()
					.user(user)
					.name(name)
					.content(content)
					.num(num)
					.date(date)
					.type(type)
					.time(time)
					.build();

			if(volunteerService.updateVolunteer(no, volunteerInfo)==1) {
				if(volunteerService.selectVolunteer(no).isEmpty()) {
					return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
				}
				Volunteer volunteer = volunteerService.selectVolunteer(no).get();
				volunteerImgService.deleteVolunteerImg(volunteer);

				for(MultipartFile file : files) {
					String volunteerPath = basePath + no + File.separator;

					File baseFile = new File(volunteerPath);
					if(!baseFile.exists()) {
						baseFile.mkdirs();
					}
					String filePath = volunteerPath + file.getOriginalFilename();
					Path path = Paths.get(filePath).toAbsolutePath();

					file.transferTo(path.toFile());
					VolunteerImg volunteerImg = VolunteerImg
							.builder()
							.volunteer(volunteer)
							.imgPath(filePath)
							.build();
					volunteerImgService.registerVolunteerImg(volunteerImg);
				}

				return ResponseEntity.status(200).body("Success");
			}
		}

		return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
	}

	@GetMapping()
	@ApiOperation(value = "봉사 전체 조회", notes = "봉사 전체 조회")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<List<VolunteerResponse>> getAllVolunteer() {
		List<VolunteerResponse> volunteerResponses = new ArrayList<>();
		if(volunteerService.selectAllVolunteer().isPresent()) {
			List<Volunteer> volunteers = volunteerService.selectAllVolunteer().get();
			for(Volunteer volunteer : volunteers) {
				if(volunteerImgService.selectVolunteerImg(volunteer).isPresent()
						&& volunteerParticipantService.getVolunteerParticipant(volunteer).isPresent()) {
					List<VolunteerImg> volunteerImgs = volunteerImgService.selectVolunteerImg(volunteer).get();

					List<String> paths = new ArrayList<>();
					for(VolunteerImg volunteerImg : volunteerImgs) {
						paths.add(volunteerImg.getImgPath());
					}

					List<VolunteerParticipant> volunteerParticipants =
							volunteerParticipantService.getVolunteerParticipant(volunteer).get();

					volunteerResponses.add(VolunteerResponse
							.builder()
							.volunteer(volunteer)
							.imgPath(paths)
							.totalCnt(volunteerParticipants.size())
							.build());
				}
			}
			return ResponseEntity.status(200).body(volunteerResponses);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	@GetMapping("/volunteer/{email}")
    @ApiOperation(value = "유저 봉사 전체 조회", notes = "유저 봉사 조회")
    @ApiResponses({
    	@ApiResponse(code = 200, message = "성공"),
    	@ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<VolunteerParticipant>> getAllDonateByUser(@PathVariable String email) {
    	if(userService.getUserByEmail(email).isPresent()) {
    		User user = userService.getUserByEmail(email).get();
    		if(volunteerParticipantService.getVolunteerParticipantByUser(user).isPresent()) {
    			List<VolunteerParticipant> participants = volunteerParticipantService.getVolunteerParticipantByUser(user).get();
    			return ResponseEntity.status(200).body(participants);
    		}
    	}
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
	
	@GetMapping("/{no}")
	@ApiOperation(value = "봉사활동 상세 조회", notes = "봉사활동 상세 조회")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<VolunteerResponse> getVolunteer(@PathVariable Long no){
		if(volunteerService.selectVolunteer(no).isPresent()) {
			Volunteer volunteer = volunteerService.selectVolunteer(no).get();

			if(volunteerImgService.selectVolunteerImg(volunteer).isPresent()
			&& volunteerParticipantService.getVolunteerParticipant(volunteer).isPresent()) {
				List<VolunteerImg> volunteerImgs = volunteerImgService.selectVolunteerImg(volunteer).get();

				List<String> paths = new ArrayList<>();
				for(VolunteerImg volunteerImg : volunteerImgs) {
					paths.add(volunteerImg.getImgPath());
				}

				List<VolunteerParticipant> volunteerParticipants =
						volunteerParticipantService.getVolunteerParticipant(volunteer).get();

				VolunteerResponse volunteerResponse = VolunteerResponse
						.builder()
						.volunteer(volunteer)
						.imgPath(paths)
						.totalCnt(volunteerParticipants.size())
						.build();

				return ResponseEntity.status(200).body(volunteerResponse);
			}
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	
	@DeleteMapping("/{no}")
	@ApiOperation(value = "봉사활동 정보 삭제", notes = "<strong> 봉사활동 게시글 번호</strong> 로 해당 봉사활동 정보 삭제")
	public ResponseEntity<?> delete(@PathVariable Long no) {
		try {
			if(volunteerService.selectVolunteer(no).isPresent()) {
				Volunteer volunteer = volunteerService.selectVolunteer(no).get();

				volunteerParticipantService.deleteAllVolunteerParticipant(volunteer);
				volunteerImgService.deleteVolunteerImg(volunteer);

				int response = volunteerService.deleteVolunteer(no);
				return ResponseEntity.status(200).body("Success");
			}
			return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
		}
	}

	@PostMapping("/participant")
	@ApiOperation(value = "봉사 참가자 등록", notes = "<strong>봉사 참가자</strong>등록")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 400, message = "중복"),
			@ApiResponse(code = 500, message = "실패")
	})
	public ResponseEntity<?> volunteerParticipant(@RequestBody ParticipantRequest participantRequest) {
		try {
			if(userService.getUserByEmail(participantRequest.getUserEmail()).isEmpty()
			|| volunteerService.selectVolunteer(participantRequest.getProjectId()).isEmpty()) {
				return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
			}
			User user = userService.getUserByEmail(participantRequest.getUserEmail()).get();
			Volunteer volunteer = volunteerService.selectVolunteer(participantRequest.getProjectId()).get();

			if(volunteerParticipantService.getVolunteerParticipant(user, volunteer).isEmpty()) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				VolunteerParticipant volunteerParticipant = VolunteerParticipant.builder()
						.user(user)
						.volunteer(volunteer)
						.amount(participantRequest.getAmount())
						.joinDate(new Date(System.currentTimeMillis()))
						.build();
				volunteerParticipantService.registerVolunteerParticipant(volunteerParticipant);
				return ResponseEntity.status(200).body("Success");
			}
			return ResponseEntity.status(400).body("Bad Request");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
		}
	}

	@DeleteMapping("/participant")
	@ApiOperation(value = "봉사 참가자 삭제", notes = "<strong>참가자</strong>를 삭제")
	@ApiResponses({
			@ApiResponse(code = 204, message = "성공"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<?> deleteVolunteerParticipant(@RequestBody ParticipantRequest participantRequest) {
		try {
			if(userService.getUserByEmail(participantRequest.getUserEmail()).isEmpty()
					|| volunteerService.selectVolunteer(participantRequest.getProjectId()).isEmpty()) {
				return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
			}
			User user = userService.getUserByEmail(participantRequest.getUserEmail()).get();
			Volunteer volunteer = volunteerService.selectVolunteer(participantRequest.getProjectId()).get();

			if(volunteerParticipantService.getVolunteerParticipant(user, volunteer).isPresent()) {
				VolunteerParticipant volunteerParticipant = volunteerParticipantService.getVolunteerParticipant(user, volunteer).get();
				volunteerParticipantService.deleteVolunteerParticipant(volunteerParticipant);
				return ResponseEntity.status(204).body("Success");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
	}
}
