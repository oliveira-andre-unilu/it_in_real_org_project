package lu.lamtco.timelink.rws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lu.lamtco.timelink.domain.Customer;
import lu.lamtco.timelink.domain.Project;
import lu.lamtco.timelink.persister.CustomerRepository;
import lu.lamtco.timelink.persister.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer testCustomer;

    @BeforeEach
    void setup() {
        projectRepository.deleteAll();
        customerRepository.deleteAll();

        testCustomer = new Customer();
        testCustomer.setCompanyName("ACME SA");
        testCustomer.setTaxNumber("123456789");
        customerRepository.save(testCustomer);

        Project testProject = new Project();
        testProject.setName("Project Alpha");
        testProject.setNumber("P001");
        testProject.setCustomer(testCustomer);
        projectRepository.save(testProject);
    }

    @Test
    void testGetAllProjects() throws Exception {
        mockMvc.perform(get("/api/projects")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Project Alpha"))
                .andExpect(jsonPath("$[0].number").value("P001"))
                .andExpect(jsonPath("$[0].customer.companyName").value("ACME SA"));
    }

    @Test
    void testCreateProject() throws Exception {
        Project newProject = new Project();
        newProject.setName("Project Beta");
        newProject.setNumber("P002");
        newProject.setCustomer(testCustomer);

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProject)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Project Beta"))
                .andExpect(jsonPath("$.number").value("P002"))
                .andExpect(jsonPath("$.customer.companyName").value("ACME SA"));
    }
}
