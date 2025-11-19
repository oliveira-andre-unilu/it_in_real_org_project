package lu.lamtco.timelink.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Timestamp tag indicating type of entry")
public enum Tag {
    WORK,
    BREAK
}