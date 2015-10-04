package Case_1.domain.concrete;

import Case_1.domain.abs.RestObject;
import Case_1.domain.abs.Validatable;
import Case_1.util.rules.ArgList;
import Case_1.util.rules.BusinessRules;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
@Getter
@Setter
@AllArgsConstructor
public class CourseInstance extends RestObject implements Validatable {

    private int id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean definitive;
    private Double basePrice;

    @Override
    public boolean validate() {
        if (id < 0) {
            return false;
        }
        try {
            if (startDate == null
                    || BusinessRules
                    .get("days_course_not_allowed", ArgList.class)
                    .contains(startDate.getDayOfWeek().toString())) {
                return false;

            }
            if (endDate == null
                    || endDate.isBefore(startDate)
                    || BusinessRules
                    .get("days_course_not_allowed", ArgList.class)
                    .contains(endDate.getDayOfWeek().toString())) {
                return false;
            }
        } catch (BusinessRules.RuleNotFoundException e) {
            return false;
        }
        return true;
    }
}
