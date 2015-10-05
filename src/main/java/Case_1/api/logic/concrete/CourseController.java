package Case_1.api.logic.concrete;

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
import Case_1.util.i18n.LangUtil;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

/**
 * Created by alex on 10/5/15.
 */
@Path(CourseController.ROOT)
@Produces("application/json")
public class CourseController extends RestController<CourseRepository> {
    private CourseRepository repository;

    public CourseController(final CourseRepository repository){
        this.repository = repository;
    }

    // for use in @Path
    protected static final String ROOT = "/courses";

    // for the general controller
    private static final String BY_ID = "/courses/{id}";
    private static final String CREATE = "/create";
    private static final String NAME = "Courses";

    @POST
    @Path(CREATE)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importFile(@FormDataParam("file") final InputStream stream) {
        InputStreamDataConnection connection = new InputStreamDataConnection();
        try {
            connection.setConnection(stream);
            connection.open();
            DataResult result = connection.execute("");
            CourseParser parser = new CourseParser();
            List<Course> list = parser.parse(result.valuesToList(0));
            getRepository().addAll(list);
            connection.close();
        } catch (DataConnectionException e) {
            // TODO bad request, no cookie
            e.printStackTrace();
        } catch (CourseParser.CourseParsingException e) {
            e.printStackTrace();
        }
        return RestUtil.buildMessageResponse(LangUtil.labelFor("success.courses.created"));
    }

    @Override
    protected CourseRepository getRepository() {
        if(repository==null) {
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
