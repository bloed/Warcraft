package warcraft.logic;

import java.util.ArrayList;

public class MergeMovesThread extends Thread{
    //This class is in charge of merge the moves of the strategy of the boats. Runs in parrallel.
    public MergeMovesThread(Integer pStart, Integer pFinal, ArrayList<Move> pList){
        _NewMoves = new ArrayList();
        _Start = pStart;
        _Final = pFinal;
        _MovesToMerge = pList;
    }
    @Override
    public void run(){
        //both pStart is inclusive and pFinal is exlusive. This runs in parallel. 
        String currentAction = _MovesToMerge.get(0).getAction();
        Double degree1 = _MovesToMerge.get(0).getDegree();
        Double value1 = _MovesToMerge.get(0).getValue();
        Double degree2 = degree1;//in case there is only one action
        Double value2 = value1;
        for(int currentIndex = _Start + 1 ; currentIndex < _Final; currentIndex++){
            if(!_MovesToMerge.get(currentIndex).getAction().equals(currentAction)){
                degree1 = _MovesToMerge.get(currentIndex).getDegree();
                value1 = _MovesToMerge.get(currentIndex).getValue();
                _NewMoves.add(new Move(degree2,0,currentAction,value2));
                currentAction = invertAction(currentAction);
                degree2 = degree1;//in case there is only one action
                value2 = value1;
            }
            else{//same action, we have to merge it
                degree2 = (degree1 + _MovesToMerge.get(currentIndex).getDegree())%_MovesToMerge.get(currentIndex).getDegree();//acumulator of degrees
                value2 = (_MovesToMerge.get(currentIndex).getValue() + value1)/2;//acumulator of values
                value1 =  value2;
                degree1 = degree2;
            }
        }
        _NewMoves.add(new Move(degree2,0,currentAction,value2));
    }
    public ArrayList<Move> getProcessedMoves(){
        return _NewMoves;
    }
    private String invertAction(String pAction){
        if (pAction.equals("avanzar")){
            return "disparar";
        }
        else{
            return "avanzar";
        }
    }
    
    private Integer _Start;
    private Integer _Final;
    private ArrayList<Move> _MovesToMerge;
    private ArrayList<Move> _NewMoves;
}
