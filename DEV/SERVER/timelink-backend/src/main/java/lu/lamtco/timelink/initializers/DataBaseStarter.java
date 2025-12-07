package lu.lamtco.timelink.initializers;

import lu.lamtco.timelink.domain.Customer;
import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.domain.Project;
import lu.lamtco.timelink.domain.Role;
import lu.lamtco.timelink.persister.CustomerRepository;
import lu.lamtco.timelink.persister.EmployeeRepository;
import lu.lamtco.timelink.persister.ProjectRepository;
import lu.lamtco.timelink.security.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataBaseStarter implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final ProjectRepository projectRepository;
    private final AuthService authService;

    public DataBaseStarter(EmployeeRepository employeeRepository, CustomerRepository customerRepository, ProjectRepository projectRepository ,AuthService authService) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.projectRepository = projectRepository;
        this.authService = authService;
    }

    @Transactional
    @Override
    public void run(String... args) {

        employeeRepository.save(new Employee("admin", "admin", "admin@timelink.com", authService.hashPassword("admin"), Role.ADMIN, 35.5));
        employeeRepository.save(new Employee("staff", "staff", "staff@timelink.com", authService.hashPassword("staff"), Role.STAFF, 15.5));
        Customer customer1 = new Customer("Uni.lu", "LU123456789", "info@uni.lu", "02255888", "02255888");
        Customer customer2 = new Customer("Code42", "LU987654321", "info@code42.lu", "0025998", "0025998");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        Project project1 = new Project("IT in real org", "1", customer1, "49.504575;5.949298");
        projectRepository.save(project1);
        Project project2 = new Project("Integration of management systems", "2", customer2, "49.600764;6.134055");
        projectRepository.save(project2);
    }

    //Helper functions
}
