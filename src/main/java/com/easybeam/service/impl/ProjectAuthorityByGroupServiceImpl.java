package com.easybeam.service.impl;

import com.easybeam.service.ProjectAuthorityByGroupService;
import com.easybeam.domain.ProjectAuthorityByGroup;
import com.easybeam.repository.ProjectAuthorityByGroupRepository;
import com.easybeam.service.dto.ProjectAuthorityByGroupDTO;
import com.easybeam.service.mapper.ProjectAuthorityByGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ProjectAuthorityByGroup.
 */
@Service
@Transactional
public class ProjectAuthorityByGroupServiceImpl implements ProjectAuthorityByGroupService{

    private final Logger log = LoggerFactory.getLogger(ProjectAuthorityByGroupServiceImpl.class);

    private final ProjectAuthorityByGroupRepository projectAuthorityByGroupRepository;

    private final ProjectAuthorityByGroupMapper projectAuthorityByGroupMapper;

    public ProjectAuthorityByGroupServiceImpl(ProjectAuthorityByGroupRepository projectAuthorityByGroupRepository, ProjectAuthorityByGroupMapper projectAuthorityByGroupMapper) {
        this.projectAuthorityByGroupRepository = projectAuthorityByGroupRepository;
        this.projectAuthorityByGroupMapper = projectAuthorityByGroupMapper;
    }

    /**
     * Save a projectAuthorityByGroup.
     *
     * @param projectAuthorityByGroupDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProjectAuthorityByGroupDTO save(ProjectAuthorityByGroupDTO projectAuthorityByGroupDTO) {
        log.debug("Request to save ProjectAuthorityByGroup : {}", projectAuthorityByGroupDTO);
        ProjectAuthorityByGroup projectAuthorityByGroup = projectAuthorityByGroupMapper.toEntity(projectAuthorityByGroupDTO);
        projectAuthorityByGroup = projectAuthorityByGroupRepository.save(projectAuthorityByGroup);
        return projectAuthorityByGroupMapper.toDto(projectAuthorityByGroup);
    }

    /**
     *  Get all the projectAuthorityByGroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProjectAuthorityByGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectAuthorityByGroups");
        return projectAuthorityByGroupRepository.findAll(pageable)
            .map(projectAuthorityByGroupMapper::toDto);
    }

    /**
     *  Get one projectAuthorityByGroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProjectAuthorityByGroupDTO findOne(Long id) {
        log.debug("Request to get ProjectAuthorityByGroup : {}", id);
        ProjectAuthorityByGroup projectAuthorityByGroup = projectAuthorityByGroupRepository.findOne(id);
        return projectAuthorityByGroupMapper.toDto(projectAuthorityByGroup);
    }

    /**
     *  Delete the  projectAuthorityByGroup by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectAuthorityByGroup : {}", id);
        projectAuthorityByGroupRepository.delete(id);
    }
}
