package org.example.repository;

import org.example.domain.events.TransactionExecuted;

import java.util.List;

public class TransactionEventStore {
    public void saveTransactionEvent(TransactionExecuted transactionExecuted) {
        // Save transaction event to the database
    }

    public TransactionExecuted getTransactionEventById(int transactionId) {
        // Get transaction event from the database
        return null;
    }

    public List<TransactionExecuted> getTransactionEventByAccountId(int accountId) {
        // Get transaction event from the database
        return null;
    }
}
