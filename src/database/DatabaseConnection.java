package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/minizalo";
    private static final String USER = "root"; // Thay đổi thành username của bạn
    private static final String PASSWORD = "Thinkpadx204"; // Thay đổi thành password của bạn

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}