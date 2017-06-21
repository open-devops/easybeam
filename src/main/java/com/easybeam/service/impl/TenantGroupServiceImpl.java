package com.easybeam.service.impl;

import com.easybeam.service.TenantGroupService;
import com.easybeam.domain.TenantGroup;
import com.easybeam.repository.TenantGroupRepository;
import com.easybeam.service.dto.TenantGroupDTO;
import com.easybeam.service.mapper.TenantGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TenantGroup.
 */
@Service
@Transactional
public class TenantGroupServiceImpl implements TenantGroupService{

    private final Logger log = LoggerFactory.getLogger(TenantGroupServiceImpl.class);

    private final TenantGroupRepository tenantGroupRepository;

    private final TenantGroupMapper tenantGroupMapper;

    public TenantGroupServiceImpl(TenantGroupRepository tenantGroupRepository, TenantGroupMapper tenantGroupMapper) {
        this.tenantGroupRepository = tenantGroupRepository;
        this.tenantGroupMapper = tenantGroupMapper;
    }

    /**
     * Save a tenantGroup.
     *
     * @param tenantGroupDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TenantGroupDTO save(TenantGroupDTO tenantGroupDTO) {
        log.debug("Request to save TenantGroup : {}", tenantGroupDTO);
        TenantGroup tenantGroup = tenantGroupMapper.toEntity(tenantGroupDTO);
        tenantGroup = tenantGroupRepository.save(tenantGroup);
        return tenantGroupMapper.toDto(tenantGroup);
    }

    /**
     *  Get all the tenantGroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TenantGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TenantGroups");
        return tenantGroupRepository.findAll(pageable)
            .map(tenantGroupMapper::toDto);
    }

    /**
     *  Get one tenantGroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TenantGroupDTO findOne(Long id) {
        log.debug("Request to get TenantGroup : {}", id);
        TenantGroup tenantGroup = tenantGroupRepository.findOneWithEagerRelationships(id);
        return tenantGroupMapper.toDto(tenantGroup);
    }

    /**
     *  Delete the  tenantGroup by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TenantGroup : {}", id);
        tenantGroupRepository.delete(id);
    }
}
