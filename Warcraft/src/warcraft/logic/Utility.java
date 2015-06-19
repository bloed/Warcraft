package warcraft.logic;
import java.util.Random;


public class Utility {
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
        Integer[] boatsId = new Integer[pAmountOfBoats];
        Integer randomBoatId;
        for(int arrayIndex = 0 ; arrayIndex < pAmountOfBoats ; arrayIndex++){
            randomBoatId = generateRand(Constants.MIN_ID, Constants.MAX_ID);
            boatsId[arrayIndex] = randomBoatId;
        }
        return boatsId;
    }
    public static void showBytes(byte [] buffer) {
        System.out.write(buffer, 0, buffer.length);
    }

    public static Boolean collide(int pPosX, int pPosY, Boat[] pBoats, int pPosX2, int pPosY2, Boolean shoot){
	for(int index = 0 ; index< pBoats.length; index++){
            if(pPosX != pBoats[index].getUIActions().getX() && pPosY != pBoats[index].getUIActions().getY())
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
	return false;
    }
    public final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;

    }
}
