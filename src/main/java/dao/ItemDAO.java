package dao;

import dataBase.ConnectionBD;
import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    private final static String SQL_FIND_BY_COLECCION_ID = "SELECT * FROM Item WHERE id_coleccion = ?";
    private final static String SQL_INSERT = "INSERT INTO Item (nombre, descripcion, estado, fecha_adquisicion, precio, id_coleccion) VALUES (?, ?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE Item SET nombre = ?, descripcion = ?, estado = ?, fecha_adquisicion = ?, precio = ?, id_coleccion = ? WHERE id_item = ?";
    private final static String SQL_DELETE = "DELETE FROM Item WHERE id_item = ?";

    public List<Item> findByColeccionId(int idColeccion) {
        List<Item> items = new ArrayList<>();
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_COLECCION_ID)) {
            pst.setInt(1, idColeccion);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setIdItem(rs.getInt("id_item"));
                item.setNombre(rs.getString("nombre"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setEstado(rs.getString("estado"));
                item.setFechaAdquisicion(rs.getDate("fecha_adquisicion").toLocalDate()); // Conversión correcta
                item.setPrecio(rs.getDouble("precio"));
                item.setIdColeccion(rs.getInt("id_coleccion"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void save(Item item) {
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, item.getNombre());
            pst.setString(2, item.getDescripcion());
            pst.setString(3, item.getEstado());
            pst.setDate(4, java.sql.Date.valueOf(item.getFechaAdquisicion())); // Conversión correcta
            pst.setDouble(5, item.getPrecio());
            pst.setInt(6, item.getIdColeccion());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                item.setIdItem(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Item item) {
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {
            pst.setString(1, item.getNombre());
            pst.setString(2, item.getDescripcion());
            pst.setString(3, item.getEstado());
            pst.setDate(4, java.sql.Date.valueOf(item.getFechaAdquisicion())); // Conversión correcta
            pst.setDouble(5, item.getPrecio());
            pst.setInt(6, item.getIdColeccion());
            pst.setInt(7, item.getIdItem());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int idItem) {
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {
            pst.setInt(1, idItem);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}