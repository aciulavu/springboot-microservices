package com.microservices.departmentservice.service;

import com.microservices.departmentservice.domain.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {

    DepartmentDTO saveDepartmnet(DepartmentDTO departmentDTO);
    DepartmentDTO getDepartmentByCode(String departmentCode);

    List<DepartmentDTO> getAllDepartments();

    DepartmentDTO updateDepartment(DepartmentDTO departmentDTO);

    void deleteDepartment(Long departmentId);
}
