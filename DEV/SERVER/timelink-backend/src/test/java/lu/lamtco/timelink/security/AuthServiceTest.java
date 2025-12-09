package lu.lamtco.timelink.security;

import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.domain.Role;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.persister.EmployeeRepository;
import lu.lamtco.timelink.services.EmployeeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;
    private JWTUtils jwtUtils;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        jwtUtils = mock(JWTUtils.class);
        employeeService = mock(EmployeeService.class);
        authService = new AuthService(employeeRepository, jwtUtils, employeeService);
    }

    // modify name of the test to be consistent with the AuthService class
    // test with valid credentials
    @Test
    void signInAndGetToken_ShouldReturnToken_WhenCredentialsAreValid()
            throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication {
        String email = "user@example.com";
        String rawPassword = "secret123";
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        String token = "jwtToken";
        // change logic here
        Long employeeId = 1L;
        Role role = Role.STAFF;

        Employee storedEmployee = new Employee();
        storedEmployee.setEmail(email);
        storedEmployee.setPassword(hashedPassword);
        // consstency
        storedEmployee.setId(employeeId);
        storedEmployee.setRole(role);

        // also change logic here no more repository but service used
        when(employeeService.getEmployeeByEmail(email)).thenReturn(storedEmployee);
        when(jwtUtils.generateToken(email, employeeId, role)).thenReturn(token);

        String result = authService.signInAndGetToken(email, rawPassword);

        assertEquals(token, result);
        verify(jwtUtils, times(1)).generateToken(email, employeeId, role);
    }

    // when email doesn't exist
    // also modify name of the test to be consistent with teh AuthService class
    @Test
    void signInAndGetToken_ShouldThrowException_WhenEmailNotFound()
            throws NonConformRequestedDataException, UnexistingEntityException {
        String email = "missing@example.com";
        // also change logic here no more repository but service used
        when(employeeService.getEmployeeByEmail(email)).thenThrow(new UnexistingEntityException("Employee not found"));

        assertThrows(UnexistingEntityException.class,
                () -> authService.signInAndGetToken(email, "password"));
    }

    // password is invalid
    // also change test name
    @Test
    void signInAndGetToken_ShouldThrowException_WhenPasswordIsInvalid()
            throws NonConformRequestedDataException, UnexistingEntityException {
        String email = "user@example.com";
        String wrongPassword = "wrong";
        String hashedPassword = BCrypt.hashpw("correct", BCrypt.gensalt());
        // change logic here
        Long employeeId = 1L;
        Role role = Role.STAFF;

        Employee storedEmployee = new Employee();
        storedEmployee.setEmail(email);
        storedEmployee.setPassword(hashedPassword);
        // consistency
        storedEmployee.setId(employeeId);
        storedEmployee.setRole(role);

        // also change logic here no more repository but service used
        when(employeeService.getEmployeeByEmail(email)).thenReturn(storedEmployee);

        assertThrows(InvalidAuthentication.class,
                () -> authService.signInAndGetToken(email, wrongPassword));

        verify(jwtUtils, never()).generateToken(anyString(), anyLong(), any());
    }

    // add new test for new functions in AuthService

    // for getAuthData
    @Test
    void getAuthData_ShouldReturnUserAuthData_WhenTokenIsValid() throws InvalidAuthentication {
        String token = "validToken";
        UserAuthData expectedAuthData = new UserAuthData("user@example.com", 1L, Role.STAFF);

        when(jwtUtils.decodeToken(token)).thenReturn(expectedAuthData);

        UserAuthData result = authService.getAuthData(token);

        assertEquals(expectedAuthData, result);
        verify(jwtUtils, times(1)).decodeToken(token);
    }

    // for getAuthData but token is invalid
    void getAuthData_ShouldThrowInvalidAuthentication_WhenTokenIsInvalid() throws InvalidAuthentication {
        String token = "invalidToken";

        when(jwtUtils.decodeToken(token)).thenThrow(new InvalidAuthentication("Invalid token"));

        assertThrows(InvalidAuthentication.class, () -> authService.getAuthData(token));
    }

    // for hashPassword
    @Test
    void hashPassword_ShouldReturnHashedPassword() {
        String plainPassword = "password";

        String result = authService.hashPassword(plainPassword);

        assertTrue(result.startsWith("$2a$"), "Password should be hashed with BCrypt");
        assertTrue(BCrypt.checkpw(plainPassword, result), "Hashed password should be equals rto original");
    }
}
