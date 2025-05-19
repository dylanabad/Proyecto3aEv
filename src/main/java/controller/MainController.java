// Controlador principal que maneja las acciones de la interfaz principal.
// Permite agregar, editar y eliminar colecciones, además de cargar los datos en la tabla.

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

    // Métodos para establecer el usuario
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


    // Inicialización del controlador
    @FXML
    private void initialize() {
        nombreItemCol.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        descripcionItemCol.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        estadoColumn.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
        fechaAdquisicionColumn.setCellValueFactory(cellData -> cellData.getValue().fechaAdquisicionProperty());
        precioColumn.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());
        idColeccionColumn.setCellValueFactory(cellData -> cellData.getValue().idColeccionProperty().asObject());


        coleccionesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            coleccionSeleccionada = newSelection; // Actualiza la colección seleccionada
            if (newSelection != null) {
                cargarItems(newSelection);
            }
        });
    }

    // Carga los ítems de la colección seleccionada
    private void cargarItems(Coleccion coleccion) {
        items.clear();
        items.addAll(itemDAO.findByColeccionId(coleccion.getIdColeccion()));
        itemsTable.setItems(items);
    }

    // Manejar acción de agregar ítem
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

    // Manejar acción de editar ítem
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
    // Manejar acción de eliminar ítem
    @FXML
    private void handleDeleteItem() {
        Item itemSeleccionado = itemsTable.getSelectionModel().getSelectedItem();
        if (itemSeleccionado != null) {
            items.remove(itemSeleccionado);
            itemDAO.delete(itemSeleccionado.getIdItem());
        }
    }

    // Manejar acción de agregar colección
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


    //Manejar acción de editar colección
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

    // Manejar acción de eliminar colección
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

    //Recarga la tabla de colecciones del usuario
    private void cargarColecciones() {
        List<Coleccion> lista = coleccionDAO.findByUsuarioId(usuario.getIdUsuario());
        ObservableList<Coleccion> colecciones = FXCollections.observableArrayList(lista);
        coleccionesTable.setItems(colecciones);

    }

    //Muestra el diálogo para editar o agregar una colección
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
            if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE)  {
                return controller.getColeccion();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Myestra una alerta de advertencia al usuario
    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    //Muestra el diálogo para editar o agregar un ítem
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

            // Cambia la comparación:
            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                return controller.getItem();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}