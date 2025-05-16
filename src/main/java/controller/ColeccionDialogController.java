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


}
