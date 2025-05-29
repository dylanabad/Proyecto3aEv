package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.List;

public class Coleccion {
    private Integer idColeccion;
    private SimpleStringProperty nombre;
    private SimpleStringProperty descripcion;
    private SimpleStringProperty categoria;
    private Usuario usuario; // Relación con Usuario
    private List<Item> items; // Relación con Item
    private SimpleIntegerProperty idUsuario; // Propiedad observable para idUsuario

    public Coleccion() {
        this.nombre = new SimpleStringProperty();
        this.descripcion = new SimpleStringProperty();
        this.categoria = new SimpleStringProperty();
        this.idUsuario = new SimpleIntegerProperty(); // Inicialización de idUsuario
    }

    public Coleccion(Integer idColeccion, String nombre, String descripcion, String categoria, Usuario usuario) {
        this.idColeccion = idColeccion;
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.categoria = new SimpleStringProperty(categoria);
        this.usuario = usuario;
        this.idUsuario = new SimpleIntegerProperty(usuario != null ? usuario.getIdUsuario() : null); // Asignación condicional
    }

    public Integer getIdColeccion() {
        return idColeccion;
    }

    public void setIdColeccion(Integer idColeccion) {
        this.idColeccion = idColeccion;
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

    public String getCategoria() {
        return categoria.get();
    }

    public void setCategoria(String categoria) {
        this.categoria.set(categoria);
    }

    public SimpleStringProperty categoriaProperty() {
        return categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getIdUsuario() {
        return idUsuario.get();
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario.set(idUsuario);
    }

    public SimpleIntegerProperty idUsuarioProperty() {
        return idUsuario;
    }
}