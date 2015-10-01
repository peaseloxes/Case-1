package Case_1.util;

import java.util.Random;

/**
 * Created by alex on 9/30/15.
 */
public class NumUtil {

    /**
     * Returns a random integer between 0 and max.
     *
     * @param max the upper limit
     * @return a random integer
     */
    public static int getRandomInt(final int max) {
        Random r = new Random();
        return r.nextInt(max);
    }

    /**
     * Returns a random double between 0 and max.
     *
     * @param max the upper limit
     * @return  a random double
     */
    public static double getRandomDouble(final int max) {
        Random r = new Random();
        double decimals = (r.nextDouble()) - 0.01;
        int num = getRandomInt(max);
        return num + decimals;
    }

    /**
     * Performs a fifty - fifty dice roll.
     *
     * @return 50% on true, 50% on false
     */
    public static boolean fiftyFifty() {
        Random r = new Random();
        return r.nextBoolean();
    }

    /**
     * Performs a one in ten dice roll.
     *
     * @return 10% on true, 90% on false.
     */
    public static boolean oneInTen() {
        Random r = new Random();
        if (r.nextDouble() < 0.11) {
            return true;
        }
        return false;
    }
}
