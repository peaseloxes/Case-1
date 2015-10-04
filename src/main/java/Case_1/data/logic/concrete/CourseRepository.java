package Case_1.data.logic.concrete;

import Case_1.data.logic.abs.DataSource;
import Case_1.data.logic.abs.Repository;
import Case_1.domain.concrete.Course;

import java.util.List;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class CourseRepository extends Repository<Course> {

    /**
     * Custom constructor for dependency injection.
     *
     * @param dataSource the datasource to use
     */
    public CourseRepository(final DataSource<Course> dataSource) {
        super(dataSource);
    }

    /**
     * Finds courses with names matching <b>title</b>.
     * <p/>
     * Will return an empty list if no matches are found.
     *
     * @param name the title to match on
     * @return courses where the names match
     */
    public List<Course> findCoursesWithNameLike(final String name) {
        return getDataSource().findWhere(
                new String[]{"name"},
                new String[]{name}
        );
    }
}
