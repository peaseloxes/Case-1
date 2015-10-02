package Case_1.util.pref;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */

import lombok.AccessLevel;
import lombok.Getter;


/**
 * Used to fetch properties from the config.properties file.
 */
public enum Property {

    /**
     * Returns the connection url for the database driver.
     */
    DB_URL("jdbc:oracle:thin:@localhost:1521:XE") {
        @Override
        protected String getResponse() throws PrefUtil
                .PropertyNotFoundException {
            String driver = PrefUtil.getProperty("datasource_driver");
            String host = PrefUtil.getProperty("datasource_host");
            String port = PrefUtil.getProperty("datasource_port");
            String dbName = PrefUtil.getProperty("datasource_dbName");
            return driver + "@" + host + ":" + port + ":" + dbName;
        }
    },

    /**
     * Returns the database username.
     */
    DB_USERNAME("alex") {
        @Override
        protected String getResponse() throws PrefUtil
                .PropertyNotFoundException {
            return PrefUtil.getProperty("user");
        }
    },

    DB_PASSWORD("oracle") {
        @Override
        protected String getResponse() throws PrefUtil
                .PropertyNotFoundException {
            return PrefUtil.getProperty("password");
        }
    };

    /**
     * Contains the default response for the enumeration.
     */
    @Getter(AccessLevel.PROTECTED)
    private String defaultResponse;

    /**
     * Constructor containing the default response.
     *
     * @param defaultResponse the default response.
     */
    Property(final String defaultResponse) {
        this.defaultResponse = defaultResponse;
    }

    /**
     * Base method overriding toString, calling each Property's getter.
     *
     * @return the value belonging to the specific Property.
     */
    @Override
    public String toString() {
        try {
            return this.getResponse();
        } catch (PrefUtil.PropertyNotFoundException e) {
            // catch exception and return default
            return getDefaultResponse();
        }
    }

    /**
     * Returns the response from the Property enum.
     *
     * @return the value retrieved by the Property.
     * @throws PrefUtil.PropertyNotFoundException if the property can not be
     * read.
     */
    protected abstract String getResponse() throws
            PrefUtil.PropertyNotFoundException;
}