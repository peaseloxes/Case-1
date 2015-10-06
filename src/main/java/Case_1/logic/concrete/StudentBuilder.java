package Case_1.logic.concrete;

import Case_1.domain.concrete.Address;
import Case_1.domain.concrete.Company;
import Case_1.domain.concrete.Student;
import Case_1.logic.abs.Builder;

/**
 * Created by alex on 10/6/15.
 */
public class StudentBuilder implements Builder {
    private Student student;

    private StudentBuilder() {
        student = new Student();
    }

    /**
     * Retrieve a StudentBuilder instance.
     *
     * @return a StudentBuilder instance.
     */
    public static StudentBuilder getInstance() {
        return new StudentBuilder();
    }

    @Override
    public StudentBuilder id(final int id) {
        student.setId(id);
        return this;
    }

    public StudentBuilder firstName(final String firstName) {
        student.setFirstName(firstName);
        return this;
    }

    public StudentBuilder lastName(final String lastName) {
        student.setLastName(lastName);
        return this;
    }

    public StudentBuilder email(final String email) {
        student.setEmail(email);
        return this;
    }

    public StudentBuilder addressCompany(
            // company
            final int companyId,
            final String companyName,
            final String bidNumber,
            final String accountNumber,
            final String phoneNumber,
            final String department,
            // address
            final int addressId,
            final String streetNumber,
            final String streetName,
            final String postalCode,
            final String city
    ) {

        Address address = new Address(addressId, streetNumber, streetName, postalCode, city);
        Company company = new Company(companyId, address, companyName, accountNumber, bidNumber, phoneNumber, department);
        student.setCompany(company);
        return this;
    }

    @Override
    public boolean validate() {
        if (student == null) {
            return false;
        }
        return student.validate();
    }

    @Override
    public Student create() {
        return student;
    }
}
