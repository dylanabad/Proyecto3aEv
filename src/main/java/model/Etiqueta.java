package model;

import java.util.Objects;

public class Etiqueta {
    private int idEtiqueta;
    private String nombre;

    // Constructor vacío
    public Etiqueta() {}

    // Constructor con parámetros
    public Etiqueta(int idEtiqueta, String nombre) {
        this.idEtiqueta = idEtiqueta;
        this.nombre = nombre;
    }

    // Getters y setters
    public int getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(int idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Etiqueta etiqueta = (Etiqueta) o;
        return idEtiqueta == etiqueta.idEtiqueta && Objects.equals(nombre, etiqueta.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEtiqueta, nombre);
    }

    // Método toString para representación en texto
    @Override
    public String toString() {
        return nombre;
    }
}