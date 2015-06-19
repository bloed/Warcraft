package warcraft.logic;




import warcraft.UI.OceanInterface;



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
    
    
}
