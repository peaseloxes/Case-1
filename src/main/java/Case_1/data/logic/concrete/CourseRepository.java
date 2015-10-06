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
     * <p>
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

    /**
     * Finds a course given a course code.
     *
     * @param code the code
     * @return a course or null
     */
    public Course getByCourseCode(final String code) {
        return getDataSource().findWhere(
                new String[]{"name"},
                new String[]{code}
        ).get(0);
    }

    /**
     * Get all courses.
     *
     * @return all known courses
     */
    public List<Course> getAll() {
        return getDataSource()
                .getAll();
    }

    /**
     * Get all courses.
     *
     * @param start starting from
     * @return all courses starting from <b>start</b>
     */
    public List<Course> getAll(final int start) {
        return getDataSource()
                .getAll(start);
    }


    /**
     * Adds a course to the datasource of this repository.
     *
     * @param course the course to add
     * @return true if successful
     */
    public boolean addCourse(final Course course) {
        return getDataSource().add(course);
    }

    public boolean addAll(final List<Course> list) {
        for (Course course : list) {
            addCourse(course);
        }
        return true;
    }
}
