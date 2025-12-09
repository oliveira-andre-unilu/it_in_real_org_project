package lu.lamtco.timelink.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectTest {

    @Test
    void testProjectFieldsAndTimestamps() {
        Customer customer = new Customer();
        Project project = new Project();
        project.setName("Project X");
        project.setNumber("PX123");
        project.setCustomer(customer);
        project.setLocation("Berlin");

        TimestampEntry timestamp = new TimestampEntry();
        timestamp.setProject(project);
        project.getTimestamps().add(timestamp);

        assertThat(project.getName()).isEqualTo("Project X");
        assertThat(project.getCustomer()).isEqualTo(customer);
        assertThat(project.getTimestamps()).hasSize(1).contains(timestamp);
    }
}
