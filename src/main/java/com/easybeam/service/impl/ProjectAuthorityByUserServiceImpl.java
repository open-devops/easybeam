package com.easybeam.service.impl;

import com.easybeam.service.ProjectAuthorityByUserService;
import com.easybeam.domain.ProjectAuthorityByUser;
import com.easybeam.repository.ProjectAuthorityByUserRepository;
import com.easybeam.service.dto.ProjectAuthorityByUserDTO;
import com.easybeam.service.mapper.ProjectAuthorityByUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ProjectAuthorityByUser.
 */
@Service
@Transactional
public class ProjectAuthorityByUserServiceImpl implements ProjectAuthorityByUserService{

    private final Logger log = LoggerFactory.getLogger(ProjectAuthorityByUserServiceImpl.class);

    private final ProjectAuthorityByUserRepository projectAuthorityByUserRepository;

    private final ProjectAuthorityByUserMapper projectAuthorityByUserMapper;

    public ProjectAuthorityByUserServiceImpl(ProjectAuthorityByUserRepository projectAuthorityByUserRepository, ProjectAuthorityByUserMapper projectAuthorityByUserMapper) {
        this.projectAuthorityByUserRepository = projectAuthorityByUserRepository;
        this.projectAuthorityByUserMapper = projectAuthorityByUserMapper;
    }

    /**
     * Save a projectAuthorityByUser.
     *
     * @param projectAuthorityByUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProjectAuthorityByUserDTO save(ProjectAuthorityByUserDTO projectAuthorityByUserDTO) {
        log.debug("Request to save ProjectAuthorityByUser : {}", projectAuthorityByUserDTO);
        ProjectAuthorityByUser projectAuthorityByUser = projectAuthorityByUserMapper.toEntity(projectAuthorityByUserDTO);
        projectAuthorityByUser = projectAuthorityByUserRepository.save(projectAuthorityByUser);
        return projectAuthorityByUserMapper.toDto(projectAuthorityByUser);
    }

    /**
     *  Get all the projectAuthorityByUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProjectAuthorityByUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectAuthorityByUsers");
        return projectAuthorityByUserRepository.findAll(pageable)
            .map(projectAuthorityByUserMapper::toDto);
    }

    /**
     *  Get one projectAuthorityByUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProjectAuthorityByUserDTO findOne(Long id) {
        log.debug("Request to get ProjectAuthorityByUser : {}", id);
        ProjectAuthorityByUser projectAuthorityByUser = projectAuthorityByUserRepository.findOne(id);
        return projectAuthorityByUserMapper.toDto(projectAuthorityByUser);
    }

    /**
     *  Delete the  projectAuthorityByUser by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectAuthorityByUser : {}", id);
        projectAuthorityByUserRepository.delete(id);
    }
}
