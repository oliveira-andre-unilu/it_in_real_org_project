package lu.lamtco.timelink.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeTest {

    @Test
    void testEmployeeFieldsAndTimestamps() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setSurname("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setPassword("secret");
        employee.setRole(Role.STAFF);
        employee.setHourlyRate(50.0);

        TimestampEntry timestamp = new TimestampEntry();
        timestamp.setEmployee(employee);

        employee.getTimestamps().add(timestamp);

        assertThat(employee.getName()).isEqualTo("John");
        assertThat(employee.getTimestamps()).hasSize(1).contains(timestamp);
        assertThat(timestamp.getEmployee()).isEqualTo(employee);
    }
}
