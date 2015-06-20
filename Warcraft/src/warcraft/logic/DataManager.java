package warcraft.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataManager {
    //Class used to save and load objects when saving the game.
    public static boolean saveObject (String pFile, Object objToSave)
    {
        ObjectOutputStream writer;
        try {
            writer = new ObjectOutputStream(new FileOutputStream(pFile));
            writer.writeObject(objToSave);
            writer.close();
            return true;
        } catch (IOException ex) {
            System.out.println("Error reading Serialized object with error \n" + ex);
            return false; 
        }
    }    
    public static Object readObject (String pFile)
    {
        Object obj;
        ObjectInputStream reader;
        try {
            reader = new ObjectInputStream(new FileInputStream(pFile));
            obj = reader.readObject();
            reader.close();
            return obj;
        } catch (IOException | ClassNotFoundException ex) { 
            System.out.println("Error reading Serialized object with error \n" + ex);
            return null; 
        }
    }
}
