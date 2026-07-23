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
 * @author USER
 */
public class PruebaInsertarLista_precios {
    public static void main(String[] args) throws SQLException {

        Scanner sc = new Scanner(System.in);
        Lista_precios precio = new Lista_precios();
        Lista_preciosDAO dao = new Lista_preciosDAO();
        System.out.print("Ingrese el ID del precio:");
        sc.nextLine();

        System.out.print("Ingrese la descripcion del precio:");
        precio.setdescrip_precio(sc.nextLine());

        boolean resultado = dao.insertarLista_precios(precio);
        if (resultado) {
            System.out.println("\nEl precio se guardo correctamente en VIVEROBD.");
        } else {
            System.out.println("\nNo se pudo guardar el precio.");
        }
    }
}


        
        
        
        