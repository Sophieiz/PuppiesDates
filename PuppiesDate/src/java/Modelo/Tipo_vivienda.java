/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Tipo_vivienda {

    private int idTipo_vivienda;
    private String descripcion;
    private boolean activo;

    public Tipo_vivienda() {
    }

    public int getIdTipo_vivienda() {
        return idTipo_vivienda;
    }

    public void setIdTipo_vivienda(int idTipo_vivienda) {
        this.idTipo_vivienda = idTipo_vivienda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
