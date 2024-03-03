package org.example;

import org.example.config.DatabaseConnection;

import java.sql.Connection;

public class BankMain {
    public static void main(String[] args) throws Exception {
        Account account = new Account(1, 100);
        Transaction transaction = new Transaction(1, account.getId(), 50, "W");
        BankTransaction bankTransaction = new BankTransaction();
        try (Connection conn = DatabaseConnection.connect()) {
            bankTransaction.executeTransaction(transaction, conn);
        }
        System.out.println("Transaction successful: " + transaction.getCode() + " " + transaction.getAmount());
        System.out.println("New balance: " + account.getBalance());
    }
}
