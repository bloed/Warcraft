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
}
