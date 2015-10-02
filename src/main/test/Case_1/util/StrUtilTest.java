package Case_1.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class StrUtilTest {

    @Test
    public void testRandomString() throws Exception {
        assertThat(StrUtil.randomString(50),notNullValue());
    }
}