package com.microservices.organizationservice.domain.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Schema(
        description = "OrganizationDTO Model Information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationDTO {

    private Long id;
    @Schema(
            description = "Organization Name"
    )
    @NotBlank(message = "Organization name should not be null or empty.")
    private String organizationName;
    @Schema(
            description = "Organization Description"
    )
    @NotBlank(message = "Organization description should not be null or empty.")
    private String organizationDescription;
    @Schema(
            description = "Organization Code"
    )
    @NotBlank(message = "Organization code should not be null or empty.")
    private String organizationCode;
    @Schema(
            description = "Organization Creation Date"
    )
    private LocalDateTime createdDate;
}
