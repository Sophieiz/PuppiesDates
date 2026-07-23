package Modelo;

import java.sql.Timestamp;

public class Historial_estado_solicitud {

    private int idHistorial_estado_solicitud;
    private Timestamp fecha_cambio;
    private String observacion;
    private int Solicitud_adopcion_idSolicitud_adopcion;
    private int Estado_solicitud_idEstado_solicitud;

    // Campo de apoyo (no es columna) para mostrar el nombre del estado en JSP sin hacer join manual
    private String descripcionEstado_solicitud;

    public Historial_estado_solicitud() {
    }

    public int getIdHistorial_estado_solicitud() {
        return idHistorial_estado_solicitud;
    }

    public void setIdHistorial_estado_solicitud(int idHistorial_estado_solicitud) {
        this.idHistorial_estado_solicitud = idHistorial_estado_solicitud;
    }

    public Timestamp getFecha_cambio() {
        return fecha_cambio;
    }

    public void setFecha_cambio(Timestamp fecha_cambio) {
        this.fecha_cambio = fecha_cambio;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getSolicitud_adopcion_idSolicitud_adopcion() {
        return Solicitud_adopcion_idSolicitud_adopcion;
    }

    public void setSolicitud_adopcion_idSolicitud_adopcion(int Solicitud_adopcion_idSolicitud_adopcion) {
        this.Solicitud_adopcion_idSolicitud_adopcion = Solicitud_adopcion_idSolicitud_adopcion;
    }

    public int getEstado_solicitud_idEstado_solicitud() {
        return Estado_solicitud_idEstado_solicitud;
    }

    public void setEstado_solicitud_idEstado_solicitud(int Estado_solicitud_idEstado_solicitud) {
        this.Estado_solicitud_idEstado_solicitud = Estado_solicitud_idEstado_solicitud;
    }

    public String getDescripcionEstado_solicitud() {
        return descripcionEstado_solicitud;
    }

    public void setDescripcionEstado_solicitud(String descripcionEstado_solicitud) {
        this.descripcionEstado_solicitud = descripcionEstado_solicitud;
    }
}
