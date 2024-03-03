package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Account {
    private int id;
    private double balance;
    public Account(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void updateBalance(Connection conn, Account account, double newBalance) throws SQLException {
        String sql = "UPDATE account SET balance = ? WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setDouble(1, newBalance);
            statement.setInt(2, account.getId());
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    }

    public static Account getAccountById(int accountId, Connection conn) throws SQLException {
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
