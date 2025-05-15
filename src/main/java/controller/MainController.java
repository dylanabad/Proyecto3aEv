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
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.time.LocalDate;
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

    private Usuario usuario;
    private ColeccionDAO coleccionDAO = new ColeccionDAO();
    private ItemDAO itemDAO = new ItemDAO(); // Declaración de itemDAO

    private ObservableList<Item> items = FXCollections.observableArrayList();

    private Usuario usuarioActivo;

    public void setUsuarioActivo(Usuario usuario) {
        this.usuarioActivo = usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioActivo = usuario;
        welcomeLabel.setText("Bienvenido, " + usuario.getNombre());

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
            coleccionSeleccionada = newSelection; // Actualiza la colección seleccionada
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
                nueva.setUsuario(usuario); // Usa el campo usuario
                return nueva;
            }
            return null;
        });

        Optional<Coleccion> result = dialog.showAndWait();

        result.ifPresent(coleccion -> {
            coleccionDAO.save(coleccion); // Cambia insertar por save
            cargarColecciones(); // Refresca la tabla
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
            coleccionesTable.getItems().remove(coleccionSeleccionada); // Elimina la colección de la tabla
            coleccionDAO.delete(coleccionSeleccionada.getIdColeccion()); // Elimina la colección de la base de datos
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
            if (result.isPresent()) {
                System.out.println("Botón presionado: " + result.get());
                if (result.get() == ButtonType.OK) {
                    return controller.getColeccion();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Si se cancela o cierra el diálogo
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