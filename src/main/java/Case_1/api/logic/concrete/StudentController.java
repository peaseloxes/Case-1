package Case_1.api.logic.concrete;

import Case_1.api.domain.concrete.Pagination;
import Case_1.api.logic.abs.RestController;
import Case_1.api.util.RestUtil;
import Case_1.data.access.abs.DataConnectionException;
import Case_1.data.access.concrete.OracleDataConnection;
import Case_1.data.logic.abs.DataSource;
import Case_1.data.logic.concrete.StudentRepository;
import Case_1.data.object.concrete.StudentDataHandler;
import Case_1.domain.concrete.Student;
import Case_1.logic.concrete.StudentBuilder;
import Case_1.util.DateUtil;
import Case_1.util.i18n.LangUtil;
import lombok.NoArgsConstructor;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

/**
 * Created by alex on 10/6/15.
 */
@Path(StudentController.ROOT)
@Produces("application/json")
@NoArgsConstructor
public class StudentController extends RestController<StudentRepository> {

    // for injection, if desired
    private StudentRepository repository;

    public StudentController(final StudentRepository repository) {
        this.repository = repository;
    }

    // for use in @Path
    public static final String ROOT = "/students";

    // for the general controller
    private static final String BY_ID = "/";
    private static final String BY_WEEK = "/{year}/{week}";
    private static final String CREATE = "/create";
    private static final String NAME = "Students";

    @GET
    @Path(BY_ID)
    public Response getById(@PathParam("id") final int id) {
        try {
            Pagination<Student> pagination = new Pagination("/", 0, 0, Arrays.asList(getRepository().getById(id)));
            pagination.setNext("/" + (id - 1));
            pagination.setPrev("/" + (id + 1));
            if (id < 0) {
                pagination.setPrev("");
            }
            return RestUtil.buildResponse(pagination, "Student");
        } catch (DataConnectionException e) {

            // we wrote proper messages, so add error message for user info
            return RestUtil.buildMessageResponse(LangUtil.labelFor("error.student.notFound") + " " + e.getMessage());
        } catch (Exception e) {

            // who knows what's in here, don't return its message
            return RestUtil.buildMessageResponse(LangUtil.labelFor("error.student.notFound"));
        }
    }

    @POST
    @Path(CREATE)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createStudent(
            @FormDataParam("courseInstanceId") final String courseInstanceId,
            @FormDataParam("firstName") final String firstName,
            @FormDataParam("lastName") final String lastName,
//            @FormDataParam("email") final String email,
            @FormDataParam("companyName") final String companyName,
            @FormDataParam("bidNumber") final String bidNumber,
//            @FormDataParam("accountNumber") final String accountNumber,
//            @FormDataParam("phoneNumber") final String phoneNumber,
            @FormDataParam("department") final String department
//            @FormDataParam("streetNumber") final String streetNumber,
//            @FormDataParam("streetName") final String streetName,
//            @FormDataParam("postalCode") final String postalCode,
//            @FormDataParam("city") final String city
    ) {
        StudentBuilder studentBuilder = StudentBuilder.getInstance();
        Student student = studentBuilder
                .firstName(firstName)
                .lastName(lastName)
                .email("email")
                .addressCompany(
                        0,
                        companyName,
                        bidNumber,
                        "accountNumber",
                        "phoneNumber",
                        department,
                        0,
                        "streetNumber",
                        "streetName",
                        "postalCode",
                        "city")
                .create();
        try {
            getRepository().add(student);
            int instanceId = Integer.valueOf(courseInstanceId);
            getRepository().subscribeTo(student, instanceId);
        } catch (DataConnectionException e) {

            // we wrote proper messages, so add error message for user info
            return RestUtil.buildMessageResponse(LangUtil.labelFor("error.user.notCreated") + " " + e.getMessage());
        } catch (Exception e) {

            // who knows what's in here, don't return its message
            return RestUtil.buildMessageResponse(LangUtil.labelFor("error.user.notCreated"));
        }

        return RestUtil.buildMessageResponse(LangUtil.labelFor("success.user.created"));
    }

    @GET
    @Path(BY_WEEK)
    public Response getByWeek(
            @PathParam("year") final int year,
            @PathParam("week") final int week

    ) {
        try {
            Pagination<Student> page = new Pagination<>(
                    getIdUrl(),
                    0,
                    0,
                    getRepository().getStudentCoursesByYearWeek(year, week));
            page.setPrev(DateUtil.getPreviousYearWeek(year, week));
            page.setNext(DateUtil.getNextYearWeek(year, week));
            return RestUtil.buildResponse(page, "Students by week");
        } catch (DataConnectionException e) {

            // we wrote proper messages, so add error message for user info
            return RestUtil.buildMessageResponse(LangUtil.labelFor("error.student.notFound") + " " + e.getMessage());
        } catch (Exception e) {

            // who knows what's in here, don't return its message
            return RestUtil.buildMessageResponse(LangUtil.labelFor("error.student.notFound"));
        }


    }

    @Override
    protected StudentRepository getRepository() {
        if (repository == null) {
            repository = new StudentRepository(
                    new DataSource<Student>(
                            new StudentDataHandler(
                                    new OracleDataConnection()
                            )
                    )
            );
        }
        return repository;
    }

    @Override
    protected String getIdUrl() {
        return BY_ID;
    }

    @Override
    protected String getName() {
        return NAME;
    }
}
