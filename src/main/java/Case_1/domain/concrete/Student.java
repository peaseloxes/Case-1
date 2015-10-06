package Case_1.domain.concrete;

import Case_1.domain.abs.RestObject;
import Case_1.domain.abs.Validatable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by alex on 10/6/15.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student extends RestObject implements Validatable {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Company company;
    // private int privateId;
    // private int discountId;

    @Override
    public boolean validate() {
        // students are lazy
        return true;
    }
}
