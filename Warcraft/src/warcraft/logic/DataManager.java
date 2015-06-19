/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcraft.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Xelop
 */
public class DataManager {
    
    public static boolean grabarObjeto (String file, Object obj)
    {
        ObjectOutputStream salida;
        try {
            salida = new ObjectOutputStream(new FileOutputStream(file));
            //salida.writeObject("guardar un objeto compuesto\n");
            salida.writeObject(obj);
            salida.close();
            return true;
        } catch (IOException ex) {
            System.out.println("Error reading Serialized object with error \n" + ex);
            return false; 
        }
    }    
    public static Object leerObjeto (String file)
    {
        Object obj;
        ObjectInputStream entrada;
        try {
            entrada = new ObjectInputStream(new FileInputStream(file));
            obj = entrada.readObject();
            entrada.close();
            return obj;
        } catch (IOException | ClassNotFoundException ex) { 
            System.out.println("Error reading Serialized object with error \n" + ex);
            return null; 
        }
    }
}
