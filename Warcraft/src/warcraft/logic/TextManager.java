package warcraft.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


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
            System.out.println("Moves.txt is finished!");
        }
    } 
    private PrintWriter createFile(String pFileName){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(pFileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return writer;
    }
    private void addRecord(PrintWriter pWriter, String pRecord){
        pWriter.println(pRecord);
    }
    private void closeFile(PrintWriter pWriter){
        pWriter.close();
    } 
    public static Scanner openFile(String pFileName){
        try{
            Scanner scanner = new Scanner(new File(pFileName));
            return scanner;
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}