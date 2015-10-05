package Case_1.data.access.concrete;

import Case_1.data.access.abs.DataConnectionException;
import Case_1.util.pref.PrefUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class OracleDataConnectionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private OracleDataConnection oracleDataConnection;

    /**
     * Mocks
     */
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;

    private static ResultSet mockResultSet;
    private static ResultSetMetaData mockResultSetMetaData;

    private static ResultSet mockEmptyResultSet;
    private static ResultSetMetaData mockEmptyResultSetMetaData;

    @Before
    public void setUp() throws SQLException {

        // TODO clean up mess below, move to utility class?
        // see same stuff in CourseRepositoryTest

        try {
            PrefUtil.setPropertiesFileName("config.properties");
        } catch (IOException | PrefUtil.PropertyNotFoundException e) {
            // do nothing, but should reload preferences so we
            // get proper error labels in case other unit test
            // reset properties file
        }

        mockResultSetMetaData = mock(ResultSetMetaData.class);
        when(mockResultSetMetaData.getColumnCount())
                .thenReturn(2);
        when(mockResultSetMetaData.getColumnLabel(1))
                .thenReturn("id");
        when(mockResultSetMetaData.getColumnLabel(2))
                .thenReturn("name");

        mockEmptyResultSetMetaData = mock(ResultSetMetaData.class);
        when(mockEmptyResultSetMetaData.getColumnCount())
                .thenReturn(0);

        mockResultSet = mock(ResultSet.class);
        Mockito.when(mockResultSet.next())
                .thenReturn(true).thenReturn(false);
        when(mockResultSet.getObject("id"))
                .thenReturn(1);
        when(mockResultSet.getObject("name"))
                .thenReturn("Course_1");
        when(mockResultSet.getMetaData())
                .thenReturn(mockResultSetMetaData);

        mockEmptyResultSet = mock(ResultSet.class);
        when(mockEmptyResultSet.next())
                .thenReturn(false);
        when(mockEmptyResultSet.getMetaData())
                .thenReturn(mockEmptyResultSetMetaData);

        mockPreparedStatement = mock(PreparedStatement.class);
        when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet)
                .thenReturn(mockEmptyResultSet)
                .thenThrow(SQLException.class);
        when(mockPreparedStatement.executeUpdate())
                .thenReturn(0)
                .thenThrow(SQLException.class);

        mockConnection = mock(Connection.class);
        when(mockConnection.prepareStatement(anyString()))
                .thenReturn(mockPreparedStatement);
        doNothing().doThrow(SQLException.class).when(mockConnection).close();

        oracleDataConnection = new OracleDataConnection();
        oracleDataConnection.setConnection(mockConnection);

    }

    @Test
    public void testOpen() throws Exception {
        // can only fail without a correct database connection

        try {
            PrefUtil.setPropertiesFileName("testconfig.properties");
        } catch (IOException | PrefUtil.PropertyNotFoundException e) {
            // do nothing, but this should reload preferences so we
            // can fake a missing database
        }

        thrown.expect(DataConnectionException.class);
        oracleDataConnection.open();
    }

    @Test
    public void testGetConnection() throws Exception {
        assertThat(oracleDataConnection.getConnection(), is(mockConnection));
    }

    @Test
    public void testSetConnection() throws Exception {
        oracleDataConnection.setConnection(mockConnection);
        assertThat(oracleDataConnection.getConnection(), is(mockConnection));
    }

    @Test
    public void testExecute() throws Exception {
        SQLQuery query = new SQLQuery();
        query.setSql("doesn't matter");
        query.addParam(5, SQLQuery.Type.INT);

        // first time should get a result with two variables
        DataResult result = oracleDataConnection.execute(query);

        if (!result.isEmpty()) {
            assertThat(result.getRow(0).get("id"), instanceOf(Integer.class));
            assertThat(result.getRow(0).get("name"), instanceOf(String.class));
        } else {
            fail("result was empty when it shouldn't have been");
        }

        // second time should be an empty result
        result = oracleDataConnection.execute(new SQLQuery());
        assertThat(result.isEmpty(), is(true));

        // last time should be a DataConnectionException caused by
        // an SQLException
        thrown.expect(DataConnectionException.class);
        oracleDataConnection.execute(new SQLQuery());
    }

    @Test
    public void testExecuteUpdate() throws Exception {

        SQLQuery query = new SQLQuery();
        query.setSql("doesn't matter");
        query.addParam(5, SQLQuery.Type.INT);

        boolean result = oracleDataConnection.executeUpdate(query);
        assertThat(result, is(true));

        thrown.expect(DataConnectionException.class);
        oracleDataConnection.executeUpdate(new SQLQuery());
    }

    @Test
    public void testClose() throws Exception {

        // first time is doNothing()
        assertThat(oracleDataConnection.close(), is(true));

        // second time is throw SQLException
        // which we want converted to DataConnectionException
        thrown.expect(DataConnectionException.class);
        oracleDataConnection.close();

    }
}