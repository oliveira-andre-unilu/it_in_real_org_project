package lu.lamtco.timelink.security;

import lu.lamtco.timelink.domain.Role;

import java.io.Serializable;

public record UserAuthData(String userName, long id, Role role) implements Serializable {
}
