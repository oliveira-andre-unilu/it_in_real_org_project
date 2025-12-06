package lu.lamtco.timelink.business;

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
 * Service class for managing Employee entities.
 * Handles creation, retrieval, update, and deletion of employees.
 * Validates user access, self-identity, and data conformity.
 *
 * @version 0.1
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthService authService;

    /**
     * Constructor for EmployeeService.
     * @param employeeRepository Repository for employee persistence
     * @param authService Service for authentication and authorization
     */
    public EmployeeService(EmployeeRepository employeeRepository, AuthService authService) {
        this.employeeRepository = employeeRepository;
        this.authService = authService;
    }

    /**
     * Creates a new employee.
     * Only ADMIN users can create employees.
     * @param jwtToken JWT token for authentication
     * @param dto DTO containing employee data
     * @return Created Employee entity
     * @throws NonConformRequestedDataException If email is invalid or already taken
     * @throws InvalidAuthentication If JWT token is invalid
     * @throws UnauthorizedActionException If user is not an ADMIN
     */
    @Transactional
    public Employee createEmployee(String jwtToken, EmployeeDTO dto) throws NonConformRequestedDataException, InvalidAuthentication, UnauthorizedActionException {
        authService.verifyAdminAccess(jwtToken);

        if(!this.verifyEmployeeDetails(dto)){
            throw new NonConformRequestedDataException("The requested email does already exist or is not conform");
        }
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setSurname(dto.getSurName());
        employee.setEmail(dto.getEmail());
        employee.setPassword(authService.hashPassword(dto.getPassword()));
        employee.setRole(dto.getRole());
        employee.setHourlyRate(dto.getHourlyRate());

        return employeeRepository.save(employee);
    }

    /**
     * Retrieves all employees.
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Retrieves an employee by ID.
     * Admins can access any employee; regular users can only access their own record.
     * @param jwtToken JWT token for authentication
     * @param id ID of the employee
     * @return Employee entity
     * @throws UnexistingEntityException If employee does not exist
     * @throws InvalidAuthentication If JWT token is invalid
     * @throws UnauthorizedActionException If user is not authorized
     */
    public Employee getEmployeeById(String jwtToken, long id) throws UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        boolean isNotAdmin = false;
        try {
            authService.verifyAdminAccess(jwtToken);
        } catch (UnauthorizedActionException e) {
            isNotAdmin = true;
        }

        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            Employee result = employee.get();
            if(isNotAdmin){
                authService.verifySelfIdentityAccess(jwtToken, result.getId(), result.getEmail());
            }
            return result;
        } else {
            throw new UnexistingEntityException("Employee with id " + id + " not found");
        }
    }

    /**
     * Retrieves an employee by email.
     * @param email Email of the employee
     * @return Employee entity
     * @throws NonConformRequestedDataException If email is invalid
     * @throws UnexistingEntityException If employee does not exist
     */
    public Employee getEmployeeByEmail(String email) throws NonConformRequestedDataException, UnexistingEntityException {
        if(!this.verifyEmail(email)){
            throw new NonConformRequestedDataException("The requested email is not conform");
        }
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        if(employee.isPresent()){
            return employee.get();
        } else {
            throw new UnexistingEntityException("The username does not exist!");
        }
    }

    /**
     * Updates an existing employee.
     * Admins can update any employee; users can update their own record.
     * @param jwtToken JWT token for authentication
     * @param id ID of the employee
     * @param dto DTO containing updated employee data
     * @return Updated Employee entity
     * @throws NonConformRequestedDataException If email is invalid or already taken
     * @throws UnexistingEntityException If employee does not exist
     * @throws InvalidAuthentication If JWT token is invalid
     * @throws UnauthorizedActionException If user is not authorized
     */
    @Transactional
    public Employee updateEmployee(String jwtToken, long id, EmployeeDTO dto) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        Employee employee = getEmployeeById(jwtToken, id);

        if(dto.getEmail() != null){
            if(!verifyEmail(dto.getEmail())){
                throw new NonConformRequestedDataException("The requested email does already exist or is not conform");
            }
            employee.setEmail(dto.getEmail());
        }
        if(dto.getName() != null) employee.setName(dto.getName());
        if(dto.getSurName() != null) employee.setSurname(dto.getSurName());
        if(dto.getPassword() != null) employee.setPassword(authService.hashPassword(dto.getPassword()));
        if(dto.getRole() != null) employee.setRole(dto.getRole());
        if(dto.getHourlyRate() != null) employee.setHourlyRate(dto.getHourlyRate());

        return employeeRepository.save(employee);
    }

    /**
     * Deletes an employee.
     * Admins can delete any employee; users can delete their own record.
     * @param jwtToken JWT token for authentication
     * @param id ID of the employee
     * @return True if deletion was successful
     * @throws UnexistingEntityException If employee does not exist
     * @throws InvalidAuthentication If JWT token is invalid
     * @throws UnauthorizedActionException If user is not authorized
     */
    @Transactional
    public boolean deleteEmployee(String jwtToken, long id) throws UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        Employee employee = getEmployeeById(jwtToken, id);
        employeeRepository.delete(employee);
        return true;
    }

    // Helper methods

    /**
     * Verifies that the employee details are valid.
     * @param dto EmployeeDTO to validate
     * @return True if email is valid and not taken
     */
    private boolean verifyEmployeeDetails(EmployeeDTO dto) {
        return this.verifyEmail(dto.getEmail());
    }

    /**
     * Verifies if an email is valid and not already used.
     * @param email Email to check
     * @return True if email is valid and free
     */
    private boolean verifyEmail(String email) {
        Optional<Employee> isExisting = employeeRepository.findByEmail(email);
        if(isExisting.isEmpty() || !email.contains("@")) {
            return false;
        }
        return true;
    }
}