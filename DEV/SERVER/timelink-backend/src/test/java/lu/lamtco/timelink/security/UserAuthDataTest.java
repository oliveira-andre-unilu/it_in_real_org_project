package lu.lamtco.timelink.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lu.lamtco.timelink.domain.Role;

public class UserAuthDataTest {

    // create user auth data with all args
    @Test
    void shouldCreateUserAuthDataWithAllFields() {
        String userName = "john.doe@company.com";
        long id = 123L;
        Role role = Role.ADMIN;

        UserAuthData authData = new UserAuthData(userName, id, role);

        assertEquals(userName, authData.userName());
        assertEquals(id, authData.id());
        assertEquals(role, authData.role());
    }

    // create user auth data without all args
    @Test
    void shouldSupportNullUserName() {
        UserAuthData authData = new UserAuthData(null, 1L, Role.STAFF);

        assertNull(authData.userName());
        assertEquals(1L, authData.id());
        assertEquals(Role.STAFF, authData.role());
    }

    @Test
    void shouldBeEqualToSameValues() {
        UserAuthData auth1 = new UserAuthData("test@test.com", 1L, Role.STAFF);
        UserAuthData auth2 = new UserAuthData("test@test.com", 1L, Role.STAFF);

        assertEquals(auth1, auth2);
        assertEquals(auth1.hashCode(), auth2.hashCode());
    }

    @Test
    void shouldNotBeEqualToDifferentValues() {
        UserAuthData auth1 = new UserAuthData("test1@test.com", 1L, Role.STAFF);
        UserAuthData auth2 = new UserAuthData("test2@test.com", 2L, Role.ADMIN);

        assertNotEquals(auth1, auth2);
    }

    @Test
    void shouldNotBeEqualToNull() {
        UserAuthData authData = new UserAuthData("test@test.com", 1L, Role.STAFF);

        assertNotEquals(null, authData);
    }

    @Test
    void shouldNotBeEqualToDifferentType() {
        UserAuthData authData = new UserAuthData("test@test.com", 1L, Role.STAFF);

        assertNotEquals("string", authData);
    }

    @Test
    void toString_ShouldContainAllFields() {
        UserAuthData authData = new UserAuthData("test@test.com", 123L, Role.ADMIN);
        String str = authData.toString();

        assertTrue(str.contains("test@test.com"));
        assertTrue(str.contains("123"));
        assertTrue(str.contains("ADMIN") || str.contains("Role.ADMIN"));
    }

}
