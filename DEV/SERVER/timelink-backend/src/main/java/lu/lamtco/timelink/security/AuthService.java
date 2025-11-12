package lu.lamtco.timelink.security;

import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.persister.EmployeeRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final JWTUtils jwtUtils;

    public AuthService(EmployeeRepository employeeRepository, JWTUtils jwtUtils) {
        this.employeeRepository = employeeRepository;
        this.jwtUtils = jwtUtils;
    }

    public void signUp(Employee employee) {
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered!");
        }
        employee.setPassword(hashPassword(employee.getPassword()));
        employeeRepository.save(employee);
    }

    public String signIn(String email, String password) {
        Employee storedEmployee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password!"));

        if (!checkPassword(password, storedEmployee.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password!");
        }

        return jwtUtils.generateToken(storedEmployee.getEmail());
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    private boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
