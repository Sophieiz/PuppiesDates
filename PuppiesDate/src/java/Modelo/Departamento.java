/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Departamento {

    private int idDepartamento;
    private String nombre;
    private String tipoDivision; // "LOCALIDAD" o "MUNICIPIO"

    public Departamento() {
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDivision() {
        return tipoDivision;
    }

    public void setTipoDivision(String tipoDivision) {
        this.tipoDivision = tipoDivision;
    }
}