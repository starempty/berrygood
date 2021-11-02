package com.berrygood.domain.wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.berrygood.domain.wallet.entity.Wallet;

public interface WalletRepository  extends JpaRepository<Wallet, Long> {
	Optional<Wallet> findByUserNo(Long ownerid);
}
