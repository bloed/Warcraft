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
                double degree = (double)Utility.generateRand(10, 100)/100;
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
    public ArrayList<Move> mainSelectMovesForBoat(Integer pBoatId){
        //divides the 200000 records and send them to execute in parallel
        Integer amountOfProcessors = Runtime.getRuntime().availableProcessors();
        Integer amountOfRecordsPerProcessor = Constants.AMOUNT_OF_REGISTERS / amountOfProcessors;
        System.out.println(amountOfRecordsPerProcessor);
        return null;
    }
    public ArrayList<Move> selectMovesForBoat(Integer pBoatId, Integer pStart, Integer pFinal){
        //both pStart is inclusive and pFinal is exlusive. This runs in parallel.
        Scanner scanner = openFile(Constants.FILENAME);
        String currentRecord;
        Integer currentId;
        ArrayList<Move> moves = new ArrayList();  
        if(scanner != null){
            Integer amountToIgnore = pStart;
            while(amountToIgnore != 0){
                scanner.next();
                amountToIgnore--;
            }
            Integer amountToRead =pFinal - pStart;
            while(amountToRead != 0){
                currentRecord = scanner.next();
                currentId = getRecordId(currentRecord);
                if(currentId == pBoatId ||currentId%10 == pBoatId%10){//if same Id, or id that ends wit the same digit
                    moves.add(processRecord(currentRecord));
                }
                amountToRead--;
            }
            scanner.close();
        }
        return moves;
    }
    public ArrayList<Move> mergeMovesForBoat(ArrayList<Move> pList, Integer pStart, Integer pFinal){
        //both pStart is inclusive and pFinal is exlusive. This runs in parallel.
        ArrayList<Move> newMoves = new ArrayList();  
        String currentAction = pList.get(0).getAction();
        Double degree1 = pList.get(0).getDegree();
        Double value1 = pList.get(0).getValue();
        Double degree2 = degree1;//in case there is only one action
        Double value2 = value1;
        for(int currentIndex = pStart + 1 ; currentIndex < pFinal; currentIndex++){
            String caca = pList.get(currentIndex).getAction();
            if(!pList.get(currentIndex).getAction().equals(currentAction)){
                degree1 = pList.get(currentIndex).getDegree();
                value1 = pList.get(currentIndex).getValue();
                newMoves.add(new Move(degree2,0,currentAction,value2));
                System.out.println(degree2+"|"+"0"+"|"+currentAction+"|"+value2);
                currentAction = invertAction(currentAction);
                degree2 = degree1;//in case there is only one action
                value2 = value1;
            }
            else{//same action, we have to merge it
                degree2 = (degree1 + pList.get(currentIndex).getDegree())%degree1;//acumulator of degrees
                value2 = (pList.get(currentIndex).getValue() + value1)/2;//acumulator of values
                if (degree2 == 0){
                    degree2 = 0.1;
                }
                value1 =  value2;
                degree1 = degree2;
            }
        }
        newMoves.add(new Move(degree2,0,currentAction,value2));
        System.out.println(degree2+"|"+"0"+"|"+currentAction+"|"+value2);
        return newMoves;
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
        //aclaracion de codigo
        return new Move(Double.parseDouble(records.get(0)),Integer.parseInt(records.get(1)),
                        records.get(2),Double.parseDouble(records.get(3)));
    }
    
    private Integer getRecordId(String pRecord){
        Integer delimeter = pRecord.indexOf("|");
        pRecord = pRecord.substring(delimeter+1, pRecord.length());
        delimeter = pRecord.indexOf("|");
        String id = pRecord.substring(0, delimeter);
        return Integer.parseInt(id);
    }
    private String invertAction(String pAction){
        if (pAction.equals("avanzar")){
            return "disparar";
        }
        else{
            return "avanzar";
        }
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