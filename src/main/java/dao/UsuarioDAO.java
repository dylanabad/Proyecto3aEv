package dao;

import dataBase.ConnectionBD;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final static String SQL_ALL = "SELECT * FROM Usuario";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Usuario WHERE id_usuario = ?";
    private final static String SQL_INSERT = "INSERT INTO Usuario (nombre, email, contraseña) VALUES(?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE Usuario SET nombre = ?, email = ?, contraseña = ? WHERE id_usuario = ?";
    private final static String SQL_DELETE = "DELETE FROM Usuario WHERE id_usuario = ?";

    /**
     * Obtiene todos los usuarios almacenados en la base de datos.
     * @return Lista con todos los usuarios encontrados.
     */
    public static List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection con = ConnectionBD.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_ALL)) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEmail(rs.getString("email"));
                usuario.setContrasena(rs.getString("contraseña"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    /**
     * Busca un usuario por su ID.
     * @param idUsuario ID del usuario que se desea buscar.
     * @return El usuario encontrado o null si no existe.
     */
    public static Usuario findById(int idUsuario) {
        Usuario usuario = null;
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_FIND_BY_ID)) {
            pst.setInt(1, idUsuario);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEmail(rs.getString("email"));
                usuario.setContrasena(rs.getString("contraseña"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }
    /**
     * Inserta un nuevo usuario en la base de datos.
     * @param usuario Objeto Usuario con los datos a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public static boolean insertUsuario(Usuario usuario) {
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
            pst.setString(1, usuario.getNombre());
            pst.setString(2, usuario.getEmail());
            pst.setString(3, usuario.getContrasena());
            return pst.executeUpdate() > 0; // Devuelve true si se insertó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Devuelve false en caso de error
        }
    }

    /**
     * Actualiza los datos de un usuario existente.
     * @param usuario Objeto Usuario con los datos actualizados (debe incluir el ID).
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public static boolean updateUsuario(Usuario usuario) {
        boolean updated = false;
        if (usuario != null) {
            try (Connection con = ConnectionBD.getConnection();
                 PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {
                pst.setString(1, usuario.getNombre());
                pst.setString(2, usuario.getEmail());
                pst.setString(3, usuario.getContrasena());
                pst.setInt(4, usuario.getIdUsuario());
                updated = pst.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return updated;
    }
    /**
     * Elimina un usuario de la base de datos según su ID.
     * @param idUsuario ID del usuario a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public static boolean deleteUsuario(int idUsuario) {
        boolean deleted = false;
        try (Connection con = ConnectionBD.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {
            pst.setInt(1, idUsuario);
            deleted = pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }
    /**
     * Método para autenticar un usuario mediante email y contraseña.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return El usuario si las credenciales son correctas, null si no.
     */
    public Usuario login(String email, String password) {
        String query = "SELECT * FROM usuario WHERE email = ? AND contraseña = ?";
        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setEmail(rs.getString("email"));
                    // Otros campos si es necesario
                    return usuario;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}