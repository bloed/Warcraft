/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcraft.UI;

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
import warcraft.logic.Boat;
import warcraft.logic.Constants;
import warcraft.logic.Game;
import warcraft.logic.Utility;

/**
 *
 * @author Xelop
 */
public class BoatInterface  {
    private String _CurrentLife;
    private int _LifePoints;
    private JLabel _Label;
    private double _CurrentAngle; //0: initial position, vertical boat. Values change + or - from there by decimals EX: <-- -0.1/0.1 --->
    private OceanInterface _Screen;
    private Thread _MainThread;
    private Game _Game;
    private Boat _Boat;
    private int _X, _Y, _Width, _Heigth;
    
    
    public BoatInterface(OceanInterface pOcean, Point pCoordenates, Thread pThread, Game pGame, Boat pBoat){
        _CurrentAngle = 0;
        _CurrentLife = "boat.png";
        _Label = new JLabel();
        _Label.setSize(40, 100);
        _Screen = pOcean;
        _MainThread = pThread;
        _Game = pGame;
        _LifePoints = 3;
        _Boat = pBoat;
        _Label.setLocation(pCoordenates);
        _X = pCoordenates.x;
        _Y = pCoordenates.y;
        
        ImageIcon newImage = new ImageIcon( BoatInterface.class.getResource("/warcraft/Images/"+getCurrentLife()));
        _Label.setIcon(newImage);
        _Screen.getBackgroundLBL().add(_Label);
        _Label.setLocation(pCoordenates);
        //restoreValues();
        //moveBoat(500);
        //rotateBoat(6.28);
        //moveBoat(1000);
        
        
    }
    public BoatInterface(OceanInterface pOcean, Point pCoordenates, Thread pThread, Game pGame, Boat pBoat, String pCurrentLife, Integer pCurrentPoints, double pAngle){
        _CurrentAngle = pAngle;
        _CurrentLife = pCurrentLife;
        _Label = new JLabel();
        _Label.setSize(40, 100);
        _Screen = pOcean;
        _MainThread = pThread;
        _Game = pGame;
        _LifePoints = pCurrentPoints;
        _Boat = pBoat;
        _Label.setLocation(pCoordenates);
        _X = pCoordenates.x;
        _Y = pCoordenates.y;
        
        ImageIcon newImage = new ImageIcon( BoatInterface.class.getResource("/warcraft/Images/"+getCurrentLife()));
        _Label.setIcon(newImage);
        _Screen.getBackgroundLBL().add(_Label);
        //restoreValues();
    }
    
