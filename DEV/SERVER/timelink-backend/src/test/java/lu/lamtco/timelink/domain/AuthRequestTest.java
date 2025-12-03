package lu.lamtco.timelink.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class AuthRequestTest {

    @Test
    // constructor with no arguments
    void testEmptyConstructorAndSetters() {
        AuthRequest auth = new AuthRequest();
        auth.setEmail("user@example.com");
        auth.setPassword("password");

        assertThat(auth.getEmail()).isEqualTo("user@example.com");
        assertThat(auth.getPassword()).isEqualTo("password");
    }

    @Test
    // contrsuctor with arguments
    void testNotEmptyConstructor() {
        AuthRequest auth = new AuthRequest("user@example.com", "password");

        assertThat(auth.getEmail()).isEqualTo("user@example.com");
        assertThat(auth.getPassword()).isEqualTo("password");
    }
}
