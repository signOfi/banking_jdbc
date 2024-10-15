package com.cogent.banking.console.app.service.serviceImpl;

import com.cogent.banking.console.app.entity.User;
import com.cogent.banking.console.app.service.UserDao;
import com.cogent.banking.console.app.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private final Connection connection;

    public UserDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public User registerUser(User user) {
        String query = "insert into user (firstName, lastName, email, username, password, isEmployee) " +
                "values (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.setString(5, user.getPassword());
            int isEmployee = user.isEmployee() ? 1 : 0;
            preparedStatement.setInt(6, isEmployee);
            preparedStatement.execute();
            return user;
        } catch (SQLException e) {
            System.out.println("Error code for adding " + e);
        }
        return null;
    }

    @Override
    public User login(String username, String password) {
        String query = "SELECT * FROM user where username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                int checkSqlValForIsEmployee = resultSet.getInt("isEmployee");
                boolean isEmployee = checkSqlValForIsEmployee == 1;

                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        isEmployee
                );
            }

        } catch (SQLException e) {
            System.out.println("Error code for getting " + e);
        }

        return null;
    }

}












