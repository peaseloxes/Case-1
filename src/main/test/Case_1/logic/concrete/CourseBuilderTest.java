package Case_1.logic.concrete;

import Case_1.domain.concrete.Course;
import Case_1.domain.concrete.CourseInstance;
import Case_1.logic.abs.IncorrectVariablesException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class CourseBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGetInstance() throws Exception {
        assertThat(CourseBuilder.getInstance(),
                instanceOf(CourseBuilder.class));
    }

    @Test
    public void testValidate() throws Exception {
        CourseBuilder builder = CourseBuilder.getInstance();
        builder.id(1);
        builder.code("foo");
        builder.duration(5);
        builder.maxApplicants(1);

        assertThat(builder.validate(), is(true));
        builder.id(-1);
        assertThat(builder.validate(), is(false));

        builder.id(1);
        builder.maxApplicants(0);
        assertThat(builder.validate(), is(false));

        builder.id(1);
        builder.maxApplicants(1);
        builder.duration(Integer.MAX_VALUE);
        assertThat(builder.validate(), is(false));

        builder.duration(5);
        builder.code(null);
        assertThat(builder.validate(), is(false));

        builder.id(1);
        builder.code("foo");
        builder.maxApplicants(1);
        builder.duration(5);
        builder.instance(0, null, null, 0D);
        assertThat(builder.validate(), is(false));

        // note that with course date rules the day might be illegal
        // adjust accordingly, currently thursday 1st of january
        LocalDateTime localDateTime = LocalDateTime.of(2015, Month.JANUARY, 1
                , 2, 0);

        // builder needs to be new because one false instance will
        // prevent a second false instance from ever being checked

        // start date on an illegal day
        builder = CourseBuilder.getInstance();
        builder.id(1);
        builder.duration(5);
        builder.maxApplicants(1);
        builder.instance(0, localDateTime.plusDays(2), localDateTime.plusDays(4), 0D);
        assertThat(builder.validate(), is(false));

        // two start dates on same day
        builder = CourseBuilder.getInstance();
        builder.id(1);
        builder.duration(5);
        builder.maxApplicants(1);
        builder.instance(0, localDateTime, localDateTime.plusDays(1), 0D);
        builder.instance(0, localDateTime, localDateTime.plusDays(5), 0D);
        assertThat(builder.validate(), is(false));

        // end date before start date
        builder = CourseBuilder.getInstance();
        builder.id(1);
        builder.duration(5);
        builder.maxApplicants(1);
        builder.instance(0, localDateTime, localDateTime.minusDays(1), 0D);
        assertThat(builder.validate(), is(false));

        // end date on an illegal day
        builder = CourseBuilder.getInstance();
        builder.id(1);
        builder.duration(5);
        builder.maxApplicants(1);
        builder.instance(0, localDateTime, localDateTime.plusDays(2), 0D);
        assertThat(builder.validate(), is(false));

        // two end dates on same day
        builder = CourseBuilder.getInstance();
        builder.id(1);
        builder.duration(5);
        builder.maxApplicants(1);
        builder.instance(0, localDateTime.minusDays(1), localDateTime.plusDays(1), 0D);
        builder.instance(0, localDateTime.minusDays(2), localDateTime.plusDays(1), 0D);
        assertThat(builder.validate(), is(false));

        thrown.expect(IncorrectVariablesException.class);
        CourseBuilder.getInstance().id(-1).create(); // invalid course object
    }

    @Test
    public void testCreate() throws Exception {
        CourseBuilder builder = CourseBuilder.getInstance();
        builder.id(1);

        // note that with course date rules the day might be illegal
        // adjust accordingly, currently thursday 1st of january
        LocalDateTime localDateTime = LocalDateTime.of(2015, Month.JANUARY,1
                ,2,0);
        Course course = CourseBuilder.getInstance()
                .id(1)
                .title("Course_1")
                .code("ABS1")
                .duration(5)
                .maxApplicants(10)
                .instance(1, localDateTime, localDateTime.plusDays(5), 100D)
                .create();
        assertThat(course.getId(), is(1));
        assertThat(course.getTitle(), is("Course_1"));
        assertThat(course.getDurationDays(), is(5));
        assertThat(course.getMaxApplicants(), is(10));
        assertThat(course.getInstances().isEmpty(), is(false));

        CourseInstance instance = course.getInstances().get(0);
        assertThat(instance.getId(), is(1));
        assertThat(instance.getStartDate(), is(localDateTime));
        assertThat(instance.getEndDate(), is(localDateTime.plusDays(5)));
        assertThat(instance.getBasePrice(), is(100D));
        assertThat(instance.isDefinitive(), is(false));

        thrown.expect(IncorrectVariablesException.class);
        CourseBuilder.getInstance().create(); // invalid course object
    }
}