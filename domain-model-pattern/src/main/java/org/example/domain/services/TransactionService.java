package org.example.domain.services;

import org.example.domain.model.Transaction;
import org.example.repository.TransactionRepository;

import java.sql.Connection;

public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void saveTransaction(Transaction transaction) throws Exception {
        transactionRepository.saveTransaction(transaction);
    }
}
