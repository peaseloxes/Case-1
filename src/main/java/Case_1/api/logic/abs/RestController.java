package Case_1.api.logic.abs;

import Case_1.api.domain.concrete.Pagination;
import Case_1.api.util.RestUtil;
import Case_1.data.logic.abs.Repository;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

/**
 * Created by alex on 10/5/15.
 */
public abstract class RestController<T extends Repository> {

     /**
     * Fetch the repository.
     *
     * @return a repository
     */
    protected abstract T getRepository();

    /**
     * Fetch the url for the REST location.
     *
     * @return an uri
     */
    protected abstract String getIdUrl();

    /**
     * Fetch the name for the repository items.
     *
     * @return a name
     */
    protected abstract String getName();

    /**
     * General get all things.
     *
     * @return a Response
     */
    @GET
    public Response get() {
        return RestUtil.buildResponse(
                new Pagination<>("/", 0, 0, getRepository().getAll()),
                getName()
        );
    }
}
