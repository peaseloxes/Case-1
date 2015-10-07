package Case_1.data.logic.concrete;

import Case_1.data.access.abs.DataConnectionException;
import Case_1.data.access.concrete.DataResult;
import Case_1.data.access.concrete.OracleDataConnection;
import Case_1.data.access.concrete.SQLQuery;
import Case_1.data.logic.abs.DataSource;
import Case_1.data.object.concrete.StudentDataHandler;
import Case_1.domain.concrete.Student;
import Case_1.logic.concrete.StudentBuilder;
import Case_1.util.i18n.LangUtil;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by alex on 10/7/15.
 */
public class StudentRepositoryTest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static StudentRepository repository;
    private static OracleDataConnection mock;
    private static DataResult studentResult;
    private static DataResult emptyResult;
    private static DataResult courseResult;
    private static DataResult instanceResult;
    private static DataResult addressResult;
    private static DataResult companyResult;

    private static Student student;

    @BeforeClass
    public static void setItAllUp() throws Exception {

        student = StudentBuilder.getInstance()
                .firstName("first")
                .lastName("last")
                .addressCompany(1, "", "", "", "", "", 0, "", "", "", "")
                .create();

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
        repository = new StudentRepository(new DataSource<>(new StudentDataHandler(mock)));
    }

    @Test
    public void testAdd() throws Exception {
        // because handler verifies if student exists
        when(mock.execute(any(SQLQuery.class)))

                .thenReturn(addressResult) // fetch address
                .thenReturn(emptyResult) // student does net yet exist
                .thenReturn(addressResult); // fetch company

        when(mock.executeUpdate(any(SQLQuery.class)))
                .thenReturn(true);

        assertThat(repository.add(student), is(true));
    }

    @Test
    public void testGetById() throws Exception {
        when(mock.execute(any(SQLQuery.class)))
                .thenReturn(studentResult)
                .thenReturn(instanceResult)
                .thenReturn(courseResult);

        assertThat(repository.getById(1).getFirstName(), is("first"));

        when(mock.execute(any(SQLQuery.class)))
                .thenReturn(emptyResult);
        thrown.expect(DataConnectionException.class);
        thrown.expectMessage(LangUtil.labelFor("error.student.notFound"));
        repository.getById(1);
    }

    @Test
    public void testGetAll() throws Exception {
        when(mock.execute(any(SQLQuery.class)))
                .thenReturn(studentResult)
                .thenReturn(companyResult)
                .thenReturn(addressResult);
        assertThat(repository.getAll().get(0).getFirstName(), is("first"));
    }

    @Test
    public void testSubscribeTo() throws Exception {
        when(mock.execute(any(SQLQuery.class)))
                .thenReturn(studentResult);
        when(mock.executeUpdate(any(SQLQuery.class)))
                .thenReturn(true);
        assertThat(repository.subscribeTo(student, 1), is(true));
    }

    @Test
    public void testGetStudentCoursesByWeek() throws Exception {
        when(mock.execute(any(SQLQuery.class)))
                .thenReturn(studentResult)
                .thenReturn(companyResult)
                .thenReturn(addressResult)
                .thenReturn(instanceResult)
                .thenReturn(courseResult);
        assertThat(repository.getStudentCoursesByYearWeek(0, 0).get(0).getFirstName(), is("first"));
    }
}