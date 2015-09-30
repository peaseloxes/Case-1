package Case_1.util;

import java.util.Random;

/**
 * Created by alex on 9/30/15.
 */
public class NumUtil {

    public static int getRandomInt(final int max) {
        Random r = new Random();
        return r.nextInt(max);
    }

    public static double getRandomDouble(final int max) {
        Random r = new Random();
        double decimals = (r.nextDouble()) - 0.01;
        int num = getRandomInt(max);
        return num + decimals;
    }

    public static boolean fiftyFifty() {
        Random r = new Random();
        return r.nextBoolean();
    }

    public static boolean oneInTen() {
        Random r = new Random();
        if (r.nextDouble() < 0.11) {
            return true;
        }
        return false;
    }
}
