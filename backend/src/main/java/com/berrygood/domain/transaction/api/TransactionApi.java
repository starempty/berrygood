package com.berrygood.domain.transaction.api;

import java.util.List;

import com.berrygood.domain.transaction.api.request.TxInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.berrygood.domain.transaction.application.TransactionService;
import com.berrygood.domain.transaction.entity.Transaction;

import io.swagger.annotations.*;

@Api(value="transaction api", tags = {"Transaction"})
@RestController
@RequestMapping("api/transaction")
public class TransactionApi {
	@Autowired
	TransactionService txService;
	
	@PostMapping
	@ApiOperation(value="transaction 등록")
	@ApiResponses({
		@ApiResponse(code=200, message="성공"),
		@ApiResponse(code=500, message="서버 오류"),
	})
	public ResponseEntity<String> register(@RequestBody @ApiParam(value="등록할 거래 정보", required = true) TxInfo tx){
		Transaction transaction = Transaction.builder()
									.block_hash(tx.getBlock_hash())
									.block_number(tx.getBlock_number())
									.fromhash(tx.getFromhash())
									.gas_price(tx.getGas_price())
									.hash(tx.getHash())
									.r(tx.getR())
									.tohash(tx.getTohash())
									.transaction_index(tx.getTransaction_index())
									.build();
		txService.registerTransaction(transaction);
		return ResponseEntity.status(200).body("Success");
	}
	
	@GetMapping("/to/{to}")
	@ApiOperation(value="transaction to 조회")
	@ApiResponses({
		@ApiResponse(code=200, message="성공"),
		@ApiResponse(code=500, message="서버 오류"),
	})
	public ResponseEntity<List<Transaction>> getTransaction(@PathVariable @ApiParam(value="조회할 to account") String to){
		List<Transaction> transactions;
		if(txService.getTransactions(to).isPresent()) {
			transactions = txService.getTransactions(to).get();
			return ResponseEntity.status(200).body(transactions);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@GetMapping("/from/{from}")
	@ApiOperation(value="transaction to 조회")
	@ApiResponses({
		@ApiResponse(code=200, message="성공"),
		@ApiResponse(code=500, message="서버 오류"),
	})
	public ResponseEntity<List<Transaction>> getFromTransaction(@PathVariable @ApiParam(value="조회할 from account") String from){
		List<Transaction> transactions;
		if(txService.getFromTransactions(from).isPresent()) {
			transactions = txService.getFromTransactions(from).get();
			return ResponseEntity.status(200).body(transactions);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
