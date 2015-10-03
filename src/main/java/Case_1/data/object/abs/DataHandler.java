package Case_1.data.object.abs;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public interface DataHandler<C, T> {

    /**
     * Set the connection for this handler.
     *
     * @param connection the connection to set
     */
    void setConnection(final C connection);

    /**
     * Get an object <b>T</b> by id.
     *
     * @param id the id to search for
     * @return T or null
     */
    T getById(final int id);

}
