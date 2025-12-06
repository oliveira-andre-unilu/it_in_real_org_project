package lu.lamtco.timelink.domain;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Defines the type of a timestamp entry.
 * WORK: Represents a working period.
 * BREAK: Represents a break period.
 */
@Schema(description = "Timestamp tag indicating type of entry")
public enum Tag {
    /**
     * Working period entry.
     */
    WORK,

    /**
     * Break period entry.
     */
    BREAK
}