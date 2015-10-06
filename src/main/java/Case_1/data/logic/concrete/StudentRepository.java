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

    public StudentRepository(DataSource<Student> dataSource) {
        super(dataSource);
    }

    public boolean add(final Student student) throws DataConnectionException {
        return getDataSource().add(student);
    }

    public boolean subscribeTo(final Student student, final int instanceId) throws DataConnectionException {
        return getDataSource().subscribeTo(student, instanceId);
    }

    public List<Student> getStudentCoursesByYearWeek(final int year, final int week) throws DataConnectionException {
        return getDataSource().getStudentCoursesByYearWeek(year,week);
    }
}
