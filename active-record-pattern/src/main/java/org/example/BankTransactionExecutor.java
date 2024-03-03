package org.example;

import java.sql.Connection;
public class BankTransactionExecutor {

    public void executeTransaction(Transaction transaction, Connection conn) throws Exception {
        Account account = Account.getAccountById(transaction.getAccountId(), conn);
        if ("W".equals(transaction.getCode()) ) {
            if (account.getBalance() >= transaction.getAmount() && transaction.getAmount() > 0) {
                account.updateBalance(conn, account, account.getBalance() - transaction.getAmount());
            } else {
                throw new Exception("Insufficient balance or invalid withdrawal amount.");
            }
        } else if ("D".equals(transaction.getCode())) {
            if (transaction.getAmount() > 0) {
                throw new Exception("Invalid deposit amount.");
            }
            account.updateBalance(conn, account, account.getBalance() + transaction.getAmount());
        }
        transaction.saveTransaction(conn, transaction.getAccountId(), transaction.getAmount(), transaction.getCode());
    }
}
