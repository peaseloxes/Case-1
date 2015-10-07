package Case_1.data.object.concrete;

import Case_1.data.access.abs.DataConnection;
import Case_1.data.access.abs.DataConnectionException;
import Case_1.data.access.concrete.DataResult;
import Case_1.data.access.concrete.SQLQuery;
import Case_1.data.object.abs.DataHandler;
import Case_1.domain.concrete.Course;
import Case_1.domain.concrete.CourseInstance;
import Case_1.domain.concrete.Student;
import Case_1.logic.concrete.CourseBuilder;
import Case_1.logic.concrete.StudentBuilder;
import Case_1.util.DateUtil;
import Case_1.util.i18n.LangUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

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
            String sql = "SELECT * FROM COURSE WHERE ID = ?";
            SQLQuery query = new SQLQuery();
            query.setSql(sql);
            query.addParam(id, SQLQuery.Type.INT);

            DataResult result = connection.execute(query);
            if (!result.isEmpty()) {
                // TODO use builder and make sure validity is ok, also course instances
                Map<String, Object> map = result.getRow(0);
                course = CourseBuilder.getInstance()
                        .id(((BigDecimal) map.get("ID")).intValue())
                        .title((String) map.get("TITLE"))
                        .code((String) map.get("COURSECODE"))
                        .duration(((BigDecimal) map.get("DURATIONDAYS")).intValue())
                        .maxApplicants(((BigDecimal) map.get("MAXAPPLICANTS")).intValue())
                        .create();
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
            e.printStackTrace();
        }
        return course;
    }

    @Override
    public boolean add(final Course course) throws DataConnectionException {
        boolean response = false;

        try {
            if (!courseExists(course.getCode())) {
                addCourse(course);
                response = true;
            }
            int courseid = getCourseIdByCode(course.getCode());
            for (CourseInstance courseInstance : course.getInstances()) {
                if (!courseInstanceExistsFor(courseid, courseInstance.getStartDate())) {
                    addCourseInstanceFor(courseInstance, courseid);
                }
            }
        } catch (NullPointerException e) {
            throw new DataConnectionException(LangUtil.labelFor("error.course.addition"));
        }
        return response;
    }

    private void addCourseInstanceFor(CourseInstance courseInstance, int id) throws DataConnectionException {
        connection.open();
        // then course instances
        String sql = "INSERT INTO COURSEINSTANCE (ID,COURSEID,STARTDATE,ENDDATE,DEFINITIVE,BASEPRICE) " +
                "values (SEQ_COURSEINSTANCE.NEXTVAL,?,?,?,?,?)";
        SQLQuery query = new SQLQuery();
        query.setSql(sql);
        query.addParam(id, SQLQuery.Type.INT);
        query.addParam(Timestamp.valueOf(courseInstance.getStartDate()), SQLQuery.Type.DATE);
        query.addParam(Timestamp.valueOf(courseInstance.getEndDate()), SQLQuery.Type.DATE);
        query.addParam(courseInstance.isDefinitive(), SQLQuery.Type.BOOLEAN);
        query.addParam(courseInstance.getBasePrice(), SQLQuery.Type.DOUBLE);
        connection.executeUpdate(query);
        connection.close();
    }


    @Override
    public List<Course> getByKey(final String key, final Object value) {
        List<Course> courses = new ArrayList<>();
        try {
            connection.open();
            String sql = "SELECT * FROM COURSE WHERE ? = ?";
            SQLQuery query = new SQLQuery();
            query.setSql(sql);
            query.addParam(key, SQLQuery.Type.STRING);

            // TODO make object generic
            query.addParam(value, SQLQuery.Type.STRING);

            DataResult result = connection.execute(query);

            Iterator<Map<String, Object>> it = result.getIterator();

            while (it.hasNext()) {
                Map<String, Object> map = it.next();
                CourseBuilder builder = CourseBuilder.getInstance();

                SQLQuery instanceQuery = new SQLQuery();
                instanceQuery.setSql("SELECT * FROM COURSEINSTANCE WHERE COURSEID = ?");
                instanceQuery.addParam(map.get("id"), SQLQuery.Type.INT);

                DataResult instanceResult = connection.execute(instanceQuery);

                builder
                        .id((Integer) map.get("id"))
                        .code((String) map.get("code"))
                        .duration((Integer) map.get("duration"))
                        .maxApplicants((Integer) map.get("applicants"));

                Iterator<Map<String, Object>> itt = instanceResult.getIterator();
                while (itt.hasNext()) {
                    Map<String, Object> instance = itt.next();
                    builder.instance(
                            (Integer) instance.get("ID"),
                            ((Timestamp) instance.get("STARTDATE")).toLocalDateTime(),
                            ((Timestamp) instance.get("ENDDATE")).toLocalDateTime(),
                            (double) instance.get("BASEPRICE")
                    );
                }

                Course course = builder.create();
                courses.add(course);
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
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        try {
            connection.open();
            String sql = "SELECT * FROM COURSE ORDER BY ID";
            SQLQuery query = new SQLQuery();
            query.setSql(sql);

            DataResult result = connection.execute(query);

            Iterator<Map<String, Object>> it = result.getIterator();

            while (it.hasNext()) {
                Map<String, Object> map = it.next();
                CourseBuilder builder = CourseBuilder.getInstance();

                SQLQuery instanceQuery = new SQLQuery();
                instanceQuery.setSql("SELECT * FROM COURSEINSTANCE WHERE COURSEID = ?");
                instanceQuery.addParam(((BigDecimal) map.get("ID")).intValue(), SQLQuery.Type.INT);

                builder
                        .id(((BigDecimal) map.get("ID")).intValue())
                        .title((String) map.get("TITLE"))
                        .code((String) map.get("COURSECODE"))
                        .duration(((BigDecimal) map.get("DURATIONDAYS")).intValue())
                        .maxApplicants(((BigDecimal) map.get("MAXAPPLICANTS")).intValue());

                DataResult instanceResult = connection.execute(instanceQuery);

                Iterator<Map<String, Object>> itt = instanceResult.getIterator();
                while (itt.hasNext()) {
                    Map<String, Object> instance = itt.next();
                    builder.instance(
                            ((BigDecimal) instance.get("ID")).intValue(),
                            ((Timestamp) instance.get("STARTDATE")).toLocalDateTime(),
                            ((Timestamp) instance.get("ENDDATE")).toLocalDateTime(),
                            ((BigDecimal) instance.get("BASEPRICE")).doubleValue()
                    );
                }

                Course course = builder.create();
                courses.add(course);
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
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public boolean subscribeTo(Course subscriber, int subscribableId) {
        throw new UnsupportedOperationException("Silly you, courses can't subscribe to anything");
    }

    /** TODO should be moved up to repository level,
     // CourseRepository & StudentRepository should do the combining
     // that way students remain in students and courses in courses
     //
     // merge with {@link StudentDataHandler#getStudentCoursesByYearWeek(int, int)}
     //
     // worst methods in entire application:
     */
    @Override
    @Deprecated
    public List<Course> getStudentCoursesByYearWeek(final int year, final int week) throws DataConnectionException {
        List<Course> courses = new ArrayList<>();
        try {
            //select * from work_table where created_date beween to_date('9/18/2007','MM/DD/YYYY') and to_date('03/29/2008','MM/DD/YYYY')
            connection.open();

            String startDate = DateUtil.getStartDate(year, week);
            String endDate = DateUtil.getEndDate(year, week);

            String sql = "SELECT * FROM COURSEINSTANCE WHERE STARTDATE between to_date(?,'DD/MM/YYYY') and to_date(?,'DD/MM/YYYY')";
            SQLQuery query = new SQLQuery();
            query.setSql(sql);
            query.addParam(startDate, SQLQuery.Type.STRING);
            query.addParam(endDate, SQLQuery.Type.STRING);
            DataResult courseInstances = connection.execute(query);

            Iterator<Map<String,Object>> instanceIterator = courseInstances.getIterator();

            while(instanceIterator.hasNext()) {
                Map<String,Object> courseInstance = instanceIterator.next();
                int courseId = ((BigDecimal) courseInstance.get("COURSEID")).intValue();
                int courseInstanceId = ((BigDecimal) courseInstance.get("ID")).intValue();
                sql = "SELECT * FROM COURSE WHERE ID = ?";
                query = new SQLQuery();
                query.setSql(sql);
                query.addParam(courseId, SQLQuery.Type.INT);
                DataResult courseResult = connection.execute(query);

                Map<String,Object> courseResultMap = courseResult.getRow(0);

                CourseBuilder courseBuilder = CourseBuilder.getInstance();
                courseBuilder.id(((BigDecimal) courseResultMap.get("ID")).intValue())
                        .title((String) courseResultMap.get("TITLE"))
                        .code((String) courseResultMap.get("COURSECODE"))
                        .duration(((BigDecimal) courseResultMap.get("DURATIONDAYS")).intValue())
                        .maxApplicants(((BigDecimal) courseResultMap.get("MAXAPPLICANTS")).intValue());

                sql = "SELECT * FROM STUDENT WHERE ID IN(SELECT ID FROM STUDENT_COURSEINSTANCE WHERE COURSEINSTANCEID = ?)";
                query = new SQLQuery();
                query.setSql(sql);
                query.addParam(courseInstanceId, SQLQuery.Type.INT);
                DataResult studentResult = connection.execute(query);

                Iterator<Map<String,Object>> studentIterator = studentResult.getIterator();

                List<Student> studentList = new LinkedList<>();
                while (studentIterator.hasNext()){
                    Map<String,Object> studentMap = studentIterator.next();

                    StudentBuilder studentBuilder = StudentBuilder.getInstance();
                    studentBuilder.id(((BigDecimal) studentMap.get("ID")).intValue())
                            .firstName((String) studentMap.get("FIRSTNAME"))
                            .lastName((String) studentMap.get("LASTNAME"))
                            .email((String) studentMap.get("EMAIL"));
                    studentList.add(studentBuilder.create());

                }

                courseBuilder.instance(
                        ((BigDecimal) courseInstance.get("ID")).intValue(),
                        ((Timestamp) courseInstance.get("STARTDATE")).toLocalDateTime(),
                        ((Timestamp) courseInstance.get("ENDDATE")).toLocalDateTime(),
                        ((BigDecimal) courseInstance.get("BASEPRICE")).doubleValue(),
                        studentList
                );
                courses.add(courseBuilder.create());
            }

        }catch (DataConnectionException e) {
            e.printStackTrace();
        }
        return courses;
    }


    private boolean courseExists(final String code) throws DataConnectionException {
        connection.open();
        String sql = "SELECT * FROM COURSE WHERE COURSECODE = ?";
        SQLQuery query = new SQLQuery();
        query.setSql(sql);
        query.addParam(code, SQLQuery.Type.STRING);
        DataResult result = connection.execute(query);

        connection.close();
        if (result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean courseInstanceExistsFor(final int courseId, final LocalDateTime start) throws DataConnectionException {
        connection.open();
        String sql = "SELECT * FROM COURSEINSTANCE WHERE COURSEID = ? AND STARTDATE = to_date(?,'DD/MM/YYYY')";
        SQLQuery query = new SQLQuery();
        query.setSql(sql);
        query.addParam(courseId, SQLQuery.Type.INT);
        query.addParam(start.getDayOfMonth() + "/" + start.getMonthValue() + "/" + start.getYear(), SQLQuery.Type.STRING);

        DataResult result = connection.execute(query);

        connection.close();
        if (result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private int getCourseIdByCode(final String code) throws DataConnectionException {
        connection.open();
        String sql = "SELECT * FROM COURSE WHERE COURSECODE = ?";
        SQLQuery query = new SQLQuery();
        query.setSql(sql);
        query.addParam(code, SQLQuery.Type.STRING);
        DataResult result = connection.execute(query);

        connection.close();
        if (result.isEmpty()) {
            throw new DataConnectionException("No course found");
        } else {
            return ((BigDecimal) result.getRow(0).get("ID")).intValue();
        }
    }

    private void addCourse(final Course course) throws DataConnectionException {
        connection.open();

        // add course
        String sql = "INSERT INTO COURSE (ID,COURSECODE,TITLE,DURATIONDAYS,MAXAPPLICANTS) " +
                "values (SEQ_COURSE.NEXTVAL,?,?,?,?)";
        SQLQuery query = new SQLQuery();
        query.setSql(sql);
        query.addParam(course.getCode(), SQLQuery.Type.STRING);
        query.addParam(course.getTitle(), SQLQuery.Type.STRING);
        query.addParam(course.getDurationDays(), SQLQuery.Type.INT);
        query.addParam(course.getMaxApplicants(), SQLQuery.Type.INT);

        connection.executeUpdate(query);
    }
}
