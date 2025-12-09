package lu.lamtco.timelink.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnauthorizedActionException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;

public class ExceptionsTest {

    @Test
    void invalidAuthentication_ShouldCreateWithMessage() {
        String message = "Invalid token";
        InvalidAuthentication exception = new InvalidAuthentication(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void nonConformRequestedDataException_ShouldCreateWithMessage() {
        String message = "Data not conform";
        NonConformRequestedDataException exception = new NonConformRequestedDataException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void unauthorizedActionException_ShouldCreateWithDefaultConstructor() {
        UnauthorizedActionException exception = new UnauthorizedActionException();

        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void unauthorizedActionException_ShouldCreateWithMessage() {
        String message = "Not authorized";
        UnauthorizedActionException exception = new UnauthorizedActionException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void unexistingEntityException_ShouldCreateWithMessage() {
        String message = "Entity not found";
        UnexistingEntityException exception = new UnexistingEntityException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void allExceptions_ShouldBeSubclassesOfException() {
        assertTrue(new InvalidAuthentication("test") instanceof Exception);
        assertTrue(new NonConformRequestedDataException("test") instanceof Exception);
        assertTrue(new UnauthorizedActionException("test") instanceof Exception);
        assertTrue(new UnexistingEntityException("test") instanceof Exception);
    }
}
