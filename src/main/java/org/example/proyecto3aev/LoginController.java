package org.example.proyecto3aev;


import dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Usuario;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML private PasswordField passwordField;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        Usuario usuario = usuarioDAO.login(email, password);
        if (usuario != null) {
            // Cambiar a la pantalla principal
            // Por ejemplo, MainApp.loadMainView(usuario);
            showAlert("Login correcto", "Bienvenido, " + usuario.getNombre(), Alert.AlertType.INFORMATION);
        } else {
            showAlert("Login fallido", "Credenciales incorrectas", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
