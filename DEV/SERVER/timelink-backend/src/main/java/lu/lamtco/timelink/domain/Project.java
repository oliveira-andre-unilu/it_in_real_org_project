package lu.lamtco.timelink.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a project entity linked to a customer.
 * A project can have multiple timestamp entries and belongs to a single customer.
 */
@Entity
@Getter
@Setter
@Schema(description = "Project entity representing a customer project")
public class Project {

    /**
     * Unique identifier of the project.
     * Example: 1
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the project", example = "1")
    private Long id;

    /**
     * Name of the project.
     * Example: Website Redesign
     */
    @Schema(description = "Name of the project", example = "Website Redesign")
    private String name;

    /**
     * Project number or code.
     * Example: PRJ-2025-001
     */
    @Schema(description = "Project number or code", example = "PRJ-2025-001")
    private String number;

    /**
     * Customer associated with the project.
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @Schema(description = "Customer associated with the project")
    private Customer customer;

    /**
     * Location of the project (optional).
     * Example: Berlin
     */
    @Schema(description = "Location of the project (optional)", example = "Berlin")
    private String location;

    /**
     * List of timestamp entries for this project.
     * This field is hidden in API documentation.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(hidden = true)
    private List<TimestampEntry> timestamps = new ArrayList<>();

    /** Default constructor for JPA */
    public Project() {
        super();
    }

    /**
     * Constructs a new project with the provided details.
     *
     * @param name     Name of the project
     * @param number   Project number or code
     * @param customer Associated customer
     * @param location Location of the project
     */
    public Project(String name, String number, Customer customer, String location) {
        super();
        this.name = name;
        this.number = number;
        this.customer = customer;
        this.location = location;
    }
}