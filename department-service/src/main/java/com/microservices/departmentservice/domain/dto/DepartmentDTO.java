package com.microservices.departmentservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Schema(
        description = "DepartmentDTO Model Information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDTO {
    private Long id;
    @Schema(
            description = "Department Name"
    )
    @NotBlank(message = "Department name should not be null or empty.")
    private String departmentName;
    @Schema(
            description = "Department Description"
    )
    @NotBlank(message = "Department description should not be null or empty.")
    private String departmentDescription;
    @Schema(
            description = "Department Code"
    )
    @NotBlank(message = "Department code should not be null or empty.")
    private String departmentCode;
}
