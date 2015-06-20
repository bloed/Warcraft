package warcraft.logic;

import java.util.ArrayList;
import java.util.Scanner;

public class SelectMovesThread extends Thread{
    //Class used to select the moves for a boat with an specific id. Runs in parallel
    
    public SelectMovesThread(Integer pStart, Integer pFinal , Integer pBoatId){
        _Moves = new ArrayList();
        _Start = pStart;
        _Final = pFinal;
        _BoatId = pBoatId;
    }
    
    @Override
    public void run(){
        //both pStart is inclusive and pFinal is exlusive. This runs in parallel.
        Scanner scanner = TextManager.openReadFile(Constants.FILENAME);
        String currentRecord;
        Integer currentId; 
        if(scanner != null){
            Integer amountToIgnore = _Start;
            while(amountToIgnore != 0){
                scanner.next();//just are read but not processed
                amountToIgnore--;//until we reach pStart
            }
            Integer amountToRead =_Final - _Start;
            while(amountToRead != 0){
                currentRecord = scanner.next();
                currentId = getRecordId(currentRecord);
                if(currentId == _BoatId ||currentId%10 == _BoatId%10){//if same Id, or id that ends wit the same digit
                    _Moves.add(processRecord(currentRecord));
                }
                amountToRead--;
            }
            scanner.close();
        }
    }
    public ArrayList<Move> getProcessedMoves(){
        return _Moves;
    }
    private Integer getRecordId(String pRecord){
        //reveices a string of the form degree|id|action|value
        Integer delimeter = pRecord.indexOf("|");
        pRecord = pRecord.substring(delimeter+1, pRecord.length());//we cut it to reach the id
        delimeter = pRecord.indexOf("|");
        String id = pRecord.substring(0, delimeter);//we cut it to reach the id
        return Integer.parseInt(id);
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
        Double degree = Double.parseDouble(records.get(0));
        Integer id = Integer.parseInt(records.get(1));
        String action = records.get(2);
        Double value = Double.parseDouble(records.get(3));
        return new Move(degree, id, action, value);
    }
    
    private Integer _BoatId;
    private Integer _Start;
    private Integer _Final;
    private ArrayList<Move> _Moves;
}
