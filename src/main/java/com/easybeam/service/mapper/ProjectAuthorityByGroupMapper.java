package com.easybeam.service.mapper;

import com.easybeam.domain.*;
import com.easybeam.service.dto.ProjectAuthorityByGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProjectAuthorityByGroup and its DTO ProjectAuthorityByGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {TenantGroupMapper.class, TenantRoleMapper.class, ProjectMapper.class, })
public interface ProjectAuthorityByGroupMapper extends EntityMapper <ProjectAuthorityByGroupDTO, ProjectAuthorityByGroup> {

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")

    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    ProjectAuthorityByGroupDTO toDto(ProjectAuthorityByGroup projectAuthorityByGroup); 

    @Mapping(source = "groupId", target = "group")

    @Mapping(source = "roleId", target = "role")

    @Mapping(source = "projectId", target = "project")
    ProjectAuthorityByGroup toEntity(ProjectAuthorityByGroupDTO projectAuthorityByGroupDTO); 
    default ProjectAuthorityByGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProjectAuthorityByGroup projectAuthorityByGroup = new ProjectAuthorityByGroup();
        projectAuthorityByGroup.setId(id);
        return projectAuthorityByGroup;
    }
}
