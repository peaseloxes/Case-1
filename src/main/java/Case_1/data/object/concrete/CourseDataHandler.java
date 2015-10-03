package Case_1.data.object.concrete;

import Case_1.data.access.abs.DataConnection;
import Case_1.data.access.abs.DataConnectionException;
import Case_1.data.access.concrete.DataResult;
import Case_1.data.access.concrete.SQLQuery;
import Case_1.data.object.abs.DataHandler;
import Case_1.domain.Course;

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
                course = new Course(
                        (Integer)result.getRow(0).get("id"),
                        (String)result.getRow(0).get("name"));
            }
            connection.close();
        } catch (DataConnectionException e) {
            // will only throw exception on opening or closing:
            // if on opening, there's no connection to be closed
            // if on closing no sense in trying again
            // hence no finally with connection.close()
            //
            // connection.execute() will close on its own if
            // exception is encountered
        }
        return course;
    }
}
