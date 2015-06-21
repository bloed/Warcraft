/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcraft.logic;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import warcraft.UI.OceanInterface;
import warcraft.logic.Boat;
import warcraft.logic.Constants;
import warcraft.logic.Game;
import warcraft.logic.Utility;

/**
 *
 * @author Xelop
 */
public class BoatActions  {
    //All th actions needed for the correct representation on the Interfacee
    
    public int getX() {
        return _X;
    }

    public void setX(int x) {
        this._X = x;
    }

    public int getY() {
        return _Y;
    }

    public void setY(int y) {
        this._Y = y;
    }
    
    public int getWidth() {
        return _Width;
    }

    public void setWidth(int width) {
        this._Width = width;
    }

    public int getHeigth() {
        return _Heigth;
    }

    public void setHeigth(int length) {
        this._Heigth = length;
    }

    public double getCurrentAngle() {
        return _CurrentAngle;
    }

    public String getCurrentLife() {
        return _CurrentLife;
    }

    public void setCurrentLife(String _CurrentLife) {
        this._CurrentLife = _CurrentLife;
    }

    public void setCurrentAngle(double _CurrentAngle) {
        this._CurrentAngle = _CurrentAngle;
    }
    
    
    public BoatActions(OceanInterface pOcean, Point pCoordenates, Thread pThread, Game pGame, Boat pBoat){ //constructor of a nw boat
        _CurrentAngle = 0;
        _CurrentLife = "boat.png";
        _Label = new JLabel();
        _Label.setSize(Constants.INITIAL_WIDTH, Constants.INITIAL_HEIGTH);
        _Screen = pOcean;
        _MainThread = pThread;
        _Game = pGame;
        _Boat = pBoat;
        _X = pCoordenates.x;
        _Y = pCoordenates.y;
        _Width = Constants.INITIAL_WIDTH;
        _Heigth = Constants.INITIAL_HEIGTH;
        
        ImageIcon newImage = new ImageIcon( BoatActions.class.getResource("/warcraft/Images/"+getCurrentLife()));
        _Label.setIcon(newImage);
        _Screen.getBackgroundLBL().add(_Label);
        _Label.setLocation(pCoordenates);
    }
    public BoatActions(OceanInterface pOcean, Point pCoordenates, Thread pThread, Game pGame, Boat pBoat, String pCurrentLife, double pAngle){ 
         //constructor of a saveed boat
        
        _CurrentAngle = pAngle;
        _CurrentLife = pCurrentLife;
        _Label = new JLabel();
        _Label.setSize(Constants.INITIAL_WIDTH, Constants.INITIAL_HEIGTH);
        _Screen = pOcean;
        _MainThread = pThread;
        _Game = pGame;
        _Boat = pBoat;
        _Label.setLocation(pCoordenates);
        _X = pCoordenates.x;
        _Y = pCoordenates.y;
        _Width = Constants.INITIAL_WIDTH;
        _Heigth = Constants.INITIAL_HEIGTH;
        
        ImageIcon newImage = new ImageIcon(BoatActions.class.getResource("/warcraft/Images/"+getCurrentLife()));
        _Label.setIcon(newImage);
        _Screen.getBackgroundLBL().add(_Label);
        
    }
    
