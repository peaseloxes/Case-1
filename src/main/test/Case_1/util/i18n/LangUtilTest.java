package Case_1.util.i18n;

import Case_1.util.pref.PrefUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class LangUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        // LangUtil will be in locale specified by testconfig.properties
        // via PrefUtil.
        PrefUtil.setPropertiesFileName("testconfig.properties");
    }

    @Test
    public void testSetLocale() throws Exception {
        // set to default Locale
        LangUtil.setLocale("nl_NL");

        // test that bundle is now in default locale
        assertThat(LangUtil.getBundle().getLocale(), is(new Locale("nl_NL")));
    }

    @Test
    public void testGetBundle() throws Exception {

        // get the bundle
        ResourceBundle bundle = LangUtil.getBundle();

        // bundle exists
        assertThat(bundle, notNullValue());

        // bundle is in language specified by testconfig.properties
        assertThat(bundle.getLocale().getLanguage(),
                is(new Locale("en_us").getLanguage()));
    }

    @Test
    public void testLabelFor() throws Exception {

        // test existing label
        assertThat(LangUtil.labelFor("name"), is("Course Planner"));

        // test non-existent label
        assertThat(LangUtil.labelFor("does not exist"), is(LangUtil.MISSING_LABEL));
    }

    @Test
    public void testSetDefaultLocale() throws Exception {
        // set to default Locale
        LangUtil.setDefaultLocale();

        // test that bundle is now in default locale
        assertThat(LangUtil.getBundle().getLocale(), is(Locale.getDefault()));
    }
}