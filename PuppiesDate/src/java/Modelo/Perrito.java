package Modelo;

import java.sql.Date;

public class Perrito {

    private int idPerrito;
    private String nombre;
    private String especie;
    private String raza;
    private Date fecha_nacimiento;
    private String sexo;
    private String microchip;
    private String etapa_madurez;
    private String especialidad;
    private String condiciones_especiales;
    private String titulo_historia;
    private String historia;
    private String foto;
    private String ciudad;
    private int Estado_perrito_idEstado_perrito;

    // Campo de apoyo (no es columna) para mostrar el nombre del estado en JSP sin hacer join manual
    private String descripcionEstado_perrito;

    public Perrito() {
    }

    public int getIdPerrito() {
        return idPerrito;
    }

    public void setIdPerrito(int idPerrito) {
        this.idPerrito = idPerrito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getMicrochip() {
        return microchip;
    }

    public void setMicrochip(String microchip) {
        this.microchip = microchip;
    }

    public String getEtapa_madurez() {
        return etapa_madurez;
    }

    public void setEtapa_madurez(String etapa_madurez) {
        this.etapa_madurez = etapa_madurez;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCondiciones_especiales() {
        return condiciones_especiales;
    }

    public void setCondiciones_especiales(String condiciones_especiales) {
        this.condiciones_especiales = condiciones_especiales;
    }

    public String getTitulo_historia() {
        return titulo_historia;
    }

    public void setTitulo_historia(String titulo_historia) {
        this.titulo_historia = titulo_historia;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getEstado_perrito_idEstado_perrito() {
        return Estado_perrito_idEstado_perrito;
    }

    public void setEstado_perrito_idEstado_perrito(int Estado_perrito_idEstado_perrito) {
        this.Estado_perrito_idEstado_perrito = Estado_perrito_idEstado_perrito;
    }

    public String getDescripcionEstado_perrito() {
        return descripcionEstado_perrito;
    }

    public void setDescripcionEstado_perrito(String descripcionEstado_perrito) {
        this.descripcionEstado_perrito = descripcionEstado_perrito;
    }
}
