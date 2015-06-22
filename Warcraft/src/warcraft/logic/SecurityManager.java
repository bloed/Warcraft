package warcraft.logic;

import java.io.File;
import java.io.PrintWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.swing.JOptionPane;



public class SecurityManager {
//Class used to manage all the asymetrich algorithm to  provide secure saved games. It is bases on pins.    
    private SecurityManager(){
        try{
            _Cipher = Cipher.getInstance("RSA");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static SecurityManager getInstance(){
        if (_Instance == null){
            _Instance = new SecurityManager();
        }
        return _Instance;
    }
    public String mainAsymetricEncryption(String pFileName, Object pObjToEncrypt){
        String pin = Utility.generateRand(Constants.MIN_VALUE_PIN, Constants.MAX_VALUE_PIN).toString();
        while(pinExists(pin)){//pin must be unique
            pin = Utility.generateRand(Constants.MIN_VALUE_PIN, Constants.MAX_VALUE_PIN).toString();
        }
        writeNewPin(pin);//the new ping used to generate keys must be add to the .txt
        KeyPair keyPair = generateKeys(pin);
        Key publicKey = keyPair.getPublic();
        encrypt(publicKey , pFileName, pObjToEncrypt);
        return pin;
    } 
    public Object mainAsymetricDEncryption(String pPin){
        //returns a Game corresponding to that pin.
        if (pinExists(pPin)){
            KeyPair keyPair = generateKeys(pPin);
            Key privateKey = keyPair.getPrivate();
            byte[] encryptedData = null;
            Object game = null;
            File[] listOfFiles = TextManager.getAllFilenames("./" + Constants.SAVED_GAMES_DIRECTORY);
            for (int fileNumber = 0; fileNumber < listOfFiles.length; fileNumber++){
                if (listOfFiles[fileNumber].isFile()){
                    String  hexRepresentation = listOfFiles[fileNumber].getName();//get the name of the file
                    encryptedData = Utility.hexStringToByteArray(hexRepresentation);//transofrms it into its binary representation
                    if (deEncrypt(privateKey, encryptedData) == true){//tries to dencrypt
                        game = DataManager.readObject(Constants.SAVED_GAMES_DIRECTORY + "/" + hexRepresentation);
                        break;
                    }
                }
            }
            return game;//if not correct pin, return null
        }
        return null;//not even try, the pin doesnt even exists
    }
    private KeyPair generateKeys(String pPin){
        try{
            byte[] seed = pPin.getBytes();
            KeyPairGenerator keysGenerator = KeyPairGenerator.getInstance("RSA");
            keysGenerator.initialize(Constants.KEY_SIZE, new SecureRandom(seed));
            KeyPair keyPair =  keysGenerator.generateKeyPair();
            return keyPair;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }    
    }  
    private void encrypt(Key pKey, String pFileName , Object pObjToEncrypt){
        //encripts the filename, and creates a file with the object
        try{
            byte[] dataBytes = pFileName.getBytes();
            _Cipher.init(Cipher.ENCRYPT_MODE, pKey);  // encrypt with public key
            byte[] cipherData = _Cipher.doFinal(dataBytes);
            String hexCipherData = Utility.bytesToHex(cipherData);//transforms cypher data in hex
            DataManager.saveObject(Constants.SAVED_GAMES_DIRECTORY + "/"+hexCipherData, pObjToEncrypt);//saves objecs
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }   
    private Boolean deEncrypt(Key pKey, byte[] cipherData){
        try{
            _Cipher.init(Cipher.DECRYPT_MODE, pKey); // dencrypt with private key
            byte[] deEncryptData = _Cipher.doFinal(cipherData);
            return true;//the cipherData was dencrypted correctly.
        }
        catch(Exception e){//invalid key
            System.out.println(e.getMessage());
            return false;
        }
    }   
    private Boolean pinExists(String pPin){
        Scanner scan = TextManager.openReadFile(Constants.PINS_FILENAME);
        String currentPin;
        while(scan.hasNext()){
            currentPin = scan.next();
            if (currentPin.equals(pPin)){
                return true;//the pin already existss
            }
        }
        return false;//no pin was found
    }
    private void writeNewPin(String pPin){//writes the new ping in the txt
        PrintWriter writer = TextManager.openWriteFile(Constants.PINS_FILENAME);
        TextManager.addRecord(writer, pPin);
        TextManager.closeFile(writer);
    }
    private static SecurityManager _Instance = null;
    private Cipher _Cipher;
}



