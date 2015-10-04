package Case_1.util.pref;

import Case_1.util.i18n.LangUtil;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class PrefUtil {
    private static final Logger LOGGER = LogManager.getLogger(PrefUtil.class
            .getName());

    @Getter
    private static final Properties PROPERTIES = new Properties();

    /**
     * The default properties file.
     */
    @Getter
    private static String propertiesFileName = "config.properties";

    static {
        try {

            // load properties
            loadProperties();

            // set locale based on properties
            setLocale();

        } catch (IOException e) {

            // warn user via log message
            LOGGER.error(LangUtil.labelFor("error.properties.notFound"));

            e.printStackTrace();
        }
    }

    /**
     * Change the properties file path.
     * <p/>
     * The properties file will be reloaded.
     *
     * @param path the path to the new properties file
     * @throws IOException if the file can not be read
     */
    public static void setPropertiesFileName(final String path) throws
            IOException, PropertyNotFoundException {
        PrefUtil.propertiesFileName = path;

        // reload properties
        loadProperties();

        // reload locale
        setLocale();
    }


    /**
     * Retrieves a property from the properties file.
     *
     * @param propertyName the property key
     * @return the property value
     * @throws PropertyNotFoundException if the property can not be found.
     */
    public static String getProperty(final String propertyName) throws
            PropertyNotFoundException {
        final String result = PROPERTIES.getProperty(propertyName);
        if (result != null) {
            return result;
        } else {
            throw new PropertyNotFoundException();
        }
    }

    /**
     * Loads properties from the config.properties file.
     * <p/>
     * Will throw an IOException if the config file is unreadable or missing.
     *
     * @throws IOException if the config file can not be read.
     */
    private static void loadProperties() throws IOException {
        InputStream stream = PrefUtil.class.getClassLoader()
                .getResourceAsStream(propertiesFileName);

        if (stream != null) {
            PROPERTIES.clear();
            PROPERTIES.load(stream);
            stream.close();
        } else {
            // stream already null, no need to close
            throw new FileNotFoundException(LangUtil.labelFor("error" +
                    ".properties.notFound"));
        }
    }

    /**
     * Sets the locale based on the properties file.
     */
    private static void setLocale() {
        try {
            LangUtil.setLocale(PrefUtil.getProperty("locale"));
        } catch (PropertyNotFoundException e) {
            LangUtil.setDefaultLocale();
        }
    }

    /**
     * A custom exception for when properties can not be read.
     * Implies the use of default properties.
     */
    public static class PropertyNotFoundException extends Exception {
        // no need for messages
    }
}
