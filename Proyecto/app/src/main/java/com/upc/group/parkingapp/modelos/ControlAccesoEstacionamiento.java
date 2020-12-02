package com.upc.group.parkingapp.modelos;

public class ControlAccesoEstacionamiento {
    private String id;
    private String personaDni;
    private String placa;
    private String marca;
    private String modelo;
    private String fechaIngreso;
    private String fechaSalida;
    private String horaIngreso;
    private String horaSalida;
    private String duracionEstadia;
    private int estado;

    public ControlAccesoEstacionamiento() {
    }

    //OBTENEMOS LA FECHA DE INGRESO Y SALIDA
    @Override
    public String toString() {
        return fechaIngreso+" "+horaIngreso;
    }

    //CREAMOS LOS GETER AND SETERS

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(String horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getPersonaDni() {
        return personaDni;
    }

    public void setPersonaDni(String personaDni) {
        this.personaDni = personaDni;
    }



    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }


}
