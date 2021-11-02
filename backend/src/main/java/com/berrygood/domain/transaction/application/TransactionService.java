package com.berrygood.domain.transaction.application;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.berrygood.domain.transaction.entity.Transaction;

public interface TransactionService {
	@Transactional
	void registerTransaction(Transaction transaction);
	
	@Transactional
	public Optional<List<Transaction>> getTransactions(String hash);
	
	@Transactional
	public Optional<List<Transaction>> getFromTransactions(String from);
}
