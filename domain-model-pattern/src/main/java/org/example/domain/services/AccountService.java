package org.example.domain.services;

import org.example.domain.model.Account;
import org.example.domain.model.Money;
import org.example.domain.model.Transaction;
import org.example.repository.AccountRepository;

import java.sql.SQLException;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void executeTransaction(int accountId, Transaction transaction) throws Exception {
        Account account = getAccountById(accountId);
        account.executeTransaction(transaction);
        accountRepository.updateAccount(account);
    }

    public void transferMoney(int fromAccountId, int toAccountId, Money amount) throws Exception {
        Account fromAccount = getAccountById(fromAccountId);
        Account toAccount = getAccountById(toAccountId);
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
        accountRepository.updateAccount(fromAccount);
        accountRepository.updateAccount(toAccount);
    }

    public Account getAccountById(int accountId) throws SQLException {
        return accountRepository.getAccountById(accountId);
    }
}
