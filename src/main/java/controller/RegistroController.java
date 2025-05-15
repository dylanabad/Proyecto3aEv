package controller;

import dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Usuario;

public class RegistroController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField contrasenaField;

    @FXML
    private PasswordField confirmarContrasenaField;

    @FXML
    private void handleRegister() {
        String nombre = nombreField.getText();
        String email = emailField.getText();
        String contrasena = contrasenaField.getText();
        String confirmarContrasena = confirmarContrasenaField.getText();

        if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.");
            return;
        }

        if (!contrasena.equals(confirmarContrasena)) {
            mostrarAlerta("Las contraseñas no coinciden.");
            return;
        }

        Usuario usuario = new Usuario(0, nombre, email, contrasena);
        boolean registrado = UsuarioDAO.insertUsuario(usuario);

        if (registrado) {
            mostrarAlerta("Usuario registrado con éxito.");
            cargarLogin(); // Redirige al login
        } else {
            mostrarAlerta("Error al registrar el usuario.");
        }
    }

    private void cargarLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyecto3aev/login.fxml"));
            Scene loginScene = new Scene(loader.load());
            Stage stage = (Stage) nombreField.getScene().getWindow(); // Obtiene la ventana actual
            stage.setScene(loginScene);
            stage.setTitle("Gestor de Colecciones - Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Registro");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyecto3aev/login.fxml"));
            Scene loginScene = new Scene(loader.load());
            Stage stage = (Stage) nombreField.getScene().getWindow(); // Obtiene la ventana actual
            stage.setScene(loginScene);
            stage.setTitle("Gestor de Colecciones - Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}