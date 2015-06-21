package warcraft.logic;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import warcraft.UI.OceanInterface;

public class Boat extends Thread {
    //This class represents a single boat.
    
    public ArrayList<Move> getMoves() {
        return _Moves;
    }
    
    public Integer getCurrentMove() {
        return _CurrentMove;
    }
    
    public void setStop(Boolean pState){
        _Stop = pState;
    }
    
    public Integer getCurrentLife() {
        return _CurrentLife;
    }

    public void setCurrentLife(Integer _CurrentLife) {
        this._CurrentLife = _CurrentLife;
    }
    
    
    public Boat(ArrayList<Move> pList){
        _Moves = pList;
        _CurrentMove = 0;
        _CurrentLife = Constants.MAX_LIFE;
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
    
    @Override
    public void run(){
        
        if(_CurrentLife > 0)
            _Stop = false;
        else
            _Stop = true;
        
        while(!_Stop){
            
            for(int index = getCurrentMove(); index < getMoves().size(); index++){
                if(_Stop != true){
                    double degree = Utility.getDegree(getMoves().get(index).getDegree());
                    _UIActions.rotateBoat(degree);

                    if(getMoves().get(index).getAction().equals("avanzar")){
                        _UIActions.moveBoat(getMoves().get(index).getValue().intValue());
                    }else{
                        _UIActions.shoot(getMoves().get(index).getValue());
                    }
                    
                    _CurrentMove++;
                    
                }else{
                    break;
                }
            }
            _CurrentMove = 0;
            
        }
    }
    
    public void setOnOcean(OceanInterface pOcean, Game pGame){
        _Game = pGame;
        Boolean collide = true;
        int x = 0;
        int y = 0;
        
        while(collide){ //for the spawn to be in the screen and boats not colliding
            x = Utility.generateRand(0, Constants.RANDOM_SCREEN_X);
            y = Utility.generateRand(0, Constants.RANDOM_SCREEN_Y);
            collide = Utility.collide(x, y, pGame.getBoats(), x+Constants.INITIAL_WIDTH, y+Constants.INITIAL_HEIGTH, false,this);
        }
        
        _Coordenates = new Point(x,y);
        setUIActions(new BoatActions(pOcean, _Coordenates,this, pGame,this));   
    }
    public BoatActions getUIActions() {
        return _UIActions;
    }
    public void setUIActions(BoatActions pUIActions) {
        this._UIActions = pUIActions;
    }
    public void setSavedBoat(OceanInterface pOcean, Point pCoordenates, Game pGame, String pCurrentLife, Integer pCurrentPoints, double pAngle, Integer pCurrentMove){
        _Game = pGame;
       _CurrentLife = pCurrentPoints;
        _CurrentMove = pCurrentMove;
        _UIActions = new BoatActions(pOcean, pCoordenates, this, _Game, this, pCurrentLife, pAngle);
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
    public void gotHit(){
        _CurrentLife--;
        _UIActions.hitted();
        if(_CurrentLife <= 0){ //in case it recieves two shoots
            _Game.setAliveBoats(_Game.getAliveBoats()-1);
            _Stop = true; //stops the thread

            if(_Game.getAliveBoats() == 1){
                JOptionPane.showMessageDialog(null, "Juego Finalizado!");
                _Game.pauseGame();
            }
        }
                        
    }
    
    private ArrayList<Move> _Moves;
    private Integer _CurrentMove, _CurrentLife;
    private BoatActions _UIActions;
    private Point _Coordenates;
    private Game _Game;
    private Boolean _Stop;

}
