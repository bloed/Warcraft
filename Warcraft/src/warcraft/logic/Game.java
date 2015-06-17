/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcraft.logic;

/**
 *
 * @author Josué
 */
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
