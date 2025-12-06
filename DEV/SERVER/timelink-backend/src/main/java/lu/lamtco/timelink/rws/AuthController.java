package lu.lamtco.timelink.rws;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lu.lamtco.timelink.domain.AuthRequest;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller handling user authentication.
 * Provides endpoints for signing in users and providing JWT tokens.
 *
 * @version 0.1
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authenticates a user using email and password and returns a JWT token if successful.
     *
     * @param request An AuthRequest object containing the user's email and password.
     * @return ResponseEntity containing the JWT token if authentication succeeds, or appropriate HTTP status if failed.
     */
    @Operation(summary = "User signin", description = "Authenticate a user and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "400", description = "Email is not conform")
    })
    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody AuthRequest request) {
        try {
            String token = authService.signInAndGetToken(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(token);
        } catch (NonConformRequestedDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (InvalidAuthentication | UnexistingEntityException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
