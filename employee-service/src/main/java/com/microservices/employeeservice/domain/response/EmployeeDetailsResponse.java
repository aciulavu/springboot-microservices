package com.microservices.employeeservice.domain.response;

import com.microservices.employeeservice.domain.dto.DepartmentDTO;
import com.microservices.employeeservice.domain.dto.EmployeeDTO;
import com.microservices.employeeservice.domain.dto.OrganizationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDetailsResponse {

    private EmployeeDTO employeeDTO;
    private DepartmentDTO departmentDTO;
    private OrganizationDTO organizationDTO;
}
