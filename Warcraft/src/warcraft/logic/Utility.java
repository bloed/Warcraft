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
}
