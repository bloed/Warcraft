/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package warcraft;

import warcraft.logic.TextManager;
import warcraft.logic.Utility;

/**
 *
 * @author Josue
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TextManager textManager = TextManager.getInstance();
        textManager.createStrategy();
    }
    
}
