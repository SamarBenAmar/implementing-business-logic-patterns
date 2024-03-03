package org.example.domain.model;

public class Account {

    private int id;
    private Money balance;

    public Account(int id, Money balance) {
        this.id = id;
        this.balance = balance;
    }

    public void executeTransaction(Transaction transaction) throws Exception {
        if ("W".equals(transaction.getCode())) {
            withdraw(transaction.getAmount());
        } else if ("D".equals(transaction.getCode())) {
            deposit(transaction.getAmount());
        }
    }

    public void withdraw(Money amount) throws Exception {
        if (balance.isLessThan(amount)) {
            throw new Exception("Insufficient balance");
        }
        balance.subtract(amount);
    }

    public void deposit(Money amount) {
        balance.add(amount);
    }

    public int getId() {
        return id;
    }

    public Money getBalance() {
        return balance;
    }
}

