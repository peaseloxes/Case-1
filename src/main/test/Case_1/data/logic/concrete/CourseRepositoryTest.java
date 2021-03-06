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

import java.math.BigDecimal;
import java.sql.Timestamp;
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
    private static DataResult emptyResult;
    private static DataResult instanceResult;
    private static DataResult studentResult;
    private static DataResult courseResult;
    private static DataResult addressResult;
    private static DataResult companyResult;
    private static Course course;

    @BeforeClass
    public static void setItAllUp() throws Exception {

        course = new Course();
        course.setId(1);
        course.setCode("code");
        course.setDurationDays(2);
        course.setMaxApplicants(5);
        course.setTitle("title");
        List<CourseInstance> list = new ArrayList<>();
        CourseInstance instance = new CourseInstance(
                0,
                LocalDateTime.now(),
                LocalDateTime.now(),
                false,
                0D,
                null
        );
        list.add(instance);
        course.setInstances(list);

        //        (((BigDecimal) studentMap.get("ID")).intValue())
//                .firstName((String) studentMap.get("FIRSTNAME"))
//                .lastName((String) studentMap.get("LASTNAME"))
//                .email((String) studentMap.get("EMAIL"));

        Map<String, Object> map = new HashMap<>();
        map.put("ID", new BigDecimal(1));
        map.put("FIRSTNAME", "first");
        map.put("LASTNAME", "last");
        map.put("EMAIL", "mail");
        map.put("COMPANYID", new BigDecimal(1));
        studentResult = new DataResult();
        studentResult.addNewRow(map);

//        ((BigDecimal) courseInstance.get("ID")).intValue(),
//        ((Timestamp) courseInstance.get("STARTDATE")).toLocalDateTime(),
//        ((Timestamp) courseInstance.get("ENDDATE")).toLocalDateTime(),
//        ((BigDecimal) courseInstance.get("BASEPRICE")).doubleValue()
        map = new HashMap<>();
        map.put("ID", new BigDecimal(1));
        map.put("COURSEID", new BigDecimal(1));
        map.put("STARTDATE", new Timestamp(654649646L));
        map.put("ENDDATE", new Timestamp(664649646L));
        map.put("BASEPRICE", new BigDecimal(1));
        instanceResult = new DataResult();
        instanceResult.addNewRow(map);

//        courseBuilder.id(((BigDecimal) courseResultMap.get("ID")).intValue())
//                .title((String) courseResultMap.get("TITLE"))
//                .code((String) courseResultMap.get("COURSECODE"))
//                .duration(((BigDecimal) courseResultMap.get("DURATIONDAYS")).intValue())
//                .maxApplicants(((BigDecimal) courseResultMap.get("MAXAPPLICANTS")).intValue());
        map = new HashMap<>();
        map.put("ID", new BigDecimal(1));
        map.put("TITLE", "title");
        map.put("COURSECODE", "code");
        map.put("DURATIONDAYS", new BigDecimal(5));
        map.put("MAXAPPLICANTS", new BigDecimal(10));
        courseResult = new DataResult();
        courseResult.addNewRow(map);

        map = new HashMap<>();
        map.put("ID", new BigDecimal(1));
        addressResult = new DataResult();
        addressResult.addNewRow(map);

        map = new HashMap<>();
        map.put("ID", new BigDecimal(1));
        map.put("ADDRESSID", new BigDecimal(1));
        companyResult = new DataResult();
        companyResult.addNewRow(map);

        emptyResult = new DataResult();

        mock = mock(OracleDataConnection.class);
        when(mock.open())
                .thenReturn(true);
        when(mock.close())
                .thenReturn(true);
        when(mock.execute(any(SQLQuery.class)))
                .thenReturn(courseResult)
                .thenReturn(emptyResult);
//        when(mock.executeUpdate(any(SQLQuery.class)))
//                .thenReturn(true) // first Course
//                .thenReturn(true) // first CourseInstance
//                .thenReturn(false) // Second Course
//                .thenReturn(false) // Second Course instance
//                .thenThrow(SQLException.class); // third whatever

        repository = new CourseRepository(new DataSource<>(new CourseDataHandler(mock)));
    }

    @Test
    public void testGetById() throws Exception {
        when(mock.execute(any(SQLQuery.class)))
                .thenReturn(courseResult)
                .thenReturn(emptyResult)
                .thenReturn(null);

        // get a result that exists
        Course result = repository.getById(1);

        // assert its id is indeed 1
        assertThat(result.getId(), is(1));

        // get a result that does not exist
        result = repository.getById(2);

        // assert that it is null
        assertThat(result, nullValue());
    }

    @Test
    public void testGetStudentCoursesByYearWeek() throws Exception {
        when(mock.executeUpdate(any(SQLQuery.class)))
                .thenReturn(true);

        when(mock.execute(any(SQLQuery.class)))
                .thenReturn(instanceResult)
                .thenReturn(courseResult)
                .thenReturn(studentResult);

        assertThat(repository.getStudentCoursesByYearWeek(0, 0).get(0).getCode(), is("code"));
    }

    @Test
    public void testAdd() throws Exception {

        when(mock.executeUpdate(any(SQLQuery.class)))
                .thenReturn(true);

        when(mock.execute(any(SQLQuery.class)))
                .thenReturn(emptyResult)
                .thenReturn(courseResult)
                .thenReturn(emptyResult);

        assertThat(repository.addCourse(course), is(true));

    }

}