package Case_1.api.logic.concrete;

import Case_1.api.domain.concrete.Pagination;
import Case_1.api.logic.abs.RestController;
import Case_1.api.util.RestUtil;
import Case_1.data.access.abs.DataConnectionException;
import Case_1.data.access.concrete.DataResult;
import Case_1.data.access.concrete.InputStreamDataConnection;
import Case_1.data.access.concrete.OracleDataConnection;
import Case_1.data.logic.abs.DataSource;
import Case_1.data.logic.concrete.CourseRepository;
import Case_1.data.object.concrete.CourseDataHandler;
import Case_1.domain.concrete.Course;
import Case_1.logic.concrete.CourseParser;
import Case_1.util.DateUtil;
import Case_1.util.i18n.LangUtil;
import lombok.NoArgsConstructor;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

/**
 * Created by alex on 10/5/15.
 */
@Path(CourseController.ROOT)
@Produces("application/json")
@NoArgsConstructor
public class CourseController extends RestController<CourseRepository> {

    // for injection, if desired
    private CourseRepository repository;
    public CourseController(final CourseRepository repository) {
        this.repository = repository;
    }

    // for use in @Path
    public static final String ROOT = "/courses";

    // for the general controller
    private static final String BY_ID = "/";
    private static final String BY_WEEK = "/{year}/{week}";
    private static final String CREATE = "/create";
    private static final String NAME = "Courses";

    @POST
    @Path(CREATE)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importFile(@FormDataParam("file") final InputStream stream) {

        try {
            if (stream == null) {
                // no stream, can't do anything
                throw new IllegalArgumentException(LangUtil.labelFor("error.courses.notCreated")
                        + " " + LangUtil.labelFor("error.file.notFound"));
            }

            // get a dataconnection
            InputStreamDataConnection connection = new InputStreamDataConnection();

            // set the stream
            connection.setConnection(stream);

            // open the stream
            connection.open();

            // execute without parameters
            DataResult result = connection.execute("");

            // close stream
            connection.close();

            // get a parser
            CourseParser parser = new CourseParser();

            // parse the result from the dataconnection
            List<Course> list = parser.parse(result.valuesToList(0));

            // add them to the repository
            getRepository().addAll(list);

        } catch (DataConnectionException
                | CourseParser.CourseParsingException
                | IllegalArgumentException e) {

            // we wrote proper messages, so add error message for user info
            return RestUtil.buildMessageResponse(LangUtil.labelFor("error.courses.notCreated") + " " + e.getMessage());
        } catch (Exception e) {

            // who knows what's in here, don't return its message
            return RestUtil.buildMessageResponse(LangUtil.labelFor("error.courses.notCreated"));
        }
        return RestUtil.buildMessageResponse(LangUtil.labelFor("success.courses.created"));
    }

    @GET
    @Path(BY_WEEK)
    public Response getByWeek(
            @PathParam("year") final int year,
            @PathParam("week") final int week

    ) {
        try {
            Pagination<Course> page = new Pagination<>(getIdUrl(), 0, 0, getRepository().getStudentCoursesByYearWeek(year, week));
            page.setPrev(DateUtil.getPreviousYearWeek(year, week));
            page.setNext(DateUtil.getNextYearWeek(year, week));
            return RestUtil.buildResponse(page, "Students by week");
        } catch (DataConnectionException e) {

            // we wrote proper messages, so add error message for user info
            return RestUtil.buildMessageResponse(LangUtil.labelFor("error.courses.notFound") + " " + e.getMessage());
        } catch (Exception e) {

            // who knows what's in here, don't return its message
            return RestUtil.buildMessageResponse(LangUtil.labelFor("error.courses.notFound"));
        }
    }

    @Override
    protected CourseRepository getRepository() {
        if (repository == null) {
            repository = new CourseRepository(
                    new DataSource<Course>(
                            new CourseDataHandler(
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
