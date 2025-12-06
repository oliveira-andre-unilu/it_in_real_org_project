package lu.lamtco.timelink.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity representing a timestamp entry for tracking work or break periods.
 * Contains information about start time, duration, location, associated employee and project, and a tag.
 *
 * @version 0.1
 */
@Entity
@Getter
@Setter
@Schema(description = "Timestamp entry for tracking work or breaks")
public class TimestampEntry {

    /** Unique identifier of the timestamp entry */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the timestamp entry", example = "1")
    private Long id;

    /** Start time of the timestamp entry */
    @Schema(description = "Start time of the timestamp entry", example = "2025-11-19T09:00:00")
    private LocalDateTime startingTime;

    /** Duration in hours */
    @Schema(description = "Duration in hours", example = "1.5")
    private Double duration;

    /** Latitude of the entry location */
    @Schema(description = "Latitude of the entry location", example = "52.5200")
    private String latitude;

    /** Longitude of the entry location */
    @Schema(description = "Longitude of the entry location", example = "13.4050")
    private String longitude;

    /** Employee associated with this timestamp entry */
    @ManyToOne
    @JoinColumn(name = "employee_id")
    @Schema(description = "Employee associated with this timestamp entry")
    @JsonIgnore
    private Employee employee;

    /** Project associated with this timestamp entry */
    @ManyToOne
    @JoinColumn(name = "project_id")
    @Schema(description = "Project associated with this timestamp entry")
    @JsonIgnore
    private Project project;

    /** Tag indicating the type of entry (WORK or BREAK) */
    @Enumerated(EnumType.STRING)
    @Schema(description = "Tag of the entry (work or break)", example = "WORK")
    private Tag tag;
}