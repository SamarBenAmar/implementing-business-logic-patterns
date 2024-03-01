package org.example;
public class Transaction {

    private final int id;
    private final int accountId;
    private final double amount;
    private final String code;

    public Transaction(int id, int accountId, double amount, String code) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.code = code;
    }

    public int getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCode() {
        return code;
    }

}