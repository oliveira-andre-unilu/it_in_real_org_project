package lu.lamtco.timelink.rws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lu.lamtco.timelink.domain.Customer;
import lu.lamtco.timelink.persister.CustomerRepository;
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
public class CustomerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        Customer testCustomer = new Customer();
        testCustomer.setCompanyName("ACME SA");
        testCustomer.setTaxNumber("123456789");
        testCustomer.setEmail("contact@acme.com");
        testCustomer.setTel("0123456789");
        testCustomer.setFax("0123456788");
        repository.save(testCustomer);
    }

    @Test
    void testGetAllCustomers() throws Exception {
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyName").value("ACME SA"));
    }

    @Test
    void testCreateCustomer() throws Exception {
        Customer newCustomer = new Customer();
        newCustomer.setCompanyName("Beta SARL");
        newCustomer.setTaxNumber("987654321");
        newCustomer.setEmail("beta@example.com");
        newCustomer.setTel("0123");
        newCustomer.setFax("0456");

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("Beta SARL"));
    }
}