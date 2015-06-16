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
        System.out.println(Runtime.getRuntime().availableProcessors());
        TextManager textManager = TextManager.getInstance();
        //textManager.generateTXTStrategy();
        textManager.mainStrategy();
    }
    
}
