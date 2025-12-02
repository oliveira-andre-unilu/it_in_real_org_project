package lu.lamtco.timelink.dto;

import lombok.Getter;
import lombok.Setter;
import lu.lamtco.timelink.domain.Role;

@Getter
@Setter
public class EmployeeDTO {
    private String name;
    private String surName;
    private String email;
    private String password;
    private Role role;
    private Double hourlyRate;
}
