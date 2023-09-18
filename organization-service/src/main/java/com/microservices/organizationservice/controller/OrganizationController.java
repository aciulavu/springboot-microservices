package com.microservices.organizationservice.controller;

import com.microservices.organizationservice.domain.dto.OrganizationDTO;
import com.microservices.organizationservice.service.OrganizationService;
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
@Tag(name = "Organization Service - OrganizationController",
        description = "Organization Controller exposes REST APIs for Organization Service")
@RestController
@Slf4j
@RequestMapping("api/organizations/")
@AllArgsConstructor
public class OrganizationController {

    private OrganizationService organizationService;

    @Operation(
            summary = "Create Organization REST API",
            description = "Create Organization REST API is used to save an organization in a database."
    )
    @ApiResponse(
            responseCode = "201", description = "HTTP Status 201 CREATED"
    )
    @PostMapping()
    public ResponseEntity<OrganizationDTO> createOrganization(@Valid @RequestBody OrganizationDTO organizationDTO){
        log.debug("Received request POST /api/organizations with body {}", organizationDTO);
        return new ResponseEntity<>(organizationService.createOrganization(organizationDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Organization REST API",
            description = "Get Organization REST API is used to get an organization from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 404 Organization Not Found")}
    )
    @GetMapping("{organization-code}")
    public ResponseEntity<OrganizationDTO> getOrganizationByCode(@PathVariable("organization-code") String organizationCode){
        log.debug("Received request GET /api/organizations/{}", organizationCode);
        return new ResponseEntity<>(organizationService.getOrganzationByCode(organizationCode), HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Organizations REST API",
            description = "Get All Organizations REST API is used to retrieve all organizations from the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations(){
        log.debug("Received request GET /api/organizations/");
        List<OrganizationDTO> organizations = organizationService.getAllOrganizations();
        return new ResponseEntity<>(organizations, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Organization REST API",
            description = "Update Organization REST API is used to update an organization in the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 400 Organization Not Found")}
    )

    @PutMapping("{id}")
    public ResponseEntity<OrganizationDTO> updateOrganization(@PathVariable("id") Long organizationId,
                                                              @Valid @RequestBody OrganizationDTO organizationDTO){
        log.debug("Received request PUT /api/organizations/{} with body {}", organizationId, organizationDTO);
        organizationDTO.setId(organizationId);
        OrganizationDTO updatedOrg = organizationService.updateOrganization(organizationDTO);
        return new ResponseEntity<>(updatedOrg, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Organization REST API",
            description = "Delete Organization REST API is used to delete an organization from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 404 Organization Not Found")}
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrganization(@PathVariable("id") Long orgId){
        log.debug("Received request DELETE /api/organizations/{} ", orgId);
        organizationService.deleteOrganization(orgId);
        return new ResponseEntity<>("Organization successfully deleted!", HttpStatus.OK);
    }

}
