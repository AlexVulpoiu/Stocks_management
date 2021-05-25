package database.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfiguration {

    private static final String URL = "jdbc:mysql://localhost:3306/stocks_management";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static Connection databaseConnection;

    private DatabaseConfiguration() {}

    public static Connection getDatabaseConnection() {

        try {
            if(databaseConnection == null || databaseConnection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                databaseConnection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch(SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        return databaseConnection;
    }
}
