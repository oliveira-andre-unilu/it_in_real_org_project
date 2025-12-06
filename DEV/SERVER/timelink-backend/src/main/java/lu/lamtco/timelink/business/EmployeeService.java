package lu.lamtco.timelink.services;

import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.dto.EmployeeDTO;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnauthorizedActionException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.persister.EmployeeRepository;
import lu.lamtco.timelink.security.AuthService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthService authService;

    public EmployeeService(EmployeeRepository employeeRepository, AuthService authService) {
        this.employeeRepository = employeeRepository;
        this.authService = authService;
    }

    //Methods used in the rws

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
        String hashedPassword = authService.hashPassword(dto.getPassword());
        employee.setPassword(hashedPassword);
        employee.setRole(dto.getRole());
        employee.setHourlyRate(dto.getHourlyRate());

        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(String jwtToken, long id) throws UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {

        //Firt verify if it is an admin user
        boolean isNotAdmin=false;
        try{
            authService.verifyAdminAccess(jwtToken);
        }catch(UnauthorizedActionException e){
            //If user is not Admin verify self identity
            isNotAdmin=true;
        }

        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            //Verify self identity if needed
            Employee result = employee.get();
            if(isNotAdmin){
                authService.verifySelfIdentityAccess(jwtToken, result.getId(), result.getEmail());
            }
            return result;
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
    public Employee updateEmployee(String jwtToken, long id, EmployeeDTO dto) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        Employee employee = getEmployeeById(jwtToken, id); //Verification done at this stage

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
    public boolean deleteEmployee(String jwtToken, long id) throws UnexistingEntityException, InvalidAuthentication, UnauthorizedActionException {
        Employee employee = getEmployeeById(jwtToken, id); //Verification done at this stage
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
