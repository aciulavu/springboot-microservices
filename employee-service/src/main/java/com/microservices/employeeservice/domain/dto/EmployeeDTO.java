package com.microservices.employeeservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    @Schema(
            description = "Employee First Name"
    )
    @NotBlank(message = "First name should not be null or empty.")
    private String firstName;
    @Schema(
            description = "Employee Last Name"
    )
    @NotBlank(message = "Last name should not be null or empty.")
    private String lastName;
    @Schema(
            description = "Employee Email"
    )
    @NotBlank(message = "Email should not be null or empty.")
    @Email(message = "Email address should be valid.")
    private String email;
    @Schema(
            description = "Department Code"
    )
    @NotBlank(message = "Department code should not be null or empty.")
    private String departmentCode;

    @Schema(
            description = "Organization Code"
    )
    private String organizationCode;

}