    public BufferedImage createTransformedImage(double pAngle, String pFile, JLabel pLabel) { //radians
        try {
            URL resource = BoatInterface.class.getResource("/warcraft/Images/"+pFile); //url image in package
            BufferedImage image = ImageIO.read(resource); //type buffered image for rotation
            
            double sin = Math.abs(Math.sin(pAngle)); //value of the geometric circumference
            double cos = Math.abs(Math.cos(pAngle)); //value of the geometric circumference
            int width = image.getWidth();
            int heigth = image.getHeight();
            int newWidth = (int) Math.floor(width * cos + heigth * sin); //new value from the moved positions of th angle
            int newHeigth = (int) Math.floor(heigth * cos + width * sin);
            
            
            if(Utility.collide(_X, _Y, _Game.getBoats(),
                        _X+newWidth, _Y+ newHeigth, false,_Boat)){
                pAngle = pAngle+Constants.MAX_RAD_VAL;
            }
            _Width = newWidth;
            _Heigth = newHeigth;
            pLabel.setSize(newWidth, newHeigth);
            
           
            
            BufferedImage result = new BufferedImage(newWidth, newHeigth, Transparency.TRANSLUCENT); //creates the new image space
            Graphics2D g2d = result.createGraphics(); //gets graphics of the image space
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //value of the antialisng of the image manipulation, reducing strss on edges
            g2d.translate((newWidth - width) / 2, (newHeigth - heigth) / 2); //distances to translate of the picture along the axis (x,y)
            g2d.rotate(pAngle, width / 2, heigth / 2);//rotates and translates the image again
            g2d.drawRenderedImage(image, null); //sets the picture
            g2d.dispose(); //deletes graphics
            
            return result;
        } catch (IOException ex) {
            Logger.getLogger(BoatInterface.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public void rotateBoat(double pAngle){
        pAngle += getCurrentAngle();
        while(getCurrentAngle()<pAngle){
            pAngle-=0.01;
            if(pAngle<0)
                setCurrentAngle(getCurrentAngle() + pAngle +0.01); //0.1 alambrado!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            else
                setCurrentAngle(getCurrentAngle() + 0.01);
            BufferedImage newImage = createTransformedImage(getCurrentAngle(), getCurrentLife(),_Label);
            
            _Label.setIcon(new ImageIcon(newImage));
            
            
            //restoreValues();
            try {
                _MainThread.sleep(50); ////////////////alambrado!!!
            } catch (InterruptedException ex) {
                Logger.getLogger(BoatInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void moveBoat(int pPixels){
        double currentCos = 0;
        double currentSen = 0;
        while(pPixels > 0){
            currentCos += Math.cos(getCurrentAngle());
            currentSen += Math.sin(getCurrentAngle());
            if(currentCos >= 1){
                currentCos-=1;
                if(_Label.getLocation().y-1>0 && !Utility.collide(_X, _Y-1, _Game.getBoats(), _X+_Width, _Y-1+_Heigth, false,_Boat)){
                   
                    _Label.setLocation(_Label.getLocation().x, _Label.getLocation().y-1); //invertd logic
                    _Y-=1;
                }
                    pPixels-=1;
            }else if(currentCos <= -1){
                currentCos+=1;
                if(_Label.getLocation().y+_Label.getSize().height+1 < 650 && !Utility.collide(_X, _Y+1, _Game.getBoats(), _X+_Width, _Y+1+_Heigth, false,_Boat)){ //alambrado!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    
                    _Label.setLocation(_Label.getLocation().x, _Label.getLocation().y+1); //invertd logic
                    _Y+=1;
                }
                
                pPixels-=1;
            }
            
            if(currentSen >= 1){
                currentSen -= 1;
                
                if(_Label.getLocation().x+1+_Label.getSize().width < 900 && !Utility.collide(_X+1, _Y, _Game.getBoats(), _X+1+_Width, _Y+_Heigth, false,_Boat)){    //alambrado!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    
                    _Label.setLocation(_Label.getLocation().x+1, _Label.getLocation().y); //invertd logic
                    _X+=1;
                }
                pPixels-=1;
            }else if(currentSen <= -1){
                currentSen += 1;
                if(_Label.getLocation().x-1 > 0 && !Utility.collide(_X-1, _Y, _Game.getBoats(), _X-1+_Width, _Y+_Heigth, false,_Boat)){
                    
                    _Label.setLocation(_Label.getLocation().x-1, _Label.getLocation().y); //invertd logic
                    _X-=1;
                }
                pPixels-=1;
            }
            try {
                _MainThread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(BoatInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    public void shoot(double pTime){
        //hay que verificar que no se salga del cuadrado
        JLabel shoot = new JLabel();
        shoot.setIcon(new ImageIcon(createTransformedImage(getCurrentAngle(), "torpedo.png",shoot)));
        _Screen.getBackgroundLBL().add(shoot);
        shoot.setLocation(new Point(_Label.getSize().width/2+_Label.getLocation().x,_Label.getSize().height/2+_Label.getLocation().y));
        
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
            pTime-=0.02;
            
            if(Utility.collide(shoot.getLocation().x, shoot.getLocation().y, _Game.getBoats(),
                        shoot.getLocation().x+shoot.getSize().width, shoot.getLocation().y+shoot.getSize().height, true,_Boat)){
                hit = true;
            }
            
            try {
                _MainThread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(BoatInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        _Screen.getBackgroundLBL().remove(shoot);
        _Screen.getBackgroundLBL().validate();
        _Screen.getBackgroundLBL().repaint();
    }
    /*public void restoreValues(){
        _X= _Label.getLocation().x;
        _Y= _Label.getLocation().y;
        _Width = _Label.getSize().width;
        _Heigth = _Label.getSize().height;
    }*/
    public void hitted(){
        setLifePoints(getLifePoints() - 1);
        System.out.println(getLifePoints());
        if(getLifePoints() == 2)
            setCurrentLife("boat_firsthit.png");
        else if(getLifePoints() ==1)
            setCurrentLife("boat_secondhit.png");
        else{ 
            setCurrentLife("boat_lasthit.png");
        }
        _Label.setIcon(new ImageIcon(createTransformedImage(getCurrentAngle(), getCurrentLife(), _Label)));
    }

    /**
     * @return the x
     */
    public int getX() {
        return _X;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this._X = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return _Y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this._Y = y;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return _Width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this._Width = width;
    }

    /**
     * @return the length
     */
    public int getHeigth() {
        return _Heigth;
    }

    /**
     * @param length the length to set
     */
    public void setHeigth(int length) {
        this._Heigth = length;
    }

    /**
     * @return the _LifePoints
     */
    public int getLifePoints() {
        return _LifePoints;
    }

    /**
     * @param _LifePoints the _LifePoints to set
     */
    public void setLifePoints(int _LifePoints) {
        this._LifePoints = _LifePoints;
    }

    /**
     * @return the _CurrentAngle
     */
    public double getCurrentAngle() {
        return _CurrentAngle;
    }

    /**
     * @return the _CurrentLife
     */
    public String getCurrentLife() {
        return _CurrentLife;
    }

    /**
     * @param _CurrentLife the _CurrentLife to set
     */
    public void setCurrentLife(String _CurrentLife) {
        this._CurrentLife = _CurrentLife;
    }

    /**
     * @param _CurrentAngle the _CurrentAngle to set
     */
    public void setCurrentAngle(double _CurrentAngle) {
        this._CurrentAngle = _CurrentAngle;
    }
    
    
    
}
