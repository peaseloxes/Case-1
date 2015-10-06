package Case_1.api.logic.concrete;

import Case_1.data.access.concrete.OracleDataConnection;
import Case_1.data.access.concrete.SQLQuery;
import Case_1.data.logic.abs.DataSource;
import Case_1.data.logic.concrete.CourseRepository;
import Case_1.data.object.concrete.CourseDataHandler;
import Case_1.domain.concrete.Course;
import Case_1.util.i18n.LangUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by alex on 10/5/15.
 */
public class CourseControllerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String TEST_FILENAME = "CustomTestFile.txt";
    private static String fullPath = "";

    private CourseController controller;


    @Before
    public void setUp() {
        controller = new CourseController(
                new CourseRepository(
                        new DataSource<Course>(
                                new CourseDataHandler(
                                        new OracleDataConnection()
                                )
                        )
                )
        );
    }

    @Test
    public void testImportFile() throws Exception {

        LangUtil.setLocale("en");
        OracleDataConnection mock = mock(OracleDataConnection.class);

        when(mock.executeUpdate(any(SQLQuery.class)))
                .thenReturn(true);

        // make sure we have language support
        controller = new CourseController(
                new CourseRepository(
                        new DataSource<Course>(
                                new CourseDataHandler(
                                        mock
                                )
                        )
                )
        );

        createTestFile();

        FileInputStream stream = new FileInputStream(new File(fullPath));

        Response response = controller.importFile(stream);

        // can not actually add courses to DataConnection, so expect a failure message
        assertThat(response.getEntity(), is("\"Courses not added: Failed to add course\""));
    }

    @Test
    public void testGetRepository() throws Exception {
        assertThat(controller.getRepository(), instanceOf(CourseRepository.class));
    }

    @Test
    public void testGetIdUrl() throws Exception {
        assertThat(controller.getIdUrl(), notNullValue());
    }

    @Test
    public void testGetName() throws Exception {
        assertThat(controller.getName(), notNullValue());
    }

    @Test
    public void testGet() throws Exception {
        assertThat(controller.get(), instanceOf(Response.class));
    }

    private void createTestFile() throws IOException {

        // add a file to the test folder
        testFolder.newFile(TEST_FILENAME);

        // create the full path based on generated test folder
        fullPath = testFolder.getRoot().getAbsolutePath()
                + System.getProperty("file.separator")
                + TEST_FILENAME;

        // create some lines
        List<String> lines = new ArrayList<>();
        lines.add("Titel: C# Programmeren");
        lines.add("Cursuscode: CNETIN");
        lines.add("Duur: 5 dagen");
        lines.add("Startdatum: 14/10/2013");
        lines.add("");
        lines.add("Titel: C# Programmeren");
        lines.add("Cursuscode: CNETIN");
        lines.add("Duur: 5 dagen");
        lines.add("Startdatum: 21/10/2013");
        lines.add("");


        // write the lines to the test file
        Files.write(Paths.get(fullPath), lines);

        // read the lines we just wrote
        List<String> writtenLines = Files.readAllLines(Paths.get(fullPath));

        // make sure they have been written
        assertThat(lines.get(0), is(writtenLines.get(0)));
    }
}