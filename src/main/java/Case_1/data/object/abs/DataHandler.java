package Case_1.data.object.abs;

import Case_1.data.access.abs.DataConnectionException;

import java.util.List;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public interface DataHandler<C, T> {

    /**
     * Set the connection for this handler.
     *
     * @param connection the connection to set
     */
    void setConnection(final C connection);

    /**
     * Get an object <b>T</b> by id.
     *
     * @param id the id to search for
     * @return T or null
     */
    T getById(final int id);


    /**
     * Adds an item.
     *
     * @param item the item to add
     * @return true if successful
     */
    boolean add(T item) throws DataConnectionException;

    /**
     * Finds an item by column / value pair.
     *
     * @param key column name
     * @param value required value
     * @return the item(s)
     */
    List<T> getByKey(String key, Object value);

    List<T> getAll();

    List<T> getAll(final int start, final int limit);

    boolean subscribeTo(T subscriber, int subscribableId) throws DataConnectionException;
}
