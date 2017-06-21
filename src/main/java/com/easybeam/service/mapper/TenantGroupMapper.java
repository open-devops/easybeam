package com.easybeam.service.mapper;

import com.easybeam.domain.*;
import com.easybeam.service.dto.TenantGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TenantGroup and its DTO TenantGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TenantRoleMapper.class, })
public interface TenantGroupMapper extends EntityMapper <TenantGroupDTO, TenantGroup> {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.login", target = "accountLogin")
    TenantGroupDTO toDto(TenantGroup tenantGroup); 

    @Mapping(source = "accountId", target = "account")
    @Mapping(target = "users", ignore = true)
    TenantGroup toEntity(TenantGroupDTO tenantGroupDTO); 
    default TenantGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        TenantGroup tenantGroup = new TenantGroup();
        tenantGroup.setId(id);
        return tenantGroup;
    }
}
