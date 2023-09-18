package com.microservices.organizationservice.repository;

import com.microservices.organizationservice.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByOrganizationCode (String organizationCode);
}
