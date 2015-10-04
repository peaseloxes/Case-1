package Case_1.util.rules;

import Case_1.util.pref.PrefUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class BusinessRulesTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static final String VALID_TEST_CONFIG_FILE = "testRules.properties";

    @Before
    public void setUp() throws Exception {
        BusinessRules.setRuleFileName(VALID_TEST_CONFIG_FILE);
    }

    @Test
    public void testSetRuleFileName() throws Exception {
        // all good
        BusinessRules.setRuleFileName(VALID_TEST_CONFIG_FILE);

        // should blow up
        thrown.expect(PrefUtil.PropertyNotFoundException.class);
        BusinessRules.setRuleFileName("does not exist");

    }

    @Test
    public void testGet() throws Exception {
        assertThat(BusinessRules.get("number", Integer.class), is(1));
        assertThat(BusinessRules.get("number", String.class), is("1"));
        assertThat(BusinessRules.get("number", Double.class), is(1.0));

        assertThat(BusinessRules.get("decimal", Double.class), is(1.5));
        assertThat(BusinessRules.get("decimal", Float.class), is(1.5F));

        assertThat(BusinessRules.get("text", String.class), is("text"));

        thrown.expect(BusinessRules.RuleNotFoundException.class);
        BusinessRules.get("text",Integer.class);

    }

    @Test
    public void testGetNull() throws Exception {
        thrown.expect(BusinessRules.RuleNotFoundException.class);
        BusinessRules.get(null,Integer.class);
    }
}