package model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Item {
    private IntegerProperty idItem;
    private StringProperty nombre;
    private StringProperty descripcion;
    private StringProperty estado;
    private ObjectProperty<LocalDate> fechaAdquisicion;
    private DoubleProperty precio;
    private IntegerProperty idColeccion; // Nueva propiedad

    public Item() {
        this.idItem = new SimpleIntegerProperty();
        this.nombre = new SimpleStringProperty();
        this.descripcion = new SimpleStringProperty();
        this.estado = new SimpleStringProperty();
        this.fechaAdquisicion = new SimpleObjectProperty<>();
        this.precio = new SimpleDoubleProperty();
        this.idColeccion = new SimpleIntegerProperty(); // Inicialización
    }

    // Getters y setters para idItem
    public int getIdItem() {
        return idItem.get();
    }

    public void setIdItem(int idItem) {
        this.idItem.set(idItem);
    }

    public IntegerProperty idItemProperty() {
        return idItem;
    }

    // Getters y setters para nombre
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    // Getters y setters para descripcion
    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    // Getters y setters para estado
    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    // Getters y setters para fechaAdquisicion
    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion.get();
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion.set(fechaAdquisicion);
    }

    public ObjectProperty<LocalDate> fechaAdquisicionProperty() {
        return fechaAdquisicion;
    }

    // Getters y setters para precio
    public double getPrecio() {
        return precio.get();
    }

    public void setPrecio(double precio) {
        this.precio.set(precio);
    }

    public DoubleProperty precioProperty() {
        return precio;
    }

    // Getters y setters para idColeccion
    public int getIdColeccion() {
        return idColeccion.get();
    }

    public void setIdColeccion(int idColeccion) {
        this.idColeccion.set(idColeccion);
    }

    public IntegerProperty idColeccionProperty() {
        return idColeccion;
    }
}