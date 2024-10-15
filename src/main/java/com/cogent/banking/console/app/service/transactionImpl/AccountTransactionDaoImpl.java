package com.cogent.banking.console.app.service.transactionImpl;

import com.cogent.banking.console.app.service.AccountTransactionDao;
import com.cogent.banking.console.app.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountTransactionDaoImpl implements AccountTransactionDao {

    private final Connection connection;

    public AccountTransactionDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }


    @Override
    public void createTransaction(int withdrawOrDeposit, double beforeBalance, double afterBalance, int accountId) {

        String query = "insert into account_transaction (withdraw_deposit, before_balance," +
                " after_balance, timestamp, account_id) values (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, withdrawOrDeposit);
            preparedStatement.setDouble(2, beforeBalance);
            preparedStatement.setDouble(3, afterBalance);
            preparedStatement.setDate(4, new java.sql.Date(System.currentTimeMillis()));
            preparedStatement.setInt(5, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding to the account transaction table " + e);
        }

    }
}
