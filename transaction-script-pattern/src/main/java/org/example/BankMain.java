package org.example;

public class BankMain {
    public static void main(String[] args) throws Exception {
        Account account = new Account(1, 100);
        Transaction transaction = new Transaction(account.getId(), 50, "W");
        BankTransaction bankTransaction = new BankTransaction("jdbc:postgresql://localhost:5432/test?user=postgres&password=postgres");
        bankTransaction.executeTransaction(transaction);
        System.out.println("Transaction successful: " + transaction.getCode() + " " + transaction.getAmount());
        System.out.println("New balance: " + account.getBalance());
    }
}