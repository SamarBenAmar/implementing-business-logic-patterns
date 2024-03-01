package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankTransaction {

    private final String dbConnection;

    public BankTransaction(String dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void executeTransaction(Transaction transaction) throws Exception {
        try (Connection conn = DriverManager.getConnection(dbConnection)) {
            Account account = getAccountById(transaction.getAccountId(), conn);
            if ("W".equals(transaction.getCode()) && account.getBalance() < transaction.getAmount()) {
                throw new Exception("Insufficient balance");
            }

            conn.setAutoCommit(false);
            try {
                if ("W".equals(transaction.getCode())){
                    updateBalance(conn, account, account.getBalance() - transaction.getAmount());
                } else if ("D".equals(transaction.getCode())) {
                    updateBalance(conn, account, account.getBalance() + transaction.getAmount());
                }
                createTransaction(conn, transaction.getAccountId(), transaction.getAmount(), transaction.getCode());
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
    }

    private void updateBalance(Connection conn, Account account, double newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setDouble(1, newBalance);
            statement.setInt(2, account.getId());
            statement.executeUpdate();
        }
    }

    public void createTransaction(Connection conn, int accountId, double amount, String code) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, amount, code) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, accountId);
            statement.setDouble(2, amount);
            statement.setString(3, code);
            statement.executeUpdate();
        }
    }

    public Account getAccountById(int accountId, Connection conn) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, accountId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Account(resultSet.getInt("id"), resultSet.getDouble("balance"));
                }
            }
        }
        throw new SQLException("Account not found");
    }
}

