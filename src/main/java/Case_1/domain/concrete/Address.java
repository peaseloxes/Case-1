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
public class Address extends RestObject {
    private int id;
    private String streetNumber;
    private String streetName;
    private String postalCode;
    private String city;
}
