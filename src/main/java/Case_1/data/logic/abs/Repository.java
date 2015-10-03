package Case_1.data.logic.abs;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class Repository<T> {

    @Getter(AccessLevel.PROTECTED)
    private DataSource<T> dataSource;

    public Repository(final DataSource<T> dataSource) {
        this.dataSource = dataSource;
    }

    public T getById(final int id) {
        return dataSource.findById(id);
    }

}
