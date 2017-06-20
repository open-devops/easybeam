package com.easybeam.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ProjectAuthorityByGroup entity.
 */
public class ProjectAuthorityByGroupDTO implements Serializable {

    private Long id;

    private String description;

    private Long groupId;

    private String groupName;

    private Long roleId;

    private String roleName;

    private Long projectId;

    private String projectName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long tenantGroupId) {
        this.groupId = tenantGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String tenantGroupName) {
        this.groupName = tenantGroupName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long tenantRoleId) {
        this.roleId = tenantRoleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String tenantRoleName) {
        this.roleName = tenantRoleName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectAuthorityByGroupDTO projectAuthorityByGroupDTO = (ProjectAuthorityByGroupDTO) o;
        if(projectAuthorityByGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectAuthorityByGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectAuthorityByGroupDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
