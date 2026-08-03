/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Raza {

    private int idRaza;
    private String nombre;
    private int Especie_idEspecie;

    public Raza() {
    }

    public int getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(int idRaza) {
        this.idRaza = idRaza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEspecie_idEspecie() {
        return Especie_idEspecie;
    }

    public void setEspecie_idEspecie(int Especie_idEspecie) {
        this.Especie_idEspecie = Especie_idEspecie;
    }
}