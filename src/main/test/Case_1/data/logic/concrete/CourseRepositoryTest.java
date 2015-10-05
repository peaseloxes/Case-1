package Case_1.data.logic.concrete;

import Case_1.data.access.concrete.DataResult;
import Case_1.data.access.concrete.OracleDataConnection;
import Case_1.data.access.concrete.SQLQuery;
import Case_1.data.logic.abs.DataSource;
import Case_1.data.object.concrete.CourseDataHandler;
import Case_1.domain.concrete.Course;
import Case_1.domain.concrete.CourseInstance;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * TODO is semi integration test?
 *
 * @author Alex
 * @version %I%, %G%
 */
public class CourseRepositoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static CourseRepository repository;
    private static OracleDataConnection mock;
    private static DataResult filledResult;
    private static DataResult emptyResult;

    @BeforeClass
    public static void setItAllUp() throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "Course_1");
        map.put("id", 1);
        map.put("applicants", 10);
        map.put("duration", 5);
        filledResult = new DataResult();
        filledResult.addNewRow(map);

        emptyResult = new DataResult();

        mock = mock(OracleDataConnection.class);
        when(mock.open())
                .thenReturn(true);
        when(mock.close())
                .thenReturn(true);
        when(mock.execute(any(SQLQuery.class)))
                .thenReturn(filledResult)
                .thenReturn(emptyResult);
        when(mock.executeUpdate(any(SQLQuery.class)))
                .thenReturn(true) // first Course
                .thenReturn(true) // first CourseInstance
                .thenReturn(false) // Second Course
                .thenReturn(false) // Second Course instance
                .thenThrow(SQLException.class); // third whatever

        repository = new CourseRepository(new DataSource<>(new CourseDataHandler(mock)));
    }

    @Test
    public void testGetById() throws Exception {

        // get a result that exists
        Course result = repository.getById(1);

        // assert its id is indeed 1
        assertThat(result.getId(), is(1));
        assertThat(result.getTitle(), is("Course_1"));
        assertThat(result.getMaxApplicants(), is(10));
        assertThat(result.getDurationDays(), is(5));

        // get a result that does not exist
        result = repository.getById(2);

        // assert that it is null
        assertThat(result, nullValue());
    }

    @Test
    public void testFindCoursesWithNameLike() throws Exception {

    }

    @Test
    public void testAdd() throws Exception {

        // note proper DB addition is not tested/verified
        // merely the logic getting it there
        // SQLConnection is mocked to return true/false/exception
        // regardless of input

        // don't use builder to avoid validation
        Course course = new Course();
        course.setId(2);
        course.setCode("code2");
        course.setDurationDays(2);
        course.setMaxApplicants(5);
        course.setTitle("course2");
        List<CourseInstance> list = new ArrayList<>();
        CourseInstance instance = new CourseInstance(
                0,
                LocalDateTime.now(),
                LocalDateTime.now(),
                false,
                0D
        );
        list.add(instance);
        course.setInstances(list);

        // true
        boolean success_result = repository.addCourse(course);
        assertThat(success_result, is(true));

        // false
        boolean fail_result = repository.addCourse(course);
        assertThat(fail_result, is(false));

        // exception
        thrown.expect(SQLException.class);
        repository.addCourse(course);
    }
}