package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import dataBase.ConnectionBD;

public class TestConsulta {
    public static void main(String[] args) {
        try (Connection conn = ConnectionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario")) {

            System.out.println("Lista de usuarios:");
            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");

                System.out.println("- ID: " + id + ", Nombre: " + nombre + ", Email: " + email);
            }

        } catch (Exception e) {
            System.out.println(" Error al consultar los usuarios:");
            e.printStackTrace();
        }
    }
}