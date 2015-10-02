package Case_1.util;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by alex on 9/30/15.
 */
public class NumUtilTest extends TestCase {
    private final int amountOfTests = 100;

    @Test
    public void testGetRandomInt() throws Exception {
        for (int i = 0; i < amountOfTests; i++) {
            assertTrue(NumUtil.getRandomInt(5) < 5);
        }
    }

    @Test
    public void testGetRandomDouble() throws Exception {
        for (int i = 0; i < amountOfTests; i++) {
            assertTrue(NumUtil.getRandomDouble(5) < 5);
        }
    }

    @Test
    public void testFiftyFifty() throws Exception {
        for (int i = 0; i < amountOfTests; i++) {
            int count = 100;
            boolean success = false;
            while (count > 0) {
                if (NumUtil.fiftyFifty()) {
                    success = (true);
                }
                count--;
            }
            if (!success) {
                fail("Not one truth was generated in 100 tries with the fifty fifty util");
            }
        }

    }

    @Test
    public void testOneInTen() throws Exception {
        for (int i = 0; i < amountOfTests; i++) {
            int count = 1000;
            boolean success = false;
            while (count > 0) {
                if (NumUtil.oneInTen()) {
                    success = true;
                }
                count--;
            }
            if (!success) {
                fail("Not one truth was generated in 1000 tries with the one in ten util");
            }
        }
    }
}