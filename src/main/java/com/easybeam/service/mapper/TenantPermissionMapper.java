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
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default TenantPermission fromId(Long id) {
        if (id == null) {
            return null;
        }
        TenantPermission tenantPermission = new TenantPermission();
        tenantPermission.setId(id);
        return tenantPermission;
    }
}
