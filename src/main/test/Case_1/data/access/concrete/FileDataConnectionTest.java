package Case_1.data.access.concrete;

import Case_1.data.access.abs.DataConnectionException;
import Case_1.util.pref.PrefUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class FileDataConnectionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private static final String TEST_FILENAME = "CustomTestFile.txt";
    private static String fullPath;

    @Before
    public void setUp() {
        try {
            PrefUtil.setPropertiesFileName("config.properties");
        } catch (IOException | PrefUtil.PropertyNotFoundException e) {
            // do nothing, but should reload preferences so we
            // get proper error labels in case other unit test
            // reset properties file
        }
    }

    @Test
    public void testOpen() throws Exception {
        // will always return true
        assertThat(new FileDataConnection().open(), is(true));
    }

    @Test
    public void testGetConnection() throws Exception {
        FileDataConnection connection = new FileDataConnection();
        connection.setConnection(TEST_FILENAME);
        assertThat(connection.getConnection(), is(TEST_FILENAME));
    }

    @Test
    public void testSetConnection() throws Exception {
        FileDataConnection connection = new FileDataConnection();
        connection.setConnection(TEST_FILENAME);
        assertThat(connection.getConnection(), is(TEST_FILENAME));
    }

    @Test
    public void testExecute() throws Exception {
        FileDataConnection connection = new FileDataConnection();

        // create a test file we can use
        createTestFile();

        // set the path to the test file location
        connection.setConnection(fullPath);

        // get the results
        DataResult result = connection.execute(null);

        // assert the 5 lines are present in the DataResult
        assertThat(result.getRow(0).get("line 1"), is("This is line one"));
        assertThat(result.getRow(0).get("line 2"), is("This is line, two-ish"));
        assertThat(result.getRow(0).get("line 3"), is("This is line        three"));
        assertThat(result.getRow(0).get("line 4"), is(""));
        assertThat(result.getRow(0).get("line 5"), is("This is line five   with a tab"));

        try{
            connection.setConnection("filepath that does not exist.woot");
            connection.execute(null);
            fail("DataConnectionException expected");
        }catch (DataConnectionException e){
            // using try catch instead of @Rule
            // because we want to continue the test
        }

        thrown.expect(DataConnectionException.class);
        connection.setConnection(null);
        connection.execute(null);
    }

    @Test
    public void testExecuteUpdate() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        new FileDataConnection().executeUpdate(null);
    }

    @Test
    public void testClose() throws Exception {
        // will always return true
        assertThat(new FileDataConnection().close(), is(true));
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
        lines.add("This is line one");
        lines.add("This is line, two-ish");
        lines.add("This is line        three");
        lines.add("");
        lines.add("This is line five   with a tab");

        // write the lines to the test file
        Files.write(Paths.get(fullPath),lines);

        // read the lines we just wrote
        List<String> writtenLines = Files.readAllLines(Paths.get(fullPath));

        // make sure they have been written
        assertThat(lines.get(0), is(writtenLines.get(0)));
    }
}