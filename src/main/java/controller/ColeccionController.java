package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import model.Coleccion;
import dao.ColeccionDAO;

public class ColeccionController {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField categoriaField;
    @FXML
    private TextField descripcionField;


    private Coleccion coleccion;
    private ColeccionDAO coleccionDAO = new ColeccionDAO();
    private ObservableList<Coleccion> coleccionList = FXCollections.observableArrayList();

    public void setColeccion(Coleccion coleccion) {
        this.coleccion = coleccion;

        if (coleccion != null) {
            nombreField.setText(coleccion.getNombre());
            categoriaField.setText(coleccion.getCategoria());
            descripcionField.setText(coleccion.getDescripcion());
        }
    }

    public Coleccion getColeccion() {
        Coleccion coleccion = new Coleccion();
        coleccion.setNombre(nombreField.getText());
        coleccion.setDescripcion(descripcionField.getText());
        coleccion.setCategoria(categoriaField.getText());

        // Mensajes de depuración
        System.out.println("Nombre: " + coleccion.getNombre());
        System.out.println("Descripción: " + coleccion.getDescripcion());
        System.out.println("Categoría: " + coleccion.getCategoria());

        return coleccion;
    }

    @FXML
    private void handleAgregarColeccion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyecto3aev/coleccionDialog.fxml"));
            DialogPane dialogPane = loader.load();

            ColeccionDialogController dialogController = loader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Agregar Colección");

            dialog.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    Coleccion nuevaColeccion = dialogController.getColeccion();
                    if (nuevaColeccion != null) {
                        coleccionDAO.save(nuevaColeccion); // Guardar en la base de datos
                        coleccionList.add(nuevaColeccion); // Actualizar la lista observable
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}