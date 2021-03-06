package Case_1.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * Created by alex on 10/7/15.
 */
public class DateUtil {

    /**
     * Retrieves the next year/week given a current year/week
     *
     * @param year a year
     * @param week a week
     * @return /year/week
     */
    public static String getNextYearWeek(final int year, final int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.valueOf(getYearWeekDate(year, week).plusWeeks(1)));
        return "/" + calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Retrieves the previous year/week given a current year/week
     *
     * @param year a year
     * @param week a week
     * @return /year/week
     */
    public static String getPreviousYearWeek(final int year, final int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.valueOf(getYearWeekDate(year, week).minusWeeks(1)));
        return "/" + calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static String getStartDate(final int year, final int week){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week - 1);

        return format.format(calendar.getTime());
    }

    public static String getEndDate(final int year, final int week){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);

        return format.format(calendar.getTime());
    }

    /**
     * Converts a year and week into a date.
     * @param year the year
     * @param week the week
     * @return a date
     */
    private static LocalDate getYearWeekDate(final int year, final int week) {
        return LocalDate.parse(
                String.format("%s/%s/1", year, week),
                DateTimeFormatter.ofPattern("YYYY/w/e")
        );
    }
}
