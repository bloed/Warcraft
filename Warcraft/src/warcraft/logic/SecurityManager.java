package warcraft.logic;

import java.io.PrintWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
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
            keysGenerator.initialize(1024, new SecureRandom(seed));
            KeyPair keyPair =  keysGenerator.generateKeyPair();
            return keyPair;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }    
    }
    
    public byte[] encrypt(Key pKey, String pString){
        try{
            byte[] dataBytes = pString.getBytes();
            //byte[] b = string.getBytes(Charset.forName("UTF-8"));
            _Cipher.init(Cipher.ENCRYPT_MODE, pKey);  // Cifra con la clave publica
            System.out.println("3a. Cifrar con clave publica");
            byte[] cipherData = _Cipher.doFinal(dataBytes);
            System.out.println("TEXTO CIFRADO:");
            Utility.showBytes(cipherData);
            System.out.println("\n-------------------------------");
            return cipherData;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public String deEncrypt(Key pKey, byte[] cipherData){
        try{
            _Cipher.init(Cipher.DECRYPT_MODE, pKey); // Descrifra con la clave privad
            System.out.println("3b. Descifrar con clave privada");
            byte[] deEncryptData = _Cipher.doFinal(cipherData);
            System.out.println("TEXTO DESCIFRADO:");
            Utility.showBytes(deEncryptData);
            System.out.println("\n-------------------------------");
            return new String(deEncryptData, "UTF-8");//transforms the deencrypted dat to a string
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return "";
        }
    }   
    public void mainAsymetricEncryption(String pMessage){
        String pin = Utility.generateRand(1000, 10000).toString();
        while(isNotValidPin(pin)){//pin must be unique
            pin = Utility.generateRand(1000, 10000).toString();
        }
        writeNewPin(pin);//the new ping used to generate keys must be addes to the .txt
        KeyPair keyPair = generateKeys(pin);
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();
        byte [] encryptData = encrypt(publicKey , pMessage);
    }
    
    public String mainAsymetricDEncryption(byte[] EncyprtedData, String pPin){
        if (isNotValidPin(pPin)){
            //dencrypt
            KeyPair keyPair = generateKeys(pPin);
            Key privateKey = keyPair.getPrivate();
            deEncrypt(privateKey, EncyprtedData);
        }
        else{ 
            JOptionPane.showMessageDialog(null, "The pin that was given doesn´t correpond to any game.");
        }
        return "";
    }
    public Boolean isNotValidPin(String pPin){
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
    public void writeNewPin(String pPin){
        PrintWriter writer = TextManager.openWriteFile("Pins.txt");
        TextManager.addRecord(writer, pPin);
        TextManager.closeFile(writer);
    }
}
 

