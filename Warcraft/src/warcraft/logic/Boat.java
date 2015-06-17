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
public class Boat {
    private ArrayList<Move> _Moves;
    private Integer _CurrentMove;
    
    public Boat(ArrayList<Move> pList){
        _Moves = pList;
        _CurrentMove = 0;
    }
    public void setCurrentMove(Integer pCurrentMove){
        _CurrentMove = pCurrentMove;
    }
    public String getAllMoves(){
        String result = "";
        for(int indexArray = 0 ; indexArray < _Moves.size() ;indexArray++){
            result += _Moves.get(indexArray).toString();
            result += "\n";
        }
        return result;
    }
}
