package warcraft.logic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;

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
    
    public void generateKeys(Integer pPin){
        try{
            byte[] seed = ByteBuffer.allocate(4).putInt(pPin).array();//transforms integer into array of bytes
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(512, new SecureRandom(seed));
            KeyPair kp = kpg.genKeyPair();
            Key publicKey = kp.getPublic();
            //System.out.println(publicKey);
            Key privateKey = kp.getPrivate(); 
            //System.out.println(privateKey);
            // now we proceed to save them
            /*KeyFactory fact = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(),
            RSAPublicKeySpec.class);
            RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(),
            RSAPrivateKeySpec.class);

            saveToFile("public.key", pub.getModulus(),
              pub.getPublicExponent());
            saveToFile("private.key", priv.getModulus(),
              priv.getPrivateExponent());*/
            RSAPrivateKey clavePrivadaRSA = (RSAPrivateKey) privateKey;
            System.out.println("exponente descrifrado: " + clavePrivadaRSA.getPrivateExponent().toString() );
            System.out.println("modulo: " + clavePrivadaRSA.getModulus().toString() );

            RSAPublicKey clavePublicaRSA = (RSAPublicKey) publicKey;
            System.out.println("exponente cifrado: " + clavePublicaRSA.getPublicExponent().toString() );
            System.out.println("modulo: " + clavePublicaRSA.getModulus().toString() );
            
            byte[] cipherData = encrypt(publicKey , "asdfasdf");
            deEncrypt(privateKey, cipherData);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }    
    }
    public void saveToFile(String fileName,
        BigInteger mod, BigInteger exp) throws IOException {
        
        ObjectOutputStream oout = new ObjectOutputStream(
          new BufferedOutputStream(new FileOutputStream(fileName)));
        try {
          oout.writeObject(mod);
          oout.writeObject(exp);
        } catch (Exception e) {
          throw new IOException("Unexpected error", e);
        } finally {
          oout.close();
        }
    }
    
    public byte[] encrypt(Key pKey, String pString){
        try{
            byte[] dataBytes = pString.getBytes();
            //byte[] b = string.getBytes(Charset.forName("UTF-8"));
            _Cipher.init(Cipher.ENCRYPT_MODE, pKey);  // Cifra con la clave publica
            System.out.println("3a. Cifrar con clave publica");
            byte[] cipherData = _Cipher.doFinal(dataBytes);
            System.out.println("TEXTO CIFRADO");
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
            System.out.println("TEXTO DESCIFRADO");
            Utility.showBytes(deEncryptData);
            System.out.println("\n-------------------------------");
            return null;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    

}
 

