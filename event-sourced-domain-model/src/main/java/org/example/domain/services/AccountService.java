package org.example.domain.services;

import org.example.domain.events.TransactionExecuted;
import org.example.domain.model.Account;
import org.example.domain.model.Transaction;
import org.example.repository.AccountRepository;
import org.example.repository.TransactionEventStore;

import java.sql.SQLException;

public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionEventStore transactionEventStore;

    public AccountService(AccountRepository accountRepository, TransactionEventStore transactionEventStore) {
        this.accountRepository = accountRepository;
        this.transactionEventStore = transactionEventStore;
    }

    public TransactionExecuted executeTransaction(int accountId, Transaction transaction) throws Exception {
        Account account = getAccountById(accountId);
        TransactionExecuted transactionExecuted = account.executeTransaction(transaction);
        accountRepository.updateAccount(account);
        transactionEventStore.saveTransactionEvent(transactionExecuted);
        return transactionExecuted;
    }

    public void transferMoney(int fromAccountId, int toAccountId, Transaction transaction) throws Exception {
        Account fromAccount = getAccountById(fromAccountId);
        Account toAccount = getAccountById(toAccountId);
        fromAccount.withdraw(transaction.getAmount());
        toAccount.deposit(transaction.getAmount());
        accountRepository.updateAccount(fromAccount);
        accountRepository.updateAccount(toAccount);
    }

    public Account getAccountById(int accountId) throws SQLException {
        return accountRepository.getAccountById(accountId);
    }
}
