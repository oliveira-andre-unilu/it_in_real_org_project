package lu.lamtco.timelink.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Employee role")
public enum Role {
    STAFF,
    ADMIN
}