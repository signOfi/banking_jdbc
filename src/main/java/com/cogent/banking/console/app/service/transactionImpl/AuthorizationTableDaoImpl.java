package com.cogent.banking.console.app.service.transactionImpl;

import com.cogent.banking.console.app.service.AuthorizationTableDao;
import com.cogent.banking.console.app.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthorizationTableDaoImpl implements AuthorizationTableDao {

    private final Connection connection;

    public AuthorizationTableDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void createAuthorization(int employeeId, int approveOrDeny) {


        String query = "insert into authorization_table (employee_id, timestamp, approve_deny) values (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, employeeId);
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(3, approveOrDeny);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding to the authorization table " + e);
        }

    }


}
