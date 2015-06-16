/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcraft.logic;

import java.util.ArrayList;

/**
 *
 * @author Josué
 */
public class MergeMovesThread extends Thread{
    
    private Integer _Start;
    private Integer _Final;
    private ArrayList<Move> _MovesToMerge;
    private ArrayList<Move> _NewMoves;
    
    MergeMovesThread(Integer pStart, Integer pFinal, ArrayList<Move> pList){
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
                //System.out.println(degree2+"|"+"0"+"|"+currentAction+"|"+value2);
                currentAction = invertAction(currentAction);
                degree2 = degree1;//in case there is only one action
                value2 = value1;
            }
            else{//same action, we have to merge it
                degree2 = (degree1 + _MovesToMerge.get(currentIndex).getDegree())%degree1;//acumulator of degrees
                value2 = (_MovesToMerge.get(currentIndex).getValue() + value1)/2;//acumulator of values
                if (degree2 == 0){//simple solution
                    degree2 = 0.1;
                }
                value1 =  value2;
                degree1 = degree2;
            }
        }
        _NewMoves.add(new Move(degree2,0,currentAction,value2));
    }
    
    private String invertAction(String pAction){
        if (pAction.equals("avanzar")){
            return "disparar";
        }
        else{
            return "avanzar";
        }
    }
    public ArrayList<Move> getProcessedMoves(){
        return _NewMoves;
    }
  
}
