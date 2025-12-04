package lu.lamtco.timelink.exeptions;

public class UnauthorizedActionException extends Exception {
    public UnauthorizedActionException() {super();}
    public UnauthorizedActionException(String message) {
        super(message);
    }
}
