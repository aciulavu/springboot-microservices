package com.microservices.employeeservice.mapper;

import com.microservices.employeeservice.domain.Employee;
import com.microservices.employeeservice.domain.dto.EmployeeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO toEmployeeDTO(Employee employee);
}
