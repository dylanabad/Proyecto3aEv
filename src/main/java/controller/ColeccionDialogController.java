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
        } else {
            this.coleccion = new Coleccion();
        }
    }

    public Coleccion getColeccion() {
        System.out.println("Obteniendo colección editada");
        System.out.println("Nombre: " + nombreField.getText());
        System.out.println("Categoría: " + categoriaField.getText());
        System.out.println("Descripción: " + descripcionField.getText());

        coleccion.setNombre(nombreField.getText());
        coleccion.setCategoria(categoriaField.getText());
        coleccion.setDescripcion(descripcionField.getText());
        return coleccion;
    }
}
