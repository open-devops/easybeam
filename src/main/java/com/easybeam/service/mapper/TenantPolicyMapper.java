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
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default TenantPolicy fromId(Long id) {
        if (id == null) {
            return null;
        }
        TenantPolicy tenantPolicy = new TenantPolicy();
        tenantPolicy.setId(id);
        return tenantPolicy;
    }
}
