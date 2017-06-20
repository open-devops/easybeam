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
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default TenantUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        TenantUser tenantUser = new TenantUser();
        tenantUser.setId(id);
        return tenantUser;
    }
}
