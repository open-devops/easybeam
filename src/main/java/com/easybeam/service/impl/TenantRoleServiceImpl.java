package com.easybeam.service.impl;

import com.easybeam.service.TenantRoleService;
import com.easybeam.domain.TenantRole;
import com.easybeam.repository.TenantRoleRepository;
import com.easybeam.service.dto.TenantRoleDTO;
import com.easybeam.service.mapper.TenantRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TenantRole.
 */
@Service
@Transactional
public class TenantRoleServiceImpl implements TenantRoleService{

    private final Logger log = LoggerFactory.getLogger(TenantRoleServiceImpl.class);

    private final TenantRoleRepository tenantRoleRepository;

    private final TenantRoleMapper tenantRoleMapper;

    public TenantRoleServiceImpl(TenantRoleRepository tenantRoleRepository, TenantRoleMapper tenantRoleMapper) {
        this.tenantRoleRepository = tenantRoleRepository;
        this.tenantRoleMapper = tenantRoleMapper;
    }

    /**
     * Save a tenantRole.
     *
     * @param tenantRoleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TenantRoleDTO save(TenantRoleDTO tenantRoleDTO) {
        log.debug("Request to save TenantRole : {}", tenantRoleDTO);
        TenantRole tenantRole = tenantRoleMapper.toEntity(tenantRoleDTO);
        tenantRole = tenantRoleRepository.save(tenantRole);
        return tenantRoleMapper.toDto(tenantRole);
    }

    /**
     *  Get all the tenantRoles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TenantRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TenantRoles");
        return tenantRoleRepository.findAll(pageable)
            .map(tenantRoleMapper::toDto);
    }

    /**
     *  Get one tenantRole by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TenantRoleDTO findOne(Long id) {
        log.debug("Request to get TenantRole : {}", id);
        TenantRole tenantRole = tenantRoleRepository.findOneWithEagerRelationships(id);
        return tenantRoleMapper.toDto(tenantRole);
    }

    /**
     *  Delete the  tenantRole by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TenantRole : {}", id);
        tenantRoleRepository.delete(id);
    }
}
