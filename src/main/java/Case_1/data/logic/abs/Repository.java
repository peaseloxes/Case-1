package Case_1.data.logic.abs;

import Case_1.data.access.abs.DataConnectionException;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public abstract class Repository<T> {

    @Getter(AccessLevel.PROTECTED)
    private DataSource<T> dataSource;

    /**
     * Constructor with injection.
     *
     * @param dataSource the datasource to use
     */
    public Repository(final DataSource<T> dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Find an item given its id.
     *
     * @param id the id
     * @return the corresponding item
     * @throws DataConnectionException if item can not be found
     */
    public T getById(final int id) throws DataConnectionException {
        return dataSource.findById(id);
    }

    /**
     * Fetches all items in the repository.
     *
     * @return all the items
     */
    public List<T> getAll() {
        return getDataSource()
                .getAll();
    }
}
