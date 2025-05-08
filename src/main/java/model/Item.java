package model;

import java.util.Date;

public class Item {
    private int idItem;
    private String descripcion;
    private String estado;
    private Date fechaAdquisicion;
    private double precio;
    private Coleccion coleccion;

    // Constructor vacío
    public Item() {}

    public Item(int idItem, String descripcion, String estado, Date fechaAdquisicion, double precio, Coleccion coleccion) {
        this.idItem = idItem;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaAdquisicion = fechaAdquisicion;
        this.precio = precio;
        this.coleccion = coleccion;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Coleccion getColeccion() {
        return coleccion;
    }

    public void setColeccion(Coleccion coleccion) {
        this.coleccion = coleccion;
    }
}
