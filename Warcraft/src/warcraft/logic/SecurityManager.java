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
    private static SecurityManager _Instance = null;
    private Cipher _Cipher;
    
    public SecurityManager(){
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
    public KeyPair generateKeys(String pPin){
        try{
            
            byte[] seed = pPin.getBytes();
            KeyPairGenerator keysGenerator = KeyPairGenerator.getInstance("RSA");
            keysGenerator.initialize(512, new SecureRandom(seed));
            KeyPair keyPair =  keysGenerator.generateKeyPair();
            return keyPair;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }    
    }
    
    public void encrypt(Key pKey, String pFileName , Object pObjToEncrypt){
        //encripts the filename, and creates a file with the object
        try{
            byte[] dataBytes = pFileName.getBytes();
            _Cipher.init(Cipher.ENCRYPT_MODE, pKey);  // Cifra con la clave publica
            System.out.println("3a. Cifrar con clave publica");
            byte[] cipherData = _Cipher.doFinal(dataBytes);
            System.out.println("TEXTO CIFRADO:");
            Utility.showBytes(cipherData);
            System.out.println("\n-------------------------------");
            String hexCipherData = Utility.bytesToHex(cipherData);//transforms cypher data in hex
            DataManager.grabarObjeto("Saved Games/"+hexCipherData, pObjToEncrypt);//saves objecs
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public Boolean deEncrypt(Key pKey, byte[] cipherData){
        try{
            _Cipher.init(Cipher.DECRYPT_MODE, pKey); // Descrifra con la clave privad
            System.out.println("3b. Descifrar con clave privada");
            byte[] deEncryptData = _Cipher.doFinal(cipherData);
            System.out.println("TEXTO DESCIFRADO:");
            Utility.showBytes(deEncryptData);
            System.out.println("\n-------------------------------");
            return true;//the cipherData was dencrypted correctly.
        }
        catch(Exception e){//invalid key
            System.out.println(e.getMessage());
            return false;
        }
    }   
    public void mainAsymetricEncryption(String pFileName, Object pObjToEncrypt){
        String pin = Utility.generateRand(1000, 10000).toString();
        while(pinExists(pin)){//pin must be unique
            pin = Utility.generateRand(1000, 10000).toString();
        }
        writeNewPin(pin);//the new ping used to generate keys must be addes to the .txt
        KeyPair keyPair = generateKeys(pin);
        Key publicKey = keyPair.getPublic();
        encrypt(publicKey , pFileName, pObjToEncrypt);
    }
    
    public Object mainAsymetricDEncryption(String pPin){
        //returns a Game corresponding to that pin.
        if (pinExists(pPin)){
            //dencrypt
            KeyPair keyPair = generateKeys(pPin);
            Key privateKey = keyPair.getPrivate();
            byte[] encryptedData = null;
            Object game = null;
            File[] listOfFiles = TextManager.getAllFilenames("./Saved Games");
            for (int fileNumber = 0; fileNumber < listOfFiles.length; fileNumber++) {
                if (listOfFiles[fileNumber].isFile()){
                    String  hexRepresentation = listOfFiles[fileNumber].getName();
                    encryptedData = Utility.hexStringToByteArray(hexRepresentation);
                    if (deEncrypt(privateKey, encryptedData) == true){
                        game = DataManager.leerObjeto("Saved Games/" + hexRepresentation);
                        break;
                    }
                    
                }
            }
            return game;//if not correct pin, return null
        }
        else{ 
            JOptionPane.showMessageDialog(null, "The pin that was given doesn´t correpond to any game.");
        }
        return null;
    }
    private Boolean pinExists(String pPin){
        Scanner scan = TextManager.openReadFile("Pins.txt");
        String currentPin;
        while(scan.hasNext()){
            currentPin = scan.next();
            if (currentPin.equals(pPin)){
                return true;//the pin already existss
            }
        }
        return false;
    }
    private void writeNewPin(String pPin){
        PrintWriter writer = TextManager.openWriteFile("Pins.txt");
        TextManager.addRecord(writer, pPin);
        TextManager.closeFile(writer);
    }
    
}



