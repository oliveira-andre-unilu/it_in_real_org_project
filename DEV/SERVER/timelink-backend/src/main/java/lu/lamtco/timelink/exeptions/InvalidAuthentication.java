package lu.lamtco.timelink.exeptions;

public class InvalidAuthentication extends Exception {
    public InvalidAuthentication(String message) {
        super(message);
    }
}
