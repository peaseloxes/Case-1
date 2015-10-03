package Case_1.data.logic.abs;

import Case_1.data.access.abs.DataConnection;
import Case_1.data.object.abs.DataHandler;
import lombok.Getter;

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

    public DataSource(final DataHandler<DataConnection, T> handler) {
        this.handler = handler;
    }

    public T findById(final int id) {
        return handler.getById(id);
    }

    public List<T> getAll() {
        return null;
    }

    public List<T> getAll(final int start) {
        return null;
    }

    public List<T> getAll(final int start, final int limit) {
        return null;
    }

    public List<T> findWhere(final String[] keys, final Object[] values) {
        return null;
    }

    public List<T> findWhereLike(final String[] keys, final Object[] values) {
        return null;
    }
}
