package warcraft.logic;

public class Game {
    Boat[] _BoatsArray;
    private static Game _Instance = null;
    
    public Game(){
        _BoatsArray = new Boat[Runtime.getRuntime().availableProcessors()];
    }
    public static Game getInstance(){
        if (_Instance == null){
            _Instance = new Game();
        }
        return _Instance;
    }
    public void setBoats(Boat[] pArray){
        _BoatsArray = pArray;
        System.out.println(_BoatsArray.length + " boats were added");
    }
    
}
