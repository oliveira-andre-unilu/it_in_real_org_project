package lu.lamtco.timelink.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lu.lamtco.timelink.domain.Tag;

@Getter
@Setter
public class SimpleTimeStampEntryDTO {
    private LocalDateTime startTime;

    private Double duration;

    private String latitude;

    private String longitude;

    private Long projectId;

    private Tag tag;
}
