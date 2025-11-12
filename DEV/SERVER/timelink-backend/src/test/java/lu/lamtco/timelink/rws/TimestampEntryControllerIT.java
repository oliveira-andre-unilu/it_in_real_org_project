package lu.lamtco.timelink.rws;

import lu.lamtco.timelink.domain.*;
import lu.lamtco.timelink.persister.CustomerRepository;
import lu.lamtco.timelink.persister.EmployeeRepository;
import lu.lamtco.timelink.persister.ProjectRepository;
import lu.lamtco.timelink.persister.TimestampEntryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class TimestampEntryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TimestampEntryRepository timestampEntryRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Project project;

    @BeforeEach
    void setup() {
        timestampEntryRepository.deleteAll();
        projectRepository.deleteAll();
        customerRepository.deleteAll();
        employeeRepository.deleteAll();

        Employee employee = new Employee();
        employee.setName("John");
        employee.setSurname("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setPassword("secret");
        employee.setRole(Role.STAFF);
        employee.setHourlyRate(50.0);
        employee = employeeRepository.save(employee);

        Customer customer = new Customer();
        customer.setCompanyName("Example Corp");
        customer.setEmail("contact@example.com");
        customer.setFax("123456");
        customer.setTaxNumber("TAX123");
        customer.setTel("987654321");
        customer = customerRepository.save(customer);

        project = new Project();
        project.setName("Project X");
        project.setNumber("P001");
        project.setLocation("Office");
        project.setCustomer(customer);
        project = projectRepository.save(project);

        TimestampEntry timestampEntry = new TimestampEntry();
        timestampEntry.setEmployee(employee);
        timestampEntry.setProject(project);
        timestampEntry.setDuration(12.00);
        timestampEntry.setLongitude("Longitude1");
        timestampEntry.setLatitude("Latitude1");
        timestampEntry.setStartingTime(LocalDateTime.now());
        timestampEntry.setTag(Tag.WORK);
        timestampEntryRepository.save(timestampEntry);
    }


    @Test
    void testGetAllTimestamps() throws Exception {
        mockMvc.perform(get("/api/timestamps"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateTimestamp() throws Exception {
        Employee employee = employeeRepository.findAll().get(0);

        String newTimestampJson = """
        {
          "employee": {"id": %d},
          "project": {"id": %d},
          "startingTime": "2025-11-10T10:00:00",
          "duration": 1.0,
          "latitude":"Latitude1",
          "longitude":"Longitude1", 
          "tag": "WORK"
        }
        """.formatted(employee.getId(), project.getId());

        mockMvc.perform(post("/api/timestamps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTimestampJson))
                .andExpect(status().isOk());
    }
}
