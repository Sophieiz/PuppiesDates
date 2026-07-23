/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Aprendiz
 */
public class Clientes {
    private int idClientes;
    private String nombre;
    private String apellido;
    private String documento; 
    private String telefono;
    private String correo;
    private int Tipo_documento_id;
    private int Actividad_idActividad;
    private int Roles_idRoles;
    
    public int getidClientes () {
       return idClientes;
    }
    
    public void setidClientes (int idClientes) {
        this.idClientes = idClientes;
    }
    
    public String getnombre () {
       return nombre;
    }
    
    public void setnombre (String nombre) {
        this.nombre = nombre;
    }
    
    public String getapellido () {
        return apellido;
    }
    
    public void setapellido (String apellido) {
        this.apellido = apellido;
    }
    
    public String getdocumendo () {
    return documento;
    }
    
    public void setdocumento (String documento) {
        this.documento = documento;
    }
    
    public String gettelefono() {
        return telefono;
    }
    
    public void settelefono (String telefono) {
        this.telefono = telefono;
    }
    
    public String getcorreo () {
        return correo;
    }
    
    public void setcorreo (String correo) {
        this.correo = correo;
    }

    public int getTipo_documento_id() {
       return Tipo_documento_id;   
    }
    
    public void setTipo_documento_id() {
        this.Tipo_documento_id = Tipo_documento_id;
    }
    
    public int getActividad_idActividad() {
        return Actividad_idActividad;
    }
    
    public void setActvidad_idActividad() {
        this.Actividad_idActividad = Actividad_idActividad;
    }
    
    public int getRoles_idRoles() {
        return Roles_idRoles;  
    }
    
    public void setRoles_idRoles() {
        this.Roles_idRoles = Roles_idRoles; 
    }

  
}
