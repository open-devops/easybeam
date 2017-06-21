package com.easybeam.service;

import com.easybeam.service.dto.ProjectAuthorityByUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ProjectAuthorityByUser.
 */
public interface ProjectAuthorityByUserService {

    /**
     * Save a projectAuthorityByUser.
     *
     * @param projectAuthorityByUserDTO the entity to save
     * @return the persisted entity
     */
    ProjectAuthorityByUserDTO save(ProjectAuthorityByUserDTO projectAuthorityByUserDTO);

    /**
     *  Get all the projectAuthorityByUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProjectAuthorityByUserDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" projectAuthorityByUser.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProjectAuthorityByUserDTO findOne(Long id);

    /**
     *  Delete the "id" projectAuthorityByUser.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
