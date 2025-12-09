package lu.lamtco.timelink.rws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.domain.Role;
import lu.lamtco.timelink.persister.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee testEmployee;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        testEmployee = new Employee();
        testEmployee.setName("Max");
        testEmployee.setSurname("Mustermann");
        testEmployee.setEmail("max@example.com");
        testEmployee.setPassword("hashed");
        testEmployee.setRole(Role.STAFF);
        testEmployee.setHourlyRate(15.0);
        repository.save(testEmployee);
    }

    @Test
    void testGetAllEmployees() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Max"));
    }

    @Test
    void testCreateEmployee() throws Exception {
        Employee newEmp = new Employee();
        newEmp.setName("Anna");
        newEmp.setSurname("Muster");
        newEmp.setEmail("anna@example.com");
        newEmp.setPassword("hashed");
        newEmp.setRole(Role.STAFF);
        newEmp.setHourlyRate(20.0);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEmp)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Anna"));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        testEmployee.setHourlyRate(18.0);

        mockMvc.perform(put("/api/employees/" + testEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hourlyRate").value(18.0));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/api/employees/" + testEmployee.getId()))
                .andExpect(status().isNoContent());
    }
}