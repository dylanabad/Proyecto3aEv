package model;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.util.List;

public class Item {
    private Integer idItem;
    private SimpleStringProperty nombre;
    private SimpleStringProperty descripcion;
    private SimpleStringProperty estado;
    private ObjectProperty<LocalDate> fechaAdquisicion;
    private SimpleDoubleProperty precio;
    private Coleccion coleccion;
    private List<Etiqueta> etiquetas;
    private SimpleIntegerProperty idColeccion; // Propiedad observable para idColeccion

    public Item() {
        this.nombre = new SimpleStringProperty();
        this.descripcion = new SimpleStringProperty();
        this.estado = new SimpleStringProperty();
        this.fechaAdquisicion = new SimpleObjectProperty<>();
        this.precio = new SimpleDoubleProperty();
        this.idColeccion = new SimpleIntegerProperty(); // Inicialización de idColeccion
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public SimpleStringProperty descripcionProperty() {
        return descripcion;
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public SimpleStringProperty estadoProperty() {
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

    public Double getPrecio() {
        return precio.get();
    }

    public void setPrecio(Double precio) {
        this.precio.set(precio);
    }

    public SimpleDoubleProperty precioProperty() {
        return precio;
    }

    public Coleccion getColeccion() {
        return coleccion;
    }

    public void setColeccion(Coleccion coleccion) {
        this.coleccion = coleccion;
    }

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public int getIdColeccion() {
        return idColeccion.get();
    }

    public void setIdColeccion(int idColeccion) {
        this.idColeccion.set(idColeccion);
    }

    public SimpleIntegerProperty idColeccionProperty() {
        return idColeccion;
    }
}