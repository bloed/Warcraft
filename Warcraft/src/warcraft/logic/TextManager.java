package warcraft.logic;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
    public void createStrategy(){
        //creates the txt with the 200 000 registers of possible moves
        String filename = "moves.txt";
        PrintWriter writer  = createFile(filename);
        if(writer!=null){
            for(int counter = 0; counter <= 200000; counter++){
                double degree = (double)Utility.generateRand(0, 100)/100;
                Integer id = Utility.generateRand(100, 1000);
                Integer intAction = Utility.generateRand(0, 2);
                String stringAction = _Moves[intAction];
                Double value;
                if(intAction == 0){//advance
                    value = (double)Utility.generateRand(10, 201);
                }
                else{//shoot
                    value = (double)Utility.generateRand(8, 42)/10;
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
}
