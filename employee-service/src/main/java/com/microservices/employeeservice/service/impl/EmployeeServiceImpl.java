package com.microservices.employeeservice.service.impl;

import com.microservices.employeeservice.client.DepartmentClient;
import com.microservices.employeeservice.client.OrganizationClient;
import com.microservices.employeeservice.domain.Employee;
import com.microservices.employeeservice.domain.dto.DepartmentDTO;
import com.microservices.employeeservice.domain.dto.EmployeeDTO;
import com.microservices.employeeservice.domain.dto.OrganizationDTO;
import com.microservices.employeeservice.domain.response.EmployeeDetailsResponse;
import com.microservices.employeeservice.exception.ResourceNotFoundException;
import com.microservices.employeeservice.mapper.EmployeeMapper;
import com.microservices.employeeservice.repository.EmployeeRepository;
import com.microservices.employeeservice.service.EmployeeService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    public static final String EMPLOYEE = "Employee";
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;

    private DepartmentClient departmentClient;
    private OrganizationClient organizationClient;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        DepartmentDTO departmentDTO = departmentClient.getDepartment(employeeDTO.getDepartmentCode());
        employeeDTO.setDepartmentCode(departmentDTO.getDepartmentCode());

        Employee employee = employeeRepository.save(employeeMapper.toEmployee(employeeDTO));
        log.debug("Created Employee: {}, {} with id:{}", employee.getFirstName(), employee.getLastName(), employee.getId());
        return employeeMapper.toEmployeeDTO(employeeRepository.save(employee));
    }

    @Override
    //@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    //@Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    public EmployeeDetailsResponse getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, "id", employeeId));
        log.debug("Found Employee with id:{}", employeeId);

        DepartmentDTO departmentDTO = departmentClient.getDepartment(employee.getDepartmentCode());
        OrganizationDTO organizationDTO = organizationClient.getOrganizationByCode(employee.getOrganizationCode());

        return EmployeeDetailsResponse.builder()
                .departmentDTO(departmentDTO)
                .employeeDTO(employeeMapper.toEmployeeDTO(employee))
                .organizationDTO(organizationDTO)
                .build();
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        log.debug("Getting all employees...");
        return employees.stream()
                .map(employee -> employeeMapper.toEmployeeDTO(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        log.debug("Checking for existing employee with id {}.", employeeDTO.getId());
        Employee existingEmployee = employeeRepository.findById(employeeDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, "id", employeeDTO.getId()));

        existingEmployee.setFirstName(employeeDTO.getFirstName());
        existingEmployee.setLastName(employeeDTO.getLastName());
        existingEmployee.setEmail(employeeDTO.getEmail());
        DepartmentDTO departmentDTO = departmentClient.getDepartment(employeeDTO.getDepartmentCode());
        existingEmployee.setDepartmentCode(departmentDTO.getDepartmentCode());
        log.debug("Employee updated: {}", existingEmployee);
        return employeeMapper.toEmployeeDTO(employeeRepository.save(existingEmployee));
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, "id", employeeId));
        log.debug("Employee with id: {} deleted", existingEmployee.getId());
        employeeRepository.deleteById(existingEmployee.getId());
    }

    public EmployeeDetailsResponse getDefaultDepartment(Long employeeId, Exception exception){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, "id", employeeId));
        log.debug("Found Employee with id:{}", employeeId);

        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .departmentName("Default Name")
                .departmentCode("Default code")
                .departmentDescription("Default description")
                .build();
        log.debug("Build default Department");
        return EmployeeDetailsResponse.builder()
                .departmentDTO(departmentDTO)
                .employeeDTO(employeeMapper.toEmployeeDTO(employee))
                .build();
    }
}
