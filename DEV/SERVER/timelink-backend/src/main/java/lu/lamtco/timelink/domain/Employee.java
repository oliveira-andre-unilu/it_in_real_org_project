package lu.lamtco.timelink.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Schema(description = "Employee entity representing a system user")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the employee", example = "1")
    private Long id;

    @Schema(description = "First name of the employee", example = "John")
    private String name;

    @Schema(description = "Surname of the employee", example = "Doe")
    private String surname;

    @Schema(description = "Email address of the employee", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Password for authentication (hashed in database)", example = "SecurePassword123")
    private String password;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Role of the employee", example = "STAFF")
    private Role role;

    @Schema(description = "Hourly rate of the employee", example = "25.5")
    private Double hourlyRate;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(hidden = true)
    private List<TimestampEntry> timestamps = new ArrayList<>();

    public Employee() {
        super();
    }

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