package com.berrygood.domain.transaction.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel("TransactionInfo")
public class TxInfo {
	String block_hash;
	String block_number;
	String fromhash;
	String gas_price;
	String hash;
	String r;
	String tohash;
	String transaction_index;
}
