package dao;

import dataBase.ConnectionBD;
import model.Etiqueta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtiquetaDAO {
    private final static String SQL_ALL = "SELECT * FROM Etiqueta";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Etiqueta WHERE id_etiqueta = ?";
    private final static String SQL_INSERT = "INSERT INTO Etiqueta (nombre) VALUES(?)";
    private final static String SQL_UPDATE = "UPDATE Etiqueta SET nombre = ? WHERE id_etiqueta = ?";
    private final static String SQL_DELETE = "DELETE FROM Etiqueta WHERE id_etiqueta = ?";

    /**
     * Recupera todas las etiquetas almacenadas en la base de datos.
     * @return Lista con todas las etiquetas.
     */
    public static List<Etiqueta> findAll() {
        List<Etiqueta> etiquetas = new ArrayList<>();
        try (Connection con = ConnectionBD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL)) {
            while (rs.next()) {
                Etiqueta etiqueta = new Etiqueta();
                etiqueta.setIdEtiqueta(rs.getInt("id_etiqueta"));
                etiqueta.setNombre(rs.getString("nombre"));
                etiquetas.add(etiqueta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etiquetas;
    }
    /**
     * Busca una etiqueta por su ID.
     * @param idEtiqueta Identificador de la etiqueta.
     * @return Objeto Etiqueta si se encuentra, o null si no existe.
     */
    public static Etiqueta findById(int idEtiqueta) {
        Etiqueta etiqueta = null;
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)) {
            pst.setInt(1, idEtiqueta);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                etiqueta = new Etiqueta();
                etiqueta.setIdEtiqueta(rs.getInt("id_etiqueta"));
                etiqueta.setNombre(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etiqueta;
    }

    /**
     * Inserta una nueva etiqueta en la base de datos.
     * @param etiqueta Objeto Etiqueta a insertar.
     * @return Etiqueta con el ID generado asignado, o null si falla la inserción.
     */
    public static Etiqueta insertEtiqueta(Etiqueta etiqueta) {
        if (etiqueta != null) {
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, etiqueta.getNombre());
                pst.executeUpdate();
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    etiqueta.setIdEtiqueta(rs.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                etiqueta = null;
            }
        }
        return etiqueta;
    }

    /**
     * Actualiza una etiqueta existente en la base de datos.
     * @param etiqueta Objeto Etiqueta con datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public static boolean updateEtiqueta(Etiqueta etiqueta) {
        boolean updated = false;
        if (etiqueta != null) {
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {
                pst.setString(1, etiqueta.getNombre());
                pst.setInt(2, etiqueta.getIdEtiqueta());
                updated = pst.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return updated;
    }

    /**
     * Elimina una etiqueta de la base de datos por su ID.
     * @param idEtiqueta Identificador de la etiqueta a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public static boolean deleteEtiqueta(int idEtiqueta) {
        boolean deleted = false;
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {
            pst.setInt(1, idEtiqueta);
            deleted = pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }
}
