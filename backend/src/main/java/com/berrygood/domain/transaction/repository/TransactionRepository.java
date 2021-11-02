package com.berrygood.domain.transaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.berrygood.domain.transaction.entity.Transaction;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {
	Optional<List<Transaction>> findAllByTohash(String tohash);
	Optional<List<Transaction>> findAllByFromhash(String fromhash);
	
}
