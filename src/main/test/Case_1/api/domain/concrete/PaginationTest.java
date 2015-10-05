package Case_1.api.domain.concrete;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by alex on 10/5/15.
 */
public class PaginationTest {

    @Test
    public void testPagination() {
        Pagination pagination = new Pagination("test", 5, 10, new ArrayList<String>());
        assertThat(pagination.getElements(), instanceOf(ArrayList.class));
        assertThat(pagination.getNext(), is("test?start=15&limit=10"));
        assertThat(pagination.getPrev(), is("test?start=0&limit=10"));

        pagination = new Pagination();
        assertThat(pagination.getNext(), is(""));
        assertThat(pagination.getPrev(), is(""));

        pagination = new Pagination("", 0, 0, new ArrayList<String>());
        assertThat(pagination.getNext(), is(""));
        assertThat(pagination.getPrev(), is(""));

    }
}