package com.berrygood.domain.wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.berrygood.domain.wallet.entity.CompWallet;

public interface CompWalletRepository  extends JpaRepository<CompWallet, Long> {
	Optional<CompWallet> findByCompNo(Long ownerid);
}
