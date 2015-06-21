package warcraft.logic;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sun.swing.UIAction;
import warcraft.UI.BoatInterface;
import warcraft.UI.OceanInterface;

public class Boat extends Thread {
    //This class represents a single boat.
    public Boat(ArrayList<Move> pList){
        _Moves = pList;
        _CurrentMove = 0;
    }
    public void setCurrentMove(Integer pCurrentMove){
        _CurrentMove = pCurrentMove;
    }
    public String getAllMoves(){
        String result = "";
        for(int indexArray = 0 ; indexArray < getMoves().size() ;indexArray++){
            result += getMoves().get(indexArray).toString();
            result += "\n";
        }
        return result;
    }
    public void run(){
        for(int index = getCurrentMove(); index < getMoves().size(); index++){
            if(_UIActions.getLifePoints() <= 0){
                _Game.setAliveBoats(_Game.getAliveBoats()-1);
                
                if(_Game.getAliveBoats() == 1)
                    JOptionPane.showMessageDialog(null, "Juego Finalizado!");
                break;
            }
            double degree = Utility.getDegree(getMoves().get(index).getDegree());
            _UIActions.rotateBoat(degree);
            
            if(getMoves().get(index).getAction().equals("avanzar")){
                System.out.println(getMoves().get(index).getValue().intValue());
                _UIActions.moveBoat(getMoves().get(index).getValue().intValue());
            }else{
                _UIActions.shoot(getMoves().get(index).getValue());
            }
            _CurrentMove++;
        }
    }
    public void setOnOcean(OceanInterface pOcean, Game pGame){
        _Game = pGame;
        Boolean collide = true;
        int x = 0;
        int y = 0;
        
        while(collide){
            x = Utility.generateRand(0, 800);
            y = Utility.generateRand(0, 500);
            collide = Utility.collide(x, y, pGame.getBoats(), x+40, y+100, false,this);
        }
        _Coordenates = new Point(x,y);
        setUIActions(new BoatInterface(pOcean, _Coordenates,this, pGame,this));   
    }
    public BoatInterface getUIActions() {
        return _UIActions;
    }
    public void setUIActions(BoatInterface pUIActions) {
        this._UIActions = pUIActions;
    }
    public void setSavedBoat(OceanInterface pOcean, Point pCoordenates, Game pGame, String pCurrentLife, Integer pCurrentPoints, double pAngle){
        _Game = pGame;
        _UIActions = new BoatInterface(pOcean, pCoordenates, this, _Game, this, pCurrentLife, pCurrentPoints, pAngle);
    }
    
    private ArrayList<Move> _Moves;
    private Integer _CurrentMove;
    private BoatInterface _UIActions;
    private Point _Coordenates;
    private Game _Game;

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
    
    public void setCurrentX(Integer pX){
        _UIActions.setX(pX);
    }
    public void setCurrentY(Integer pY){
        _UIActions.setX(pY);
    }
    public void setCurrentAngle(double pAngle){
        _UIActions.setCurrentAngle(pAngle);
    }
}
