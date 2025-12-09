package lu.lamtco.timelink.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for transferring customer data
 * between client and server without exposing the entity directly.
 *
 * @version 0.1
 */
@Getter
@Setter
public class CustomerDTO {

    /** Name of the company or customer */
    private String companyName;

    /** Tax number of the customer */
    private String taxNumber;

    /** Email address of the customer */
    private String email;

    /** Telephone number of the customer */
    private String tel;

    /** Fax number of the customer */
    private String fax;
}