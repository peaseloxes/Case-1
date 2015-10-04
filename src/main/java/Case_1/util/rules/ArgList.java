package Case_1.util.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class ArgList {
    private static List<String> list;

    private ArgList(List<String> list) {
        this.list = list;
    }

    /**
     * Takes a String and returns an ArgList.
     *
     * @param string the string to parse
     * @return an ArgList containing the string as list
     */
    public static ArgList valueOf(final String string) {
        // editors might think this method is unused, however
        // called via reflection in BusinessRules
        list = new ArrayList<>();
        String cleanString = string.trim().toUpperCase();
        list.addAll(Arrays.asList(cleanString.split(",")));
        return new ArgList(list);
    }

    /**
     * Checks if the argument is contained by the list of Strings.
     *
     * @param argument the argument to check for
     * @return true if present, false otherwise
     */
    public boolean contains(final String argument) {
        return list.contains(argument);
    }
}
