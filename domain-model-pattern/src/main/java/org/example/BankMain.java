package org.example;


import org.example.config.DatabaseConnection;
import org.example.domain.model.Account;
import org.example.domain.model.BankTransactionExecutor;
import org.example.domain.model.Money;
import org.example.domain.model.Transaction;
import org.example.domain.services.AccountService;
import org.example.domain.services.TransactionService;
import org.example.repository.AccountRepository;
import org.example.repository.TransactionRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Currency;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class BankMain {
    public static void main(String[] args) throws Exception {

        TransactionRepository transactionRepository = new TransactionRepository();
        AccountRepository accountRepository = new AccountRepository();
        TransactionService transactionService = new TransactionService(transactionRepository);
        AccountService accountService = new AccountService(accountRepository);
        Account account = new Account(1, new Money(BigDecimal.valueOf(100), Currency.getInstance("Euro")));
        Transaction transaction = new Transaction(1, account.getId(), new Money(BigDecimal.valueOf(50), Currency.getInstance("Euro")), "W");
        BankTransactionExecutor bankTransaction = new BankTransactionExecutor(transactionService, accountService);
        try (Connection conn = DatabaseConnection.connect()) {
            bankTransaction.executeTransaction(transaction);
        }
        System.out.println("Transaction successful: " + transaction.getCode() + " " + transaction.getAmount());
        System.out.println("New balance: " + account.getBalance());
    }
}