package Modelo;

import java.sql.Timestamp;

public class Solicitud_adopcion {

    private int idSolicitud_adopcion;
    private String direccion;
    private int departamentoId;
    private Integer municipioId;
    private Integer localidadId;
    private String barrio;
    private String profesion;
    private Integer viveEnId;
    private Integer tipoViviendaId;
    private String nucleo_familiar;
    private boolean tiene_mascotas;
    private Timestamp fecha_solicitud;
    private int Usuarios_idUsuarios;
    private int Perrito_idPerrito;
    private int Estado_solicitud_idEstado_solicitud;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String documentoUsuario;
    private String correoUsuario;
    private String nombrePerrito;
    private String fotoPerrito;
    private String descripcionEstado_solicitud;
    private String nombreDepartamento;
    private String nombreUbicacion;
    private String descripcionViveEn;
    private String descripcionTipoVivienda;

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

    public int getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(int departamentoId) {
        this.departamentoId = departamentoId;
    }

    public Integer getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Integer municipioId) {
        this.municipioId = municipioId;
    }

    public Integer getLocalidadId() {
        return localidadId;
    }

    public void setLocalidadId(Integer localidadId) {
        this.localidadId = localidadId;
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

    public Integer getViveEnId() {
        return viveEnId;
    }

    public void setViveEnId(Integer viveEnId) {
        this.viveEnId = viveEnId;
    }

    public Integer getTipoViviendaId() {
        return tipoViviendaId;
    }

    public void setTipoViviendaId(Integer tipoViviendaId) {
        this.tipoViviendaId = tipoViviendaId;
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

    public String getFotoPerrito() {
        return fotoPerrito;
    }

    public void setFotoPerrito(String fotoPerrito) {
        this.fotoPerrito = fotoPerrito;
    }

    public String getDescripcionEstado_solicitud() {
        return descripcionEstado_solicitud;
    }

    public void setDescripcionEstado_solicitud(String descripcionEstado_solicitud) {
        this.descripcionEstado_solicitud = descripcionEstado_solicitud;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getNombreUbicacion() {
        return nombreUbicacion;
    }

    public void setNombreUbicacion(String nombreUbicacion) {
        this.nombreUbicacion = nombreUbicacion;
    }

    public String getDescripcionViveEn() {
        return descripcionViveEn;
    }

    public void setDescripcionViveEn(String descripcionViveEn) {
        this.descripcionViveEn = descripcionViveEn;
    }

    public String getDescripcionTipoVivienda() {
        return descripcionTipoVivienda;
    }

    public void setDescripcionTipoVivienda(String descripcionTipoVivienda) {
        this.descripcionTipoVivienda = descripcionTipoVivienda;
    }
}