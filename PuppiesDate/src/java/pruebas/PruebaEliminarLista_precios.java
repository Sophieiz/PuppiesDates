/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;
import Controlador.Lista_preciosDAO;
import Modelo.Lista_precios;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author isabe
 */
public class PruebaEliminarLista_precios {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Lista_preciosDAO dao = new Lista_preciosDAO();
        
        System.out.println("\nEliminar un precio");
        System.out.print("Ingrese el ID de la reserva que va eliminar: ");
        int idEliminar = sc.nextInt();

        
        if (dao.eliminarListaPrecios(idEliminar)) {
            System.out.println("Precio eliminado de VIVEROBD");
        } else {
            System.out.println("No se encontro ningun precio con el ID: " + idEliminar);
        }

        sc.close(); 
 }
    }
    

