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
public class PruebaActualizarLista_precios {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Lista_preciosDAO dao = new Lista_preciosDAO();
        
        System.out.println("\nModificar precio existente");
        System.out.print("Ingrese el ID del precio que desea modificar: ");
        int idMod = sc.nextInt();
        sc.nextLine();

        Lista_precios resAct = new Lista_precios();
        resAct.setidLista_Precios(idMod);

        System.out.print("Ingrese la nueva descripcion de precios: ");
        resAct.setdescrip_precio(sc.nextLine());

        if (dao.actualizarListaPrecios(resAct)) {
            System.out.println(" Los precios ha sido actualizado correctamente.");
        } else {
            System.out.println("Error: No se encontró ningun precio con el ID " + idMod);
        }
       
    }
    
}
