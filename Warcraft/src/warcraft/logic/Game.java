package warcraft.logic;




import java.io.Serializable;
import warcraft.UI.OceanInterface;



public class Game implements Serializable {
    //this class contains all the boats and basically all the game. When saving the game only this class is saved.
    
    public Integer getAliveBoats() {
        return _AliveBoats;
    }

    public void setAliveBoats(Integer _AliveBoats) {
        this._AliveBoats = _AliveBoats;
    }
    
    public Game(){
        _BoatsArray = new Boat[Runtime.getRuntime().availableProcessors()];//creates boats depending of the quantity of processors
        _AliveBoats = _BoatsArray.length;
    }
    public static Game getInstance(){
        if (_Instance == null){
            _Instance = new Game();
        }
        return _Instance;
    }
    public static void setInstance(Game pGame){
        _Instance = pGame;
    }
    public void setBoats(Boat[] pArray){
        _BoatsArray = pArray;
        System.out.println(_BoatsArray.length + " boats were added.");
    }
    public void setOceans(OceanInterface pOcean){
        for(int index = 0; index< _BoatsArray.length; index++){
            _BoatsArray[index].setOnOcean(pOcean,this);
        }
    }
    public void startThreads(){
        for(int index = 0; index< _BoatsArray.length; index++){
            _BoatsArray[index].start();
        }
    }
    public Boat[] getBoats(){
        return _BoatsArray;
    }
    
    public void loadGame(SavedGame pSaved){
        OceanInterface ocean = new OceanInterface(this, true);
        ocean.setVisible(true);
        _AliveBoats = pSaved.getAlive();
        SavedBoat[] savedBoats = pSaved.getSavedBoats();
        
        for(int index = 0; index < _BoatsArray.length; index++){
            SavedBoat currentSBoat = savedBoats[index];
            _BoatsArray[index] = new Boat(currentSBoat.getMoves());
            
            Boat currentBoat = _BoatsArray[index];
            
            currentBoat.setSavedBoat(ocean, currentSBoat.getCoordenates(), this, currentSBoat.getCurrentLife(),
                    currentSBoat.getCurrentPoints(), currentSBoat.getAngle(), currentSBoat.getCurrentMove());
        }
        startThreads();
    }
    
    public void pauseGame(){
        for(int index = 0; index < _BoatsArray.length; index++){
            _BoatsArray[index].setStop(true);
        }
    }
    
    public SavedGame saveGame(){
        return new SavedGame(_BoatsArray, _AliveBoats);
    }
    
    Boat[] _BoatsArray;
    private static Game _Instance = null;
    private Integer _AliveBoats;    
}
