package com.easybeam.service;

import com.easybeam.service.dto.TenantGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TenantGroup.
 */
public interface TenantGroupService {

    /**
     * Save a tenantGroup.
     *
     * @param tenantGroupDTO the entity to save
     * @return the persisted entity
     */
    TenantGroupDTO save(TenantGroupDTO tenantGroupDTO);

    /**
     *  Get all the tenantGroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TenantGroupDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tenantGroup.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TenantGroupDTO findOne(Long id);

    /**
     *  Delete the "id" tenantGroup.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
