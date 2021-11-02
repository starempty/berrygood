package com.berrygood.domain.wallet.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor
public class WalletInfo {
	    private String email;
	    private String address;
	    private int berry;
		private int balance;
		private int receivecnt;
}
