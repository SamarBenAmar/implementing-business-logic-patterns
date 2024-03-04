package org.example.domain.model;

import org.example.domain.events.DepositExecuted;
import org.example.domain.events.TransactionExecuted;
import org.example.domain.events.WithdrawalExecuted;

import java.math.BigDecimal;

public class Account {

    private int id;
    private Money balance;

    public Account(int id, Money balance) {
        this.id = id;
        this.balance = balance;
    }

    public TransactionExecuted executeTransaction(Transaction transaction) throws Exception {
        if ("W".equals(transaction.getCode())) {
            return withdraw(transaction.getAmount());
        } else if ("D".equals(transaction.getCode())) {
            return deposit(transaction.getAmount());
        }
        throw new Exception("Invalid transaction code");
    }

    public WithdrawalExecuted withdraw(Money amount) throws Exception {
        if (balance.isLessThan(amount)){
            throw new Exception("Insufficient balance");
        }
        balance.subtract(amount);
        return new WithdrawalExecuted(this.id, amount);
    }

    public DepositExecuted deposit(Money amount) {
        balance.add(amount);
        return new DepositExecuted(this.id, amount);
    }

    public int getId() {
        return id;
    }

    public Money getBalance() {
        return balance;
    }
}

