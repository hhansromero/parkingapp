package com.upc.group.parkingapp.modelos;

public class Empresa {

    private String id;
    private String ruc;
    private String nombreLocal;
    private String representante;
    private String direccion;
    private int niveles;
    private int totalEst = 0;
    private String horario;
    private boolean termCondiciones = true;
    private String latitud;
    private String longitud;

    public Empresa() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getNiveles() {
        return niveles;
    }

    public void setNiveles(int niveles) {
        this.niveles = niveles;
    }

    public int getTotalEst() {
        return totalEst;
    }

    public void setTotalEst(int totalEst) {
        this.totalEst = totalEst;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public boolean isTermCondiciones() {
        return termCondiciones;
    }

    public void setTermCondiciones(boolean termCondiciones) {
        this.termCondiciones = termCondiciones;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
