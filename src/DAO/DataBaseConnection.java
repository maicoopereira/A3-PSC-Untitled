
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataBaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/estoque";
    private static final String USER = "root";
    private static final String PASSWORD = "riyXB3Lu";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

