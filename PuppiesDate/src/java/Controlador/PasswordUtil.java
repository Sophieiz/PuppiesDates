/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

  
    public static String hashPassword(String claveTextoPlano) {
        return BCrypt.hashpw(claveTextoPlano, BCrypt.gensalt(12));
    }


    public static boolean verificarPassword(String claveTextoPlano, String hashGuardado) {
        try {
            return BCrypt.checkpw(claveTextoPlano, hashGuardado);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
