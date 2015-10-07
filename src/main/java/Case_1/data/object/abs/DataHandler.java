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
    T getById(final int id) throws DataConnectionException;


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
     * @param key   column name
     * @param value required value
     * @return the item(s)
     */
    List<T> getByKey(String key, Object value);

    /**
     * Get all items.
     *
     * @return all items
     */
    List<T> getAll();

    /**
     * TODO should not be in datahandler, move up!
     *
     * @param subscriber     the subscriber
     * @param subscribableId the subscribable id
     * @return true if subscribed
     * @throws DataConnectionException if subscription not possible
     */
    boolean subscribeTo(T subscriber, int subscribableId) throws DataConnectionException;

    /**
     * TODO should not be in datahandler, move up!
     *
     * @param year the year
     * @param week the week
     * @return the list of items matching week/year
     * @throws DataConnectionException if no items could be found
     */
    List<T> getStudentCoursesByYearWeek(int year, int week) throws DataConnectionException;
}
