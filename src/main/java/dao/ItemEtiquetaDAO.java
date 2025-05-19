package dao;

import dataBase.ConnectionBD;
import model.ItemEtiqueta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemEtiquetaDAO {
    private final static String SQL_ALL = "SELECT * FROM Item_Etiqueta";
    private final static String SQL_INSERT = "INSERT INTO Item_Etiqueta (id_item, id_etiqueta) VALUES(?, ?)";
    private final static String SQL_DELETE = "DELETE FROM Item_Etiqueta WHERE id_item = ? AND id_etiqueta = ?";

    /**
     * Obtiene todas las relaciones entre Items y Etiquetas registradas en la base de datos.
     * @return Lista de objetos ItemEtiqueta que representan las relaciones.
     */
    public static List<ItemEtiqueta> findAll() {
        List<ItemEtiqueta> itemEtiquetas = new ArrayList<>();
        try (Connection con = ConnectionBD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL)) {
            while (rs.next()) {
                ItemEtiqueta itemEtiqueta = new ItemEtiqueta();
                itemEtiqueta.setItemId(rs.getInt("id_item"));
                itemEtiqueta.setEtiquetaId(rs.getInt("id_etiqueta"));
                itemEtiquetas.add(itemEtiqueta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemEtiquetas;
    }

    /**
     * Inserta una nueva relación entre un Item y una Etiqueta.
     * @param itemEtiqueta Objeto que contiene los IDs del item y la etiqueta.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public static boolean insertItemEtiqueta(ItemEtiqueta itemEtiqueta) {
        boolean inserted = false;
        if (itemEtiqueta != null) {
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
                pst.setInt(1, itemEtiqueta.getItemId());
                pst.setInt(2, itemEtiqueta.getEtiquetaId());
                inserted = pst.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return inserted;
    }
    /**
     * Elimina una relación específica entre un Item y una Etiqueta según sus IDs.
     * @param idItem ID del item.
     * @param idEtiqueta ID de la etiqueta.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public static boolean deleteItemEtiqueta(int idItem, int idEtiqueta) {
        boolean deleted = false;
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {
            pst.setInt(1, idItem);
            pst.setInt(2, idEtiqueta);
            deleted = pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }
}