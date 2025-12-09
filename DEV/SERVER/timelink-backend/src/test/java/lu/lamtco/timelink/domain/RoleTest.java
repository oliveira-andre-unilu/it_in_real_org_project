package lu.lamtco.timelink.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    void testRoleEnumValues() {
        assertThat(Role.values()).containsExactly(Role.STAFF, Role.ADMIN);
    }

    @Test
    void testValueOfRole() {
        assertThat(Role.valueOf("STAFF")).isEqualTo(Role.STAFF);
        assertThat(Role.valueOf("ADMIN")).isEqualTo(Role.ADMIN);
    }
}
