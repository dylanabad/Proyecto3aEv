// Controlador que gestiona la lógica de inicio de sesión.
// Verifica las credenciales del usuario y permite el acceso a la aplicación si son válidas.
package controller;

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


    /**
     * Método que se ejecuta al presionar el botón de login.
     * Verifica las credenciales del usuario y, si son válidas, abre la ventana principal.
     */
    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        Usuario usuario = usuarioDAO.login(email, password);
        if (usuario != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyecto3aev/main.fxml"));
                Parent root = loader.load();

                // Pasar usuario al controlador
                MainController mainController = loader.getController();
                mainController.setUsuario(usuario);

                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            showAlert("Login fallido", "Credenciales incorrectas", Alert.AlertType.ERROR);
        }
    }


    /**
     * Muestra una alerta con el mensaje indicado.
     *
     * @param title Título de la alerta
     * @param msg Mensaje de la alerta
     * @param type Tipo de alerta (INFORMATION, ERROR, etc.)
     */
    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
