package com.microservices.organizationservice.service.impl;

import com.microservices.organizationservice.domain.Organization;
import com.microservices.organizationservice.domain.dto.OrganizationDTO;
import com.microservices.organizationservice.exception.ResourceNotFoundException;
import com.microservices.organizationservice.mapper.OrganizationMapper;
import com.microservices.organizationservice.repository.OrganizationRepository;
import com.microservices.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;
    private OrganizationMapper organizationMapper;
    @Override
    public OrganizationDTO createOrganization(OrganizationDTO organizationDTO) {
        Organization organization = organizationRepository.save(organizationMapper.toOrganization(organizationDTO));
        log.debug("Created Organization: {} with id:{}", organization.getOrganizationName(), organization.getId());
        return organizationMapper.toOrganizationDTO(organization);
    }

    @Override
    public OrganizationDTO getOrganzationByCode(String organizationCode) {
        Organization organization = organizationRepository.findByOrganizationCode(organizationCode)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "code", organizationCode));
        log.debug("Found Organization with code:{}", organizationCode);
        return organizationMapper.toOrganizationDTO(organization);
    }

    @Override
    public List<OrganizationDTO> getAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();
        log.debug("Getting all organizations...");
        return organizations.stream()
                .map(organization -> organizationMapper.toOrganizationDTO(organization))
                .collect(Collectors.toList());
    }

    @Override
    public OrganizationDTO updateOrganization(OrganizationDTO organizationDTO) {
        log.debug("Checking for existing organization with id {}.", organizationDTO.getId());
        Organization existingOrganization = organizationRepository.findById(organizationDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", organizationDTO.getId()));

        existingOrganization.setOrganizationName(organizationDTO.getOrganizationName());
        existingOrganization.setOrganizationDescription(organizationDTO.getOrganizationDescription());
        existingOrganization.setOrganizationCode(organizationDTO.getOrganizationCode());

        log.debug("Organization updated: {}", existingOrganization);
        return organizationMapper.toOrganizationDTO(organizationRepository.save(existingOrganization));
    }

    @Override
    public void deleteOrganization(Long orgId) {
        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", orgId));
        log.debug("Organization with id: {} deleted", organization.getId());
        organizationRepository.deleteById(organization.getId());
    }
}
