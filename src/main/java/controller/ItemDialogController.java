// Controlador para el diálogo de edición o creación de ítems.
// Maneja la inicialización de los campos del formulario y la obtención de datos ingresados por el usuario.
package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Item;

public class ItemDialogController {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField descripcionField;
    @FXML
    private TextField precioField;
    @FXML
    private ChoiceBox<String> estadoChoiceBox;
    @FXML
    private DatePicker fechaAdquisicionPicker;

    private Item item;

    /**
     * Inicializa el controlador al cargarse el FXML.
     * Se ejecuta automáticamente y configura los elementos iniciales del formulario.
     */
    @FXML
    private void initialize() {
        // Usa exactamente los valores que espera tu base de datos
        estadoChoiceBox.getItems().addAll("nuevo", "usado", "en venta", "reservado");
    }
    /**
     * Establece un ítem en el formulario, usado cuando se va a editar un ítem existente.
     * Los campos se rellenan con los datos actuales del ítem.
     *
     * @param item El ítem a editar
     */
    public void setItem(Item item) {
        this.item = item;

        if (item != null) {
            nombreField.setText(item.getNombre());
            descripcionField.setText(item.getDescripcion());
            estadoChoiceBox.setValue(item.getEstado());
            fechaAdquisicionPicker.setValue(item.getFechaAdquisicion());
            precioField.setText(String.valueOf(item.getPrecio()));
        }
    }

    /**
     * Obtiene los valores ingresados en el formulario y los guarda en un objeto Item.
     * Si no se está editando uno existente, se crea uno nuevo.
     *
     * @return El ítem con los datos del formulario
     */
    public Item getItem() {
        if (item == null) {
            item = new Item();
        }
        System.out.println("Capturando valores del diálogo...");
        item.setNombre(nombreField.getText());
        item.setDescripcion(descripcionField.getText());
        item.setEstado(estadoChoiceBox.getValue());
        item.setFechaAdquisicion(fechaAdquisicionPicker.getValue());
        try {
            item.setPrecio(Double.parseDouble(precioField.getText()));
        } catch (NumberFormatException e) {
            System.out.println("Precio inválido, estableciendo a 0");
            item.setPrecio(0.0);
        }
        System.out.println("Item capturado: " + item.getNombre() + ", " + item.getEstado() + ", " + item.getPrecio());
        return item;
    }
}
