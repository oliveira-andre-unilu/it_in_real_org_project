package lu.lamtco.timelink.services;

import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.dto.EmployeeDTO;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.persister.EmployeeRepository;
import lu.lamtco.timelink.security.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private AuthService authService;

    public EmployeeService(EmployeeRepository employeeRepository, AuthService authService) {
        this.employeeRepository = employeeRepository;
        this.authService = authService;
    }

    //Methods used in the rws

    @Transactional
    public Employee createEmployee(EmployeeDTO dto) throws NonConformRequestedDataException {
        if(!this.verifyEmployeeDetails(dto)){
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

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(long id) throws UnexistingEntityException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            return employee.get();
        }else{
            throw new UnexistingEntityException("Employee with id " + id + " not found");
        }
    }

    public Employee getEmployeeByEmail(String email) throws NonConformRequestedDataException, UnexistingEntityException {
        Optional<Employee> employee;
        if(!this.verifyEmail(email)){
            throw new NonConformRequestedDataException("The requested email is not conform");
        }
        employee = employeeRepository.findByEmail(email);
        if(employee.isPresent()){
            return employee.get();
        }else{
            throw new UnexistingEntityException("The username does not exist!!!");
        }
    }

    @Transactional
    public Employee updateEmployee(long id, EmployeeDTO dto) throws NonConformRequestedDataException , UnexistingEntityException {
        Employee employee = getEmployeeById(id);

        if(dto.getEmail() != null){
            if(!verifyEmail(dto.getEmail())){
                throw new NonConformRequestedDataException("The requested email does already exist or is not conform");
            }
            employee.setEmail(dto.getEmail());
        }
        if(dto.getName() != null){
            employee.setName(dto.getName());
        }
        if(dto.getSurName() != null){
            employee.setSurname(dto.getSurName());
        }
        if(dto.getPassword() != null){
            String hashedPassword = authService.hashPassword(dto.getPassword());
            employee.setPassword(hashedPassword);
        }
        if(dto.getRole() != null){
            employee.setRole(dto.getRole());
        }
        if(dto.getHourlyRate() != null){
            employee.setHourlyRate(dto.getHourlyRate());
        }
        return employeeRepository.save(employee);
    }

    @Transactional
    public boolean deleteEmployee(long id) throws UnexistingEntityException {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
        return true;
    }


    // Helper function
    private boolean verifyEmployeeDetails(EmployeeDTO dto) {
        return this.verifyEmail(dto.getEmail());
    }

    private boolean verifyEmail(String email) {
        //Checking if the employee does not already exist
        Optional<Employee> isExisting = employeeRepository.findByEmail(email);
        if(isExisting.isEmpty()) {
            return false;
        }
        if(!email.contains("@")){
            return false;
        }
        return true;
    }
}
