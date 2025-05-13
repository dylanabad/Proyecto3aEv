package org.example.proyecto3aev;

import dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Usuario;

import java.io.IOException;

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
            try {
                // Cargar la vista del MainController
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyecto3aev/main.fxml"));
                Parent root = loader.load();

                // Pasar el usuario al MainController
                MainController mainController = loader.getController();
                mainController.setUsuario(usuario);

                // Cambiar la escena
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

                showAlert("Login correcto", "Bienvenido, " + usuario.getNombre(), Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "No se pudo cargar la vista principal.", Alert.AlertType.ERROR);
            }
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
