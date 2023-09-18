package com.microservices.organizationservice.mapper;

import com.microservices.organizationservice.domain.Organization;
import com.microservices.organizationservice.domain.dto.OrganizationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    Organization toOrganization(OrganizationDTO organizationDTO);

    OrganizationDTO toOrganizationDTO(Organization organization);
}
