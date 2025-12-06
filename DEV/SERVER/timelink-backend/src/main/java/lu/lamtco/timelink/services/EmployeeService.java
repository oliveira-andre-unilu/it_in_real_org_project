package lu.lamtco.timelink.services;

import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.dto.EmployeeDTO;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnauthorizedActionException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.persister.EmployeeRepository;
import lu.lamtco.timelink.security.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service responsible for managing employee entities.
 * Provides create, update, delete and lookup operations,
 * including validation and authorization checks.
 *
 * @version 0.1
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthService authService;

    public EmployeeService(EmployeeRepository employeeRepository, AuthService authService) {
        this.employeeRepository = employeeRepository;
        this.authService = authService;
    }

    /**
     * Creates a new employee after validating its data and verifying admin rights.
     *
     * @param jwtToken authentication token
     * @param dto employee data
     * @return the saved employee entity
     * @throws NonConformRequestedDataException if the provided data is invalid or email already exists
     * @throws InvalidAuthentication if the authentication token is invalid
     * @throws UnauthorizedActionException if the user is not an admin
     */
    @Transactional
    public Employee createEmployee(String jwtToken, EmployeeDTO dto) throws NonConformRequestedDataException, InvalidAuthentication, UnauthorizedActionException {
        authService.verifyAdminAccess(jwtToken);

        if (!this.verifyEmployeeDetails(dto)) {
            throw new NonConformRequestedDataException("The requested email does already exist or is not conform");
        }

        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setSurname(dto.getSurName());
        employee.setEmail(dto.getEmail());
        String hashedPassword = authService.hashPassword(dto.getPassword());
        employee.setPassword(hashedPassword);
        employee.setRole(dto.getRole());
        employee.setHourlyRate(dto.getHourlyRate());

        return employeeRepository.save(employee);
    }

    /**
     * Returns all employees.
     *
     * @return list of employees
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Retrieves an employee by its ID.
     * Non-admins must match the target employee's identity.
     *
     * @param jwtToken authentication token
     * @param id employee ID
     * @return the employee
     * @throws UnexistingEntityException if the employee does not exist
     * @throws InvalidAuthentication if the authentication is invalid
     * @throws UnauthorizedActionException if access is not allowed
     */
    public Employee getEmployeeById(String jwtToken, long id) throws UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {

        boolean isNotAdmin = false;
        try {
            authService.verifyAdminAccess(jwtToken);
        } catch (UnauthorizedActionException e) {
            isNotAdmin = true;
        }

        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee result = employee.get();
            if (isNotAdmin) {
                authService.verifySelfIdentityAccess(jwtToken, result.getId(), result.getEmail());
            }
            return result;
        } else {
            throw new UnexistingEntityException("Employee with id " + id + " not found");
        }
    }

    /**
     * Finds an employee by its email after validating the format.
     *
     * @param email the email to search for
     * @return the matching employee
     * @throws NonConformRequestedDataException if the email is not conform
     * @throws UnexistingEntityException if no employee is found
     */
    public Employee getEmployeeByEmail(String email) throws NonConformRequestedDataException, UnexistingEntityException {
        Optional<Employee> employee;
        if (!this.verifyEmail(email)) {
            throw new NonConformRequestedDataException("The requested email is not conform");
        }
        employee = employeeRepository.findByEmail(email);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new UnexistingEntityException("The username does not exist!!!");
        }
    }

    /**
     * Updates an employee entity with the provided fields.
     *
     * @param jwtToken authentication token
     * @param id employee ID
     * @param dto updated employee data
     * @return the updated employee
     * @throws NonConformRequestedDataException if provided data is invalid
     * @throws UnexistingEntityException if the employee does not exist
     * @throws InvalidAuthentication if the authentication token is invalid
     * @throws UnauthorizedActionException if the user cannot access this resource
     */
    @Transactional
    public Employee updateEmployee(String jwtToken, long id, EmployeeDTO dto) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        Employee employee = getEmployeeById(jwtToken, id);

        if (dto.getEmail() != null) {
            if (!verifyEmail(dto.getEmail())) {
                throw new NonConformRequestedDataException("The requested email does already exist or is not conform");
            }
            employee.setEmail(dto.getEmail());
        }
        if (dto.getName() != null) {
            employee.setName(dto.getName());
        }
        if (dto.getSurName() != null) {
            employee.setSurname(dto.getSurName());
        }
        if (dto.getPassword() != null) {
            String hashedPassword = authService.hashPassword(dto.getPassword());
            employee.setPassword(hashedPassword);
        }
        if (dto.getRole() != null) {
            employee.setRole(dto.getRole());
        }
        if (dto.getHourlyRate() != null) {
            employee.setHourlyRate(dto.getHourlyRate());
        }

        return employeeRepository.save(employee);
    }

    /**
     * Deletes an employee by its ID.
     *
     * @param jwtToken authentication token
     * @param id employee ID
     * @return true if deletion succeeded
     * @throws UnexistingEntityException if the employee does not exist
     * @throws InvalidAuthentication if authentication fails
     * @throws UnauthorizedActionException if access is forbidden
     */
    @Transactional
    public boolean deleteEmployee(String jwtToken, long id) throws UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        Employee employee = getEmployeeById(jwtToken, id);
        employeeRepository.delete(employee);
        return true;
    }

    // Helper functions

    /**
     * Validates whether employee details are acceptable.
     *
     * @param dto employee data
     * @return true if valid
     */
    private boolean verifyEmployeeDetails(EmployeeDTO dto) {
        return this.verifyEmail(dto.getEmail());
    }

    /**
     * Checks if an email exists and if it contains the expected format.
     *
     * @param email email to verify
     * @return true if valid
     */
    private boolean verifyEmail(String email) {
        Optional<Employee> isExisting = employeeRepository.findByEmail(email);
        if (isExisting.isEmpty()) {
            return false;
        }
        if (!email.contains("@")) {
            return false;
        }
        return true;
    }
}
