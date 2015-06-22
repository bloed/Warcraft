/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcraft.logic;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Xelop
 */
//Class used to implement de serializable in order to safe the game in a serializable file


public class SavedGame implements Serializable {
    
    public SavedBoat[] getSavedBoats(){
        return pSavedBoat;
    }
    
    public Integer getAlive(){
        return _CurrentAlive;
    }
    
    public SavedGame(Boat[] pBoats, Integer pCurrentAlive){
        _CurrentAlive = pCurrentAlive;
        pSavedBoat = new SavedBoat[pBoats.length];
        setSavedBoats(pBoats);
    }
    

    public void loadGame(){
        Game pGame = Game.getInstance();
        pGame.loadGame(this);
    }
    
    private void setSavedBoats(Boat[] pBoats){
        for(int index = 0; index < pBoats.length; index++){
            Boat currentBoat = pBoats[index];
            pSavedBoat[index] = new SavedBoat(currentBoat.getMoves(), currentBoat.getCurrentMove(),
                    new Point(currentBoat.getUIActions().getX(), currentBoat.getUIActions().getY()),
                    currentBoat.getUIActions().getCurrentAngle(), currentBoat.getUIActions().getCurrentLife(),
                    currentBoat.getCurrentLife());
        }
    }
    
    SavedBoat[] pSavedBoat;
    Integer _CurrentAlive;
}
