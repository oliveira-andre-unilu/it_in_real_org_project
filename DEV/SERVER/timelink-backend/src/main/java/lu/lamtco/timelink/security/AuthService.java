package lu.lamtco.timelink.security;

import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.domain.Role;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnauthorizedActionException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.persister.EmployeeRepository;
import lu.lamtco.timelink.services.EmployeeService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;
    private final JWTUtils jwtUtils;

    public AuthService(EmployeeRepository employeeRepository, JWTUtils jwtUtils, EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
        this.jwtUtils = jwtUtils;
    }

    //Deprecated method, all user creation shall be done under the Employee service class
//    public void signUp(Employee employee) {
//        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("Email is already registered!");
//        }
//        employee.setPassword(hashPassword(employee.getPassword()));
//        employeeRepository.save(employee);
//    }


    public String signInAndGetToken(String email, String password) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication {
        Employee employee = employeeService.getEmployeeByEmail(email);
        //Checking if email/password are the same
        if(!checkPassword(password, employee.getPassword())){
            throw new InvalidAuthentication("Invalid user or password");
        }
        return jwtUtils.generateToken(employee.getEmail(), employee.getId(), employee.getRole());
    }

    public UserAuthData getAuthData(String token) throws InvalidAuthentication {
        return jwtUtils.decodeToken(token);
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    private boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    //Additional helper functions
    public boolean verifyAdminAccess(String webToken) throws InvalidAuthentication, UnauthorizedActionException {
        UserAuthData authData = this.getAuthData(webToken);
        if(authData.role()!= Role.ADMIN){
            throw new UnauthorizedActionException();
        }
        return true;
    }

    public boolean verifySelfIdentityAccess(String webToken, Long id, String userName) throws InvalidAuthentication, UnauthorizedActionException {
        UserAuthData authData = this.getAuthData(webToken);
        if(authData.userName().equals(userName) && authData.id() == id){
            return true;
        }else{
            throw new UnauthorizedActionException();
        }
    }
}
