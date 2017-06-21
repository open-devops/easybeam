package com.easybeam.service.mapper;

import com.easybeam.domain.*;
import com.easybeam.service.dto.TenantRoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TenantRole and its DTO TenantRoleDTO.
 */
@Mapper(componentModel = "spring", uses = {TenantPolicyMapper.class, })
public interface TenantRoleMapper extends EntityMapper <TenantRoleDTO, TenantRole> {
    
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "users", ignore = true)
    TenantRole toEntity(TenantRoleDTO tenantRoleDTO); 
    default TenantRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        TenantRole tenantRole = new TenantRole();
        tenantRole.setId(id);
        return tenantRole;
    }
}
