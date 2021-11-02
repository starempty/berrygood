package com.berrygood.domain.wallet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.berrygood.domain.wallet.entity.Wallet;
import com.berrygood.domain.blood.api.request.BloodInfo;
import com.berrygood.domain.user.application.UserService;
import com.berrygood.domain.user.entity.User;
import com.berrygood.domain.wallet.api.request.WalletInfo;
import com.berrygood.domain.wallet.application.WalletService;

import io.swagger.annotations.*;

@Api(value="Wallet api", tags = {"Wallet"})
@RestController
@RequestMapping("api/wallet")
public class WalletApi {
	@Autowired
	WalletService walletService;
	@Autowired
	UserService userService;
	
	@PostMapping
	@ApiOperation(value="개인 지갑 등록")
	@ApiResponses({
		@ApiResponse(code=200, message="성공"),
		@ApiResponse(code=500, message="서버 오류"),
	})
	public ResponseEntity<String> register(@RequestBody @ApiParam(value="등록할 지갑 정보", required = true) WalletInfo walletInfo){
		if(userService.getUserByEmail(walletInfo.getEmail()).isPresent()) {
			User user = userService.getUserByEmail(walletInfo.getEmail()).get();
			Wallet wallet = Wallet.builder()
					.user(user)
					.address(walletInfo.getAddress())
					.berry(walletInfo.getBerry())
					.balance(walletInfo.getBalance())
					.receivecnt(walletInfo.getReceivecnt())
					.build();
			walletService.registerWallet(wallet);
		}
		return ResponseEntity.status(200).body("Success");
	}
	
	@PostMapping("/update")
	@ApiOperation(value="개인 지갑 잔액 수정", notes="지갑정보 내 email을 통해 개인 지갑 잔액 수정 가능")
	public ResponseEntity<String> updateBalance(@RequestBody @ApiParam(value="수정할 지갑 정보", required = true) WalletInfo walletInfo){
		if(userService.getUserByEmail(walletInfo.getEmail()).isPresent()) {
			User user = userService.getUserByEmail(walletInfo.getEmail()).get();
			Wallet wallet = Wallet.builder()
					.user(user)
					.address(walletInfo.getAddress())
					.berry(walletInfo.getBerry())
					.balance(walletInfo.getBalance())
					.receivecnt(walletInfo.getReceivecnt())
					.build();
			if(walletService.updateWallet(user.getNo(), wallet) == 1) {
				return ResponseEntity.status(200).body("Success");
			}
		}
		return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
	}
	
	@GetMapping("/{email}")
	@ApiOperation(value="개인 지갑 조회", notes="고객 email을 통해 지갑 조회")
	public ResponseEntity<Wallet> getWallet(@PathVariable String email){
		if(userService.getUserByEmail(email).isPresent()) {
			User user = userService.getUserByEmail(email).get();
			if(walletService.getWallet(user.getNo()).isPresent()) {
				Wallet wallet = walletService.getWallet(user.getNo()).get();
				return ResponseEntity.status(200).body(wallet);
			}
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
