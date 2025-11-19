package lu.lamtco.timelink.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Schema(description = "Project entity representing a customer project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the project", example = "1")
    private Long id;

    @Schema(description = "Name of the project", example = "Website Redesign")
    private String name;

    @Schema(description = "Project number or code", example = "PRJ-2025-001")
    private String number;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @Schema(description = "Customer associated with the project")
    private Customer customer;

    @Schema(description = "Location of the project (optional)", example = "Berlin")
    private String location;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(hidden = true)
    private List<TimestampEntry> timestamps = new ArrayList<>();
}