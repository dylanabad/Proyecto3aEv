package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Item;

import java.time.LocalDate;

public class ItemDialogController {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField descripcionField;
    @FXML
    private ChoiceBox<String> estadoChoiceBox;
    @FXML
    private DatePicker fechaAdquisicionPicker;
    @FXML
    private TextField precioField;

    private Item item;

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