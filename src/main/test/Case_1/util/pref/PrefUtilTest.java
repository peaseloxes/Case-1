package Case_1.util.pref;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class PrefUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        // change the properties to the test properties file
        // so we know what to expect
        PrefUtil.setPropertiesFileName("testconfig.properties");
    }



    @Test
    public void testSetPropertiesFileName() throws Exception {

        // test a correct properties file
        PrefUtil.setPropertiesFileName("testconfig.properties");

        // test a non existent properties file
        thrown.expect(IOException.class);
        PrefUtil.setPropertiesFileName("nonExistentFile.properties");
    }

    @Test
    public void testProperties() throws Exception {

        // first test the pre-built properties
        assertThat(Property.DB_URL.toString(), is
                ("jdbc:mysql:thick:@127.0.0.1:1000:testDB"));
        assertThat(Property.DB_USERNAME.toString(), is("tester"));
        assertThat(Property.DB_PASSWORD.toString(), is("testing"));

        // test a custom property
        assertThat(PrefUtil.getProperty("custom_property"), is("c-c-c-custom " +
                "property"));

        // test a non existent property
        thrown.expect(PrefUtil.PropertyNotFoundException.class);
        PrefUtil.getProperty("non existent property");
    }

    @Test
    public void testMissingProperties() throws Exception {

        // expect an error because locale can not be read from this file
        // use a normal try-catch because we want to continue the test
        // beyond this point
        try {
            PrefUtil.setPropertiesFileName("testconfigmissing.properties");

            fail("PropertyNotFoundException expected");
        }catch (PrefUtil.PropertyNotFoundException e){
            // all good
        }

        // test we have the correct missing properties file
        assertThat(PrefUtil.getProperty("only_property"), is("Alllll byyyy " +
                "myyyy seeeeelf"));

        // test that we get the default value whe we request a pre-built property
        assertThat(Property.DB_USERNAME.toString(), is("alex"));

        // test that we get a property not found exception when calling custom properties
        thrown.expect(PrefUtil.PropertyNotFoundException.class);
        PrefUtil.getProperty("nonExistentProperty");
    }
}