package controller;

import dao.ColeccionDAO;
import dao.ItemDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Coleccion;
import model.Item;
import model.Usuario;

import java.time.LocalDate;
import java.util.List;

public class MainController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableView<Coleccion> coleccionesTable;
    @FXML
    private TableColumn<Coleccion, String> nombreCol;
    @FXML
    private TableColumn<Coleccion, String> categoriaCol;
    @FXML
    private TableColumn<Coleccion, String> descripcionCol;

    @FXML
    private TableView<Item> itemsTable;
    @FXML
    private TableColumn<Item, String> nombreItemCol;
    @FXML
    private TableColumn<Item, String> descripcionItemCol;
    @FXML
    private TableColumn<Item, String> estadoColumn;
    @FXML
    private TableColumn<Item, LocalDate> fechaAdquisicionColumn;
    @FXML
    private TableColumn<Item, Double> precioColumn;
    @FXML
    private TableColumn<Item, Integer> idColeccionColumn;

    private Usuario usuario;
    private ColeccionDAO coleccionDAO = new ColeccionDAO();
    private ItemDAO itemDAO = new ItemDAO(); // Declaración de itemDAO

    private ObservableList<Item> items = FXCollections.observableArrayList();

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        welcomeLabel.setText("Bienvenido, " + usuario.getNombre());

        // Cargar colecciones
        List<Coleccion> lista = coleccionDAO.findByUsuarioId(usuario.getIdUsuario());
        ObservableList<Coleccion> colecciones = FXCollections.observableArrayList(lista);
        coleccionesTable.setItems(colecciones);

        nombreCol.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        categoriaCol.setCellValueFactory(cellData -> cellData.getValue().categoriaProperty());
        descripcionCol.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
    }

    @FXML
    private void initialize() {
        // Configurar columnas de ítems
        nombreItemCol.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        descripcionItemCol.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        estadoColumn.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
        fechaAdquisicionColumn.setCellValueFactory(cellData -> cellData.getValue().fechaAdquisicionProperty());
        precioColumn.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());
        idColeccionColumn.setCellValueFactory(cellData -> cellData.getValue().idColeccionProperty().asObject());

        // Detectar selección de colección
        coleccionesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cargarItems(newSelection);
            }
        });
    }

    private void cargarItems(Coleccion coleccion) {
        items.clear();
        items.addAll(itemDAO.findByColeccionId(coleccion.getIdColeccion()));
        itemsTable.setItems(items);
    }

    @FXML
    private void handleAddItem() {
        Item nuevoItem = mostrarDialogoItem(null);
        if (nuevoItem != null) {
            itemDAO.save(nuevoItem); // Guarda el ítem en la base de datos
            items.add(nuevoItem); // Añade el ítem a la lista observable
        }
    }

    @FXML
    private void handleEditItem() {
        Item itemSeleccionado = itemsTable.getSelectionModel().getSelectedItem();
        if (itemSeleccionado != null) {
            Item itemEditado = mostrarDialogoItem(itemSeleccionado);
            if (itemEditado != null) {
                itemDAO.update(itemEditado);
                itemsTable.refresh();
            }
        }
    }

    @FXML
    private void handleDeleteItem() {
        Item itemSeleccionado = itemsTable.getSelectionModel().getSelectedItem();
        if (itemSeleccionado != null) {
            items.remove(itemSeleccionado);
            itemDAO.delete(itemSeleccionado.getIdItem());
        }
    }

    @FXML
    private void handleAddColeccion() {
        Coleccion nuevaColeccion = mostrarDialogoColeccion(null);
        if (nuevaColeccion != null) {
            coleccionDAO.save(nuevaColeccion);
            coleccionesTable.getItems().add(nuevaColeccion);
        }
    }

    @FXML
    private void handleEditColeccion() {
        Coleccion coleccionSeleccionada = coleccionesTable.getSelectionModel().getSelectedItem();
        if (coleccionSeleccionada != null) {
            Coleccion coleccionEditada = mostrarDialogoColeccion(coleccionSeleccionada);
            if (coleccionEditada != null) {
                coleccionDAO.update(coleccionEditada);
                coleccionesTable.refresh();
            }
        } else {
            mostrarAlerta("Por favor, selecciona una colección para editar.");
        }
    }

    @FXML
    private void handleDeleteColeccion() {
        Coleccion coleccionSeleccionada = coleccionesTable.getSelectionModel().getSelectedItem();
        if (coleccionSeleccionada != null) {
            coleccionesTable.getItems().remove(coleccionSeleccionada); // Elimina la colección de la tabla
            coleccionDAO.delete(coleccionSeleccionada.getIdColeccion()); // Elimina la colección de la base de datos
        } else {
            mostrarAlerta("Por favor, selecciona una colección para eliminar.");
        }
    }

    private Coleccion mostrarDialogoColeccion(Coleccion coleccion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyecto3aev/coleccionDialog.fxml"));
            DialogPane dialogPane = loader.load();

            ColeccionDialogController dialogController = loader.getController();
            dialogController.setColeccion(coleccion); // Configura la colección si no es null

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle(coleccion == null ? "Agregar Colección" : "Editar Colección");

            ButtonType result = dialog.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.OK) {
                return dialogController.getColeccion();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private Item mostrarDialogoItem(Item item) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyecto3aev/itemDialog.fxml"));
            DialogPane dialogPane = loader.load();

            ItemDialogController controller = loader.getController();
            controller.setItem(item); // Configura el ítem si no es null

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle(item == null ? "Agregar Ítem" : "Editar Ítem");

            ButtonType result = dialog.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.OK) {
                return controller.getItem();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}