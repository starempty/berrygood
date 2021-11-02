package com.berrygood.domain.wallet.application;

import java.util.Optional;

import javax.transaction.Transactional;

import com.berrygood.domain.wallet.entity.Wallet;


public interface WalletService {
	@Transactional
	void registerWallet(Wallet wallet);
	
	@Transactional
	int updateWallet(Long ownerid, Wallet wallet);

	@Transactional
	public Optional<Wallet> getWallet(Long ownerid);
}
