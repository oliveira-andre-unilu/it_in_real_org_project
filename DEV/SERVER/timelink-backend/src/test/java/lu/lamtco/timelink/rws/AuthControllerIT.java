package lu.lamtco.timelink.rws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lu.lamtco.timelink.domain.AuthRequest;
import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.persister.EmployeeRepository;
import lu.lamtco.timelink.security.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void testSignUpSuccess() throws Exception {
        Employee employee = new Employee();
        employee.setEmail("test@example.com");
        employee.setPassword("password123");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully!"));
    }

    @Test
    void testSignUpFailureDuplicateEmail() throws Exception {
        Employee employee = new Employee();
        employee.setEmail("test@example.com");
        employee.setPassword(authService.hashPassword("password123"));
        repository.save(employee);

        Employee duplicate = new Employee();
        duplicate.setEmail("test@example.com");
        duplicate.setPassword("password123");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email is already registered!"));
    }

    @Test
    void testSignInSuccess() throws Exception {
        Employee employee = new Employee();
        employee.setEmail("test@example.com");
        employee.setPassword(authService.hashPassword("password123"));
        repository.save(employee);

        AuthRequest request = new AuthRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testSignInFailureWrongPassword() throws Exception {
        Employee employee = new Employee();
        employee.setEmail("test@example.com");
        employee.setPassword(authService.hashPassword("password123"));
        repository.save(employee);

        AuthRequest request = new AuthRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrongpassword");

        mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid email or password!"));
    }
}
