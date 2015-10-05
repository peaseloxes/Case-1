package Case_1.api.util;

import Case_1.api.domain.concrete.Pagination;
import Case_1.util.i18n.LangUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.core.Response;

/**
 * Created by alex on 10/5/15.
 */
public class RestUtil {

    public static <T> Response buildResponse(final Pagination<T> pageModel, final String name) {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        if (pageModel.getElements().isEmpty()) {
            return Response.ok(gson.toJson(name + " " + LangUtil.labelFor("error.rest.itemsNotFound"))).build();
        }
        return Response.ok(gson.toJson(pageModel)).build();
    }

}