    public BufferedImage createTransformedImage(String pFile, JLabel pLabel) { //radians
        try {
            URL resource = BoatActions.class.getResource("/warcraft/Images/"+pFile); //url image in package
            BufferedImage image = ImageIO.read(resource); //type buffered image for rotation
            
            double sin = Math.abs(Math.sin(_CurrentAngle)); //value of the geometric circumference
            double cos = Math.abs(Math.cos(_CurrentAngle)); //value of the geometric circumference
            int width = image.getWidth();
            int heigth = image.getHeight();
            int newWidth = (int) Math.floor(width * cos + heigth * sin); //new value from the moved positions of th angle
            int newHeigth = (int) Math.floor(heigth * cos + width * sin);
            
            
            if(Utility.collide(_X, _Y, _Game.getBoats(),
                        _X+newWidth, _Y+ newHeigth, false,_Boat)){
                _CurrentAngle = _CurrentAngle+Constants.MAX_RAD_VAL;
            }
            _Width = newWidth;
            _Heigth = newHeigth;
            pLabel.setSize(newWidth, newHeigth);
 
            BufferedImage result = new BufferedImage(newWidth, newHeigth, Transparency.TRANSLUCENT); //creates the new image space
            Graphics2D g2d = result.createGraphics(); //gets graphics of the image space
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //value of the antialisng of the image manipulation, reducing strss on edges
            g2d.translate((newWidth - width) / 2, (newHeigth - heigth) / 2); //distances to translate of the picture along the axis (x,y)
            g2d.rotate(_CurrentAngle, width / 2, heigth / 2);//rotates and translates the image again
            g2d.drawRenderedImage(image, null); //sets the picture
            g2d.dispose(); //deletes graphics
            
            return result;
        } catch (IOException ex) {
            Logger.getLogger(BoatActions.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public void rotateBoat(double pAngle){
        pAngle += getCurrentAngle();
        while(getCurrentAngle()<pAngle){
            pAngle-=Constants.NOTICEABLE_DEGREE;
            if(pAngle<0) //left rotation, doesnt occurs
                setCurrentAngle(getCurrentAngle() + pAngle + Constants.NOTICEABLE_DEGREE); 
            else //right rotation
                setCurrentAngle(getCurrentAngle() + Constants.NOTICEABLE_DEGREE);
            BufferedImage newImage = createTransformedImage(getCurrentLife(),_Label);
            
            _Label.setIcon(new ImageIcon(newImage));
            
            
            //restoreValues();
            try {
                _MainThread.sleep(Constants.MOVE_TIME); ////////////////alambrado!!!
            } catch (InterruptedException ex) {
                Logger.getLogger(BoatActions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void moveBoat(int pPixels){
        double currentCos = 0;
        double currentSen = 0;
        while(pPixels > 0){
            currentCos += Math.cos(getCurrentAngle());
            currentSen += Math.sin(getCurrentAngle());
            if(currentCos >= 1){ //move up
                currentCos-=1;
                if(_Label.getLocation().y-Constants.MOVE_DISTANCE>0 && 
                        !Utility.collide(_X, _Y-Constants.MOVE_DISTANCE, _Game.getBoats(),
                        _X+_Width, _Y-Constants.MOVE_DISTANCE+_Heigth, false,_Boat)){
                   
                    _Label.setLocation(_Label.getLocation().x, _Label.getLocation().y-Constants.MOVE_DISTANCE); //invertd logic
                    _Y-=Constants.MOVE_DISTANCE;
                }
                    pPixels-=Constants.MOVE_DISTANCE;
            }else if(currentCos <= -1){ //move down
                currentCos+=1;
                if(_Label.getLocation().y+_Label.getSize().height+Constants.MOVE_DISTANCE < 650 && 
                        !Utility.collide(_X, _Y+Constants.MOVE_DISTANCE, _Game.getBoats(), _X+_Width,
                                _Y+Constants.MOVE_DISTANCE+_Heigth, false,_Boat)){ 
                    
                    _Label.setLocation(_Label.getLocation().x, _Label.getLocation().y+Constants.MOVE_DISTANCE); //invertd logic
                    _Y+=Constants.MOVE_DISTANCE;
                }
                
                pPixels-=Constants.MOVE_DISTANCE;
            }
            
            if(currentSen >= 1){// move right
                currentSen -= 1;
                
                if(_Label.getLocation().x+Constants.MOVE_DISTANCE+_Label.getSize().width < 900 &&
                        !Utility.collide(_X+Constants.MOVE_DISTANCE, _Y, _Game.getBoats(), 
                                _X+Constants.MOVE_DISTANCE+_Width, _Y+_Heigth, false,_Boat)){    //alambrado!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    
                    _Label.setLocation(_Label.getLocation().x+Constants.MOVE_DISTANCE, _Label.getLocation().y); //invertd logic
                    _X+=Constants.MOVE_DISTANCE;
                }
                pPixels-=Constants.MOVE_DISTANCE;
                
            }else if(currentSen <= -1){ //move left
                currentSen += 1;
                if(_Label.getLocation().x-Constants.MOVE_DISTANCE > 0 &&
                        !Utility.collide(_X-Constants.MOVE_DISTANCE, _Y, _Game.getBoats(),
                                _X-Constants.MOVE_DISTANCE+_Width, _Y+_Heigth, false,_Boat)){
                    
                    _Label.setLocation(_Label.getLocation().x-Constants.MOVE_DISTANCE, _Label.getLocation().y); //invertd logic
                    _X-=Constants.MOVE_DISTANCE;
                }
                pPixels-=Constants.MOVE_DISTANCE;
            }
            try {
                _MainThread.sleep(Constants.MOVE_TIME);
            } catch (InterruptedException ex) {
                Logger.getLogger(BoatActions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    public void shoot(double pTime){
        
        JLabel shoot = new JLabel();
        shoot.setIcon(new ImageIcon(createTransformedImage("torpedo.png",shoot)));
        _Screen.getBackgroundLBL().add(shoot);
        shoot.setLocation(new Point(_Width/2+_X, _Heigth/2+_Y)); //in the middle of the boat
        
        double currentCos = 0;
        double currentSen = 0;
        Boolean hit = false;
        
        while(pTime > 0 && hit == false){
            currentCos += Math.cos(getCurrentAngle());
            currentSen += Math.sin(getCurrentAngle());
            if(currentCos >= 1){
                currentCos-=1;
                shoot.setLocation(shoot.getLocation().x, shoot.getLocation().y-Constants.SHOOT_DISTANCE); //invertd logic                
            }else if(currentCos <= -1){
                currentCos+=1;
                shoot.setLocation(shoot.getLocation().x, shoot.getLocation().y+Constants.SHOOT_DISTANCE); //invertd logic
                
            }
            if(currentSen >= 1){
                currentSen -= 1;
                shoot.setLocation(shoot.getLocation().x+Constants.SHOOT_DISTANCE, shoot.getLocation().y); //invertd logic
                
            }else if(currentSen <= -1){
                currentSen += 1;
                shoot.setLocation(shoot.getLocation().x-Constants.SHOOT_DISTANCE, shoot.getLocation().y); //invertd logic
                
            }
            System.out.println(pTime);
            double decrease = (double)Constants.SHOOT_TIME/1000;
            pTime = pTime - decrease; //miliseconds
            System.out.println("current"+pTime);
            
            if(Utility.collide(shoot.getLocation().x, shoot.getLocation().y, _Game.getBoats(),
                        shoot.getLocation().x+shoot.getSize().width, shoot.getLocation().y+shoot.getSize().height, true,_Boat)){
                hit = true;
            }
            
            try {
                _MainThread.sleep(Constants.SHOOT_TIME);
            } catch (InterruptedException ex) {
                Logger.getLogger(BoatActions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        _Screen.getBackgroundLBL().remove(shoot);
        _Screen.getBackgroundLBL().validate(); //just to be sure
        _Screen.getBackgroundLBL().repaint(); //just to be sure
    }
    public void hitted(){
        if(_Boat.getCurrentLife() == 2)
            setCurrentLife("boat_firsthit.png");
        else if(_Boat.getCurrentLife() == 1)
            setCurrentLife("boat_secondhit.png");
        else{ 
            setCurrentLife("boat_lasthit.png");
        }
        _Label.setIcon(new ImageIcon(createTransformedImage(getCurrentLife(), _Label)));
    }
    public void displayAngle(){
        ImageIcon newImage = new ImageIcon( createTransformedImage(_CurrentLife, _Label));
        _Label.setIcon(newImage);
    }
    
    private String _CurrentLife;
    private JLabel _Label;
    private double _CurrentAngle; 
    private OceanInterface _Screen;
    private Thread _MainThread;
    private Game _Game;
    private Boat _Boat;
    private int _X, _Y, _Width, _Heigth;
}
