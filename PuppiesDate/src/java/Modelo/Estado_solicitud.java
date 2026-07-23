package Modelo;

public class Estado_solicitud {

    private int idEstado_solicitud;
    private String descripcion_estado;

    public Estado_solicitud() {
    }

    public int getIdEstado_solicitud() {
        return idEstado_solicitud;
    }

    public void setIdEstado_solicitud(int idEstado_solicitud) {
        this.idEstado_solicitud = idEstado_solicitud;
    }

    public String getDescripcion_estado() {
        return descripcion_estado;
    }

    public void setDescripcion_estado(String descripcion_estado) {
        this.descripcion_estado = descripcion_estado;
    }
}
