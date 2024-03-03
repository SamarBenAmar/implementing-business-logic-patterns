package org.example.repository;

import org.example.config.DatabaseConnection;
import org.example.domain.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionRepository {

    Connection conn = DatabaseConnection.connect();

    public TransactionRepository() throws SQLException {
    }

    public void saveTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transaction (account_id, amount, code) VALUES (?, ?, ?)";
        conn.setAutoCommit(false);
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, transaction.getAccountId());
            statement.setDouble(2, transaction.getAmount().amount().doubleValue());
            statement.setString(3, transaction.getCode());
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    }
}
