package com.easybeam.service;

import com.easybeam.service.dto.ProjectAuthorityByGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ProjectAuthorityByGroup.
 */
public interface ProjectAuthorityByGroupService {

    /**
     * Save a projectAuthorityByGroup.
     *
     * @param projectAuthorityByGroupDTO the entity to save
     * @return the persisted entity
     */
    ProjectAuthorityByGroupDTO save(ProjectAuthorityByGroupDTO projectAuthorityByGroupDTO);

    /**
     *  Get all the projectAuthorityByGroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProjectAuthorityByGroupDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" projectAuthorityByGroup.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProjectAuthorityByGroupDTO findOne(Long id);

    /**
     *  Delete the "id" projectAuthorityByGroup.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
