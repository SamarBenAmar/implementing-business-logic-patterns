package org.example.domain.model;

import org.example.domain.model.Transaction;
import org.example.domain.services.AccountService;
import org.example.domain.services.TransactionService;

import java.sql.Connection;

public class BankTransactionExecutor {

    private final TransactionService transactionService;

    private final AccountService accountService;

    public BankTransactionExecutor(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    public void executeTransaction(Transaction transaction) throws Exception {
        Account account = accountService.getAccountById(transaction.getAccountId());
        if (account != null) {
            accountService.executeTransaction(account.getId(), transaction);
            transactionService.saveTransaction(transaction);
        } else {
            throw new Exception("Account not found");
        }
    }
}
