package Modelo;

import java.sql.Date;

public class Perrito {

    private int idPerrito;
    private String nombre;
    private Date fecha_nacimiento;
    private String microchip;
    private String etapa_madurez;
    private String especialidad;
    private String condiciones_especiales;
    private String titulo_historia;
    private String historia;
    private String foto;

    // FK reales de la tabla (antes eran texto libre: especie, raza, sexo)
    private int Especie_idEspecie;
    private int Raza_idRaza;
    private int Sexo_perrito_idSexo_perrito;
    private int Estado_perrito_idEstado_perrito;

    // Campos de apoyo (no son columnas) para mostrar la descripción en JSP sin hacer join manual
    private String descripcionEspecie;
    private String descripcionRaza;
    private String descripcionSexo;
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

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
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

    public int getEspecie_idEspecie() {
        return Especie_idEspecie;
    }

    public void setEspecie_idEspecie(int Especie_idEspecie) {
        this.Especie_idEspecie = Especie_idEspecie;
    }

    public int getRaza_idRaza() {
        return Raza_idRaza;
    }

    public void setRaza_idRaza(int Raza_idRaza) {
        this.Raza_idRaza = Raza_idRaza;
    }

    public int getSexo_perrito_idSexo_perrito() {
        return Sexo_perrito_idSexo_perrito;
    }

    public void setSexo_perrito_idSexo_perrito(int Sexo_perrito_idSexo_perrito) {
        this.Sexo_perrito_idSexo_perrito = Sexo_perrito_idSexo_perrito;
    }

    public int getEstado_perrito_idEstado_perrito() {
        return Estado_perrito_idEstado_perrito;
    }

    public void setEstado_perrito_idEstado_perrito(int Estado_perrito_idEstado_perrito) {
        this.Estado_perrito_idEstado_perrito = Estado_perrito_idEstado_perrito;
    }

    public String getDescripcionEspecie() {
        return descripcionEspecie;
    }

    public void setDescripcionEspecie(String descripcionEspecie) {
        this.descripcionEspecie = descripcionEspecie;
    }

    public String getDescripcionRaza() {
        return descripcionRaza;
    }

    public void setDescripcionRaza(String descripcionRaza) {
        this.descripcionRaza = descripcionRaza;
    }

    public String getDescripcionSexo() {
        return descripcionSexo;
    }

    public void setDescripcionSexo(String descripcionSexo) {
        this.descripcionSexo = descripcionSexo;
    }

    public String getDescripcionEstado_perrito() {
        return descripcionEstado_perrito;
    }

    public void setDescripcionEstado_perrito(String descripcionEstado_perrito) {
        this.descripcionEstado_perrito = descripcionEstado_perrito;
    }
}