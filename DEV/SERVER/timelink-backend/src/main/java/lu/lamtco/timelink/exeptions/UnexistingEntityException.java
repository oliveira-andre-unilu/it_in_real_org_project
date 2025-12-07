package lu.lamtco.timelink.exeptions;

/**
 * Exception thrown when an operation references an entity that does not exist in the system.
 * This can occur, for example, when trying to retrieve, update, or delete a non-existent record.
 *
 * @version 0.1
 */
public class UnexistingEntityException extends Exception {

    /**
     * Constructs a new UnexistingEntityException with the specified detail message.
     *
     * @param message the detail message explaining which entity does not exist
     */
    public UnexistingEntityException(String message) {
        super(message);
    }
}