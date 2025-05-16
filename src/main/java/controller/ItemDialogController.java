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

    @FXML
    private void initialize() {
        // Usa exactamente los valores que espera tu base de datos
        estadoChoiceBox.getItems().addAll("nuevo", "usado", "en venta", "reservado");
    }

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
