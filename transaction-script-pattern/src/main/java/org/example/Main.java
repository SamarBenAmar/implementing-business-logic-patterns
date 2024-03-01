package org.example;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        Account account = new Account(1, 100);
        Transaction transaction = new Transaction(account.getId(), 50, "W");
        BankTransaction bankTransaction = new BankTransaction("jdbc:postgresql://localhost:5432/test?user=postgres&password=postgres");
        bankTransaction.executeTransaction(transaction);
        System.out.println("Transaction successful: " + transaction.getCode() + " " + transaction.getAmount());
        System.out.println("New balance: " + account.getBalance());
    }
}