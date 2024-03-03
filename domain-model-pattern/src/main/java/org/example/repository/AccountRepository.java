package org.example.repository;

import org.example.config.DatabaseConnection;
import org.example.domain.model.Account;
import org.example.domain.model.Money;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;

public class AccountRepository {

    Connection conn = DatabaseConnection.connect();

    public AccountRepository() throws SQLException {
    }

    public void updateAccount(Account account) throws SQLException {
        String sql = "UPDATE account SET balance = ? WHERE id = ?";
        conn.setAutoCommit(false);
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setDouble(1, account.getBalance().amount().doubleValue());
            statement.setInt(2, account.getId());
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    }

    public Account getAccountById(int accountId) throws SQLException {
        String sql = "SELECT * FROM account WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, accountId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Account(resultSet.getInt("id"), new Money(BigDecimal.valueOf(resultSet.getDouble("balance")), Currency.getInstance("Euro")));
                }
            }
        }
        throw new SQLException("Account not found");
    }
}
