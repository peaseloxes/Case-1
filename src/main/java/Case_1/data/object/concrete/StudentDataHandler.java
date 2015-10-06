package Case_1.data.object.concrete;

import Case_1.data.access.abs.DataConnection;
import Case_1.data.access.abs.DataConnectionException;
import Case_1.data.access.concrete.DataResult;
import Case_1.data.access.concrete.SQLQuery;
import Case_1.data.object.abs.DataHandler;
import Case_1.domain.concrete.Address;
import Case_1.domain.concrete.Company;
import Case_1.domain.concrete.Student;
import Case_1.util.i18n.LangUtil;

import java.math.BigDecimal;
import java.util.List;

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
        return null;
    }

    @Override
    public List<Student> getAll(int start, int limit) {
        return null;
    }
}
