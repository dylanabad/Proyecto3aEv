package org.example.proyecto3aev;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Usuario;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    public void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        Usuario usuario = usuarioDAO.encontrarPorEmail(email);
        if (usuario != null && usuario.getContrasena().equals(password)) {
            // Cambiar a la siguiente vista
            System.out.println("Inicio de sesión exitoso");
        } else {
            // Mostrar error
            errorLabel.setText("Credenciales incorrectas");
        }
    }
}