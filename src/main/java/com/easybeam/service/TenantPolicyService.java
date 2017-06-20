package com.easybeam.service;

import com.easybeam.service.dto.TenantPolicyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TenantPolicy.
 */
public interface TenantPolicyService {

    /**
     * Save a tenantPolicy.
     *
     * @param tenantPolicyDTO the entity to save
     * @return the persisted entity
     */
    TenantPolicyDTO save(TenantPolicyDTO tenantPolicyDTO);

    /**
     *  Get all the tenantPolicies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TenantPolicyDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tenantPolicy.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TenantPolicyDTO findOne(Long id);

    /**
     *  Delete the "id" tenantPolicy.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
