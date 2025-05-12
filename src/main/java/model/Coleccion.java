package model;

public class Coleccion {
    private int idColeccion;
    private String nombre;
    private String descripcion;
    private String categoria;
    private Usuario usuario;

    // Constructor vacío
    public Coleccion() {}

    public Coleccion(int idColeccion, String nombre, String descripcion, String categoria, Usuario usuario) {
        this.idColeccion = idColeccion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.usuario = usuario;
    }

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
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
}
