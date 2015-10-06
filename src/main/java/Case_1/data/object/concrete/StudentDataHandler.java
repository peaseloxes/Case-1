package Case_1.data.object.concrete;

import Case_1.data.access.abs.DataConnection;
import Case_1.data.access.abs.DataConnectionException;
import Case_1.data.access.concrete.DataResult;
import Case_1.data.access.concrete.SQLQuery;
import Case_1.data.object.abs.DataHandler;
import Case_1.domain.concrete.Address;
import Case_1.domain.concrete.Company;
import Case_1.domain.concrete.Student;
import Case_1.logic.concrete.StudentBuilder;
import Case_1.util.i18n.LangUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by alex on 10/6/15.
 */
public class StudentDataHandler implements
        DataHandler<DataConnection, Student> {
    private DataConnection connection;

    public StudentDataHandler(final DataConnection connection) {
        setConnection(connection);
    }

    @Override
    public void setConnection(final DataConnection connection) {
        this.connection = connection;
    }


    @Override
    public Student getById(int id) {
        return null;
    }

    //TODO proper company in database, not unique one for every student

    @Override
    public boolean add(final Student student) throws DataConnectionException {
        boolean response = false;

        try {
            // add address
            addAddres(student.getCompany().getAddress());

            // add company
            addCompany(student.getCompany());

            // add student
            if (!studentExists(student.getFirstName(), student.getLastName())) {
                addStudent(student);
            }
        } catch (NullPointerException e) {
            throw new DataConnectionException(LangUtil.labelFor("error.course.addition"));
        }
        return response;
    }

    private void addStudent(final Student student) throws DataConnectionException{
        int companyId = getCompanyId(student.getCompany());
        connection.open();
        String sql = "INSERT INTO STUDENT (ID,FIRSTNAME,LASTNAME,EMAIL,COMPANYID,PRIVATEID,DISCOUNTID)" +
                "values (SEQ_STUDENT.NEXTVAL,?,?,?,?,null,null)";
        SQLQuery query = new SQLQuery();
        query.setSql(sql);
        query.addParam(student.getFirstName(), SQLQuery.Type.STRING);
        query.addParam(student.getLastName(), SQLQuery.Type.STRING);
        query.addParam(student.getEmail(), SQLQuery.Type.STRING);
        query.addParam(companyId, SQLQuery.Type.INT);
        connection.executeUpdate(query);
        connection.close();
    }

    private boolean studentExists(final String firstName, final String lastName) throws DataConnectionException {
        connection.open();
        String sql = "SELECT * FROM STUDENT WHERE FIRSTNAME = ? AND LASTNAME = ?";
        SQLQuery query = new SQLQuery();
        query.setSql(sql);
        query.addParam(firstName, SQLQuery.Type.STRING);
        query.addParam(lastName, SQLQuery.Type.STRING);
        DataResult result = connection.execute(query);
        connection.close();
        if (result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private void addCompany(final Company company) throws DataConnectionException {
        int addressId = getAddressId(company.getAddress());
        connection.open();
        String sql = "INSERT INTO COMPANY (ID,ADDRESSID,NAME,ACCOUNTNUMBER,BIDNUMER,DEPARTMENT,PHONENUMBER,CONTACTNAME) " +
                "values (SEQ_COMPANY.NEXTVAL,?,?,?,?,?,?,?)";
        SQLQuery query = new SQLQuery();
        query.setSql(sql);
        query.addParam(addressId, SQLQuery.Type.INT);
        query.addParam(company.getName(), SQLQuery.Type.STRING);
        query.addParam(company.getAccountNumber(), SQLQuery.Type.STRING);
        query.addParam(company.getBidNumber(), SQLQuery.Type.STRING);
        query.addParam(company.getDepartment(), SQLQuery.Type.STRING);
        query.addParam(company.getPhoneNumber(), SQLQuery.Type.STRING);
        query.addParam("", SQLQuery.Type.STRING);
        connection.executeUpdate(query);
        connection.close();
    }

    private void addAddres(final Address address) throws DataConnectionException {
        connection.open();
        String sql = "INSERT INTO ADDRESS (ID,STREETNAME,STREETNUMBER,POSTALCODE,CITY) " +
                "values (SEQ_ADDRESS.NEXTVAL,?,?,?,?)";
        SQLQuery query = new SQLQuery();
        query.setSql(sql);
        query.addParam(address.getStreetName(), SQLQuery.Type.STRING);
        query.addParam(address.getStreetNumber(), SQLQuery.Type.STRING);
        query.addParam(address.getPostalCode(), SQLQuery.Type.STRING);
        query.addParam(address.getCity(), SQLQuery.Type.STRING);
        connection.executeUpdate(query);
        connection.close();
    }

    private int getCompanyId(final Company company) throws DataConnectionException {
        connection.open();
        String sql = "SELECT ID FROM COMPANY WHERE NAME = ? AND DEPARTMENT = ? AND PHONENUMBER = ?";
        SQLQuery query = new SQLQuery();
        query.setSql(sql);
        query.addParam(company.getName(), SQLQuery.Type.STRING);
        query.addParam(company.getDepartment(), SQLQuery.Type.STRING);
        query.addParam(company.getPhoneNumber(), SQLQuery.Type.STRING);

        DataResult result = connection.execute(query);
        connection.close();

        if(result.isEmpty()){
            // TODO langutil
            throw new DataConnectionException("Can't find company");
        }else{
            return ((BigDecimal)result.getRow(0).get("ID")).intValue();
        }
    }

    private int getAddressId(final Address address) throws DataConnectionException {
        connection.open();
        String sql = "SELECT ID FROM ADDRESS WHERE STREETNAME = ? AND STREETNUMBER = ? AND POSTALCODE = ? AND CITY =?";
        SQLQuery query = new SQLQuery();
        query.setSql(sql);
        query.addParam(address.getStreetName(), SQLQuery.Type.STRING);
        query.addParam(address.getStreetNumber(), SQLQuery.Type.STRING);
        query.addParam(address.getPostalCode(), SQLQuery.Type.STRING);
        query.addParam(address.getCity(), SQLQuery.Type.STRING);
        DataResult result = connection.execute(query);
        connection.close();

        if(result.isEmpty()){
            // TODO langutil
            throw new DataConnectionException("Can't find address");
        }else{
            return ((BigDecimal)result.getRow(0).get("ID")).intValue();
        }

    }

    @Override
    public List<Student> getByKey(String key, Object value) {
        return null;
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        try {
            connection.open();
            String sql = "SELECT * FROM STUDENT ORDER BY ID";
            SQLQuery query = new SQLQuery();
            query.setSql(sql);
            DataResult studentResult = connection.execute(query);


            Iterator<Map<String, Object>> it = studentResult.getIterator();
            while (it.hasNext()) {
                Map map = it.next();

                StudentBuilder builder = StudentBuilder.getInstance();
                builder .id(((BigDecimal) map.get("ID")).intValue())
                        .firstName((String) map.get("FIRSTNAME"))
                        .lastName((String)map.get("LASTNAME"))
                        .email((String)map.get("EMAIL"));


                sql = "SELECT * FROM COMPANY WHERE ID = ?";
                query = new SQLQuery();
                query.addParam(((BigDecimal) map.get("COMPANYID")).intValue(), SQLQuery.Type.INT);
                query.setSql(sql);
                DataResult companyResult = connection.execute(query);

                if(companyResult.isEmpty()){
                    throw new DataConnectionException("company not found");
                }

                sql = "SELECT * FROM ADDRESS WHERE ID = ?";
                query = new SQLQuery();
                query.addParam(((BigDecimal) companyResult.getRow(0).get("ADDRESSID")).intValue(), SQLQuery.Type.INT);
                query.setSql(sql);
                DataResult addressResult = connection.execute(query);


                if(addressResult.isEmpty()){
                    throw new DataConnectionException("company not found");
                }

                Map<String, Object> companyMap = companyResult.getRow(0);
                Map<String, Object> addressMap = addressResult.getRow(0);

                builder.addressCompany(
                        ((BigDecimal) companyMap.get("ID")).intValue(),
                        (String) companyMap.get("NAME"),
                        (String) companyMap.get("BIDNUMER"),
                        (String) companyMap.get("ACCOUNTNUMBER"),
                        (String) companyMap.get("PHONENUMBER"),
                        (String) companyMap.get("DEPARTMENT"),
                        ((BigDecimal) addressMap.get("ID")).intValue(),
                        (String) addressMap.get("STREETNUMBER"),
                        (String) addressMap.get("STREETNAME"),
                        (String) addressMap.get("POSTALCODE"),
                        (String) addressMap.get("CITY")
                );
                students.add(builder.create());
            }

        } catch (DataConnectionException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public List<Student> getAll(int start, int limit) {
        return null;
    }

    @Override
    public boolean subscribeTo(final Student student, final int courseInstanceId) throws DataConnectionException{
        int studentId = getStudentId(student);
        connection.open();
        String sql = "INSERT INTO STUDENT_COURSEINSTANCE (STUDENTID,COURSEINSTANCEID,APPLICATIONDATE) " +
                "values (?,?,SYSDATE)";
        SQLQuery query = new SQLQuery();
        query.setSql(sql);
        query.addParam(studentId, SQLQuery.Type.INT);
        query.addParam(courseInstanceId, SQLQuery.Type.INT);
        connection.executeUpdate(query);
        connection.close();
        return true;
    }

    @Override
    public List<Student> getStudentCoursesByYearWeek(final int year,final int week) throws DataConnectionException{
        List<Student> students = new ArrayList<>();

        //select * from work_table where created_date beween to_date('9/18/2007','MM/DD/YYYY') and to_date('03/29/2008','MM/DD/YYYY')
        connection.open();

        // get all courses+instances in this week

        String sql = "SELECT * FROM STUDENT ORDER BY ID";
        SQLQuery query = new SQLQuery();
        query.setSql(sql);
        DataResult studentResult = connection.execute(query);

        return students;
    }

    private int getStudentId(final Student student) throws DataConnectionException {
        connection.open();
        String sql = "SELECT ID FROM STUDENT WHERE FIRSTNAME = ? AND LASTNAME = ?";
        SQLQuery query = new SQLQuery();
        query.setSql(sql);
        query.addParam(student.getFirstName(), SQLQuery.Type.STRING);
        query.addParam(student.getLastName(), SQLQuery.Type.STRING);
        DataResult result = connection.execute(query);
        connection.close();

        if(result.isEmpty()){
            // TODO langutil
            throw new DataConnectionException("Can't find student");
        }else{
            return ((BigDecimal)result.getRow(0).get("ID")).intValue();
        }
    }
}
