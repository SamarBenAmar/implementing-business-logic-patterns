package org.example.domain.model;

import org.example.domain.model.Money;

public class Transaction {
     private int id;
    private int accountId;
    private Money amount;
    private String code;

    public Transaction(int id, int accountId, Money amount, String code) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.code = code;
    }

    public int getAccountId() {
        return accountId;
    }

    public Money getAmount() {
        return amount;
    }

    public String getCode() {
        return code;
    }
}
