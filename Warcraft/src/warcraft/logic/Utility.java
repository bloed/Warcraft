package warcraft.logic;
import java.util.Random;


public class Utility {
    //Class that contains static public methods for general purpose.
    public static Integer generateRand(int pMinRange, int pMaxRange){
        //inclusive for minRange, exclusive for maxRange
        Random generator = new Random();
        try{
            return generator.nextInt(pMaxRange - pMinRange) + pMinRange;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }    
    public static Integer[] generateBoatsId (Integer pAmountOfBoats){
        //generates random ids for all the boats
        Integer[] boatsId = new Integer[pAmountOfBoats];
        Integer randomBoatId;
        for(int arrayIndex = 0 ; arrayIndex < pAmountOfBoats ; arrayIndex++){//depending of the processor count
            randomBoatId = generateRand(Constants.MIN_ID, Constants.MAX_ID);
            boatsId[arrayIndex] = randomBoatId;
        }
        return boatsId;
    }
    public static void showBytes(byte [] pBuffer) {
        System.out.write(pBuffer, 0, pBuffer.length);
    }
    public static Boolean collide(int pPosX, int pPosY, Boat[] pBoats, int pPosX2, int pPosY2, Boolean shoot, Boat pBoat){
	for(int index = 0 ; index< pBoats.length; index++){
            if(pBoats[index].getUIActions() != null && !pBoats[index].equals(pBoat)){
		if(pPosX < pBoats[index].getUIActions().getX()+pBoats[index].getUIActions().getWidth()
                        && pPosX2 > pBoats[index].getUIActions().getX() 
                        && pPosY < pBoats[index].getUIActions().getY()+pBoats[index].getUIActions().getHeigth()
                        && pPosY2> pBoats[index].getUIActions().getY()){
                    if(shoot){
                        pBoats[index].getUIActions().hitted();
                        System.out.println("HIT!!!!");
                    }
                    
			return true;
		}
            }
	}
	return false;
    }
    public static String bytesToHex(byte[] pBytes) {
        char[] hexChars = new char[pBytes.length * 2];
        for (int byteIndex = 0; byteIndex < pBytes.length; byteIndex++ ) {
            int v = pBytes[byteIndex] & 0xFF;
            hexChars[byteIndex * 2] = hexArray[v >>> 4];
            hexChars[byteIndex * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    public static byte[] hexStringToByteArray(String pHexString) {
        int len = pHexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(pHexString.charAt(i), 16) << 4)
                                 + Character.digit(pHexString.charAt(i+1), 16));
        }
        return data;
    } 
    public static double getDegree(double pDegree){
        return pDegree * Constants.MAX_RAD_VAL;
    }
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
}
