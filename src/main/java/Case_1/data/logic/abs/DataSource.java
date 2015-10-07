package Case_1.data.logic.abs;

import Case_1.data.access.abs.DataConnection;
import Case_1.data.access.abs.DataConnectionException;
import Case_1.data.object.abs.DataHandler;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class DataSource<T> {

    @Getter
    private final DataHandler<DataConnection, T> handler;

    /**
     * Constructor with the required data handler to be used.
     *
     * @param handler the handler
     */
    public DataSource(final DataHandler<DataConnection, T> handler) {
        this.handler = handler;
    }

    /**
     * Find an item by its id
     *
     * @param id the id
     * @return the item
     * @throws DataConnectionException if the item can not be found
     */
    public T findById(final int id) throws DataConnectionException {
        return handler.getById(id);
    }

    /**
     * Find all items.
     *
     * @return all items, or an empty list
     */
    public List<T> getAll() {
        return handler.getAll();
    }

    /**
     * Find items where the keys equal values.
     * <p>
     * Will throw an IllegalArgumentException if keys and values are of different length.
     *
     * @param keys   the keys
     * @param values their desired values
     * @return the items found, or an empty list
     */
    public List<T> findWhere(final String[] keys, final Object[] values) {
        List<T> results = new ArrayList<>();
        if (keys.length != values.length) {
            throw new IllegalArgumentException("keys and values are of different length");
        }

        for (int i = 0; i < keys.length; i++) {
            results.addAll(handler.getByKey(keys[i], values[i]));
        }

        return results;
    }

    /**
     * Find items where the keys are similar to the values.
     * <p>
     * Will throw an IllegalArgumentException if keys and values are of different length.
     *
     * @param keys   the keys
     * @param values their desired values
     * @return the items found or null
     */
    public List<T> findWhereLike(final String[] keys, final Object[] values) {
        // TODO implement findWhereLike
        return null;
    }

    /**
     * Adds an item to the current data handler.
     *
     * @param object the item to add
     * @return true if successful
     * @throws DataConnectionException if addition was not possible
     */
    public boolean add(final T object) throws DataConnectionException {
        return handler.add(object);
    }

    /**
     * TODO should not be in DataSource, move up!
     *
     * @param subscriber the subscriber
     * @param subscribableId a subscribable id
     * @return true if successful
     * @throws DataConnectionException if subscription was not possible
     */
    public boolean subscribeTo(T subscriber, int subscribableId) throws DataConnectionException {
        return handler.subscribeTo(subscriber, subscribableId);
    }

    /**
     * TODO should not be in DataSource, move up!
     *
     *
     * @param year the year
     * @param week the week
     * @return a list of matching items
     * @throws DataConnectionException if not items could be retrieved
     */
    public List<T> getStudentCoursesByYearWeek(final int year, final int week) throws DataConnectionException {
        return handler.getStudentCoursesByYearWeek(year, week);
    }
}
