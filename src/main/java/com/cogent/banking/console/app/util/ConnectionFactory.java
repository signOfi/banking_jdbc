package com.cogent.banking.console.app.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static Connection connection;

    private ConnectionFactory () {
    }

    public static Connection getConnection() {
        if (connection == null) {
            String url = "jdbc:mysql://localhost:3306/banking_app";
            String user = "root";
            String password = "Hellomotto14!";
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

}
