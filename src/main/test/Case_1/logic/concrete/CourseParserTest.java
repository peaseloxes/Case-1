package Case_1.logic.concrete;

import Case_1.domain.concrete.Course;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by alex on 10/5/15.
 */
public class CourseParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    // provided lists
    private List<String> validCourse1;
    private List<String> invalidCourse1;
    private List<String> invalidCourse2;
    private List<String> invalidCourse3;
    private List<String> invalidCourse4;
    private List<String> invalidCourse5;

    // own lists
    private List<String> invalidCourse6;
    private List<String> invalidCourse7;
    private List<String> invalidCourse8;

    @Before
    public void setUp() throws Exception {
        validCourse1 = new ArrayList<>();
        validCourse1.add("Titel: C# Programmeren");
        validCourse1.add("Cursuscode: CNETIN");
        validCourse1.add("Duur: 5 dagen");
        validCourse1.add("Startdatum: 14/10/2013");
        validCourse1.add("");
        validCourse1.add("Titel: C# Programmeren");
        validCourse1.add("Cursuscode: CNETIN");
        validCourse1.add("Duur: 5 dagen");
        validCourse1.add("Startdatum: 21/10/2013");
        validCourse1.add("");
        validCourse1.add("Titel: Advanced C#");
        validCourse1.add("Cursuscode: ADCSB");
        validCourse1.add("Duur: 2 dagen");
        validCourse1.add("Startdatum: 21/10/2013");
        validCourse1.add("");

        invalidCourse1 = new ArrayList<>();
        invalidCourse1.add("Titel: C# Programmeren");
        invalidCourse1.add("Duur: 5 dagen");
        invalidCourse1.add("Cursuscode: CNETIN");
        invalidCourse1.add("Startdatum: 14/10/2013"); // <-- duplicate
        invalidCourse1.add("");

        invalidCourse2 = new ArrayList<>();
        invalidCourse2.add("Titel: C# Programmeren");
        invalidCourse2.add("Cursuscode: CNETIN");
        invalidCourse2.add("Startdatum: 14/10/2013");// <-- duration expected

        invalidCourse3 = new ArrayList<>();
        invalidCourse3.add("Titel: C# Programmeren");
        invalidCourse3.add("Cursuscode: CNETIN");
        invalidCourse3.add("Duur: 5 dagen");
        invalidCourse3.add("Startdatum: 14-10-2013");// <-- wrong format

        invalidCourse4 = new ArrayList<>();
        invalidCourse4.add("Titel: C# Programmeren");
        invalidCourse4.add("Cursuscode: CNETIN");
        invalidCourse4.add("Duur: 5");// <-- missing days identifier
        invalidCourse4.add("Startdatum: 14/10/2013");
        invalidCourse4.add("");

        invalidCourse5 = new ArrayList<>();
        invalidCourse5.add("Titel: C# Programmeren");
        invalidCourse5.add("Cursuscode: CNETIN");
        invalidCourse5.add("Duur: 5 dagen");
        invalidCourse5.add("Startdatum: 14/10/2013");
        invalidCourse5.add("Titel: Advanced C#");// <-- expecting new line
        invalidCourse5.add("Cursuscode: ADCSB");
        invalidCourse5.add("Duur: 2 dagen");
        invalidCourse5.add("Startdatum: 16/10/2013");
        invalidCourse5.add("");

        invalidCourse6 = new ArrayList<>();
        invalidCourse6.add("Titel: C# Programmeren");
        invalidCourse6.add("Cursuscode: CNETIN");
        invalidCourse6.add("Duur: vijf dagen");// <-- expecting number
        invalidCourse6.add("Startdatum: 14/10/2013");
        invalidCourse6.add("");


        // TODO retrieve from businessrules
        invalidCourse7 = new ArrayList<>();
        invalidCourse7.add("Titel: C# Programmeren");
        invalidCourse7.add("Cursuscode: CNETIN");
        invalidCourse7.add("Duur: 6 dagen");// <-- breaking business rule
        invalidCourse7.add("Startdatum: 14/10/2013");
        invalidCourse7.add("");

        invalidCourse8 = new ArrayList<>();
        invalidCourse8.add("");// <- expecting title
        invalidCourse8.add("Titel: C# Programmeren");
        invalidCourse8.add("Cursuscode: CNETIN");
        invalidCourse8.add("Duur: 5 dagen");
        invalidCourse8.add("Startdatum: 14/10/2013");
        invalidCourse8.add("");

    }

    @Test
    public void testParse() throws Exception {
        List<Course> courses = new CourseParser().parse(validCourse1);
        Course course1 = courses.get(0);

        // TODO change after uniqueness has been implemented
        assertThat(courses.size(), is(3));
        assertThat(course1.getTitle(), is("C# Programmeren"));
        assertThat(course1.getDurationDays(), is(5));
        LocalDateTime startDate = course1.getInstances().get(0).getStartDate();
        assertThat(startDate, is(LocalDateTime.of(2013, 10, 14, 0, 0)));
        assertThat(course1.getInstances().get(0).getEndDate(), is(startDate.plusDays(4)));
    }

    @Test
    public void testInvalidCourse1() throws Exception {
        // TODO invalidcourse1
    }

    @Test
    public void testInvalidCourse2() throws Exception {
        // missing duration
        thrown.expect(CourseParser.CourseParsingException.class);
        new CourseParser().parse(invalidCourse2);
    }

    @Test
    public void testInvalidCourse3() throws Exception {
        // date in wrong format
        thrown.expect(CourseParser.CourseParsingException.class);
        new CourseParser().parse(invalidCourse3);
    }

    @Test
    public void testInvalidCourse4() throws Exception {
        // missing duration day identifier
        thrown.expect(CourseParser.CourseParsingException.class);
        new CourseParser().parse(invalidCourse4);
    }

    @Test
    public void testInvalidCourse5() throws Exception {
        // no newline separating courses
        thrown.expect(CourseParser.CourseParsingException.class);
        new CourseParser().parse(invalidCourse5);
    }

    @Test
    public void testInvalidCourse6() throws Exception {
        // duration not a number
        thrown.expect(CourseParser.CourseParsingException.class);
        new CourseParser().parse(invalidCourse6);
    }

    @Test
    public void testInvalidCourse7() throws Exception {
        // duration breaking business rule
        thrown.expect(CourseParser.CourseParsingException.class);
        new CourseParser().parse(invalidCourse7);
    }

    @Test
    public void testInvalidCourse8() throws Exception {
        // duration breaking business rule
        thrown.expect(CourseParser.CourseParsingException.class);
        new CourseParser().parse(invalidCourse8);
    }
}