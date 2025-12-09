package lu.lamtco.timelink.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import lu.lamtco.timelink.domain.Role;

public class EmployeeDTOTest {

    // create employee dto with all args
    @Test
    void shouldCreateEmployeeDTOWithAllFields() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setName("John");
        dto.setSurName("Doe");
        dto.setEmail("john.doe@company.com");
        dto.setPassword("password");
        dto.setRole(Role.ADMIN);
        dto.setHourlyRate(25.5);

        assertEquals("John", dto.getName());
        assertEquals("Doe", dto.getSurName());
        assertEquals("john.doe@company.com", dto.getEmail());
        assertEquals("password", dto.getPassword());
        assertEquals(Role.ADMIN, dto.getRole());
        assertEquals(25.5, dto.getHourlyRate());
    }

    // create employee dto with no args
    @Test
    void shouldHandleNullRoleAndHourlyRate() {
        EmployeeDTO dto = new EmployeeDTO();

        assertNull(dto.getRole());
        assertNull(dto.getHourlyRate());
    }

    // update password
    @Test
    void shouldUpdatePassword() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setPassword("old");
        dto.setPassword("new");

        assertEquals("new", dto.getPassword());
    }
}
