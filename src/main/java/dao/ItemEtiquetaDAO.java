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