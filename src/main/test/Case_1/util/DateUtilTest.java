package Case_1.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by alex on 10/7/15.
 */
public class DateUtilTest {

    @Test
    public void testGetNextYearWeek() throws Exception {
        String result = DateUtil.getNextYearWeek(2013,40);
        assertThat(result, is("/2013/41"));

        result = DateUtil.getNextYearWeek(2014,52);
        assertThat(result, is("/2014/1"));
    }

    @Test
    public void testGetPreviousYearWeek() throws Exception {
        String result = DateUtil.getPreviousYearWeek(2013, 40);
        assertThat(result, is("/2013/39"));

        result = DateUtil.getPreviousYearWeek(2013,1);
        assertThat(result, is("/2012/52"));
    }
}