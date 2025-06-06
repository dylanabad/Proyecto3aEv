package controller;

import dao.ColeccionDAO;
import dao.EtiquetaDAO;
import dao.ItemDAO;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Coleccion;
import model.Etiqueta;
import model.Item;
import model.Usuario;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    private Coleccion coleccionSeleccionada;

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
    @FXML
    private Usuario usuario;
    private ColeccionDAO coleccionDAO = new ColeccionDAO();
    private ItemDAO itemDAO = new ItemDAO();

    private ObservableList<Item> items = FXCollections.observableArrayList();
    private ObservableList<Etiqueta> etiquetasDelItem = FXCollections.observableArrayList();

    private Usuario usuarioActivo;

    public void setUsuarioActivo(Usuario usuario) {
        this.usuarioActivo = usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioActivo = usuario;
        welcomeLabel.setText("Bienvenid@ a MyCollections, " + usuario.getNombre());

        List<Coleccion> lista = coleccionDAO.findByUsuarioId(usuario.getIdUsuario());
        ObservableList<Coleccion> colecciones = FXCollections.observableArrayList(lista);
        coleccionesTable.setItems(colecciones);

        nombreCol.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        categoriaCol.setCellValueFactory(cellData -> cellData.getValue().categoriaProperty());
        descripcionCol.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
    }

    @FXML
    private void initialize() {
        nombreItemCol.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        descripcionItemCol.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        estadoColumn.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
        fechaAdquisicionColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(
                () -> cellData.getValue().getFechaAdquisicion(),
                cellData.getValue().fechaAdquisicionProperty()));
        precioColumn.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());
        idColeccionColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(
                () -> cellData.getValue().getIdColeccion(),
                cellData.getValue().idColeccionProperty()));

        coleccionesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            coleccionSeleccionada = newSelection;
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
        Coleccion coleccionSeleccionada = coleccionesTable.getSelectionModel().getSelectedItem();
        if (coleccionSeleccionada == null) {
            System.out.println("Debes seleccionar una colección primero.");
            return;
        }

        Item nuevoItem = mostrarDialogoItem(null);
        if (nuevoItem != null) {
            nuevoItem.setIdColeccion(coleccionSeleccionada.getIdColeccion());

            try {
                itemDAO.save(nuevoItem);
                items.add(nuevoItem);
                System.out.println("Ítem agregado: " + nuevoItem.getNombre());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al guardar el ítem.");
            }
        } else {
            System.out.println("Diálogo cancelado o cerrado sin crear ítem.");
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
        Dialog<Coleccion> dialog = new Dialog<>();
        dialog.setTitle("Agregar Colección");

        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        TextField nombreField = new TextField();
        TextField categoriaField = new TextField();
        TextArea descripcionArea = new TextArea();

        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(new Label("Categoría:"), 0, 1);
        grid.add(categoriaField, 1, 1);
        grid.add(new Label("Descripción:"), 0, 2);
        grid.add(descripcionArea, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {
                Coleccion nueva = new Coleccion();
                nueva.setNombre(nombreField.getText());
                nueva.setCategoria(categoriaField.getText());
                nueva.setDescripcion(descripcionArea.getText());
                nueva.setUsuario(usuario);
                nueva.setIdUsuario(usuario.getIdUsuario()); // <-- AÑADE ESTA LÍNEA
                return nueva;
            }
            return null;
        });

        Optional<Coleccion> result = dialog.showAndWait();

        result.ifPresent(coleccion -> {
            coleccionDAO.save(coleccion);
            cargarColecciones();
        });
    }

    @FXML
    private void handleEditColeccion() {
        Coleccion coleccionSeleccionada = coleccionesTable.getSelectionModel().getSelectedItem();

        if (coleccionSeleccionada != null) {
            Coleccion coleccionEditada = mostrarDialogoColeccion(coleccionSeleccionada);

            if (coleccionEditada != null) {
                coleccionEditada.setIdColeccion(coleccionSeleccionada.getIdColeccion());
                coleccionEditada.setUsuario(coleccionSeleccionada.getUsuario());

                boolean updated = ColeccionDAO.updateColeccion(coleccionEditada);

                if (updated) {
                    int index = coleccionesTable.getItems().indexOf(coleccionSeleccionada);
                    coleccionesTable.getItems().set(index, coleccionEditada);
                    coleccionesTable.refresh();
                } else {
                    mostrarAlerta("No se pudo actualizar la colección en la base de datos.");
                }
            } else {
                System.out.println("El diálogo de edición fue cancelado o cerrado.");
            }
        } else {
            mostrarAlerta("Por favor, selecciona una colección para editar.");
        }
    }

    @FXML
    private void handleDeleteColeccion() {
        Coleccion coleccionSeleccionada = coleccionesTable.getSelectionModel().getSelectedItem();
        if (coleccionSeleccionada != null) {
            coleccionesTable.getItems().remove(coleccionSeleccionada);
            coleccionDAO.delete(coleccionSeleccionada.getIdColeccion());
        } else {
            mostrarAlerta("Por favor, selecciona una colección para eliminar.");
        }
    }

    private void cargarColecciones() {
        List<Coleccion> lista = coleccionDAO.findByUsuarioId(usuario.getIdUsuario());
        ObservableList<Coleccion> colecciones = FXCollections.observableArrayList(lista);
        coleccionesTable.setItems(colecciones);
    }

    public Coleccion mostrarDialogoColeccion(Coleccion coleccion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyecto3aev/coleccionDialog.fxml"));
            DialogPane dialogPane = loader.load();

            ColeccionDialogController controller = loader.getController();
            controller.setColeccion(coleccion);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Editar Colección");

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                return controller.getColeccion();
            }
        } catch (IOException e) {
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Item mostrarDialogoItem(Item item) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyecto3aev/itemDialog.fxml"));
            DialogPane dialogPane = loader.load();

            ItemDialogController controller = loader.getController();
            controller.setItem(item);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle(item == null ? "Agregar Ítem" : "Editar Ítem");

            ButtonType result = dialog.showAndWait().orElse(ButtonType.CANCEL);

            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                return controller.getItem();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void handleAddEtiquetaToItem() {
        Item selectedItem = itemsTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Selecciona un ítem", "Por favor, selecciona un ítem para añadir una etiqueta.");
            return;
        }

        List<Etiqueta> etiquetas = EtiquetaDAO.findAll();
        ChoiceDialog<Etiqueta> dialog = new ChoiceDialog<>(null, etiquetas);
        dialog.setTitle("Añadir Etiqueta");
        dialog.setHeaderText("Selecciona una etiqueta para añadir al ítem");
        dialog.setContentText("Etiqueta:");

        Optional<Etiqueta> result = dialog.showAndWait();
        result.ifPresent(etiqueta -> {
            try {
                EtiquetaDAO.addEtiquetaToItem(selectedItem.getIdItem(), etiqueta.getIdEtiqueta());
                etiquetasDelItem.add(etiqueta);
                showAlert("Éxito", "Etiqueta añadida correctamente al ítem.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "No se pudo añadir la etiqueta al ítem.");
            }
        });
    }
}