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
public class SavedGame implements Serializable {
    
    SavedBoat[] pSavedBoat;
    Integer _CurrentAlive;
    
    public SavedGame(Boat[] pBoats, Integer pCurrentAlive, Integer pAmountBoats){
        _CurrentAlive = pCurrentAlive;
        pSavedBoat = new SavedBoat[pAmountBoats];
        setSavedBoats(pBoats);
    }
    
    private void setSavedBoats(Boat[] pBoats){
        for(int index = 0; index < pBoats.length; index++){
            Boat currentBoat = pBoats[index];
            pSavedBoat[index] = new SavedBoat(currentBoat.getMoves(), currentBoat.getCurrentMove(),
                    new Point(currentBoat.getUIActions().getX(), currentBoat.getUIActions().getY()),
                    currentBoat.getUIActions().getCurrentAngle(), currentBoat.getUIActions().getCurrentLife(),
                    currentBoat.getUIActions().getLifePoints());
        }
    }
    public SavedBoat[] getSavedBoats(){
        return pSavedBoat;
    }
    
    public Integer getAlive(){
        return _CurrentAlive;
    }
    public void loadGame(){
        Game pGame = Game.getInstance();
        pGame.loadGame(this);
    }
}
