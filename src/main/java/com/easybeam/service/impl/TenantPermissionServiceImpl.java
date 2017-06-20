package com.easybeam.service.impl;

import com.easybeam.service.TenantPermissionService;
import com.easybeam.domain.TenantPermission;
import com.easybeam.repository.TenantPermissionRepository;
import com.easybeam.service.dto.TenantPermissionDTO;
import com.easybeam.service.mapper.TenantPermissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing TenantPermission.
 */
@Service
@Transactional
public class TenantPermissionServiceImpl implements TenantPermissionService{

    private final Logger log = LoggerFactory.getLogger(TenantPermissionServiceImpl.class);
    
    private final TenantPermissionRepository tenantPermissionRepository;

    private final TenantPermissionMapper tenantPermissionMapper;

    public TenantPermissionServiceImpl(TenantPermissionRepository tenantPermissionRepository, TenantPermissionMapper tenantPermissionMapper) {
        this.tenantPermissionRepository = tenantPermissionRepository;
        this.tenantPermissionMapper = tenantPermissionMapper;
    }

    /**
     * Save a tenantPermission.
     *
     * @param tenantPermissionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TenantPermissionDTO save(TenantPermissionDTO tenantPermissionDTO) {
        log.debug("Request to save TenantPermission : {}", tenantPermissionDTO);
        TenantPermission tenantPermission = tenantPermissionMapper.toEntity(tenantPermissionDTO);
        tenantPermission = tenantPermissionRepository.save(tenantPermission);
        TenantPermissionDTO result = tenantPermissionMapper.toDto(tenantPermission);
        return result;
    }

    /**
     *  Get all the tenantPermissions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TenantPermissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TenantPermissions");
        Page<TenantPermission> result = tenantPermissionRepository.findAll(pageable);
        return result.map(tenantPermission -> tenantPermissionMapper.toDto(tenantPermission));
    }

    /**
     *  Get one tenantPermission by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TenantPermissionDTO findOne(Long id) {
        log.debug("Request to get TenantPermission : {}", id);
        TenantPermission tenantPermission = tenantPermissionRepository.findOne(id);
        TenantPermissionDTO tenantPermissionDTO = tenantPermissionMapper.toDto(tenantPermission);
        return tenantPermissionDTO;
    }

    /**
     *  Delete the  tenantPermission by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TenantPermission : {}", id);
        tenantPermissionRepository.delete(id);
    }
}
