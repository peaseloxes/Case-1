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

    // TODO javadoc

    @Getter
    private final DataHandler<DataConnection, T> handler;

    public DataSource(final DataHandler<DataConnection, T> handler) {
        this.handler = handler;
    }

    public T findById(final int id) {
        return handler.getById(id);
    }

    public List<T> getAll() {
        return handler.getAll();
    }

    public List<T> getAll(final int start) {
        return null;
    }

    public List<T> getAll(final int start, final int limit) {
        return handler.getAll(start, limit);
    }

    public List<T> findWhere(final String[] keys, final Object[] values) {
        List<T> results = new ArrayList<>();
        if(keys.length != values.length) {
            throw new IllegalArgumentException("keys and values are of different length");
        }

        for (int i = 0; i < keys.length; i++) {
            results.addAll(handler.getByKey(keys[i], values[i]));
        }

        return results;
    }

    public List<T> findWhereLike(final String[] keys, final Object[] values) {
        return null;
    }

    public boolean add(final T object) throws DataConnectionException {
        return handler.add(object);
    }

    public boolean subscribeTo(T subscriber, int subscribableId) throws DataConnectionException {
        return handler.subscribeTo(subscriber, subscribableId);
    }

    public List<T> getStudentCoursesByYearWeek(final int year, final int week) throws DataConnectionException{
        return handler.getStudentCoursesByYearWeek(year,week);
    }
}
