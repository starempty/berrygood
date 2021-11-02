package com.berrygood.domain.wallet.application.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berrygood.domain.wallet.entity.CompWallet;
import com.berrygood.domain.wallet.application.CompWalletService;
import com.berrygood.domain.wallet.repository.CompWalletRepository;


@Service
public class CompWalletServiceImpl implements CompWalletService{

	@Autowired
	CompWalletRepository compWalletRepo;
	
	@Override
	public void registerWallet(CompWallet wallet) {
		compWalletRepo.save(wallet);
	}

	@Override
	public int updateWallet(Long ownerid, CompWallet wallet) {
		Optional<CompWallet> optionalWallet = compWalletRepo.findByCompNo(ownerid);
		if(optionalWallet.isPresent()) {
			CompWallet oldWallet = optionalWallet.get();
			oldWallet.updateWallet(wallet);
			
			compWalletRepo.save(oldWallet);
			return 1;
		}
		return 0;
	}
	
	@Override
	public Optional<CompWallet> getWallet(Long ownerid){
		return compWalletRepo.findByCompNo(ownerid);
	}
}
