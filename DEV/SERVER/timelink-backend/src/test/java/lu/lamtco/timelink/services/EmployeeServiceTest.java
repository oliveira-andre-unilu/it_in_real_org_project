package lu.lamtco.timelink.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.domain.Role;
import lu.lamtco.timelink.dto.EmployeeDTO;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.persister.EmployeeRepository;
import lu.lamtco.timelink.security.AuthService;

public class EmployeeServiceTest {
    private EmployeeRepository employeeRepository;
    private AuthService authService;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        authService = mock(AuthService.class);
        employeeService = new EmployeeService(employeeRepository, authService);
    }

    @Test
    void createEmployee_ShouldSaveEmployee_WhenValidData() throws NonConformRequestedDataException {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmail("employee.empl@example.com");
        employeeDTO.setName("Employee");
        employeeDTO.setSurName("Empl");
        employeeDTO.setPassword("password");
        employeeDTO.setRole(Role.STAFF);
        employeeDTO.setHourlyRate(25.0);

        when(employeeRepository.findByEmail("employee.empl@example.com")).thenReturn(Optional.empty());
        when(authService.hashPassword("password")).thenReturn("hashedPassword");
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Employee result = employeeService.createEmployee(employeeDTO);

        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getSurname());
        assertEquals("employee.empl@example.com", result.getEmail());
        assertEquals("hashedPassword", result.getPassword());
        assertEquals(Role.STAFF, result.getRole());
        assertEquals(25.0, result.getHourlyRate());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    // create employee invalid email should not work
    @Test
    void createEmployee_ShouldThrowException_WhenEmailInvalid() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmail("invalid-email"); // no @

        assertThrows(NonConformRequestedDataException.class, () -> employeeService.createEmployee(employeeDTO));
    }

    // create employee, email already exist, throw exception
    @Test
    void createEmployee_ShouldThrowException_WhenEmailAlreadyExists() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmail("existing@email.com");

        when(employeeRepository.findByEmail("existing@email.com")).thenReturn(Optional.of(new Employee()));

        assertThrows(NonConformRequestedDataException.class, () -> employeeService.createEmployee(employeeDTO));
    }

    // get all employees
    @Test
    void getAllEmployees_ShouldReturnList() {
        List<Employee> expectedEmployees = Arrays.asList(new Employee(), new Employee(), new Employee());

        when(employeeRepository.findAll()).thenReturn(expectedEmployees);

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(3, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    // get employee by id, should work
    @Test
    void getEmployeeById_ShouldReturnEmployee_WhenExists() throws UnexistingEntityException {
        long employeeId = 1L;
        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(expectedEmployee));

        Employee result = employeeService.getEmployeeById(employeeId);

        assertNotNull(result);
        assertEquals(employeeId, result.getId());
    }

    // get employee by id but it doesn't exist
    @Test
    void getEmployeeById_ShouldThrowException_WhenNotExists() {
        long employeeId = 999L;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(UnexistingEntityException.class, () -> employeeService.getEmployeeById(employeeId));
    }

    // get employee by email, tets with valid email
    @Test
    void getEmployeeByEmail_ShouldReturnEmployee_WhenValidEmail()
            throws NonConformRequestedDataException, UnexistingEntityException {
        String email = "valid@email.com";
        Employee expectedEmployee = new Employee();
        expectedEmployee.setEmail(email);

        when(employeeRepository.findByEmail(email)).thenReturn(Optional.of(expectedEmployee));

        Employee result = employeeService.getEmployeeByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    // test with no valid email this time
    @Test
    void getEmployeeByEmail_ShouldThrowException_WhenInvalidEmail() {
        String email = "invalid-email";

        assertThrows(NonConformRequestedDataException.class, () -> employeeService.getEmployeeByEmail(email));
    }

    // update employee should work because valid data
    @Test
    void updateEmployee_ShouldUpdateEmployee_WhenValidData()
            throws NonConformRequestedDataException, UnexistingEntityException {
        long employeeId = 1L;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmail("new@email.com");
        employeeDTO.setName("Updated");
        employeeDTO.setPassword("newPassword");
        employeeDTO.setRole(Role.ADMIN);
        employeeDTO.setHourlyRate(30.0);

        Employee existingEmployee = new Employee();
        existingEmployee.setId(employeeId);
        existingEmployee.setEmail("old@email.com");
        existingEmployee.setName("Old");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.findByEmail("new@email.com")).thenReturn(Optional.empty());
        when(authService.hashPassword("newPassword")).thenReturn("newHashedPassword");
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Employee result = employeeService.updateEmployee(employeeId, employeeDTO);

        assertEquals("Updated", result.getName());
        assertEquals("new@email.com", result.getEmail());
        assertEquals("newHashedPassword", result.getPassword());
        assertEquals(Role.ADMIN, result.getRole());
        assertEquals(30.0, result.getHourlyRate());
    }

    // delete employee, return true
    @Test
    void deleteEmployee_ShouldReturnTrue_WhenEmployeeExists() throws UnexistingEntityException {
        long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        boolean result = employeeService.deleteEmployee(employeeId);

        assertTrue(result);
        verify(employeeRepository, times(1)).delete(employee);
    }
}
