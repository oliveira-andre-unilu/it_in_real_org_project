package lu.lamtco.timelink.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import lu.lamtco.timelink.domain.Tag;

public class TimeStampEntryDTOTest {

    // create timestamp dto with all args
    @Test
    void shouldCreateTimeStampEntryDTOWithAllFields() {
        LocalDateTime startTime = LocalDateTime.now();
        TimeStampEntryDTO dto = new TimeStampEntryDTO();
        dto.setStartTime(startTime);
        dto.setDuration(8.5);
        dto.setLatitude("49.8153");
        dto.setLongitude("6.1296");
        dto.setEmployeeId(1L);
        dto.setProjectId(2L);
        dto.setTag(Tag.WORK);

        assertEquals(startTime, dto.getStartTime());
        assertEquals(8.5, dto.getDuration());
        assertEquals("49.8153", dto.getLatitude());
        assertEquals("6.1296", dto.getLongitude());
        assertEquals(1L, dto.getEmployeeId());
        assertEquals(2L, dto.getProjectId());
        assertEquals(Tag.WORK, dto.getTag());
    }

    // create timestamps without coordinates
    @Test
    void shouldHandleNullCoordinates() {
        TimeStampEntryDTO dto = new TimeStampEntryDTO();

        assertNull(dto.getLatitude());
        assertNull(dto.getLongitude());
    }

    // create timestamp with 0 duration
    @Test
    void shouldAcceptZeroDuration() {
        TimeStampEntryDTO dto = new TimeStampEntryDTO();
        dto.setDuration(0.0);

        assertEquals(0.0, dto.getDuration());
    }
}
