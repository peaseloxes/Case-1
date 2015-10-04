package Case_1.data.logic.concrete;

import Case_1.data.access.concrete.DataResult;
import Case_1.data.access.concrete.OracleDataConnection;
import Case_1.data.access.concrete.SQLQuery;
import Case_1.data.logic.abs.DataSource;
import Case_1.data.object.concrete.CourseDataHandler;
import Case_1.domain.concrete.Course;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.HashMap;
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

    private static CourseRepository repository;
    private static OracleDataConnection mock;
    private static ResultSet mockResultSet;
    private static ResultSet mockEmptyResultSet;
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
        when(mock.open()).thenReturn(true);
        when(mock.close()).thenReturn(true);
        when(mock.execute(any(SQLQuery.class))).thenReturn(filledResult)
                .thenReturn(emptyResult);

        repository = new CourseRepository(
                new DataSource<>(new CourseDataHandler(mock)));
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
}