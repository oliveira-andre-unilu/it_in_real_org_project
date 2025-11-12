package lu.lamtco.timelink.security;

import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.persister.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private EmployeeRepository employeeRepository;
    private JWTUtils jwtUtils;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        jwtUtils = mock(JWTUtils.class);
        authService = new AuthService(employeeRepository, jwtUtils);
    }

    @Test
    void signUp_ShouldSaveEmployee_WhenEmailNotRegistered() {
        Employee employee = new Employee();
        employee.setEmail("test@example.com");
        employee.setPassword("plainPassword");

        when(employeeRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        authService.signUp(employee);

        assertTrue(employee.getPassword().startsWith("$2a$"), "Password should be hashed");
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void signUp_ShouldThrowException_WhenEmailAlreadyExists() {
        Employee employee = new Employee();
        employee.setEmail("existing@example.com");
        employee.setPassword("password");

        when(employeeRepository.findByEmail("existing@example.com"))
                .thenReturn(Optional.of(new Employee()));

        assertThrows(IllegalArgumentException.class, () -> authService.signUp(employee));

        verify(employeeRepository, never()).save(any());
    }

    @Test
    void signIn_ShouldReturnToken_WhenCredentialsAreValid() {
        String email = "user@example.com";
        String rawPassword = "secret123";
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        String token = "jwtToken";

        Employee storedEmployee = new Employee();
        storedEmployee.setEmail(email);
        storedEmployee.setPassword(hashedPassword);

        when(employeeRepository.findByEmail(email)).thenReturn(Optional.of(storedEmployee));
        when(jwtUtils.generateToken(email)).thenReturn(token);

        String result = authService.signIn(email, rawPassword);

        assertEquals(token, result);
        verify(jwtUtils, times(1)).generateToken(email);
    }

    @Test
    void signIn_ShouldThrowException_WhenEmailNotFound() {
        when(employeeRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> authService.signIn("missing@example.com", "password"));
    }

    @Test
    void signIn_ShouldThrowException_WhenPasswordIsInvalid() {
        String email = "user@example.com";
        String wrongPassword = "wrong";
        String hashedPassword = BCrypt.hashpw("correct", BCrypt.gensalt());

        Employee storedEmployee = new Employee();
        storedEmployee.setEmail(email);
        storedEmployee.setPassword(hashedPassword);

        when(employeeRepository.findByEmail(email)).thenReturn(Optional.of(storedEmployee));

        assertThrows(IllegalArgumentException.class,
                () -> authService.signIn(email, wrongPassword));

        verify(jwtUtils, never()).generateToken(anyString());
    }
}
