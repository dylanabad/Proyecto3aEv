// Controlador que maneja las operaciones relacionadas con las colecciones.
// Incluye métodos para agregar colecciones y gestionar el diálogo correspondiente.
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

    /**
     * Establece los valores de una colección existente en los campos del formulario.
     * Se usa cuando se edita una colección.
     *
     * @param coleccion La colección cuyos datos se mostrarán en el formulario.
     */
    public void setColeccion(Coleccion coleccion) {
        this.coleccion = coleccion;

        if (coleccion != null) {
            nombreField.setText(coleccion.getNombre());
            categoriaField.setText(coleccion.getCategoria());
            descripcionField.setText(coleccion.getDescripcion());
        }
    }
    /**
     * Obtiene los datos introducidos en el formulario y los guarda en un objeto Coleccion.
     * Si no se está editando una colección existente, crea una nueva.
     *
     * @return Una instancia de Coleccion con los datos del formulario.
     */
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
