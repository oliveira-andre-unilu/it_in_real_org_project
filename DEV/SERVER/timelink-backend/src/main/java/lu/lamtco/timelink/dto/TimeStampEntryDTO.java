package lu.lamtco.timelink.dto;

import lombok.Getter;
import lombok.Setter;
import lu.lamtco.timelink.domain.Tag;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for transferring timestamp entry data
 * between client and server without exposing the entity directly.
 */
@Getter
@Setter
public class TimeStampEntryDTO {

    /** Start time of the timestamp entry */
    private LocalDateTime startTime;

    /** Duration of the timestamp entry in hours */
    private Double duration;

    /** Latitude of the entry location */
    private String latitude;

    /** Longitude of the entry location */
    private String longitude;

    /** ID of the employee associated with this timestamp */
    private Long employeeId;

    /** ID of the project associated with this timestamp */
    private Long projectId;

    /** Tag indicating the type of entry (WORK or BREAK) */
    private Tag tag;
}