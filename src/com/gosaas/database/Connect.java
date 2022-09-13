package com.gosaas.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.gosaas.constants.ProjectConstants;

public class Connect {
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(ProjectConstants.DB_URL, ProjectConstants.DB_USERNAME, ProjectConstants.DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
