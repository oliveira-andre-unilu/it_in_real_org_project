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

@Component
public class JWTUtils {

    private static final String SECRET_KEY = "YWN0aWxlYXJuX1NvZnR3YXJlRW5naW5lZXJpbmdfR3JvdXBfUHJvamVjdF9BQ1Q=";
    private static final long EXPIRATION_TIME = 86400000;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String generateToken(String username, long userId, Role role) {
        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .claim("role", role)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    public UserAuthData decodeToken(String token) throws InvalidAuthentication {
        try {
            Claims claims = Jwts.parser()
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String userName = claims.getSubject();
            long userId = claims.get("userId", Long.class);
            Role role = claims.get("role", Role.class);
            return new UserAuthData(userName, userId, role);
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidAuthentication("Invalid token!!!");
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
