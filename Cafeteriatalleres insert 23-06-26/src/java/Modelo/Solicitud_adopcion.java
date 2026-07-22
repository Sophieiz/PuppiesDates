package Modelo;

import java.sql.Timestamp;

public class Solicitud_adopcion {

    private int idSolicitud_adopcion;
    private String direccion;
    private String localidad;
    private String barrio;
    private String profesion;
    private String vive_en;
    private String tipo_vivienda;
    private String nucleo_familiar;
    private boolean tiene_mascotas;
    private Timestamp fecha_solicitud;
    private int Usuarios_idUsuarios;
    private int Perrito_idPerrito;
    private int Estado_solicitud_idEstado_solicitud;

    // Campos de apoyo (no son columnas) para mostrar datos relacionados en JSP sin hacer join manual
    private String nombreUsuario;
    private String apellidoUsuario;
    private String documentoUsuario;
    private String correoUsuario;
    private String nombrePerrito;
    private String descripcionEstado_solicitud;

    public Solicitud_adopcion() {
    }

    public int getIdSolicitud_adopcion() {
        return idSolicitud_adopcion;
    }

    public void setIdSolicitud_adopcion(int idSolicitud_adopcion) {
        this.idSolicitud_adopcion = idSolicitud_adopcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getVive_en() {
        return vive_en;
    }

    public void setVive_en(String vive_en) {
        this.vive_en = vive_en;
    }

    public String getTipo_vivienda() {
        return tipo_vivienda;
    }

    public void setTipo_vivienda(String tipo_vivienda) {
        this.tipo_vivienda = tipo_vivienda;
    }

    public String getNucleo_familiar() {
        return nucleo_familiar;
    }

    public void setNucleo_familiar(String nucleo_familiar) {
        this.nucleo_familiar = nucleo_familiar;
    }

    public boolean isTiene_mascotas() {
        return tiene_mascotas;
    }

    public void setTiene_mascotas(boolean tiene_mascotas) {
        this.tiene_mascotas = tiene_mascotas;
    }

    public Timestamp getFecha_solicitud() {
        return fecha_solicitud;
    }

    public void setFecha_solicitud(Timestamp fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
    }

    public int getUsuarios_idUsuarios() {
        return Usuarios_idUsuarios;
    }

    public void setUsuarios_idUsuarios(int Usuarios_idUsuarios) {
        this.Usuarios_idUsuarios = Usuarios_idUsuarios;
    }

    public int getPerrito_idPerrito() {
        return Perrito_idPerrito;
    }

    public void setPerrito_idPerrito(int Perrito_idPerrito) {
        this.Perrito_idPerrito = Perrito_idPerrito;
    }

    public int getEstado_solicitud_idEstado_solicitud() {
        return Estado_solicitud_idEstado_solicitud;
    }

    public void setEstado_solicitud_idEstado_solicitud(int Estado_solicitud_idEstado_solicitud) {
        this.Estado_solicitud_idEstado_solicitud = Estado_solicitud_idEstado_solicitud;
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

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
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

    public String getDescripcionEstado_solicitud() {
        return descripcionEstado_solicitud;
    }

    public void setDescripcionEstado_solicitud(String descripcionEstado_solicitud) {
        this.descripcionEstado_solicitud = descripcionEstado_solicitud;
    }
}
