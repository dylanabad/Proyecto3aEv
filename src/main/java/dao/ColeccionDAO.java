package dao;

import dataBase.ConnectionBD;
import model.Coleccion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColeccionDAO {
    private final static String SQL_ALL = "SELECT * FROM Coleccion";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Coleccion WHERE id_coleccion = ?";
    private final static String SQL_INSERT = "INSERT INTO Coleccion (nombre, descripcion, categoria, id_usuario) VALUES(?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE Coleccion SET nombre = ?, descripcion = ?, categoria = ?, id_usuario = ? WHERE id_coleccion = ?";
    private final static String SQL_DELETE = "DELETE FROM Coleccion WHERE id_coleccion = ?";

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
        String sql = "SELECT * FROM Coleccion WHERE id_usuario = ?";
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

    public static Coleccion insertColeccion(Coleccion coleccion) {
        if (coleccion != null) {
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, coleccion.getNombre());
                pst.setString(2, coleccion.getDescripcion());
                pst.setString(3, coleccion.getCategoria());
                pst.setInt(4, coleccion.getIdUsuario());
                pst.executeUpdate();
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    coleccion.setIdColeccion(rs.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                coleccion = null;
            }
        }
        return coleccion;
    }

    public static boolean updateColeccion(Coleccion coleccion) {
        boolean updated = false;
        if (coleccion != null) {
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {
                pst.setString(1, coleccion.getNombre());
                pst.setString(2, coleccion.getDescripcion());
                pst.setString(3, coleccion.getCategoria());
                pst.setInt(4, coleccion.getIdUsuario());
                pst.setInt(5, coleccion.getIdColeccion());
                updated = pst.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return updated;
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

    public void save(Coleccion coleccion) {
        String sql = "INSERT INTO colecciones (nombre, categoria, descripcion, id_usuario) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, coleccion.getNombre());
            stmt.setString(2, coleccion.getCategoria());
            stmt.setString(3, coleccion.getDescripcion());
            stmt.setInt(4, coleccion.getIdUsuario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Coleccion coleccion) {
        String sql = "UPDATE colecciones SET nombre = ?, categoria = ?, descripcion = ? WHERE id_coleccion = ?";
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, coleccion.getNombre());
            stmt.setString(2, coleccion.getCategoria());
            stmt.setString(3, coleccion.getDescripcion());
            stmt.setInt(4, coleccion.getIdColeccion());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
