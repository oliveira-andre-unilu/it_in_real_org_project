package lu.lamtco.timelink.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TimestampEntryTest {

    @Test
    void testTimestampEntryFields() {
        Employee employee = new Employee();
        Project project = new Project();

        TimestampEntry timestamp = new TimestampEntry();
        timestamp.setEmployee(employee);
        timestamp.setProject(project);
        timestamp.setTag(Tag.WORK);
        timestamp.setStartingTime(LocalDateTime.of(2025, 11, 10, 9, 0));
        timestamp.setDuration(2.5);
        timestamp.setLatitude("Latitude1");
        timestamp.setLongitude("Longitude1");

        assertThat(timestamp.getEmployee()).isEqualTo(employee);
        assertThat(timestamp.getProject()).isEqualTo(project);
        assertThat(timestamp.getTag()).isEqualTo(Tag.WORK);
        assertThat(timestamp.getStartingTime()).isEqualTo(LocalDateTime.of(2025, 11, 10, 9, 0));
        assertThat(timestamp.getDuration()).isEqualTo(2.5);
        assertThat(timestamp.getLatitude()).isEqualTo("Latitude1");
        assertThat(timestamp.getLongitude()).isEqualTo("Longitude1");
    }
}
