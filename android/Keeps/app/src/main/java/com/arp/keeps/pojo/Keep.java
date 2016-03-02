package com.arp.keeps.pojo;

/**
 * Created by Alex on 10/02/2016.
 */
public class Keep {
    private long id;
    private String contenido;
    private boolean estado;

    public Keep() {
    }

    public Keep(long id, String contenido, boolean estado) {
        this.id = id;
        this.contenido = contenido;
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Keep{" +
                "id=" + id +
                ", contenido='" + contenido + '\'' +
                ", estado=" + estado +
                '}';
    }
}
