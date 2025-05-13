package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Coleccion {
    private int idColeccion;
    private String nombre;
    private String descripcion;
    private String categoria;
    private Usuario usuario;

    // Propiedades adicionales para compatibilidad con TableView
    private final StringProperty nombreProperty = new SimpleStringProperty();
    private final StringProperty categoriaProperty = new SimpleStringProperty();
    private final StringProperty descripcionProperty = new SimpleStringProperty();

    // Constructor vacío
    public Coleccion() {}

    public Coleccion(int idColeccion, String nombre, String descripcion, String categoria, Usuario usuario) {
        this.idColeccion = idColeccion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.usuario = usuario;

        // Inicializar propiedades
        this.nombreProperty.set(nombre);
        this.categoriaProperty.set(categoria);
        this.descripcionProperty.set(descripcion);
    }

    // Getters y setters existentes
    public int getIdColeccion() {
        return idColeccion;
    }

    public void setIdColeccion(int idColeccion) {
        this.idColeccion = idColeccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.nombreProperty.set(nombre); // Sincronizar propiedad
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        this.descripcionProperty.set(descripcion); // Sincronizar propiedad
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
        this.categoriaProperty.set(categoria); // Sincronizar propiedad
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getIdUsuario() {
        if (usuario != null) {
            return usuario.getIdUsuario();
        }
        return 0; // O manejar el caso donde usuario es null
    }

    public void setIdUsuario(int idUsuario) {
        if (usuario != null) {
            usuario.setIdUsuario(idUsuario);
        } else {
            usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);
        }
    }

    // Métodos para TableView
    public StringProperty nombreProperty() {
        return nombreProperty;
    }

    public StringProperty categoriaProperty() {
        return categoriaProperty;
    }

    public StringProperty descripcionProperty() {
        return descripcionProperty;
    }
}