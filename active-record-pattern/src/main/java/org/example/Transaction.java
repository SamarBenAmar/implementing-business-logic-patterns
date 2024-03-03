package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transaction {

    private int id;
    private int accountId;
    private double amount;
    private String code;

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

    public void saveTransaction(Connection conn, int accountId, double amount, String code) throws SQLException {
        String sql = "INSERT INTO transaction (account_id, amount, code) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, accountId);
            statement.setDouble(2, amount);
            statement.setString(3, code);
            statement.executeUpdate();
        }
    }
}
