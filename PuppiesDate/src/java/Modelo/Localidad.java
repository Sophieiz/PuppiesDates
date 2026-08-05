/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Localidad {
    private int idLocalidad;
    private String nombre;
    private int departamento_idDepartamento;
    private boolean activo;

    public Localidad() {
    }

    public int getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(int idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDepartamento_idDepartamento() {
        return departamento_idDepartamento;
    }

    public void setDepartamento_idDepartamento(int departamento_idDepartamento) {
        this.departamento_idDepartamento = departamento_idDepartamento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}