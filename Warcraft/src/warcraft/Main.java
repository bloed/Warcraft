package warcraft;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import warcraft.UI.MainMenu;
import warcraft.logic.Game;
import warcraft.logic.Move;
import warcraft.logic.TextManager;
import warcraft.logic.ThreadManager;
import warcraft.logic.Utility;
import warcraft.logic.SecurityManager;

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
        //game now contains a list of all the boats
        new MainMenu().setVisible(true);

        //Game game = Game.getInstance();*/
        //game.setBoats(threadManager.mainStrategy());
        //game now contains a list of all the boatszz
        //SecurityManager securityManager = SecurityManager.getInstance();
        //SecurityManager caca = new SecurityManager();
        //securityManager.mainAsymetricEncryption("caca");
    }
}
