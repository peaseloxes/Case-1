package Case_1.logic.concrete;

import Case_1.domain.concrete.Course;
import Case_1.logic.abs.IncorrectVariablesException;
import Case_1.util.pref.PrefUtil;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class CourseParser {

    private List<Course> courseList = new ArrayList<>();

    private static String courseFileSeparator;
    private static String courseFileTitle;
    private static String courseFileCode;
    private static String courseFileDuration;
    private static String courseFileStartDate;

    /**
     * Note, courses <b>in this list</b> will be unique.
     *
     * @param lines the list of lines to parse
     * @return a List of unique courses and their instances
     * @throws CourseParsingException if a parsing error occurs
     */
    public List<Course> parse(final List<String> lines) throws
            CourseParsingException {

        try {
            loadParsingPreferences();
        } catch (PrefUtil.PropertyNotFoundException e) {
            // TODO use case validate and explain
            throw new CourseParsingException("help me, I'm trapped in an " +
                    "Exception factory");
        }

        // reset the list
        courseList = new ArrayList<>();


        // read in chunks of 5 lines
        int i = 0;
        while (i < lines.size()) {

            // line 1 of 5 > title
            final String title = extractTitle(lines.get(i));

            // line 2 of 5 > code
            i++;
            final String code = extractCode(lines.get(i));

            // line 3 of 5 > duration
            i++;
            final int duration = extractDuration(lines.get(i));

            // line 4 of 5 > starting date
            i++;
            final LocalDateTime startDate = extractStartDate(lines.get(i));

            // line 5 of 5 > save
            try {
                CourseBuilder builder = CourseBuilder.getInstance();


                courseList.add(builder.create());
            } catch (IncorrectVariablesException e) {

                // TODO use case validate and explain
                // for now just throw a hissyfit
                throw new CourseParsingException("ruh roh");
            }
            // at the end in case the section does not end with a newline
            i++;
        }
        return courseList;
    }

    ////////////////////////////////////////////////////////////////////
    // Flow for all extractor methods:
    // titleString -> comes in as parameter
    // Titel: stuff we @#! want
    //
    // dirtyTitle -> replace titleString with placeholders
    // {title}{separator}{space}stuff we @#! want
    //
    // validate that we have a correct title by checking for
    // {title}{separator}{space}
    //
    // cleanTitle -> replace placeholders in dirtyTitle with nothingness
    // stuff we @#! want
    //
    // return cleanTitle

    private String extractTitle(final String titleString)
            throws CourseParsingException {


        final String[] placeholders = new String[]{
                "{title}",
                "{separator}{space}"
        };

        final String[] occurrences = new String[]{
                courseFileTitle,
                courseFileSeparator + " "
        };

        // replace all the occurrences with their placeholders
        String dirtyTitle = replace(titleString, occurrences, placeholders);

        // validate all these things were present
        validatePresence(dirtyTitle, placeholders, courseFileTitle);

        String cleanTitle = replace(
                dirtyTitle,
                placeholders,
                new String[]{"", ""});

        return cleanTitle;
    }

    private String extractCode(final String codeString)
            throws CourseParsingException {

        final String[] placeholders = new String[]{
                "{code}",
                "{separator}{space}"
        };

        final String[] occurrences = new String[]{
                courseFileCode,
                courseFileSeparator + " "
        };

        // replace occurrences with placeholders
        String dirtyCode = replace(codeString, occurrences, placeholders);

        // validate all these things were present
        validatePresence(dirtyCode, placeholders, courseFileCode);

        // remove placeholders
        String cleanCode = replace(
                dirtyCode,
                placeholders,
                new String[]{"", ""});

        return cleanCode;
    }

    private int extractDuration(final String durationString)
            throws CourseParsingException {

        final String[] placeholders = new String[]{
                "{duration}",
                "{separator}{space}"
        };

        final String[] occurrences = new String[]{
                courseFileDuration,
                courseFileSeparator + " "
        };

        // replace occurrences with placeholders
        String dirtyDuration = replace(durationString, occurrences, placeholders);

        // validate all these things were present
        validatePresence(dirtyDuration, placeholders, courseFileDuration);

        // remove placeholders
        String cleanDuration = replace(
                dirtyDuration,
                placeholders,
                new String[]{"", ""});

        int duration; // default to 0 ok?
        try{
            duration = Integer.valueOf(cleanDuration);
        } catch(NumberFormatException e) {
            // TODO use case validation etc.
            throw new CourseParsingException("duration not a number");
        }
        return duration;
    }

    private LocalDateTime extractStartDate(final String startDateString)
            throws CourseParsingException {

        final String[] placeholders = new String[]{
                "{date}",
                "{separator}{space}"
        };

        final String[] occurrences = new String[]{
                courseFileStartDate,
                courseFileSeparator + " "
        };

        // replace occurrences with placeholders
        String dirtyDate = replace(startDateString, occurrences, placeholders);

        // validate all these things were present
        validatePresence(dirtyDate, placeholders, courseFileStartDate);

        // remove placeholders
        String cleanDate = replace(
                dirtyDate,
                placeholders,
                new String[]{"", ""});

        LocalDateTime duration = null;
        try{
            //duration = LocalDateTime.(cleanDate);
        } catch(NumberFormatException e) {
            // TODO use case validation etc.
            throw new CourseParsingException("duration not a number");
        }
        return duration;
    }

    /**
     * Replaces an occurrence with a replacement in line.
     *
     * @param line         the line to replace occurrences in
     * @param occurrences  the occurrences to replace
     * @param replacements the replacements to replace with
     * @return a String with the replacements
     * @throws IllegalArgumentException if array lengths aren't equal
     */
    private String replace(final String line,
                           final String[] occurrences,
                           final String[] replacements)
            throws IllegalArgumentException {

        if (occurrences.length != replacements.length) {
            throw new IllegalArgumentException("You shall not pass!");
        }

        String newLine = line;
        for (int i = 0; i < occurrences.length; i++) {
            newLine = newLine.replace(occurrences[i], replacements[i]);
        }
        return newLine;
    }

    /**
     * Checks for the presence of all <b>placeHolder</b> Strings in
     * <b>toValidate</b>.
     *
     * @param toValidate   the String to validate
     * @param placeHolders the placeholders to check for
     * @param lineName     the name of the line being checked
     * @throws CourseParsingException if a placeholder is not present
     */
    private void validatePresence(final String toValidate,
                                  final String[] placeHolders,
                                  final String lineName)
            throws CourseParsingException {

        // for all things
        for (int i = 0; i < placeHolders.length; i++) {

            // if thingy not present
            if (!toValidate.contains(placeHolders[i])) {

                // TODO use case yada yada
                // keep calm and panic
                throw new CourseParsingException("Illegal "
                        + lineName
                        + " line, contains no "
                        + placeHolders[i]);
            }
        }
    }

    /**
     * Loads the preferences regarding file configurations.
     *
     * @throws PrefUtil.PropertyNotFoundException when there is no file
     */
    private void loadParsingPreferences() throws PrefUtil
            .PropertyNotFoundException {
        courseFileSeparator = PrefUtil.getProperty("course_file_separator");
        courseFileTitle = PrefUtil.getProperty("course_file_title");
        courseFileCode = PrefUtil.getProperty("course_file_code");
        courseFileDuration = PrefUtil.getProperty("course_file_duration");
        courseFileStartDate = PrefUtil.getProperty("course_file_start_date");
    }

    @AllArgsConstructor
    public class CourseParsingException extends Exception {
        private String message;
    }
}
