package lu.lamtco.timelink.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import lu.lamtco.timelink.domain.Role;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;

public class JWTUtilsTest {
    private JWTUtils jwtUtils;

    @BeforeEach
    void setUp() {
        jwtUtils = new JWTUtils();
    }

    // generate valid token
    @Test
    void generateToken_ShouldReturnValidToken() {
        String username = "test@company.com";
        long userId = 123L;
        Role role = Role.ADMIN;

        String token = jwtUtils.generateToken(username, userId, role);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        // token should have three parts separated by dot
        assertEquals(3, token.split("\\.").length);
    }

    // decode token invalid token that throws exception
    @Test
    void decodeToken_ShouldThrowInvalidAuthentication_WhenTokenInvalid() {
        String invalidToken = "invalid.token.here";

        assertThrows(InvalidAuthentication.class, () -> jwtUtils.decodeToken(invalidToken));
    }

    // decode token, empty token that throws exception
    @Test
    void decodeToken_ShouldThrowInvalidAuthentication_WhenTokenEmpty() {
        assertThrows(InvalidAuthentication.class, () -> jwtUtils.decodeToken(""));
        assertThrows(InvalidAuthentication.class, () -> jwtUtils.decodeToken(null));
    }

    // validate token that return false for invalid ones
    @Test
    void validateToken_ShouldReturnFalse_ForInvalidToken() {
        assertFalse(jwtUtils.validateToken("invalid.token.here"));
        assertFalse(jwtUtils.validateToken(""));
    }

    // validate token return false because token is null
    @Test
    void validateToken_ShouldReturnFalse_ForNullToken() {
        assertFalse(jwtUtils.validateToken(null));
    }

}
