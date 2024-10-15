package com.cogent.banking.console.app.service.serviceImpl;

import com.cogent.banking.console.app.service.AuthorizationTableDao;
import com.cogent.banking.console.app.service.EmployeeDao;
import com.cogent.banking.console.app.util.AuthorizationTableFactory;
import com.cogent.banking.console.app.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoImpl implements EmployeeDao {

    private final Connection connection;
    private final AuthorizationTableDao authorizationTableDao;

    public EmployeeDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
        this.authorizationTableDao = AuthorizationTableFactory.getAuthorizationTableDao();
    }

    @Override
    public void printAllInactiveAccounts() {
        /* Print all accounts where isActive == 0 */
        String query = "SELECT * FROM account where isActive = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, 0);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String output = "AccountID: " + resultSet.getInt("accountId") + "\n" +
                        "UserID: " + resultSet.getInt("userId") + "\n" +
                        "Account Balance: $" + String.format("%.2f", resultSet.getDouble("balance")) + "\n";

                System.out.println(output);
            }
        } catch (SQLException e) {
            System.out.println("Error code for getting " + e);
        }
    }

    private boolean checkAccountIdAndIsActive(int accountId) {
        String query = "SELECT * FROM account where accountId = ? and isActive = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, 0);

            ResultSet resultSet = preparedStatement.executeQuery();

            /* If SQL query contains the correct accountId and isActive is false R.V is True */
            if (resultSet.next()) {
                return true;
            } else {
                System.out.println("The accountId either DNE or is active. Could not approve/deny");
            }

        } catch (SQLException e) {
            System.out.println("Error code for getting " + e);
        }
        return  false;
    }

    @Override
    public void approveDeny(int accountId, int approveOrDeny, int employeeId) {

        /* First check if the accountId exists and is inactive */
        if (checkAccountIdAndIsActive(accountId)) {


            /* Update is Active status to 1 */
            if (approveOrDeny == 1) {
                String query = "UPDATE ACCOUNT set isActive = ? where accountId = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, 1);
                    preparedStatement.setInt(2, accountId);
                    preparedStatement.executeUpdate();                      // commit to DB

                    /* Log the approval */
                    authorizationTableDao.createAuthorization(employeeId,1);

                    System.out.println("Account has been approved~");
                } catch (SQLException e) {
                    System.out.println("Error code for updating Balance " + e);
                }
            }

            /* Handle if denied */
            else {
                authorizationTableDao.createAuthorization(employeeId, 0);
            }

        }
    }

    @Override
    public void viewAllCustomerBankAccount(int customerId) {
        /* Validate customer id, check if in DB */
        if (validateCustomerId(customerId)) {
            /* Once validated show all accounts associated with that id */
            printAllBankAccountsByUserId(customerId);
        }
    }

    private void printAllBankAccountsByUserId(int userId) {
        /* Print all accounts where isActive == 0 */
        String query = "SELECT * FROM account where userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("All accounts for the following UserID: " + userId);

            while (resultSet.next()) {

                int isActiveInt = resultSet.getInt("isActive");
                boolean isActive = isActiveInt == 1;

                String output = "AccountID: " + resultSet.getInt("accountId") + "\n" +
                        "Account Balance: $" + String.format("%.2f", resultSet.getDouble("balance")) + "\n"
                        + "IsActive: " + isActive + "\n";

                System.out.println(output);
            }
        } catch (SQLException e) {
            System.out.println("Error code for getting " + e);
        }
    }

    private boolean validateCustomerId(int userId) {
        String query = "SELECT * FROM user where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error code for getting " + e);
        }
        return false;
    }

}
