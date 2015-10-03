package Case_1.data.access.abs;

import Case_1.data.access.concrete.DataResult;
import Case_1.data.access.concrete.SQLQuery;

/**
 * C = connection type, i.e. {@linkplain
 * java.sql.Connection
 * Connection}
 * <p/>
 * Q = query type, i.e. {@linkplain SQLQuery
 * SQLQuery}
 *
 * @author Alex
 * @version %I%, %G%
 */
public interface DataConnection<C, Q> {

    // TODO better method names, not every implementation is a database

    // Generic DataSourceException since we won't know the real type,
    // could be SQLException, FileNotFoundException etc.

    /**
     * Opens and initializes the connection.
     *
     * @return true if successful
     * @throws DataConnectionException if an error occurs
     */
    boolean open() throws DataConnectionException;

    /**
     * Returns the connection, if set.
     *
     * @return the connection if set, null otherwise.
     */
    C getConnection();

    /**
     * Sets the connection.
     *
     * @param connection the connection to use.
     */
    void setConnection(C connection);

    /**
     * Execute a query expecting a result.
     *
     * @param query The query to execute
     * @return the result
     * @throws DataConnectionException if an exception occurs during retrieval
     */
    DataResult execute(Q query) throws DataConnectionException;

    /**
     * Execute a query not expecting a result.
     *
     * @param query the query to execute
     * @return true if successful
     * @throws DataConnectionException if an exception occurs
     */
    boolean executeUpdate(Q query) throws DataConnectionException;

    /**
     * Closes any open connections or streams.
     *
     * @return true if successful
     * @throws DataConnectionException if an exception occurs
     */
    boolean close() throws DataConnectionException;

}
