package Case_1.domain.concrete;

import Case_1.domain.abs.RestObject;
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
public class Company extends RestObject {
    private int id;
    private Address address;
    private String name;
    private String accountNumber;
    private String bidNumber;
    private String phoneNumber;
    private String department;
    // private String contactName;
}
