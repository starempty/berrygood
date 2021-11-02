package com.berrygood.domain.wallet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.berrygood.domain.wallet.entity.CompWallet;
import com.berrygood.domain.comp.application.CompService;
import com.berrygood.domain.comp.entity.Comp;
import com.berrygood.domain.wallet.api.request.CompWalletInfo;
import com.berrygood.domain.wallet.application.CompWalletService;

import io.swagger.annotations.*;

@Api(value="Company wallet api", tags = {"Wallet"})
@RestController
@RequestMapping("api/compwallet")
public class CompWalletApi {
	@Autowired
	CompWalletService walletService;
	@Autowired
	CompService compService;
	
	@PostMapping
	@ApiOperation(value="기업용 지갑 등록")
	@ApiResponses({
		@ApiResponse(code=200, message="성공"),
		@ApiResponse(code=500, message="서버 오류"),
	})
	public ResponseEntity<String> register(@RequestBody @ApiParam(value="등록할 지갑 정보", required = true) CompWalletInfo walletInfo){
		if(compService.getCompByEmail(walletInfo.getEmail()).isPresent()) {
			Comp comp = compService.getCompByEmail(walletInfo.getEmail()).get();
			CompWallet wallet = CompWallet.builder()
					.comp(comp)
					.address(walletInfo.getAddress())
					.berry(walletInfo.getBerry())
					.balance(walletInfo.getBalance())
					.build();
			walletService.registerWallet(wallet);
		}
		return ResponseEntity.status(200).body("Success");
	}
	
	@PostMapping("/update")
	@ApiOperation(value="기업 지갑 잔액 수정", notes="입력받은 지갑 정보를 이용해 잔액 수정")
	public ResponseEntity<String> updateBalance(@RequestBody @ApiParam(value="수정할 지갑 정보", required = true) CompWalletInfo walletInfo){
		if(compService.getCompByEmail(walletInfo.getEmail()).isPresent()) {
			Comp comp = compService.getCompByEmail(walletInfo.getEmail()).get();
			CompWallet wallet = CompWallet.builder()
					.comp(comp)
					.address(walletInfo.getAddress())
					.berry(walletInfo.getBerry())
					.balance(walletInfo.getBalance())
					.build();
			if(walletService.updateWallet(comp.getNo(), wallet) == 1) {
				return ResponseEntity.status(200).body("Success");
			}
		}
		return ResponseEntity.status(500).body("INTERNAL_SERVER_ERROR");
	}
	
	@GetMapping("/{email}")
	@ApiOperation(value="기업용 지갑 조회", notes="사용자 email을 통해 지갑 조회")
	public ResponseEntity<CompWallet> getWallet(@PathVariable String email){
		if(compService.getCompByEmail(email).isPresent()) {
			Comp comp = compService.getCompByEmail(email).get();
			if(walletService.getWallet(comp.getNo()).isPresent()) {
				CompWallet wallet = walletService.getWallet(comp.getNo()).get();
				return ResponseEntity.status(200).body(wallet);
			}
		}else {
			System.out.println("error");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
