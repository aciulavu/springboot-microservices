package com.microservices.organizationservice.service;

import com.microservices.organizationservice.domain.dto.OrganizationDTO;

import java.util.List;

public interface OrganizationService {

    OrganizationDTO createOrganization(OrganizationDTO organizationDTO);

    OrganizationDTO getOrganzationByCode(String organizationCode);

    List<OrganizationDTO> getAllOrganizations();

    OrganizationDTO updateOrganization(OrganizationDTO organizationDTO);

    void deleteOrganization(Long orgId);
}
