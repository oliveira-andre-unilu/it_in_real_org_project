package lu.lamtco.timelink.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for transferring project data
 * between client and server without exposing the entity directly.
 *
 * @version 0.1
 */
@Getter
@Setter
public class ProjectDTO {

    /** Name of the project */
    private String name;

    /** Project number or code */
    private String number;

    /** ID of the customer associated with the project */
    private Long costumerId;

    /** Location of the project (optional) */
    private String location;
}