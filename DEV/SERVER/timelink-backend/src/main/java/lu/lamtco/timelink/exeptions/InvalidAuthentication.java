package lu.lamtco.timelink.exeptions;

/**
 * Exception thrown when authentication of a user fails.
 * This can occur when provided credentials are invalid or a JWT token is incorrect or expired.
 */
public class InvalidAuthentication extends Exception {

    /**
     * Constructs a new InvalidAuthentication exception with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidAuthentication(String message) {
        super(message);
    }
}