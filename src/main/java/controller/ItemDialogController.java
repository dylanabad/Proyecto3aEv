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
        // Inicializar valores del ChoiceBox
        estadoChoiceBox.getItems().addAll("Nuevo", "Usado");
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
        item.setNombre(nombreField.getText());
        item.setDescripcion(descripcionField.getText());
        item.setEstado(estadoChoiceBox.getValue());
        item.setFechaAdquisicion(fechaAdquisicionPicker.getValue());
        item.setPrecio(Double.parseDouble(precioField.getText()));
        return item;
    }
}