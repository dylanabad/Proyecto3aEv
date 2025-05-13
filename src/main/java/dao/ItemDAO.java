package dao;

import dataBase.ConnectionBD;
import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private final static String SQL_ALL = "SELECT * FROM Item";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Item WHERE id_item = ?";
    private final static String SQL_INSERT = "INSERT INTO Item (nombre, descripcion, estado, fecha_adquisicion, precio, id_coleccion) VALUES(?, ?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE Item SET nombre = ?, descripcion = ?, estado = ?, fecha_adquisicion = ?, precio = ?, id_coleccion = ? WHERE id_item = ?";
    private final static String SQL_DELETE = "DELETE FROM Item WHERE id_item = ?";

    public static List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (Connection con = ConnectionBD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL)) {
            while (rs.next()) {
                Item item = new Item();
                item.setIdItem(rs.getInt("id_item"));
                item.setNombre(rs.getString("nombre"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setEstado(rs.getString("estado"));
                item.setFechaAdquisicion(rs.getDate("fecha_adquisicion"));
                item.setPrecio(rs.getDouble("precio"));
                item.setIdColeccion(rs.getInt("id_coleccion"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static Item findById(int idItem) {
        Item item = null;
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)) {
            pst.setInt(1, idItem);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                item = new Item();
                item.setIdItem(rs.getInt("id_item"));
                item.setNombre(rs.getString("nombre"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setEstado(rs.getString("estado"));
                item.setFechaAdquisicion(rs.getDate("fecha_adquisicion"));
                item.setPrecio(rs.getDouble("precio"));
                item.setIdColeccion(rs.getInt("id_coleccion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static Item insertItem(Item item) {
        if (item != null) {
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, item.getNombre());
                pst.setString(2, item.getDescripcion());
                pst.setString(3, item.getEstado());
                pst.setDate(4, new java.sql.Date(item.getFechaAdquisicion().getTime()));
                pst.setDouble(5, item.getPrecio());
                pst.setInt(6, item.getIdColeccion());
                pst.executeUpdate();
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    item.setIdItem(rs.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                item = null;
            }
        }
        return item;
    }

    public static boolean updateItem(Item item) {
        boolean updated = false;
        if (item != null) {
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {
                pst.setString(1, item.getNombre());
                pst.setString(2, item.getDescripcion());
                pst.setString(3, item.getEstado());
                pst.setDate(4, new java.sql.Date(item.getFechaAdquisicion().getTime()));
                pst.setDouble(5, item.getPrecio());
                pst.setInt(6, item.getIdColeccion());
                pst.setInt(7, item.getIdItem());
                updated = pst.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return updated;
    }

    public static boolean deleteItem(int idItem) {
        boolean deleted = false;
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {
            pst.setInt(1, idItem);
            deleted = pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    // Método para obtener ítems por ID de colección
    public List<Item> findByColeccionId(int idColeccion) {
        // Implementación simulada (debes reemplazarla con lógica de base de datos)
        return new ArrayList<>();
    }

    // Método para guardar un nuevo ítem
    public void save(Item item) {
        // Implementación simulada (debes reemplazarla con lógica de base de datos)
    }

    // Método para actualizar un ítem existente
    public void update(Item item) {
        // Implementación simulada (debes reemplazarla con lógica de base de datos)
    }

    // Método para eliminar un ítem por su ID
    public void delete(int idItem) {
        // Implementación simulada (debes reemplazarla con lógica de base de datos)
    }
}
