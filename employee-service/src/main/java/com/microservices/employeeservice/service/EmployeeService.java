package com.microservices.employeeservice.service;

import com.microservices.employeeservice.domain.dto.EmployeeDTO;
import com.microservices.employeeservice.domain.response.EmployeeDetailsResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDetailsResponse getEmployeeById(Long employeeId);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);

    void deleteEmployee(Long employeeId);
}
