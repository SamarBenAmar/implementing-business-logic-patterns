package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class BankTransaction {
    private String dbUrl;
    private String user;
    private String password;


    public BankTransaction(String dbUrl, String user, String password) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = password;
    }

    public void executeTransaction(Transaction transaction) throws Exception {
        try (Connection conn = DriverManager.getConnection(dbUrl, user, password)) {
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
}
