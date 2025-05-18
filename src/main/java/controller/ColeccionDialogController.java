package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Coleccion;

public class ColeccionDialogController {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField categoriaField;
    @FXML
    private TextField descripcionField;

    private Coleccion coleccion;

    public void setColeccion(Coleccion coleccion) {
        this.coleccion = coleccion;

        if (coleccion != null) {
            nombreField.setText(coleccion.getNombre());
            categoriaField.setText(coleccion.getCategoria());
            descripcionField.setText(coleccion.getDescripcion());
        }
    }
    public Coleccion getColeccion() {
        if (coleccion == null) {
            coleccion = new Coleccion(); // Solo si no existe (para casos como crear nueva)
        }
        coleccion.setNombre(nombreField.getText());
        coleccion.setDescripcion(descripcionField.getText());
        coleccion.setCategoria(categoriaField.getText());
        return coleccion;
    }







}
