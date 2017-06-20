package com.easybeam.service;

import com.easybeam.service.dto.TenantPermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TenantPermission.
 */
public interface TenantPermissionService {

    /**
     * Save a tenantPermission.
     *
     * @param tenantPermissionDTO the entity to save
     * @return the persisted entity
     */
    TenantPermissionDTO save(TenantPermissionDTO tenantPermissionDTO);

    /**
     *  Get all the tenantPermissions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TenantPermissionDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tenantPermission.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TenantPermissionDTO findOne(Long id);

    /**
     *  Delete the "id" tenantPermission.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
