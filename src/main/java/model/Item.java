package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        this.idColeccion = new SimpleIntegerProperty();

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


    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }


    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }


    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public StringProperty estadoProperty() {
        return estado;
    }


    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion.get();
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion.set(fechaAdquisicion);
    }

    public ObjectProperty<LocalDate> fechaAdquisicionProperty() {
        return fechaAdquisicion;
    }


    public double getPrecio() {
        return precio.get();
    }

    public void setPrecio(double precio) {
        this.precio.set(precio);
    }

    public DoubleProperty precioProperty() {
        return precio;
    }


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