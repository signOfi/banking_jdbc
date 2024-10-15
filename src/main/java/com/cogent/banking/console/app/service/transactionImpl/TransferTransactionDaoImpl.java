package com.cogent.banking.console.app.service.transactionImpl;

import com.cogent.banking.console.app.service.TransferTransactionDao;
import com.cogent.banking.console.app.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransferTransactionDaoImpl implements TransferTransactionDao {

    private final Connection connection;

    public TransferTransactionDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void createTransaction(int fromId, int toId, double beforeBalance, double afterBalance) {

        String query = "insert into transfer_transaction (from_account_id, to_account_id," +
                "timestamp, before_balance, after_balance) values (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, fromId);
            preparedStatement.setInt(2, toId);
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setDouble(4, beforeBalance);
            preparedStatement.setDouble(5, afterBalance);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding to the transfer transaction table " + e);
        }


    }
}
