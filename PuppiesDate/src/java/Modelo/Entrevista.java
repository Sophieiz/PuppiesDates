/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Date;
import java.sql.Time;

public class Entrevista {

    private int idEntrevista;
    private Date fecha;
    private Time hora;
    private String observaciones;
    private int Solicitud_adopcion_idSolicitud_adopcion;
    private boolean activo;

    // Campos de apoyo (no son columnas) para mostrar datos sin hacer join manual en el JSP
    private String nombreUsuario;
    private String apellidoUsuario;
    private String correoUsuario;
    private String nombrePerrito;

    public Entrevista() {
    }

    public int getIdEntrevista() {
        return idEntrevista;
    }

    public void setIdEntrevista(int idEntrevista) {
        this.idEntrevista = idEntrevista;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getSolicitud_adopcion_idSolicitud_adopcion() {
        return Solicitud_adopcion_idSolicitud_adopcion;
    }

    public void setSolicitud_adopcion_idSolicitud_adopcion(int Solicitud_adopcion_idSolicitud_adopcion) {
        this.Solicitud_adopcion_idSolicitud_adopcion = Solicitud_adopcion_idSolicitud_adopcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getNombrePerrito() {
        return nombrePerrito;
    }

    public void setNombrePerrito(String nombrePerrito) {
        this.nombrePerrito = nombrePerrito;
    }
}