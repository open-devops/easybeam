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
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default TenantRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        TenantRole tenantRole = new TenantRole();
        tenantRole.setId(id);
        return tenantRole;
    }
}
