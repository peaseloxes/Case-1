package Case_1.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
@Getter
@Setter
public class Course {
    public Course(final int id, final String name){
        this.id = id;
        this.name = name;
    }
    private int id;
    private String name;
}
