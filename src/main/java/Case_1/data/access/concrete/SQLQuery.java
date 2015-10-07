package Case_1.data.access.concrete;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Types;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public class SQLQuery {

    @Getter
    @Setter
    private String sql;

    private List<Param> params = new LinkedList<>();
    private int counter = 0;

    /**
     * Add a parameter to the query, order will be order of insertion.
     *
     * @param object the object to add
     * @param type   the type of the object
     */
    public void addParam(final Object object, final Type type) {
        params.add(new Param(counter, object, type.getType()));
        counter++;
    }

    /**
     * Retrieves an iterator for the parameters.
     *
     * @return an iterator
     */
    public Iterator<Param> getIterator() {
        return new QueryIterator();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class Param {
        private int id;
        private Object object;
        private Integer type;
    }

    /**
     * Provides conversion between java types and sql data types.
     * <p>
     * usage:
     * <p>
     * Type.INT > if a java integer needs storing
     */
    public enum Type {
        // see
        // https://docs.oracle.com/cd/A87860_01/doc/java.817/a83724/basic3.htm
        INT(Types.INTEGER),
        STRING(Types.VARCHAR),
        DOUBLE(Types.DOUBLE),
        BOOLEAN(Types.BIT),
        DATE(Types.DATE);

        @Getter(AccessLevel.PRIVATE)
        private Integer type;

        Type(final Integer type) {
            this.type = type;
        }
    }

    // Design pattern: Iterator
    private class QueryIterator implements Iterator<Param> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            if (index < params.size()) {
                return true;
            }
            return false;
        }

        @Override
        public Param next() {
            if (this.hasNext()) {
                return params.get(index++);
            }
            return null;
        }
    }
}
