/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcraft.logic;

import java.awt.Point;
import java.util.ArrayList;
import warcraft.UI.BoatInterface;
import warcraft.UI.OceanInterface;

/**
 *
 * @author Josué
 */
public class Boat extends Thread {
    private ArrayList<Move> _Moves;
    private Integer _CurrentMove;
    private BoatInterface _UIActions;
    private Point _Coordenates;
    
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
    public void run(){
        _UIActions.moveBoat(400);
    }
    public void setOnOcean(OceanInterface pOcean){
        int x = Utility.generateRand(0, 900);
        int y = Utility.generateRand(0, 550);
        _Coordenates = new Point(x,y);
        _UIActions = new BoatInterface(pOcean, _Coordenates,this);
        
    }
    
}
