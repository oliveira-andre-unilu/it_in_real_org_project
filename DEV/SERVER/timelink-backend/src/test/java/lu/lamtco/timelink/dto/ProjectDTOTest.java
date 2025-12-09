package lu.lamtco.timelink.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ProjectDTOTest {

    // create project dto with all args
    @Test
    void shouldCreateProjectDTOWithAllFields() {
        ProjectDTO dto = new ProjectDTO();
        dto.setName("Project");
        dto.setNumber("PROJ-001");
        dto.setCostumerId(123L);
        dto.setLocation("Luxembourg");

        assertEquals("Project", dto.getName());
        assertEquals("PROJ-001", dto.getNumber());
        assertEquals(123L, dto.getCostumerId());
        assertEquals("Luxembourg", dto.getLocation());
    }

    // create project dto with no args
    @Test
    void shouldHandleNullValues() {
        ProjectDTO dto = new ProjectDTO();

        assertNull(dto.getName());
        assertNull(dto.getNumber());
        assertNull(dto.getCostumerId());
        assertNull(dto.getLocation());
    }

}
