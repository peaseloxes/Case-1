package Case_1.api.logic.concrete;

import Case_1.data.access.abs.DataConnectionException;
import Case_1.data.logic.concrete.StudentRepository;
import Case_1.domain.concrete.Student;
import Case_1.logic.concrete.StudentBuilder;
import Case_1.util.i18n.LangUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by alex on 10/7/15.
 */
public class StudentControllerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private StudentRepository mock;
    private Student student;
    private StudentController controller = new StudentController();

    @Before
    public void setUp() {

        student = StudentBuilder.getInstance()
                .firstName("first")
                .lastName("last")
                .addressCompany(1, "", "", "", "", "", 0, "", "", "", "")
                .create();
        mock = mock(StudentRepository.class);
        controller = new StudentController(mock);
    }

    @Test
    public void testGetById() throws Exception {
        when(mock.getById(1))
                .thenReturn(student);
        Object response = controller.getById(1).getEntity();
        assertThat(response, instanceOf(String.class));
        assertThat((String) response, containsString("first"));

        when(mock.getById(0))
                .thenReturn(student);
        response = controller.getById(0).getEntity();
        assertThat(response, instanceOf(String.class));
        assertThat((String) response, containsString("first"));

        when(mock.getById(-1))
                .thenReturn(null);
        response = controller.getById(-1).getEntity();
        assertThat(response, instanceOf(String.class));
        assertThat((String) response, containsString("null"));


        when(mock.getById(-1))
                .thenThrow(DataConnectionException.class);
        response = controller.getById(-1).getEntity();
        assertThat(response, instanceOf(String.class));
        assertThat((String) response, containsString(LangUtil.labelFor("error.student.notFound")));
    }

    @Test
    public void testCreateStudent() throws Exception {
        when(mock.add(any(Student.class)))
                .thenReturn(true);
        when(mock.subscribeTo(any(Student.class),anyInt()))
                .thenReturn(true);
        Object response = controller.createStudent("1","","","","","").getEntity();
        assertThat(response, instanceOf(String.class));
        assertThat((String) response, containsString(LangUtil.labelFor("success.user.created")));

        when(mock.add(any(Student.class)))
                .thenThrow(DataConnectionException.class);
        response = controller.createStudent("1", "", "", "", "", "").getEntity();
        assertThat(response, instanceOf(String.class));
        assertThat((String) response, containsString(LangUtil.labelFor("error.user.notCreated")));

    }

    @Test
    public void testGetByWeek() throws Exception {
        when(mock.getStudentCoursesByYearWeek(anyInt(),anyInt()))
                .thenReturn(Arrays.asList(student));
        Object response = controller.getByWeek(2013, 5).getEntity();
        assertThat(response, instanceOf(String.class));
        assertThat((String) response, containsString("first"));
    }
}