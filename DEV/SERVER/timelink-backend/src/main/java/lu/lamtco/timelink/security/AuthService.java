package lu.lamtco.timelink.security;

import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.domain.Role;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnauthorizedActionException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.persister.EmployeeRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * Service responsible for handling authentication and authorization.
 * Provides JWT-based authentication, password hashing, and access verification.
 *
 * @version 0.1
 */
@Service
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final JWTUtils jwtUtils;

    public AuthService(EmployeeRepository employeeRepository, JWTUtils jwtUtils) {
        this.employeeRepository = employeeRepository;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Authenticates a user with email and password, returning a JWT token if successful.
     *
     * @param email    The user's email.
     * @param password The user's plain-text password.
     * @return A JWT token containing user information.
     * @throws NonConformRequestedDataException If the provided data is invalid.
     * @throws UnexistingEntityException        If the user does not exist.
     * @throws InvalidAuthentication            If the password is incorrect.
     */
    public String signInAndGetToken(String email, String password) throws NonConformRequestedDataException, UnexistingEntityException, InvalidAuthentication {
        Employee employee = employeeRepository.findByEmail(email).orElse(null);
        if (employee == null) {
            throw new InvalidAuthentication("Unexciting user");
        }
        if (!checkPassword(password, employee.getPassword())) {
            throw new InvalidAuthentication("Invalid user or password");
        }
        return jwtUtils.generateToken(employee.getEmail(), employee.getId(), employee.getRole());
    }

    /**
     * Decodes a JWT token and returns user authentication data.
     *
     * @param token JWT token to decode.
     * @return UserAuthData containing user ID, username, and role.
     * @throws InvalidAuthentication If the token is invalid or expired.
     */
    public UserAuthData getAuthData(String token) throws InvalidAuthentication {
        return jwtUtils.decodeToken(token);
    }

    /**
     * Hashes a plain-text password using BCrypt.
     *
     * @param password The plain-text password.
     * @return A hashed password string.
     */
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * Checks if a plain-text password matches a hashed password.
     *
     * @param password       Plain-text password.
     * @param hashedPassword The hashed password.
     * @return True if passwords match, false otherwise.
     */
    private boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    /**
     * Verifies that the user associated with the given JWT token is an admin.
     *
     * @param webToken JWT token to verify.
     * @return True if the user is an admin.
     * @throws InvalidAuthentication       If the token is invalid.
     * @throws UnauthorizedActionException If the user is not an admin.
     */
    public boolean verifyAdminAccess(String webToken) throws InvalidAuthentication, UnauthorizedActionException {
        UserAuthData authData = this.getAuthData(webToken);
        if (authData.role() != Role.ADMIN) {
            throw new UnauthorizedActionException();
        }
        return true;
    }

    /**
     * Verifies that the user associated with the JWT token matches a specific ID and username.
     *
     * @param webToken JWT token to verify.
     * @param id       Expected user ID.
     * @param userName Expected username (email).
     * @return True if the JWT corresponds to the expected user.
     * @throws InvalidAuthentication       If the token is invalid.
     * @throws UnauthorizedActionException If the user does not match the expected identity.
     */
    public boolean verifySelfIdentityAccess(String webToken, Long id, String userName) throws InvalidAuthentication, UnauthorizedActionException {
        UserAuthData authData = this.getAuthData(webToken);
        if (authData.userName().equals(userName) && authData.id() == id) {
            return true;
        } else {
            throw new UnauthorizedActionException();
        }
    }
}