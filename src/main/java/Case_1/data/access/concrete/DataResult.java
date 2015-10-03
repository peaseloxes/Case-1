package Case_1.data.access.concrete;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * Encapsulates a result retrieved when using a DataConnection implementation.
 * <p/>
 * For instance a {@linkplain java.sql.ResultSet ResultSet} would be
 * converted to DataResult so that
 * {@linkplain Case_1.data.object.abs.DataHandler DataHandler}
 * implementations have a common result object to work with, regardless of the
 * DataConnection implementation.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class DataResult {

    // Easier to read than List<Map<String,Object>>
    private List<Row> data = new ArrayList<>();

    /**
     * Returns whether or not there is data.
     *
     * @return true if there is data, false otherwise
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Returns the size of the data.
     *
     * @return the amount of rows.
     */
    public int size() {
        return data.size();
    }

    /**
     * Adds a name / value pair to a row.
     *
     * @param row   the id of the row to add to
     * @param name  the key
     * @param value the value
     */
    public void addToRow(final int row, final String name, final Object value) {
        data.get(row).addValues(name, value);
    }


    /**
     * Retrieves an iterator for the data value maps.
     *
     * @return an iterator
     */
    public Iterator<Map<String, Object>> getIterator() {
        // hiding row from user, so using Map<String, Object> directly
        return new DataResultIterator();
    }

    /**
     * Retrieves the row given by row id.
     *
     * @param row the row id
     * @return the corresponding row
     */
    public Map<String, Object> getRow(final int row) {
        return data.get(row).getValues();
    }

    public void addNewRow(final Map<String, Object> map) {
        data.add(new Row(map));
    }

    @AllArgsConstructor
    private class Row {
        @Getter(AccessLevel.PRIVATE)
        private Map<String, Object> values = new LinkedHashMap<>();

        private void addValues(final String key, final Object value) {
            values.put(key, value);
        }

    }

    // Design pattern: Iterator
    private class DataResultIterator implements Iterator<Map<String, Object>> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            if (index < data.size()) {
                return true;
            }
            return false;
        }

        @Override
        public Map<String, Object> next() {
            if (this.hasNext()) {
                return data.get(index++).getValues();
            }
            return null;
        }
    }
}
