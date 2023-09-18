package com.microservices.departmentservice.mapper;

import com.microservices.departmentservice.domain.Department;
import com.microservices.departmentservice.domain.dto.DepartmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department toDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO toDepartmentDTO(Department department);
}
