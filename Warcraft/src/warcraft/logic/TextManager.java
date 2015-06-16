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
    private SelectMovesThread[] _SelectThreads;//threads that will select the moves for a boat
    private MergeMovesThread[] _MergeThreads;//theads that will merge the moves of a boat
    
    private TextManager(){
        _Moves = new String[] {"avanzar","disparar"};
        _SelectThreads = new SelectMovesThread[Runtime.getRuntime().availableProcessors()];//depending of the amount of procesor
        _MergeThreads = new MergeMovesThread[Runtime.getRuntime().availableProcessors()];
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
    
    private ArrayList<Move> mainSelectMovesForBoat(Integer pBoatId, Integer pAmountProcesors, Integer pRecordsPerProcesor){
        //divides the 200000 records and send them to execute in parallel
        initializeSelectThreads(pAmountProcesors, pRecordsPerProcesor, pBoatId);
        Utility.waitForAllThreads();//waits for all selection to finish
        ArrayList<Move> allSelectedMoves = joinAnswerSelectThreads(pAmountProcesors);
        /*for (int i = 0; i < allSelectedMoves.size() ; i++){
            System.out.println(allSelectedMoves.get(i).toString());
        }*/
        Integer movesPerMergeThread = allSelectedMoves.size() / pAmountProcesors;
        initializeMergeThreads(pAmountProcesors, movesPerMergeThread, allSelectedMoves);
        Utility.waitForAllThreads();//waits for all merge threads to finish
        ArrayList<Move> finalResult = joinAnswerMergeThreads(pAmountProcesors);
        /*for (int i = 0; i < finalResult.size() ; i++){
            System.out.println(finalResult.get(i).toString());
        }*/
        System.out.println("Se termino de generar para el barco con id de " + pBoatId + " con : " + finalResult.size() + " de movimientos diferentes.");
        return finalResult;
    }
    
    private void initializeSelectThreads(Integer pAmountOfThreads, Integer amountRegistersPerThread, Integer pBoatId){
        Integer start = 0;
        Integer Final = amountRegistersPerThread;
        for(int threadNumber = 0 ; threadNumber < pAmountOfThreads ; threadNumber++){
            _SelectThreads[threadNumber] =  new SelectMovesThread(start,Final,pBoatId);
            start = Final;//so new ranges are set up for the following thread
            Final += amountRegistersPerThread;
            this._SelectThreads[threadNumber].start();
        }
    }
    
    private void initializeMergeThreads(Integer pAmountOfThreads,Integer amountRegistersPerThread, ArrayList<Move> pList){
        Integer start = 0;
        Integer Final = amountRegistersPerThread;
        for(int threadNumber = 0 ; threadNumber < pAmountOfThreads ; threadNumber++){
            _MergeThreads[threadNumber] =  new MergeMovesThread(start, Final, pList);
            start = Final;//so new ranges are set up for the following thread
            Final += amountRegistersPerThread;
            _MergeThreads[threadNumber].start();
        }
    }
    
    private ArrayList<Move> joinAnswerSelectThreads(Integer pAmountOfThreads){
        ArrayList<Move>  result = new ArrayList();
        for(int threadNumber = 0 ; threadNumber < pAmountOfThreads ; threadNumber++){
            result.addAll(_SelectThreads[threadNumber].getProcessedMoves());
        }
        return result;
    }
    private ArrayList<Move> joinAnswerMergeThreads(Integer pAmountOfThreads){
        ArrayList<Move>  result = new ArrayList();
        for(int threadNumber = 0 ; threadNumber < pAmountOfThreads ; threadNumber++){
            result.addAll(_MergeThreads[threadNumber].getProcessedMoves());
        }
        return result;
    }
    
    public void mainStrategy(){
        Integer amountOfProcessors = Runtime.getRuntime().availableProcessors();
        Integer amountOfRecordsPerProcessor = Constants.AMOUNT_OF_REGISTERS / amountOfProcessors;
        System.out.println(amountOfRecordsPerProcessor);
        Integer[] boatsId = Utility.generateBoatsId(amountOfProcessors);
        for(int boatNumber = 0; boatNumber < amountOfProcessors; boatNumber++){
            //generates the strategy for all boats
            mainSelectMovesForBoat(boatsId[boatNumber], amountOfProcessors, amountOfRecordsPerProcessor);
        }
    }
}