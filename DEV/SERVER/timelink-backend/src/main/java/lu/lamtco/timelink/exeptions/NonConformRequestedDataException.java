package lu.lamtco.timelink.exeptions;

/**
 * Exception thrown when the provided data does not meet the required format or business rules.
 * This can occur during creation or updating of entities when validation fails.
 *
 * @version 0.1
 */
public class NonConformRequestedDataException extends Exception {

    /**
     * Constructs a new NonConformRequestedDataException with the specified detail message.
     *
     * @param message the detail message explaining why the data is non-conformant
     */
    public NonConformRequestedDataException(String message) {
        super(message);
    }
}