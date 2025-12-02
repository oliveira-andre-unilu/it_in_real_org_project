package lu.lamtco.timelink.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Schema(description = "Timestamp entry for tracking work or breaks")
public class TimestampEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the timestamp entry", example = "1")
    private Long id;

    @Schema(description = "Start time of the timestamp entry", example = "2025-11-19T09:00:00")
    private LocalDateTime startingTime;

    @Schema(description = "Duration in hours", example = "1.5")
    private Double duration;

    @Schema(description = "Latitude of the entry location", example = "52.5200")
    private String latitude;

    @Schema(description = "Longitude of the entry location", example = "13.4050")
    private String longitude;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @Schema(description = "Employee associated with this timestamp entry")
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @Schema(description = "Project associated with this timestamp entry")
    @JsonIgnore
    private Project project;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Tag of the entry (work or break)", example = "WORK")
    private Tag tag;
}