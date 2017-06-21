package com.easybeam.service.mapper;

import com.easybeam.domain.*;
import com.easybeam.service.dto.TenantPermissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TenantPermission and its DTO TenantPermissionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TenantPermissionMapper extends EntityMapper <TenantPermissionDTO, TenantPermission> {
    
    @Mapping(target = "policies", ignore = true)
    TenantPermission toEntity(TenantPermissionDTO tenantPermissionDTO); 
    default TenantPermission fromId(Long id) {
        if (id == null) {
            return null;
        }
        TenantPermission tenantPermission = new TenantPermission();
        tenantPermission.setId(id);
        return tenantPermission;
    }
}
