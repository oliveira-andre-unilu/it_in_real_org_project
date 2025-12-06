package lu.lamtco.timelink.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee entity in the system.
 * An employee is a user who can log timestamps and be assigned to projects.
 */
@Entity
@Getter
@Setter
@Schema(description = "Employee entity representing a system user")
public class Employee {

    /**
     * Unique identifier of the employee.
     * Example: 1
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the employee", example = "1")
    private Long id;

    /**
     * First name of the employee.
     * Example: John
     */
    @Schema(description = "First name of the employee", example = "John")
    private String name;

    /**
     * Surname of the employee.
     * Example: Doe
     */
    @Schema(description = "Surname of the employee", example = "Doe")
    private String surname;

    /**
     * Email address of the employee.
     * Example: john.doe@example.com
     */
    @Schema(description = "Email address of the employee", example = "john.doe@example.com")
    private String email;

    /**
     * Password for authentication (should be stored hashed in database).
     * Example: SecurePassword123
     */
    @Schema(description = "Password for authentication (hashed in database)", example = "SecurePassword123")
    private String password;

    /**
     * Role of the employee in the system.
     * Example: STAFF
     */
    @Enumerated(EnumType.STRING)
    @Schema(description = "Role of the employee", example = "STAFF")
    private Role role;

    /**
     * Hourly rate of the employee.
     * Example: 25.5
     */
    @Schema(description = "Hourly rate of the employee", example = "25.5")
    private Double hourlyRate;

    /**
     * List of timestamp entries associated with the employee.
     * This field is hidden in API documentation.
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(hidden = true)
    private List<TimestampEntry> timestamps = new ArrayList<>();

    /** Default constructor. */
    public Employee() {
        super();
    }

    /**
     * Constructs a new employee with the provided details.
     *
     * @param name       First name
     * @param surname    Surname
     * @param email      Email address
     * @param password   Password
     * @param role       Role in the system
     * @param hourlyRate Hourly rate
     */
    public Employee(String name, String surname, String email, String password, Role role, Double hourlyRate) {
        super();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.hourlyRate = hourlyRate;
    }
}