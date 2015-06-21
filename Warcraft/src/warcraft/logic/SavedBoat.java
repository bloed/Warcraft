/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcraft.logic;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Xelop
 */


public class SavedBoat implements Serializable {
    //class used to save each boat with the values needed, implements serializable too
    
    public ArrayList<Move> getMoves() {
        return _Moves;
    }
    
    public Integer getCurrentMove() {
        return _CurrentMove;
    }
    
    public Point getCoordenates() {
        return _Coordenates;
    }
    
    public double getAngle() {
        return _Angle;
    }
    
    public String getCurrentLife() {
        return _CurrentLife;
    }
    
    public Integer getCurrentPoints() {
        return _CurrentPoints;
    }
    
    
    public SavedBoat(ArrayList<Move> pMove, Integer pCurrentMove, 
            Point pCoordenates, double pAngle, String pCurrentLife, 
            Integer pCurrentPoints){
        
        _Moves = pMove;
        _CurrentMove = pCurrentMove;
        _Coordenates = pCoordenates;
        _Angle = pAngle;
        _CurrentLife = pCurrentLife;
        _CurrentPoints = pCurrentPoints;
        
    }
    
    private ArrayList<Move> _Moves;
    private Integer _CurrentMove;
    private Point _Coordenates;
    private double _Angle;
    private String _CurrentLife;
    private Integer _CurrentPoints;
    
}
