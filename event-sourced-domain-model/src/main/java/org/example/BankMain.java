package org.example;


import org.example.domain.events.TransactionExecuted;
import org.example.domain.model.Account;
import org.example.domain.model.BankTransactionExecutor;
import org.example.domain.model.Money;
import org.example.domain.model.Transaction;
import org.example.domain.services.AccountService;
import org.example.domain.services.TransactionService;
import org.example.repository.AccountRepository;
import org.example.repository.TransactionEventStore;
import org.example.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.Currency;
public class BankMain {
    public static void main(String[] args) throws Exception {
        TransactionRepository transactionRepository = new TransactionRepository();
        AccountRepository accountRepository = new AccountRepository();
        TransactionEventStore transactionEventStore = new TransactionEventStore();
        TransactionService transactionService = new TransactionService(transactionRepository);
        AccountService accountService = new AccountService(accountRepository, transactionEventStore);
        Account account = new Account(1, new Money(BigDecimal.valueOf(100), Currency.getInstance("USD")));
        Transaction transaction = new Transaction(1, account.getId(), new Money(BigDecimal.valueOf(50),  Currency.getInstance("USD")), "W");
        BankTransactionExecutor bankTransaction = new BankTransactionExecutor(transactionService, accountService);
        TransactionExecuted transactionExecuted = bankTransaction.executeTransaction(transaction);
        System.out.println(transactionExecuted.eventMessage());

        System.out.println("Transaction successful: " + transaction.getCode() + " " + transaction.getAmount());
        System.out.println("New balance: " + account.getBalance());
    }
}