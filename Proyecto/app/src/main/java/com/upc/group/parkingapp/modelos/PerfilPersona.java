package com.upc.group.parkingapp.modelos;

public class PerfilPersona {

    private String id;
    private String personId;
    private String numeroPlaca;
    private String modeloAuto;
    private boolean inicioSesionEmail;
    private boolean inicioSesionSMS;
    private boolean notificacion15MinEmail;
    private boolean notificacion15MinSMS;
    private boolean newsEmail;
    private boolean newsSMS;

    public PerfilPersona() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        this.numeroPlaca = numeroPlaca;
    }

    public String getModeloAuto() {
        return modeloAuto;
    }

    public void setModeloAuto(String modeloAuto) {
        this.modeloAuto = modeloAuto;
    }

    public boolean isInicioSesionEmail() {
        return inicioSesionEmail;
    }

    public void setInicioSesionEmail(boolean inicioSesionEmail) {
        this.inicioSesionEmail = inicioSesionEmail;
    }

    public boolean isInicioSesionSMS() {
        return inicioSesionSMS;
    }

    public void setInicioSesionSMS(boolean inicioSesionSMS) {
        this.inicioSesionSMS = inicioSesionSMS;
    }

    public boolean isNotificacion15MinEmail() {
        return notificacion15MinEmail;
    }

    public void setNotificacion15MinEmail(boolean notificacion15MinEmail) {
        this.notificacion15MinEmail = notificacion15MinEmail;
    }

    public boolean isNotificacion15MinSMS() {
        return notificacion15MinSMS;
    }

    public void setNotificacion15MinSMS(boolean notificacion15MinSMS) {
        this.notificacion15MinSMS = notificacion15MinSMS;
    }

    public boolean isNewsEmail() {
        return newsEmail;
    }

    public void setNewsEmail(boolean newsEmail) {
        this.newsEmail = newsEmail;
    }

    public boolean isNewsSMS() {
        return newsSMS;
    }

    public void setNewsSMS(boolean newsSMS) {
        this.newsSMS = newsSMS;
    }
}
