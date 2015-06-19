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
        for(int index = 0; index < _Moves.size(); index++){
            if(_UIActions.getLifePoints() <= 0)
                break;
            _UIActions.rotateBoat(_Moves.get(index).getDegree());
            
            if(_Moves.get(index).getAction().equals("avanzar")){
                System.out.println(_Moves.get(index).getValue().intValue());
                _UIActions.moveBoat(_Moves.get(index).getValue().intValue());
            }else{
                _UIActions.shoot(_Moves.get(index).getValue());
            }
        }
    }
    public void setOnOcean(OceanInterface pOcean, Game pGame){
        int x = Utility.generateRand(0, 900);
        int y = Utility.generateRand(0, 550);
        _Coordenates = new Point(x,y);
        setUIActions(new BoatInterface(pOcean, _Coordenates,this, pGame));
        
    }

    /**
     * @return the _UIActions
     */
    public BoatInterface getUIActions() {
        return _UIActions;
    }

    /**
     * @param _UIActions the _UIActions to set
     */
    public void setUIActions(BoatInterface _UIActions) {
        this._UIActions = _UIActions;
    }
    
}
