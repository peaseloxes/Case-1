package Case_1.data.access.concrete;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

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

    // LinkedHashMap to keep order of insertion
    @Getter
    private Map<Object, Integer> params = new LinkedHashMap<>();

    /**
     * Add a parameter to the query, order will be order of insertion.
     *
     * @param object the object to add
     * @param type the type of the object
     */
    public void addParam(final Object object, final Type type) {
        params.put(object, type.getType());
    }

    public enum Type {
        INT(Types.INTEGER),
        STRING(Types.VARCHAR),
        DOUBLE(Types.DOUBLE),
        DECIMAL(Types.DECIMAL);

        @Getter(AccessLevel.PRIVATE)
        private Integer type;

        Type(Integer type) {
            this.type = type;
        }
    }
}
