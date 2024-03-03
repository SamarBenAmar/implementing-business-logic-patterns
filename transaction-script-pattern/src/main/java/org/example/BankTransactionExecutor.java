package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankTransactionExecutor {

    public void executeTransaction(Transaction transaction, Connection conn) throws Exception {
        conn.setAutoCommit(false);
        try {
            Account account = getAccountById(transaction.getAccountId(), conn);
            if ("W".equals(transaction.getCode()) ) {
                if (account.getBalance() >= transaction.getAmount() && transaction.getAmount() > 0) {
                   updateBalance(conn, account, account.getBalance() - transaction.getAmount());
                } else {
                    throw new Exception("Insufficient balance or invalid withdrawal amount.");
                }
            } else if ("D".equals(transaction.getCode())) {
                if (transaction.getAmount() > 0) {
                    throw new Exception("Invalid deposit amount.");
                }
                updateBalance(conn, account, account.getBalance() + transaction.getAmount());
            }
            createTransaction(conn, transaction.getAccountId(), transaction.getAmount(), transaction.getCode());
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        }
    }

    private void updateBalance(Connection conn, Account account, double newBalance) throws SQLException {
        String sql = "UPDATE account SET balance = ? WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setDouble(1, newBalance);
            statement.setInt(2, account.getId());
            statement.executeUpdate();
        }
    }

    private void createTransaction(Connection conn, int accountId, double amount, String code) throws SQLException {
        String sql = "INSERT INTO transaction (account_id, amount, code) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, accountId);
            statement.setDouble(2, amount);
            statement.setString(3, code);
            statement.executeUpdate();
        }
    }

    private Account getAccountById(int accountId, Connection conn) throws SQLException {
        String sql = "SELECT * FROM account WHERE id = ?";
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

