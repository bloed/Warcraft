package warcraft.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class TextManager {
    private static TextManager _Instance = null;
    private final String _Moves[];
    
  
    private TextManager(){
        _Moves = new String[] {"avanzar","disparar"};
    }   
    public static TextManager getInstance(){
        if (_Instance == null){
            _Instance = new TextManager();
        }
        return _Instance;
    }    
    public void generateTXTStrategy(){
        //creates the txt with the 200 000 registers of possible moves
        PrintWriter writer  = createFile(Constants.FILENAME);
        if(writer!=null){
            for(int counter = 0; counter < Constants.AMOUNT_OF_REGISTERS ; counter++){
                double degree = (double)Utility.generateRand(Constants.MIN_DEGREE, Constants.MAX_DEGREE)/100;//we need fractions
                Integer id = Utility.generateRand(Constants.MIN_ID, Constants.MAX_ID);
                Integer intAction = Utility.generateRand(0, _Moves.length);
                String stringAction = _Moves[intAction];
                Double value;
                if(intAction == 0){//advance
                    value = (double)Utility.generateRand(Constants.MIN_VALUE_PIXELS, Constants.MAX_VALUE_PIXELS);
                }
                else{//shoot
                    value = (double)Utility.generateRand(Constants.MIN_VALUE_TIME, Constants.MAX_VALUE_TIME)/10;//we need fractions
                }
                String record = degree + "|" + id + "|" + stringAction + "|" + value;
                addRecord(writer , record);        
            }
            closeFile(writer);
            JOptionPane.showMessageDialog(null,"Moves.txt is finished!");
        }
    }
    
    public static PrintWriter createFile(String pFileName){
        //it replaces if there already exists a file with that name
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(pFileName); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return writer;
    }
    public static PrintWriter openWriteFile(String pFileName){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(pFileName, true)); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return writer;
    }
    public static void addRecord(PrintWriter pWriter, String pRecord){
        pWriter.println(pRecord);
    }
    public static void closeFile(PrintWriter pWriter){
        pWriter.close();
    } 
    public static Scanner openReadFile(String pFileName){
        try{
            Scanner scanner = new Scanner(new File(pFileName));
            return scanner;
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static File[] getAllFilenames(String pDirectory){
        File folder = new File(pDirectory);
        File[] listOfFiles = folder.listFiles();
        return listOfFiles;
    }
}