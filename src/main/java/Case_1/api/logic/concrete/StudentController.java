package Case_1.api.logic.concrete;

import Case_1.api.domain.concrete.Pagination;
import Case_1.api.logic.abs.RestController;
import Case_1.api.util.RestUtil;
import Case_1.data.access.concrete.OracleDataConnection;
import Case_1.data.logic.abs.DataSource;
import Case_1.data.logic.concrete.CourseRepository;
import Case_1.data.logic.concrete.StudentRepository;
import Case_1.data.object.concrete.CourseDataHandler;
import Case_1.data.object.concrete.StudentDataHandler;
import Case_1.domain.concrete.Course;
import Case_1.domain.concrete.Student;
import Case_1.logic.concrete.StudentBuilder;
import Case_1.util.i18n.LangUtil;
import lombok.NoArgsConstructor;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by alex on 10/6/15.
 */
@Path(StudentController.ROOT)
@Produces("application/json")
@NoArgsConstructor
public class StudentController extends RestController<StudentRepository> {

    private StudentRepository repository;

    public StudentController(final StudentRepository repository) {
        this.repository = repository;
    }

    // for use in @Path
    public static final String ROOT = "/students";

    // for the general controller
    private static final String BY_ID = "/students/";
    private static final String BY_WEEK = "/{year}/{week}";
    private static final String CREATE = "/create";
    private static final String NAME = "Students";

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
        StudentBuilder builder = StudentBuilder.getInstance();
        Student student = builder.firstName(firstName)
                .lastName(lastName)
                .email("email")
                .addressCompany(0, companyName, bidNumber, "accountNumber", "phoneNumber", department, 0, "streetNumber", "streetName", "postalCode", "city")
                .create();
        try {
            getRepository().add(student);
            int instanceId = Integer.valueOf(courseInstanceId);
            getRepository().subscribeTo(student, instanceId);
        } catch (Exception e) {
            e.printStackTrace();
            return RestUtil.buildMessageResponse(LangUtil.labelFor("error.user.notCreated") + " " + e.getMessage());
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
            Pagination<Student> page = new Pagination<>(getIdUrl(),0,0,  getRepository().getStudentCoursesByYearWeek(year, week));

            // TODO year transfer
            page.setPrev("/"+year+"/"+(week-1));
            page.setNext("/"+year+"/"+(week+1));
            return RestUtil.buildResponse(page,"Students by week");
        } catch (Exception e) {
            e.printStackTrace();
            return RestUtil.buildMessageResponse(LangUtil.labelFor("sdfsfdsfdsfd") + " " + e.getMessage());
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

    private CourseRepository getCourseRepository(){
        return new CourseRepository(
                new DataSource<Course>(
                        new CourseDataHandler(
                                new OracleDataConnection()
                        )
                )
        );
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
