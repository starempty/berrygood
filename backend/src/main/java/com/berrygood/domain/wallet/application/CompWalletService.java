package com.berrygood.domain.wallet.application;

import java.util.Optional;

import javax.transaction.Transactional;

import com.berrygood.domain.wallet.entity.CompWallet;


public interface CompWalletService {
	@Transactional
	void registerWallet(CompWallet wallet);
	
	@Transactional
	int updateWallet(Long ownerid, CompWallet wallet);

	@Transactional
	public Optional<CompWallet> getWallet(Long ownerid);
}
