package com.microservices.departmentservice.controller;

import com.microservices.departmentservice.domain.dto.DepartmentDTO;
import com.microservices.departmentservice.service.DepartmentService;
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

@RestController
@Tag(name = "Department Service - DepartmentController",
        description = "Department Controller exposes REST APIs for Department Service")
@Slf4j
@RequestMapping("api/departments/")
@AllArgsConstructor
public class DepartmentController {
    private DepartmentService departmentService;

    @Operation(
            summary = "Create Department REST API",
            description = "Create Department REST API is used to save department in a database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "HTTP Status 201 CREATED"),
            @ApiResponse(responseCode = "400", description = "HTTP Status 400 Department Code Already Exists")}
    )
    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        log.debug("Received request POST /api/departments/ with body {}", departmentDTO);
        return new ResponseEntity<>(departmentService.saveDepartmnet(departmentDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Department REST API",
            description = "Get Department REST API is used to get a department from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 404 Department Not Found")}
    )
    @GetMapping("{department-code}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable ("department-code") String departmentCode) {
        log.debug("Received request GET /api/departments/{} ", departmentCode);
        return new ResponseEntity<>(departmentService.getDepartmentByCode(departmentCode), HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Departments REST API",
            description = "Get All Departments REST API is used to retrieve all departments from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(){
        log.debug("Received request GET /api/departments/");
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Department REST API",
            description = "Update Department REST API is used to update a department in the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
            @ApiResponse(responseCode = "400", description = "HTTP Status 400 Department Code Already Exists"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 400 Department Not Found")}
    )
    @PutMapping("{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable("id") Long departmentId,
    @Valid @RequestBody DepartmentDTO departmentDTO){
        log.debug("Received request PUT /api/departments/{} with body {}", departmentId, departmentDTO);
        departmentDTO.setId(departmentId);
        DepartmentDTO updatedDepartment = departmentService.updateDepartment(departmentDTO);
        return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
    }


    @Operation(
            summary = "Delete Department REST API",
            description = "Delete Department REST API is used to delete a department from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 404 Department Not Found")}
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") Long departmentId){
        log.debug("Received request DELETE /api/departments/{}", departmentId);
        departmentService.deleteDepartment(departmentId);
        return new ResponseEntity<>("Department successfully deleted!", HttpStatus.OK);
    }
}
