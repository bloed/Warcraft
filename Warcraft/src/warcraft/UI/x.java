/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcraft.UI;

import java.awt.Point;

/**
 *
 * @author Xelop
 */
public class x extends Thread {
    public OceanInterface o;
    public x(OceanInterface o){
        this.o=o;
    }
    
    @Override
    public void run(){
        BoatInterface b = new BoatInterface(o, new Point(100,100), this);
        b.moveBoat(300);
        b.shoot(300);
    }
}
