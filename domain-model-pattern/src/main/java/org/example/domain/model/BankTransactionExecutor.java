package org.example.domain.model;

import org.example.domain.events.TransactionExecuted;
import org.example.domain.services.AccountService;
import org.example.domain.services.TransactionService;

public class BankTransactionExecutor {

    private final TransactionService transactionService;

    private final AccountService accountService;

    public BankTransactionExecutor(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    public TransactionExecuted executeTransaction(Transaction transaction) throws Exception {
        Account account = accountService.getAccountById(transaction.getAccountId());
        if (account != null) {
            TransactionExecuted transactionExecuted = accountService.executeTransaction(account.getId(), transaction);
            transactionService.saveTransaction(transaction);
            return transactionExecuted;
        } else {
            throw new Exception("Account not found");
        }
    }
}
