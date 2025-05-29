package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Item;

import java.time.LocalDate;

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
        estadoChoiceBox.getItems().addAll("nuevo", "usado", "en venta", "reservado");
    }

    public void setItem(Item item) {
        this.item = item;

        if (item != null) {
            nombreField.setText(item.getNombre());
            descripcionField.setText(item.getDescripcion());
            estadoChoiceBox.setValue(item.getEstado());
            fechaAdquisicionPicker.setValue(item.getFechaAdquisicion()); // Uso directo de LocalDate
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
        item.setFechaAdquisicion(fechaAdquisicionPicker.getValue()); // Uso directo de LocalDate
        try {
            item.setPrecio(Double.parseDouble(precioField.getText()));
        } catch (NumberFormatException e) {
            item.setPrecio(0.0);
        }
        return item;
    }
}