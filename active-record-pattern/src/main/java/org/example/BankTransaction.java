package org.example;

import java.sql.Connection;
public class BankTransaction {

    public void executeTransaction(Transaction transaction, Connection conn) throws Exception {
        Account account = Account.getAccountById(transaction.getAccountId(), conn);
        if ("W".equals(transaction.getCode()) && account.getBalance() < transaction.getAmount()) {
            throw new Exception("Insufficient balance");
        }

        conn.setAutoCommit(false);
        try {
            if ("W".equals(transaction.getCode())){
                account.updateBalance(conn, account, account.getBalance() - transaction.getAmount());
            } else if ("D".equals(transaction.getCode())) {
                account.updateBalance(conn, account, account.getBalance() + transaction.getAmount());
            }
            transaction.saveTransaction(conn, transaction.getAccountId(), transaction.getAmount(), transaction.getCode());
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        }
    }
}
