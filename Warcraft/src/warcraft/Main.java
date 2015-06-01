package warcraft;

import warcraft.logic.Move;
import warcraft.logic.TextManager;
import warcraft.logic.Utility;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TextManager textManager = TextManager.getInstance();
        //textManager.createStrategy();
        textManager.selectMovesForBoat(1,0,200000);
    }
    
}
