package com.microservices.departmentservice.service.impl;

import com.microservices.departmentservice.domain.Department;
import com.microservices.departmentservice.domain.dto.DepartmentDTO;
import com.microservices.departmentservice.exception.DepartmentCodeAlreadyExistsException;
import com.microservices.departmentservice.exception.ResourceNotFoundException;
import com.microservices.departmentservice.mapper.DepartmentMapper;
import com.microservices.departmentservice.repository.DepartmentRepository;
import com.microservices.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    public static final String DEPARTMENT = "Department";
    private DepartmentRepository departmentRepository;
    private DepartmentMapper departmentMapper;
    @Override
    public DepartmentDTO saveDepartmnet(DepartmentDTO departmentDTO) {
        Optional<Department> optionalDepartment = departmentRepository.findByDepartmentCode(departmentDTO.getDepartmentCode());
        if(optionalDepartment.isPresent()) {
            throw new DepartmentCodeAlreadyExistsException("Department code already used.");
        }
        Department department = departmentMapper.toDepartment(departmentDTO);
        log.debug("Created Department: {} with id:{}", department.getDepartmentName(), department.getId());
        return departmentMapper.toDepartmentDTO(departmentRepository.save(department));

    }

    @Override
    public DepartmentDTO getDepartmentByCode(String departmentCode) {
        Department department = departmentRepository.findByDepartmentCode(departmentCode)
                .orElseThrow(() -> new ResourceNotFoundException(DEPARTMENT, "code", departmentCode));
        log.debug("Found Department with code:{}", departmentCode);
        return departmentMapper.toDepartmentDTO(department);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        log.debug("Getting all departments...");
        return departments.stream()
                .map(department -> departmentMapper.toDepartmentDTO(department))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO) {
        log.debug("Checking for existing department with id:{}", departmentDTO.getId());
        Department existingDepartment = departmentRepository.findById(departmentDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(DEPARTMENT, "id", departmentDTO.getId()));
        if(departmentRepository.findByDepartmentCode(departmentDTO.getDepartmentCode()).isPresent()) {
            throw new DepartmentCodeAlreadyExistsException("Department code already used.");
        }
        existingDepartment.setDepartmentName(departmentDTO.getDepartmentName());
        existingDepartment.setDepartmentDescription(departmentDTO.getDepartmentDescription());
        existingDepartment.setDepartmentCode(departmentDTO.getDepartmentCode());
        log.debug("Department updated: {}", existingDepartment);
        return departmentMapper.toDepartmentDTO(departmentRepository.save(existingDepartment));
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        Department existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException(DEPARTMENT, "id", departmentId));
        log.debug("Department with id: {} deleted", existingDepartment.getId());
        departmentRepository.deleteById(existingDepartment.getId());
    }
}
