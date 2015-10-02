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
     * @param bits the number of bits used in the generation
     * @return a random string
     */
    public static String randomString(final int bits) {
        return new BigInteger(bits, new Random()).toString(32);
    }
}
