
package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/gestor_colecciones";
    private static final String USER = "dylan";
    private static final String PASSWORD = "i[@kxqF5VR0Rpkd_";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}