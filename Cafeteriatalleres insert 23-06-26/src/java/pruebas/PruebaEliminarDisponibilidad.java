/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import Controlador.DisponibilidadDAO;
import java.util.Scanner;
import Modelo.Disponibilidad;
import java.sql.SQLException;

/**
 *
 * @author isabe
 */
public class PruebaEliminarDisponibilidad {

 
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        DisponibilidadDAO dao = new DisponibilidadDAO();
        
        System.out.print("\nID a eliminar: ");
        if (dao.eliminarDisponibilidad(sc.nextInt())) System.out.println("Eliminado");
    }
}
        
        
      

