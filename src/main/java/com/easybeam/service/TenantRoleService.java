package com.easybeam.service;

import com.easybeam.service.dto.TenantRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TenantRole.
 */
public interface TenantRoleService {

    /**
     * Save a tenantRole.
     *
     * @param tenantRoleDTO the entity to save
     * @return the persisted entity
     */
    TenantRoleDTO save(TenantRoleDTO tenantRoleDTO);

    /**
     *  Get all the tenantRoles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TenantRoleDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tenantRole.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TenantRoleDTO findOne(Long id);

    /**
     *  Delete the "id" tenantRole.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
