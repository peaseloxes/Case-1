package Case_1.api.domain.concrete;

import Case_1.util.pref.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alex on 10/5/15.
 */
@Getter
@Setter
public class Pagination<T> {
    private String prev = "";
    private String next = "";
    private List<T> elements = new LinkedList<>();

    public Pagination() {

    }

    public Pagination(final String url, final int start, final int limit, List<T> items) {
        this.elements = items;
        if(elements == null) {
            elements = new LinkedList<>();
        }


        if (start > limit) {
            setPrev(url + "?" + Property.REST_START + "=" + (start
                    - limit) + "&" + Property.REST_LIMIT + "=" + limit);
        } else {
            setPrev(url + "?start=" + (0) +
                    "&limit=" + limit);
        }
        setNext(url + "?start=" + (start +
                limit) + "&limit=" + limit);

        // override in case of 0 values
        if (start == 0) {
            setPrev(url);
        }

        if (limit == 0) {
            setNext(url);
        }
    }
}
