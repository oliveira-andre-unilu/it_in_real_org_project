package lu.lamtco.timelink.dto;

import lombok.Getter;
import lombok.Setter;
import lu.lamtco.timelink.domain.Tag;

import java.time.LocalDateTime;

@Getter
@Setter
public class TimeStampEntryDTO {
    private LocalDateTime startTime;
    private Double duration;
    private String latitude, longitude;
    private Long employeeId, projectId;
    private Tag tag;
}
