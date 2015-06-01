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
    public void createStrategy(){
        //creates the txt with the 200 000 registers of possible moves
        PrintWriter writer  = createFile(Constants.FILENAME);
        if(writer!=null){
            for(int counter = 0; counter < Constants.AMOUNT_OF_REGISTERS ; counter++){
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
    private Scanner openFile(String pFileName){
        try{
            Scanner scanner = new Scanner(new File(pFileName));
            return scanner;
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Move> selectMovesForBoat(Integer pBoatId, Integer pStart, Integer pFinal){
        //both pStart is inclusive and pFinal is exlusive
        Scanner scanner = openFile(Constants.FILENAME);
        String currentRecord;
        if(scanner != null){
            Integer amountToIgnore = pStart;
            while(amountToIgnore != 0){
                scanner.next();
                amountToIgnore--;
            }
            Integer amountToRead =pFinal - pStart;
            while(amountToRead != 0){
                currentRecord = scanner.next();
                //ver si coincide con id
                //agregarlo a la lista
                amountToRead--;
            }
            scanner.close();
        }
        return null;
    }
    private Move processRecord(String pRecord){
        //receives a record of the form : degree|id|action|value
        ArrayList<String> records = new ArrayList();//[0] = degree , [1] = id, [2] = action, [3] = value
        Integer delimeter;
        String currentAttribute;
        Integer cutString;//used to know where tu cut the string
        while(!pRecord.isEmpty()){
            delimeter = pRecord.indexOf("|");
            cutString = delimeter + 1;
            if (delimeter == -1){
                delimeter = pRecord.length();//when reading valule
                cutString = pRecord.length();
            }
            currentAttribute = pRecord.substring(0, delimeter);
            pRecord =  pRecord.substring(cutString, pRecord.length());
            records.add(currentAttribute);
        }
        return new Move(Double.parseDouble(records.get(0)),Integer.parseInt(records.get(1)),
                        records.get(2),Double.parseDouble(records.get(3)));
    }
    
    private Integer getRecordId(String pRecord){
        Integer delimeter = pRecord.indexOf("|");
        pRecord = pRecord.substring(delimeter+1, pRecord.length());
        delimeter = pRecord.indexOf("|");
        String id = pRecord.substring(0, delimeter);
        System.out.println(id);
        return Integer.parseInt(id);
    }
}
/*Scanner scanner = openFile(Constants.FILENAME);
        if(scanner != null){
            while(scanner.hasNext()){
                JOptionPane.showMessageDialog(null, scanner.next());
            }
        }
        scanner.close();
//System.out.println(Runtime.getRuntime().availableProcessors());
*/