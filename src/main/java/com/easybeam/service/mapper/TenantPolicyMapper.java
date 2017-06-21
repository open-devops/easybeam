package com.easybeam.service.mapper;

import com.easybeam.domain.*;
import com.easybeam.service.dto.TenantPolicyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TenantPolicy and its DTO TenantPolicyDTO.
 */
@Mapper(componentModel = "spring", uses = {TenantPermissionMapper.class, })
public interface TenantPolicyMapper extends EntityMapper <TenantPolicyDTO, TenantPolicy> {
    
    @Mapping(target = "roles", ignore = true)
    TenantPolicy toEntity(TenantPolicyDTO tenantPolicyDTO); 
    default TenantPolicy fromId(Long id) {
        if (id == null) {
            return null;
        }
        TenantPolicy tenantPolicy = new TenantPolicy();
        tenantPolicy.setId(id);
        return tenantPolicy;
    }
}
