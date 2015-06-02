package warcraft;

import java.util.ArrayList;
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
        ArrayList<Move> test = textManager.selectMovesForBoat(113,185000,186000);
        System.out.println("---------------------------------------");
        textManager.mergeMovesForBoat(test, 0, test.size());
        System.out.println("==========================================");
    }
    
}
