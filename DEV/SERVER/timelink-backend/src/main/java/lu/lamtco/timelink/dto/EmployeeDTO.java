package lu.lamtco.timelink.dto;

import lombok.Getter;
import lombok.Setter;
import lu.lamtco.timelink.domain.Role;

/**
 * Data Transfer Object for transferring employee data
 * between client and server without exposing the entity directly.
 *
 * @version 0.1
 */
@Getter
@Setter
public class EmployeeDTO {

    /** First name of the employee */
    private String name;

    /** Surname of the employee */
    private String surName;

    /** Email address of the employee */
    private String email;

    /** Password for authentication (plain text, will be hashed before storing) */
    private String password;

    /** Role of the employee (e.g., STAFF, ADMIN) */
    private Role role;

    /** Hourly rate of the employee */
    private Double hourlyRate;
}