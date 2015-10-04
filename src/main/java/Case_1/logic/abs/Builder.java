package Case_1.logic.abs;

/**
 * Generated.
 *
 * @author Alex
 * @version %I%, %G%
 */
public interface Builder<T, O> {

    /**
     * Set the id of the object.
     *
     * @param id the id
     * @return this builder instance
     */
    T id(int id);

    /**
     * Validate the object in memory.
     * <p/>
     * Called explicitly during {@linkplain #create()}
     *
     * @return
     */
    boolean validate();

    /**
     * Retrieve the created object.
     * <p/>
     * Will throw an {@linkplain
     * IncorrectVariablesException
     * InsufficientVariablesException} if the required variables
     * have not been set.
     *
     * @return the created object
     * @see #validate()
     */
    O create();
}
