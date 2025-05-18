package dao;

import dataBase.ConnectionBD;
import model.Coleccion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColeccionDAO {
    private final static String SQL_ALL = "SELECT * FROM coleccion";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM coleccion WHERE id_coleccion = ?";
    private final static String SQL_INSERT = "INSERT INTO coleccion (nombre, descripcion, categoria, id_usuario) VALUES(?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE coleccion SET nombre = ?, descripcion = ?, categoria = ?, id_usuario = ? WHERE id_coleccion = ?";
    private final static String SQL_DELETE = "DELETE FROM coleccion WHERE id_coleccion = ?";

    public static List<Coleccion> findAll() {
        List<Coleccion> colecciones = new ArrayList<>();
        try (Connection con = ConnectionBD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL)) {
            while (rs.next()) {
                Coleccion coleccion = new Coleccion();
                coleccion.setIdColeccion(rs.getInt("id_coleccion"));
                coleccion.setNombre(rs.getString("nombre"));
                coleccion.setDescripcion(rs.getString("descripcion"));
                coleccion.setCategoria(rs.getString("categoria"));
                coleccion.setIdUsuario(rs.getInt("id_usuario"));
                colecciones.add(coleccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colecciones;
    }

    public List<Coleccion> findByUsuarioId(int idUsuario) {
        List<Coleccion> colecciones = new ArrayList<>();
        String sql = "SELECT * FROM coleccion WHERE id_usuario = ?";
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idUsuario);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Coleccion coleccion = new Coleccion();
                coleccion.setIdColeccion(rs.getInt("id_coleccion"));
                coleccion.setNombre(rs.getString("nombre"));
                coleccion.setDescripcion(rs.getString("descripcion"));
                coleccion.setCategoria(rs.getString("categoria"));
                coleccion.setIdUsuario(rs.getInt("id_usuario"));
                colecciones.add(coleccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colecciones;
    }

    public void insertar(Coleccion coleccion) {
        String sql = "INSERT INTO coleccion (nombre, categoria, descripcion, id_usuario) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("Conectado a BD: " + (conn != null));
            System.out.println("Insertando colección con nombre: " + coleccion.getNombre());

            stmt.setString(1, coleccion.getNombre());
            stmt.setString(2, coleccion.getCategoria());
            stmt.setString(3, coleccion.getDescripcion());
            stmt.setInt(4, coleccion.getUsuario().getIdUsuario());

            int filas = stmt.executeUpdate();
            System.out.println("Filas insertadas: " + filas);

        } catch (SQLException e) {
            System.out.println(" Error al insertar colección: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static boolean updateColeccion(Coleccion coleccion) {
        String sql = "UPDATE coleccion SET nombre = ?, categoria = ?, descripcion = ? WHERE id_coleccion = ?";
        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Depuración: mostrar los datos que se intentan actualizar
            System.out.println("Intentando actualizar colección...");
            System.out.println("ID: " + coleccion.getIdColeccion());
            System.out.println("Nombre: " + coleccion.getNombre());
            System.out.println("Categoría: " + coleccion.getCategoria());
            System.out.println("Descripción: " + coleccion.getDescripcion());

            // Establecer parámetros
            stmt.setString(1, coleccion.getNombre());
            stmt.setString(2, coleccion.getCategoria());
            stmt.setString(3, coleccion.getDescripcion());
            stmt.setInt(4, coleccion.getIdColeccion());

            int filasAfectadas = stmt.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    public static boolean deleteColeccion(int idColeccion) {
        boolean deleted = false;
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {
            pst.setInt(1, idColeccion);
            deleted = pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;

    }

    public boolean save(Coleccion coleccion) {
        String sql = "INSERT INTO coleccion (nombre, categoria, descripcion, id_usuario) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, coleccion.getNombre());
            stmt.setString(2, coleccion.getCategoria());
            stmt.setString(3, coleccion.getDescripcion());
            stmt.setInt(4, coleccion.getIdUsuario());

            return stmt.executeUpdate() > 0; // Devuelve true si se insertó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Devuelve false si ocurre un error
        }
    }

    public void update(Coleccion coleccion) {
        updateColeccion(coleccion);
    }


    public void delete(int idColeccion) {
        String sql = "DELETE FROM coleccion WHERE id_coleccion = ?";
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idColeccion);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
