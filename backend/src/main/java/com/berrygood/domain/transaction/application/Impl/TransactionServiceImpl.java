package com.berrygood.domain.transaction.application.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berrygood.domain.transaction.application.TransactionService;
import com.berrygood.domain.transaction.entity.Transaction;
import com.berrygood.domain.transaction.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	TransactionRepository txRepo;
	
	@Override
	public void registerTransaction(Transaction tx) {
		txRepo.save(tx);
	}
	
	@Override
	public Optional<List<Transaction>> getTransactions(String to){
		return txRepo.findAllByTohash(to);
	}

	@Override
	public Optional<List<Transaction>> getFromTransactions(String from) {
		return txRepo.findAllByFromhash(from);
	}
}
