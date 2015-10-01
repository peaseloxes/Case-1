package Case_1.util;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by alex on 10/1/15.
 */
public class StrUtil {

    /**
     * Returns a string filled with random characters.
     *
     * @param length an indication of length, the number of bits
     * @return a random string
     */
    public static String randomString(final int length){
        Random random = new Random();
        return new BigInteger(length, random).toString(32);
    }
}
