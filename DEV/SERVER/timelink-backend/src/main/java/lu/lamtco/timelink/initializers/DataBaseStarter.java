package lu.lamtco.timelink.initializers;

import lu.lamtco.timelink.domain.Customer;
import lu.lamtco.timelink.domain.Employee;
import lu.lamtco.timelink.domain.Project;
import lu.lamtco.timelink.domain.Role;
import lu.lamtco.timelink.domain.Tag;
import lu.lamtco.timelink.domain.TimestampEntry;
import lu.lamtco.timelink.persister.CustomerRepository;
import lu.lamtco.timelink.persister.EmployeeRepository;
import lu.lamtco.timelink.persister.ProjectRepository;
import lu.lamtco.timelink.persister.TimestampEntryRepository;
import lu.lamtco.timelink.security.AuthService;

import java.security.Timestamp;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataBaseStarter implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final ProjectRepository projectRepository;
    private final AuthService authService;
    private final TimestampEntryRepository timestampEntryRepository;

    public DataBaseStarter(EmployeeRepository employeeRepository, CustomerRepository customerRepository, ProjectRepository projectRepository ,AuthService authService, TimestampEntryRepository timestampEntryRepository) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.projectRepository = projectRepository;
        this.authService = authService;
        this.timestampEntryRepository = timestampEntryRepository;
    }

    @Transactional
    @Override
    public void run(String... args) {

        Employee employee1 = new Employee("admin", "admin", "admin@timelink.com", authService.hashPassword("admin"), Role.ADMIN, 35.5);
        employeeRepository.save(employee1);
        Employee employee2 = new Employee("staff", "staff", "staff@timelink.com", authService.hashPassword("staff"), Role.STAFF, 15.5);
        employeeRepository.save(employee2);
        Customer customer1 = new Customer("Uni.lu", "LU123456789", "info@uni.lu", "02255888", "02255888");
        Customer customer2 = new Customer("Code42", "LU987654321", "info@code42.lu", "0025998", "0025998");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        Project project1 = new Project("Belval University", "1", customer1, "49.504575;5.949298");
        projectRepository.save(project1);
        Project project2 = new Project("Luxembourg Gare", "2", customer2, "49.600764;6.134055");
        projectRepository.save(project2);
        Project project3 = new Project("Paris", "3", customer1, "48.853369;2.349000");
        Project project4 = new Project("New York", "4", customer1, "40.711572;-74.013276");
        Project project5 = new Project("Tokyo", "5", customer1, "35.659630;139.700661");
        projectRepository.save(project3);
        projectRepository.save(project4);
        projectRepository.save(project5);
        TimestampEntry timestampEntry = new TimestampEntry(LocalDate.of(2025, 12, 1).atTime(8, 0), 8.0, "0", "0", employee2, project1, Tag.WORK);
        TimestampEntry timestampEntry2 = new TimestampEntry(LocalDate.of(2025, 12, 2).atTime(7, 30), 7.5, "0", "0", employee2, project1, Tag.WORK);
        TimestampEntry timestampEntry3 = new TimestampEntry(LocalDate.of(2025, 12, 3).atTime(7, 0), 7.0, "0", "0", employee2, project1, Tag.WORK);
        TimestampEntry timestampEntry4 = new TimestampEntry(LocalDate.of(2025, 12, 4).atTime(8, 30), 8.5, "0", "0", employee2, project1, Tag.WORK);
        TimestampEntry timestampEntry5 = new TimestampEntry(LocalDate.of(2025, 12, 5).atTime(9, 0), 9.0, "0", "0", employee2, project1, Tag.WORK);
        timestampEntryRepository.save(timestampEntry);
        timestampEntryRepository.save(timestampEntry2);
        timestampEntryRepository.save(timestampEntry3);
        timestampEntryRepository.save(timestampEntry4);
        timestampEntryRepository.save(timestampEntry5);
    }

    //Helper functions
}
