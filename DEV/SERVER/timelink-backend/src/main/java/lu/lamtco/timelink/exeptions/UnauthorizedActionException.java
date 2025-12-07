package lu.lamtco.timelink.exeptions;

/**
 * Exception thrown when a user tries to perform an action for which they do not have sufficient permissions.
 * This typically occurs when role-based access control or ownership checks fail.
 *
 * @version 0.1
 */
public class UnauthorizedActionException extends Exception {

    /**
     * Constructs a new UnauthorizedActionException with no detail message.
     */
    public UnauthorizedActionException() {
        super();
    }

    /**
     * Constructs a new UnauthorizedActionException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the unauthorized action
     */
    public UnauthorizedActionException(String message) {
        super(message);
    }
}