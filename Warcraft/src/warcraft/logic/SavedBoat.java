/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcraft.logic;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import warcraft.UI.BoatInterface;

/**
 *
 * @author Xelop
 */
public class SavedBoat implements Serializable {
    private ArrayList<Move> _Moves;
    private Integer _CurrentMove;
    private Point _Coordenates;
    private double _Angle;
    private String _CurrentLife;
    private Integer _CurrentPoints;
    
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

    /**
     * @return the _Moves
     */
    public ArrayList<Move> getMoves() {
        return _Moves;
    }

    /**
     * @return the _CurrentMove
     */
    public Integer getCurrentMove() {
        return _CurrentMove;
    }

    /**
     * @return the _Coordenates
     */
    public Point getCoordenates() {
        return _Coordenates;
    }

    /**
     * @return the _Angle
     */
    public double getAngle() {
        return _Angle;
    }

    /**
     * @return the _CurrentLife
     */
    public String getCurrentLife() {
        return _CurrentLife;
    }

    /**
     * @return the _CurrentPoints
     */
    public Integer getCurrentPoints() {
        return _CurrentPoints;
    }
}
