package Modelo;

import java.sql.Timestamp;

public class Recuperacion_clave {

    private int idRecuperacion_clave;
    private String token;
    private Timestamp fecha_creacion;
    private Timestamp fecha_expiracion;
    private boolean usado;
    private int Usuarios_idUsuarios;

    // Campos de apoyo (no son columnas) para no tener que consultar Usuarios por separado
    private String correoUsuario;
    private String nombreUsuario;

    public Recuperacion_clave() {
    }

    public int getIdRecuperacion_clave() {
        return idRecuperacion_clave;
    }

    public void setIdRecuperacion_clave(int idRecuperacion_clave) {
        this.idRecuperacion_clave = idRecuperacion_clave;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Timestamp fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Timestamp getFecha_expiracion() {
        return fecha_expiracion;
    }

    public void setFecha_expiracion(Timestamp fecha_expiracion) {
        this.fecha_expiracion = fecha_expiracion;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public int getUsuarios_idUsuarios() {
        return Usuarios_idUsuarios;
    }

    public void setUsuarios_idUsuarios(int Usuarios_idUsuarios) {
        this.Usuarios_idUsuarios = Usuarios_idUsuarios;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
