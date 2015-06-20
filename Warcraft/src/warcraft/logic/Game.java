package warcraft.logic;




import warcraft.UI.OceanInterface;



public class Game {
    //this class contains all the boats and basically all the game. When saving the game only this class is saved.
    public Game(){
        _BoatsArray = new Boat[Runtime.getRuntime().availableProcessors()];//creates boats depending of the quantity of processors
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
    Boat[] _BoatsArray;
    private static Game _Instance = null;
}
