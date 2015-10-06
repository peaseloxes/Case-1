package Case_1.util.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class LangUtil {
    public static final String MISSING_LABEL = "<<label missing from file>>";

    private static final String BUNDLE = "i18n.label";

    private static ResourceBundle bundle;

    /**
     * The default locale in case the preferred one can not be read from
     * config.properties.
     */
    private static Locale locale;

    /**
     * Change the locale using a locale string.
     * <p/>
     * i.e.
     * <p/>
     * nl_nl
     * en_us
     *
     * @param locale the locale string
     */
    public static void setLocale(final String locale) {
        // update locale
        LangUtil.locale = new Locale(locale);

        // reset the bundle
        resetBundle();
    }

    /**
     * Returns the resource bundle for the locale specified in config
     * .properties.
     * <p/>
     * If the config.properties file can not be found it will use the JVM
     * default locale.
     *
     * @return The resource bundle for the preferred locale.
     */
    public static ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * Shorthand method to get a label from the current bundle.
     *
     * @param labelName the label name
     * @return the corresponding value in the current locale bundle
     */
    public static String labelFor(final String labelName) {
        try {
            return getBundle().getString(labelName);
        } catch (NullPointerException | MissingResourceException e) {

            // obviously can't use a label for this
            return MISSING_LABEL;
        }
    }

    /**
     * Resets the locale to the default JVM locale.
     */
    public static void setDefaultLocale() {
        LangUtil.locale = Locale.getDefault();

        // reset the bundle
        resetBundle();
    }

    /**
     * Reset the bundle.
     */
    private static void resetBundle() {
        LangUtil.bundle = ResourceBundle.getBundle(BUNDLE, LangUtil.locale);
    }
}
