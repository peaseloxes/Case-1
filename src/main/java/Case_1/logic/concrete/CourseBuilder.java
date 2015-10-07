package Case_1.logic.concrete;

import Case_1.api.logic.concrete.CourseController;
import Case_1.domain.concrete.Course;
import Case_1.domain.concrete.CourseInstance;
import Case_1.domain.concrete.Student;
import Case_1.logic.abs.Builder;
import Case_1.logic.abs.IncorrectVariablesException;
import Case_1.util.i18n.LangUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class CourseBuilder implements Builder<CourseBuilder, Course> {

    // Design pattern: builder

    private Course course;

    // force usage of getInstance()
    private CourseBuilder() {
        course = new Course();
    }

    /**
     * Retrieve a CourseBuilder instance.
     *
     * @return a CourseBuilder instance.
     */
    public static CourseBuilder getInstance() {
        return new CourseBuilder();
    }

    @Override
    public CourseBuilder id(final int id) {
        course.setId(id);
        course.setSelf(CourseController.ROOT + "/" + id);
        return this;
    }

    @Override
    public final boolean validate() {
        if (course == null) {
            return false;
        }
        return course.validate();
    }

    /**
     * Creates the course.
     * TODO which ones are required?
     *
     * @return the created Course
     * @see Builder#create()
     */
    @Override
    public Course create() {
        if (validate()) {
            return course;
        }
        throw new IncorrectVariablesException(LangUtil
                .labelFor("error.creation.incorrectVariables"));
    }

    /**
     * Set the title for the course.
     *
     * @param title the title
     * @return this builder instance
     */
    public CourseBuilder title(final String title) {
        course.setTitle(title);
        return this;
    }

    /**
     * Set the maximum amount of applicants for the course.
     *
     * @param amount the max number of applicants
     * @return this builder instance
     */
    public CourseBuilder maxApplicants(final int amount) {
        course.setMaxApplicants(amount);
        return this;
    }

    /**
     * Set the duration in days for the course.
     *
     * @param days the duration
     * @return this builder instance
     */
    public CourseBuilder duration(final int days) {
        course.setDurationDays(days);
        return this;
    }

    /**
     * Add an instance to this course.
     *
     * @param id        the course instance id
     * @param startDate the starting date
     * @param endData   the end date
     * @param basePrice the base price of the instance
     * @return this builder instance
     */
    public CourseBuilder instance(final int id,
                                  final LocalDateTime startDate,
                                  final LocalDateTime endData,
                                  final Double basePrice) {
        course.getInstances().add(
                new CourseInstance(id,
                        startDate,
                        endData,
                        false,
                        basePrice, null));
        return this;
    }

    /**
     * Add an instance to this course with a studentlist.
     *
     * @param id        the course instance id
     * @param startDate the starting date
     * @param endData   the end date
     * @param basePrice the base price of the instance
     * @param students  student list
     * @return this builder instance
     */
    public CourseBuilder instance(final int id,
                                  final LocalDateTime startDate,
                                  final LocalDateTime endData,
                                  final Double basePrice,
                                  final List<Student> students) {
        course.getInstances().add(
                new CourseInstance(id,
                        startDate,
                        endData,
                        false,
                        basePrice, students));
        return this;
    }

    /**
     * Add a code to this course.
     *
     * @param code the code
     * @return this builder instance
     */
    public CourseBuilder code(final String code) {
        course.setCode(code);
        return this;
    }
}
