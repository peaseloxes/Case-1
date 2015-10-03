package Case_1.data.access.concrete;

import Case_1.data.access.abs.DataConnection;
import Case_1.data.access.abs.DataConnectionException;
import Case_1.util.i18n.LangUtil;
import Case_1.util.pref.Property;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class OracleDataConnection implements
        DataConnection<Connection, SQLQuery> {

    // Design pattern: singleton
    // impossible to mock with mockito?
    //INSTANCE;

    private static final Logger LOGGER = LogManager.getLogger(
            OracleDataConnection.class.getName()
    );

    private Connection connection;
    private OracleDataSource oracleDataSource;

    @Override
    public boolean open() throws DataConnectionException {
        try {
            oracleDataSource = new OracleDataSource();
            oracleDataSource.setURL(Property.DB_URL.toString());
            connection = oracleDataSource.getConnection(
                    Property.DB_USERNAME.toString(),
                    Property.DB_USERNAME.toString()
            );
        } catch (SQLException e) {
            String message = LangUtil
                    .labelFor("error.dataConnection.connectionFailure")
                    + Property.DB_URL;

            LOGGER.error(message);
            e.printStackTrace();
            throw new DataConnectionException(message);
        }
        return true;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public DataResult execute(final SQLQuery query) throws
            DataConnectionException {
        DataResult response = new DataResult();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    query.getSql()
            );
            int count = 1;
            for (Object value : query.getParams().keySet()) {
                statement.setObject(count, value, query.getParams().get(value));
                count++;
            }
            final ResultSet result = statement.executeQuery();
            final ResultSetMetaData metaData = result.getMetaData();
            final int columnCount = metaData.getColumnCount();

            while (result.next()) {
                Map<String, Object> map = new HashMap<>();

                // sql has 1-based array so +1
                for (int i = 1; i <= columnCount; i++) {
                    String name = metaData.getColumnLabel(i);
                    Object value = result.getObject(name);
                    map.put(name, value);
                }
                response.addNewRow(map);
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            close();
            throw new DataConnectionException(
                    LangUtil
                            .labelFor("error.dataConnection.malformedQuery")
                            + query.getSql()
            );
        }
        return response;
    }

    @Override
    public boolean executeUpdate(final SQLQuery query) throws
            DataConnectionException {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    query.getSql()
            );
            int count = 1;
            for (Object value : query.getParams().keySet()) {
                statement.setObject(count, value, query.getParams().get(value));
                count++;
            }
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            close();
            throw new DataConnectionException(
                    LangUtil
                            .labelFor("error.dataConnection.malformedQuery")
                            + query.getSql()
            );
        }
    }

    @Override
    public boolean close() throws DataConnectionException {
        try {
            // TODO deprecated, use Oracle Connection Pool instead?
            // dataSource.close();
            connection.close();
        } catch (SQLException | NullPointerException e) {
            String message = LangUtil
                    .labelFor("error.dataConnection.connectionClosedFailure");
            LOGGER.warn(message);
            e.printStackTrace();
            throw new DataConnectionException(message);
        } finally {
            connection = null;
        }
        return true;
    }
}
