package Case_1.domain.concrete;

import Case_1.domain.abs.RestObject;
import Case_1.domain.abs.Validatable;
import Case_1.util.rules.BusinessRules;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course extends RestObject implements Validatable {

    private int id;
    private String title;
    private String code;
    private int durationDays;
    private int maxApplicants;
    private List<CourseInstance> instances = new ArrayList<>();

    @Override
    public boolean validate() {
        try {
            if (id < 0) {
                return false;
            }
            if (code == null) {
                return false;
            }
            if (maxApplicants < BusinessRules
                    .get("min_course_applicants", Integer.class)) {
                return false;
            }
            if (durationDays > BusinessRules
                    .get("max_course_duration", Integer.class)
                    || durationDays < BusinessRules
                    .get("min_course_duration", Integer.class)) {
                return false;
            }
        } catch (BusinessRules.RuleNotFoundException e) {
            return false;
        }
        Set<LocalDateTime> startDates = new HashSet<>();
        Set<LocalDateTime> endDates = new HashSet<>();
        if (instances != null) {
            for (CourseInstance instance : instances) {
                if (!instance.validate()) {
                    return false;
                }
                startDates.add(instance.getStartDate());
                endDates.add(instance.getEndDate());
            }
        }

        // do we need to force unique dates?
        try {
            if (!BusinessRules.get("course_days_sharing_allowed", Boolean
                    .class)) {

                // since hashsets don't allow duplicates
                // a hashset with duplicates would be smaller than
                // total the amount of dates
                if (startDates.size() < instances.size()) {
                    return false;
                }
                if (endDates.size() < instances.size()) {
                    return false;
                }
            }
        } catch (BusinessRules.RuleNotFoundException e) {
            return false;
        }

        return true;
    }
}
