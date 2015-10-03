package Case_1.data.access.abs;

import Case_1.data.access.concrete.DataResult;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class DataResultTest {

    @Test
    public void testIsEmpty() throws Exception {
        assertThat(new DataResult().isEmpty(), is(true));
    }

    @Test
    public void testSize() throws Exception {
        DataResult result = new DataResult();
        assertThat(result.size(), is(0));

        result.addNewRow(null);
        assertThat(result.size(), is(1));
    }

    @Test
    public void testAddToRow() throws Exception {
        DataResult result = new DataResult();
        result.addNewRow(new HashMap<>());
        result.addToRow(0, "key", "value");

        assertThat(result.getRow(0).get("key"), is("value"));
    }

    @Test
    public void testGetIterator() throws Exception {
        DataResult result = new DataResult();
        result.addNewRow(new HashMap<>());
        result.addToRow(0, "key", "value");
        result.addToRow(0, "key1", "value1");
        result.addNewRow(new HashMap<>());
        result.addToRow(1, "key2", "value2");

        Iterator<Map<String, Object>> it = result.getIterator();
        Map<String, Object> firstRow = it.next();
        assertThat(firstRow.get("key"), is("value"));
        assertThat(firstRow.get("key1"), is("value1"));

        Map<String, Object> secondRow = it.next();
        assertThat(secondRow.get("key2"), is("value2"));

        assertThat(it.hasNext(), is(false));
        assertThat(it.next(), nullValue());
    }

    @Test
    public void testGetRow() throws Exception {
        DataResult result = new DataResult();
        result.addNewRow(new HashMap<>());
        result.addToRow(0, "key", "value");

        assertThat(result.getRow(0).get("key"), is("value"));
    }

    @Test
    public void testAddNewRow() throws Exception {
        DataResult result = new DataResult();
        result.addNewRow(new HashMap<>());
        result.addToRow(0, "key", "value");

        assertThat(result.getRow(0).get("key"), is("value"));
    }
}