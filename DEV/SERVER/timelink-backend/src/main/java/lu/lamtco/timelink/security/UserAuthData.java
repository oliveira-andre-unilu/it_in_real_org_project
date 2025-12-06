package lu.lamtco.timelink.security;

import lu.lamtco.timelink.domain.Role;

import java.io.Serializable;

/**
 * Represents authenticated user data extracted from a JWT token.
 * Contains the username (email), user ID, and role of the user.
 *
 * @version 0.1
 */
public record UserAuthData(String userName, long id, Role role) implements Serializable {
}