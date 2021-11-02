package com.berrygood.domain.wallet.application.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berrygood.domain.wallet.entity.Wallet;
import com.berrygood.domain.wallet.application.WalletService;
import com.berrygood.domain.wallet.repository.WalletRepository;


@Service
public class WalletServiceImpl implements WalletService{

	@Autowired
	WalletRepository walletRepo;
	
	@Override
	public void registerWallet(Wallet wallet) {
		walletRepo.save(wallet);
	}

	@Override
	public int updateWallet(Long ownerid, Wallet wallet) {
		Optional<Wallet> optionalWallet = walletRepo.findByUserNo(ownerid);
		if(optionalWallet.isPresent()) {
			Wallet oldWallet = optionalWallet.get();
			oldWallet.updateWallet(wallet);
			
			walletRepo.save(oldWallet);
			return 1;
		}
		return 0;
	}
	
	@Override
	public Optional<Wallet> getWallet(Long ownerid){
		return walletRepo.findByUserNo(ownerid);
	}
}
