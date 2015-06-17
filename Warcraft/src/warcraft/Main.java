package warcraft;

import java.util.ArrayList;
import warcraft.UI.MainMenu;
import warcraft.logic.Game;
import warcraft.logic.Move;
import warcraft.logic.TextManager;
import warcraft.logic.ThreadManager;
import warcraft.logic.Utility;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*System.out.println(Runtime.getRuntime().availableProcessors());
        TextManager textManager = TextManager.getInstance();
        //textManager.generateTXTStrategy();
        ThreadManager threadManager = ThreadManager.getInstance();
        Game game = Game.getInstance();
        game.setBoats(threadManager.mainStrategy());
        //game now contains a list of all the boats*/
        new MainMenu().setVisible(true);
    }
}
