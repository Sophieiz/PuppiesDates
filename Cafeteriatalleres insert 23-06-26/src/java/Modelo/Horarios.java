/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.sql.Time;

/**
 *
 * @author USER
 */
public class Horarios {
    
    private int idHorarios;
    private Time hora_ini;
    private Time hora_fin;
   
    public int getidHorarios () {
    return idHorarios;
    }
    
    public void setidHorarios (int idHorarios) {
    this.idHorarios = idHorarios;
    }
    
    public Time gethora_ini () {
    return hora_ini;
    }
    
    public void sethora_ini (Time hora_ini) {
    this.hora_ini = hora_ini;
    }
    
    public Time gethora_fin () {
    return hora_fin;
    }
    
   public void sethora_fin (Time hora_fin) {
    this.hora_fin = hora_fin;
    }
}


    

