package lu.lamtco.timelink.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Authentication request containing email and password")
public class AuthRequest {

    @Schema(description = "User email address", example = "user@example.com")
    private String email;

    @Schema(description = "User password", example = "SecurePassword123")
    private String password;

    public AuthRequest() {}

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
