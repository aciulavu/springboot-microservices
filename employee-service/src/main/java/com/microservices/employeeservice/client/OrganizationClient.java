package com.microservices.employeeservice.client;

import com.microservices.employeeservice.domain.dto.OrganizationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ORGANIZATION-SERVICE")
public interface OrganizationClient {


    @GetMapping("/api/organizations/{organization-code}")
    OrganizationDTO getOrganizationByCode(@PathVariable("organization-code") String organizationCode);
}
