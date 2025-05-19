// Controlador que gestiona las operaciones relacionadas con los ítems.
// Permite agregar, editar y eliminar ítems asociados a una colección.
package controller;

import dao.ItemDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import model.Item;

public class ItemController {

    @FXML
    private TableView<Item> itemTable;
    @FXML
    private TableColumn<Item, String> nombreColumn;
    @FXML
    private TableColumn<Item, String> descripcionColumn;

    private final ItemDAO itemDAO = new ItemDAO();
    private final ObservableList<Item> itemList = FXCollections.observableArrayList();


    /**
     * Inicializa el controlador automáticamente después de cargar el FXML.
     * Configura las columnas de la tabla y carga los datos desde la base de datos.
     */
    @FXML
    private void initialize() {
        // Configurar columnas de la tabla
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());

        // Cargar datos en la tabla
        itemList.addAll(itemDAO.findByColeccionId(1)); // Cambia el ID según sea necesario
        itemTable.setItems(itemList);
    }
    /**
     * Maneja la acción de agregar un nuevo ítem.
     * Abre un diálogo para introducir los datos y guarda el ítem si se confirma.
     */
    @FXML
    private void handleAgregarItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyecto3aev/itemDialog.fxml"));
            DialogPane dialogPane = loader.load();

            ItemDialogController dialogController = loader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Agregar Item");

            dialog.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    Item nuevoItem = dialogController.getItem();
                    if (nuevoItem != null) {
                        itemDAO.save(nuevoItem);
                        itemList.add(nuevoItem); // Actualiza la tabla
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Maneja la acción de editar un ítem seleccionado.
     * Permite modificar sus datos y actualizarlos en la base de datos y la tabla.
     */
    @FXML
    private void handleEditarItem() {
        Item itemSeleccionado = itemTable.getSelectionModel().getSelectedItem();
        if (itemSeleccionado != null) {
            ItemDialogController dialogController = new ItemDialogController();
            dialogController.setItem(itemSeleccionado);
            Item itemEditado = dialogController.getItem();
            if (itemEditado != null) {
                itemDAO.update(itemEditado);
                itemList.set(itemList.indexOf(itemSeleccionado), itemEditado);
                mostrarAlerta("Item actualizado", "El item se actualizó correctamente en la base de datos.");
            }
        } else {
            mostrarAlerta("Error", "Por favor selecciona un item para editar.");
        }
    }

    /**
     * Muestra una alerta de información con el mensaje y título proporcionado.
     *
     * @param titulo Título de la alerta
     * @param mensaje Mensaje que se mostrará en la alerta
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION, mensaje, ButtonType.OK);
        alert.setTitle(titulo);
        alert.showAndWait();
    }
}