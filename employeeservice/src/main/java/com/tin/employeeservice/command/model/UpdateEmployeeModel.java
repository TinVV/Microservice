package com.tin.employeeservice.command.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeModel {
    private String id;
    @NotBlank(message = "First Name is mandatory.")
    private String firstName;
    @NotBlank(message = "Last Name is mandatory.")
    private String lastName;
    @NotBlank(message = "Kin is mandatory.")
    private String kin;
    @NotNull(message = "Discipline is mandatory.")
    private Boolean isDisciplined;
}
