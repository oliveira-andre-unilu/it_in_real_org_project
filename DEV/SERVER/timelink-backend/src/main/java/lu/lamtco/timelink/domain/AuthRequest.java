package lu.lamtco.timelink.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an authentication request containing user credentials.
 * Used to transfer email and password from the client to the authentication endpoint.
 * Validation ensures the email is valid and no fields are blank.
 *
 * @version 0.1
 */
@Setter
@Getter
@Schema(description = "Authentication request containing email and password")
public class AuthRequest {

    /**
     * User email address.
     * Must be a valid email format and cannot be blank.
     * Example: "user@example.com"
     */
    @NotBlank
    @Email
    @Schema(description = "User email address", example = "user@example.com")
    private String email;

    /**
     * User password.
     * Cannot be blank.
     * Example: "SecurePassword123"
     */
    @NotBlank
    @Schema(description = "User password", example = "SecurePassword123")
    private String password;

    /** Default constructor */
    public AuthRequest() {}

    /**
     * Constructs a new authentication request with the given email and password.
     *
     * @param email    the user's email address
     * @param password the user's password
     */
    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
