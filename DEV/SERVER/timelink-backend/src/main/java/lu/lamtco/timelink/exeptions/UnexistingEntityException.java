package lu.lamtco.timelink.exeptions;

public class UnexistingEntityException extends Exception {
    public UnexistingEntityException(String message) {
        super(message);
    }
}
