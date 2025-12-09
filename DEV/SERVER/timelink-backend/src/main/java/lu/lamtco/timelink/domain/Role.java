package lu.lamtco.timelink.domain;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Defines the roles an employee can have in the system.
 * STAFF: Regular employee with standard permissions.
 * ADMIN: Employee with administrative privileges.
 *
 * @version 0.1
 */
@Schema(description = "Employee role")
public enum Role {
    /**
     * Regular employee with standard permissions.
     */
    STAFF,

    /**
     * Employee with administrative privileges.
     */
    ADMIN
}