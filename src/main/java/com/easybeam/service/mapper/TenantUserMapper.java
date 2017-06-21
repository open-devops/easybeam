package com.easybeam.service.mapper;

import com.easybeam.domain.*;
import com.easybeam.service.dto.TenantUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TenantUser and its DTO TenantUserDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TenantGroupMapper.class, TenantRoleMapper.class, })
public interface TenantUserMapper extends EntityMapper <TenantUserDTO, TenantUser> {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.login", target = "accountLogin")
    TenantUserDTO toDto(TenantUser tenantUser); 

    @Mapping(source = "accountId", target = "account")
    TenantUser toEntity(TenantUserDTO tenantUserDTO); 
    default TenantUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        TenantUser tenantUser = new TenantUser();
        tenantUser.setId(id);
        return tenantUser;
    }
}
