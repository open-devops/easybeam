package com.easybeam.service.impl;

import com.easybeam.service.TenantPolicyService;
import com.easybeam.domain.TenantPolicy;
import com.easybeam.repository.TenantPolicyRepository;
import com.easybeam.service.dto.TenantPolicyDTO;
import com.easybeam.service.mapper.TenantPolicyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing TenantPolicy.
 */
@Service
@Transactional
public class TenantPolicyServiceImpl implements TenantPolicyService{

    private final Logger log = LoggerFactory.getLogger(TenantPolicyServiceImpl.class);
    
    private final TenantPolicyRepository tenantPolicyRepository;

    private final TenantPolicyMapper tenantPolicyMapper;

    public TenantPolicyServiceImpl(TenantPolicyRepository tenantPolicyRepository, TenantPolicyMapper tenantPolicyMapper) {
        this.tenantPolicyRepository = tenantPolicyRepository;
        this.tenantPolicyMapper = tenantPolicyMapper;
    }

    /**
     * Save a tenantPolicy.
     *
     * @param tenantPolicyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TenantPolicyDTO save(TenantPolicyDTO tenantPolicyDTO) {
        log.debug("Request to save TenantPolicy : {}", tenantPolicyDTO);
        TenantPolicy tenantPolicy = tenantPolicyMapper.toEntity(tenantPolicyDTO);
        tenantPolicy = tenantPolicyRepository.save(tenantPolicy);
        TenantPolicyDTO result = tenantPolicyMapper.toDto(tenantPolicy);
        return result;
    }

    /**
     *  Get all the tenantPolicies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TenantPolicyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TenantPolicies");
        Page<TenantPolicy> result = tenantPolicyRepository.findAll(pageable);
        return result.map(tenantPolicy -> tenantPolicyMapper.toDto(tenantPolicy));
    }

    /**
     *  Get one tenantPolicy by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TenantPolicyDTO findOne(Long id) {
        log.debug("Request to get TenantPolicy : {}", id);
        TenantPolicy tenantPolicy = tenantPolicyRepository.findOneWithEagerRelationships(id);
        TenantPolicyDTO tenantPolicyDTO = tenantPolicyMapper.toDto(tenantPolicy);
        return tenantPolicyDTO;
    }

    /**
     *  Delete the  tenantPolicy by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TenantPolicy : {}", id);
        tenantPolicyRepository.delete(id);
    }
}
