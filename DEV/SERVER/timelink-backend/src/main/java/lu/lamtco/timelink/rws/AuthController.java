package lu.lamtco.timelink.rws;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.domain.AuthRequest;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import lu.lamtco.timelink.exeptions.NonConformRequestedDataException;
import lu.lamtco.timelink.exeptions.UnexistingEntityException;
import lu.lamtco.timelink.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

//    @Operation(summary = "User signup", description = "Register a new user in the system")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "User registered successfully",
//                    content = @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = String.class))),
//            @ApiResponse(responseCode = "400", description = "Invalid user data")
//    })
//    @PostMapping("/signup")
//    public ResponseEntity<String> signUp(@RequestBody Employee employee) {
//        try {
//            authService.signUp(employee);
//            return ResponseEntity.ok("User registered successfully!");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @Operation(summary = "User signin", description = "Authenticate a user and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody AuthRequest request) {
        try {
            String token = authService.signInAndGetToken(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(token);
        } catch (NonConformRequestedDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (InvalidAuthentication | UnexistingEntityException e) {
            return ResponseEntity.notFound().build();
        }
    }
}