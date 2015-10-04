package Case_1.util.rules;

import Case_1.util.i18n.LangUtil;
import Case_1.util.pref.PrefUtil;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class BusinessRules {
    private static Map<String, String> rulesMap;

    private static final Logger LOGGER = LogManager
            .getLogger(BusinessRules.class.getName());

    private static String ruleFileName;

    static {
        ruleFileName = "rules.properties";
        try {
            rulesMap = getRules();
        } catch (PrefUtil.PropertyNotFoundException e) {
            LOGGER.error(LangUtil
                    .labelFor("error.properties.notFound"));
            // not much else we can do
        }
    }

    /**
     * Set the rule properties file location and reload the rules.
     *
     * @param newFileName the new file name
     * @throws PrefUtil.PropertyNotFoundException if the file can not be read
     */
    public static void setRuleFileName(final String newFileName)
            throws PrefUtil.PropertyNotFoundException {
        ruleFileName = newFileName;

        // refresh rules
        rulesMap = getRules();
    }

    /**
     * Load the rules from the properties file.
     *
     * @return the map of rules.
     * @throws PrefUtil.PropertyNotFoundException if the the file can not be
     *                                            read.
     */
    private static final Map<String, String> getRules()
            throws PrefUtil.PropertyNotFoundException {

        try {
            // ensure we have the rule properties loaded in PrefUtil
            PrefUtil.setPropertiesFileName(ruleFileName);

            Properties props = PrefUtil.getPROPERTIES();

            Map<String, String> map = new HashMap<>();

            for (final String propName : props.stringPropertyNames()) {
                map.put(propName, props.getProperty(propName));
            }

            // put the default properties file back in PrefUtil
            PrefUtil.setPropertiesFileName("config.properties");

            return map;
        } catch (IOException e) {
            LOGGER.error(LangUtil
                    .labelFor("error.properties.notFound"));

            // woot! anarchy!
            throw new PrefUtil.PropertyNotFoundException();
        }
    }

    /**
     * Converts rule values to the desired type.
     *
     * @param ruleName name of the rule to find
     * @param clazz    the expected return class
     * @param <T>      the expected return type
     * @return the rule as type <b>T</b>
     * @throws RuleNotFoundException if the rule can not be found or is in a
     *                               wrong format.
     */
    public static <T> T get(final String ruleName, final Class<T> clazz)
            throws RuleNotFoundException {

        try {
            // fetch the rule from the map
            String ruleValue = rulesMap.get(ruleName);

            // no sense in converting if there is no value to convert
            if (ruleValue == null) {
                // don't let the door hit you on the way out
                throw new NullPointerException();
            }

            // if the desired return type is String we can return the value
            // directly
            if (clazz.equals(String.class)) {

                // in this case T is of type String anyway
                return (T) ruleValue;
            }

            // TODO support more conversion methods
            // See rules.ArgList for a way to create custom objects
            // for parsing new types, i.e. arrays

            // get the valueOf method for the desired return type
            Method valueOf = clazz.getMethod("valueOf", String.class);

            // return the result of the method invocation
            return (T) valueOf.invoke(clazz, ruleValue);
        } catch (NoSuchMethodException
                | InvocationTargetException
                | IllegalAccessException
                | NullPointerException e) {

            // eat expected exceptions and convert them to RuleNotFoundException
            throw new RuleNotFoundException(LangUtil
                    .labelFor("error.rules.notFound")
                    + " "
                    + ruleName
                    + " > "
                    + clazz.getClass().getName());
        }
    }

    @AllArgsConstructor
    public static class RuleNotFoundException extends Exception {
        private String message;
    }
}
