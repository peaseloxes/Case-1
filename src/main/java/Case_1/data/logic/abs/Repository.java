package Case_1.data.logic.abs;

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

    public Repository(final DataSource<T> dataSource) {
        this.dataSource = dataSource;
    }

    public T getById(final int id) {
        return dataSource.findById(id);
    }

    /**
     * Get <b>limit</b> items starting from <b>start</b>.
     *
     * @param start where to start
     * @param limit amount of courses
     * @return courses from <b>start</b> to <b>start + limit</b>
     */
    public List<T> getAll(final int start, final int limit) {
        return getDataSource()
                .getAll(start, limit);
    }
}
