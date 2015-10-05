package Case_1.data.object.concrete;

import Case_1.data.access.abs.DataConnection;
import Case_1.data.access.abs.DataConnectionException;
import Case_1.data.access.concrete.DataResult;
import Case_1.data.access.concrete.SQLQuery;
import Case_1.data.object.abs.DataHandler;
import Case_1.domain.concrete.Course;
import Case_1.domain.concrete.CourseInstance;

import java.sql.Timestamp;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class CourseDataHandler implements
        DataHandler<DataConnection, Course> {

    private DataConnection connection;

    public CourseDataHandler(final DataConnection connection) {
        setConnection(connection);
    }

    @Override
    public void setConnection(final DataConnection connection) {
        this.connection = connection;
    }

    @Override
    public Course getById(final int id) {
        Course course = null;
        try {
            connection.open();
            String sql = "SELECT * FROM `COURSE` WHERE ID = ? LIMIT 1";
            SQLQuery query = new SQLQuery();
            query.setSql(sql);
            query.addParam(id, SQLQuery.Type.INT);

            DataResult result = connection.execute(query);

            if (!result.isEmpty()) {
                // TODO use builder and make sure validity is ok, also course instances
                course = new Course(
                        (Integer) result.getRow(0).get("id"),
                        (String) result.getRow(0).get("name"),
                        (String) result.getRow(0).get("code"),
                        (Integer) result.getRow(0).get("duration"),
                        (Integer) result.getRow(0).get("applicants"),
                        null);
            }
            connection.close();
        } catch (DataConnectionException | NullPointerException e) {
            // will only throw DataConnection exception on opening or closing:
            // if on opening, there's no connection to be closed
            // if on closing no sense in trying again
            // hence no finally with connection.close()
            //
            // connection.execute() will close on its own if
            // exception is encountered
        }
        return course;
    }

    @Override
    public boolean add(final Course course) {
        boolean response = false;
        try {
            connection.open();

            // first course
            String sql = "INSERT INTO COURSE (ID,COURSECODE,TITLE,DURATIONDAYS,MAXAPPLICANTS) " +
                    "values (SEQ_COURSE.NEXTVAL,?,?,?,?)";
            SQLQuery query = new SQLQuery();
            query.setSql(sql);
            //query.addParam("SEQ_COURSE.NEXTVAL", SQLQuery.Type.INT);
            query.addParam(course.getCode(), SQLQuery.Type.STRING);
            query.addParam(course.getTitle(), SQLQuery.Type.STRING);
            query.addParam(course.getDurationDays(), SQLQuery.Type.INT);
            query.addParam(course.getMaxApplicants(), SQLQuery.Type.INT);
            response = connection.executeUpdate(query);

            // then course instances
            for (CourseInstance courseInstance : course.getInstances()) {
                sql = "INSERT INTO COURSEINSTANCE (ID,COURSEID,STARTDATE,ENDDATE,DEFINITIVE,BASEPRICE) " +
                        "values (?,?,?,?,?,?)";
                query = new SQLQuery();
                query.setSql(sql);
                query.addParam(courseInstance.getId(), SQLQuery.Type.INT);
                query.addParam(course.getId(), SQLQuery.Type.INT);
                query.addParam(Timestamp.valueOf(courseInstance.getStartDate()), SQLQuery.Type.DATE);
                query.addParam(Timestamp.valueOf(courseInstance.getEndDate()), SQLQuery.Type.DATE);
                query.addParam(courseInstance.isDefinitive(), SQLQuery.Type.BOOLEAN);
                query.addParam(courseInstance.getBasePrice(), SQLQuery.Type.DOUBLE);
                connection.executeUpdate(query);
            }
            connection.close();
        } catch (DataConnectionException | NullPointerException e) {
            e.printStackTrace(); // TODO proper exception handling
        }
        return response;
    }
}
