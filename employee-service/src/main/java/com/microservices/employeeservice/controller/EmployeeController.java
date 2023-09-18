package com.microservices.employeeservice.controller;

import com.microservices.employeeservice.domain.dto.EmployeeDTO;
import com.microservices.employeeservice.domain.response.EmployeeDetailsResponse;
import com.microservices.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employee Service - EmployeeController",
        description = "Employee Controller exposes REST APIs for Employee Service")
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("api/employees/")
public class EmployeeController {

    private EmployeeService employeeService;

    @Operation(
            summary = "Create Employee REST API",
            description = "Create Employee REST API is used to save employee in a database."
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        log.debug("Received request POST /api/employees with body {}", employeeDTO);
        return new ResponseEntity<>(employeeService.createEmployee(employeeDTO), HttpStatus.CREATED);

    }

    @Operation(
            summary = "Get Employee REST API",
            description = "Get Employee REST API is used to get an employee from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 404 Employee Not Found")}
    )
    @GetMapping("{employee-id}")
    public ResponseEntity<EmployeeDetailsResponse> getEmployeeById(@PathVariable("employee-id") Long employeeId) {
        log.debug("Received request GET /api/employees/{}", employeeId);
        return new ResponseEntity<>(employeeService.getEmployeeById(employeeId), HttpStatus.OK);
    }


    @Operation(
            summary = "Get All Employees REST API",
            description = "Get All Employees REST API is used to retrieve all Employees from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        log.debug("Received request GET /api/employees/");
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Employee REST API",
            description = "Update Employee REST API is used to update an employee in the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 400 Employee Not Found")}
    )
    @PutMapping("{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable("id") Long employeeId,
                                                          @RequestBody EmployeeDTO employeeDTO){
        log.debug("Received request PUT /api/employees/{} with body {}", employeeId, employeeDTO);
        employeeDTO.setId(employeeId);
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(employeeDTO);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }


    @Operation(
            summary = "Delete Employee REST API",
            description = "Delete Employee REST API is used to delete an employee from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 404 Employee Not Found")}
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId){
        log.debug("Received request DELETE /api/employees/{} ", employeeId);
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>("Employee successfully deleted!", HttpStatus.OK);
    }

}
