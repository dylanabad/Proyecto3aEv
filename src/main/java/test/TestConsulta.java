package test;

import dataBase.ConnectionBD;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConsulta {
    public static void main(String[] args) {
        try (Connection conn = ConnectionBD.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ ¡Conexión exitosa a la base de datos!");
            } else {
                System.out.println("❌ La conexión está cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar con la base de datos:");
            e.printStackTrace();
        }

        
    }
}