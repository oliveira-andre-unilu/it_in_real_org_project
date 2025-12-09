package lu.lamtco.timelink.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lu.lamtco.timelink.domain.Role;
import lu.lamtco.timelink.exeptions.InvalidAuthentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for creating, decoding, and validating JWT tokens.
 * Handles encoding of user information (ID, role, username) and token expiration.
 *
 * @version 0.1
 */
@Component
public class JWTUtils {

    private static final String SECRET_KEY = "YWN0aWxlYXJuX1NvZnR3YXJlRW5naW5lZXJpbmdfR3JvdXBfUHJvamVjdF9BQ1Q=";
    private static final long EXPIRATION_TIME = 86400000; // 24 hours in milliseconds

    /**
     * Returns the signing key used for JWT generation and verification.
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    /**
     * Generates a JWT token for a given user.
     *
     * @param username User's email or username.
     * @param userId   User's unique ID.
     * @param role     User's role.
     * @return A signed JWT token with user information and expiration.
     */
    public String generateToken(String username, long userId, Role role) {
        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .claim("role", role.name())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Decodes a JWT token and extracts the user's authentication data.
     *
     * @param token The JWT token to decode.
     * @return UserAuthData containing username, user ID, and role.
     * @throws InvalidAuthentication If the token is invalid or expired.
     */
    public UserAuthData decodeToken(String token) throws InvalidAuthentication {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String userName = claims.getSubject();
            long userId = claims.get("userId", Long.class);
            Role role = Role.valueOf(claims.get("role", String.class));

            return new UserAuthData(userName, userId, role);

        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidAuthentication("Invalid token!!!");
        }
    }

    /**
     * Validates whether a JWT token is properly signed and not expired.
     *
     * @param token The JWT token to validate.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}