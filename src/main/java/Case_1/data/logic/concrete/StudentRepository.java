package Case_1.data.logic.concrete;

import Case_1.data.access.abs.DataConnectionException;
import Case_1.data.logic.abs.DataSource;
import Case_1.data.logic.abs.Repository;
import Case_1.domain.concrete.Student;

import java.util.List;

/**
 * Created by alex on 10/6/15.
 */
public class StudentRepository extends Repository<Student> {

    /**
     * Constructor with injection.
     *
     * @param dataSource the datasource to use
     */
    public StudentRepository(DataSource<Student> dataSource) {
        super(dataSource);
    }

    /**
     * Add a student to the repository.
     *
     * @param student the student to add
     * @return true if succesful
     * @throws DataConnectionException if the student could not be added
     */
    public boolean add(final Student student) throws DataConnectionException {
        return getDataSource().add(student);
    }

    /**
     * TODO should not be in repository, move up
     *
     * @param student    the student to add
     * @param instanceId the course instance id
     * @return true if successful
     * @throws DataConnectionException if the student could not be subscribed to isntanceId
     */
    public boolean subscribeTo(final Student student, final int instanceId) throws DataConnectionException {
        return getDataSource().subscribeTo(student, instanceId);
    }

    /**
     * TODO should not be in repository, move up.
     *
     * Get students / courses by year / week
     * @param year
     * @param week
     * @return
     * @throws DataConnectionException
     */
    public List<Student> getStudentCoursesByYearWeek(final int year, final int week) throws DataConnectionException {
        return getDataSource().getStudentCoursesByYearWeek(year, week);
    }
}
