package com.easybeam.service.mapper;

import com.easybeam.domain.*;
import com.easybeam.service.dto.ProjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Project and its DTO ProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface ProjectMapper extends EntityMapper <ProjectDTO, Project> {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.login", target = "accountLogin")
    ProjectDTO toDto(Project project); 

    @Mapping(source = "accountId", target = "account")
    Project toEntity(ProjectDTO projectDTO); 
    default Project fromId(Long id) {
        if (id == null) {
            return null;
        }
        Project project = new Project();
        project.setId(id);
        return project;
    }
}
