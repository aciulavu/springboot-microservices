package com.microservices.departmentservice.repository;

import com.microservices.departmentservice.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentCode (String departmentCode);
}
