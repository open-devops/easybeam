package com.easybeam.service.mapper;

import com.easybeam.domain.*;
import com.easybeam.service.dto.ProjectAuthorityByUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProjectAuthorityByUser and its DTO ProjectAuthorityByUserDTO.
 */
@Mapper(componentModel = "spring", uses = {TenantUserMapper.class, TenantRoleMapper.class, ProjectMapper.class, })
public interface ProjectAuthorityByUserMapper extends EntityMapper <ProjectAuthorityByUserDTO, ProjectAuthorityByUser> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")

    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    ProjectAuthorityByUserDTO toDto(ProjectAuthorityByUser projectAuthorityByUser); 

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "roleId", target = "role")

    @Mapping(source = "projectId", target = "project")
    ProjectAuthorityByUser toEntity(ProjectAuthorityByUserDTO projectAuthorityByUserDTO); 
    default ProjectAuthorityByUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProjectAuthorityByUser projectAuthorityByUser = new ProjectAuthorityByUser();
        projectAuthorityByUser.setId(id);
        return projectAuthorityByUser;
    }
}
