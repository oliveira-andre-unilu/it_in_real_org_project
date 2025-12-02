package lu.lamtco.timelink.exeptions;

public class NonConformRequestedDataException extends Exception {
    public NonConformRequestedDataException(String message) {
        super(message);
    }
}
