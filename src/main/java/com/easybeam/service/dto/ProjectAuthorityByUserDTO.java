package com.easybeam.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ProjectAuthorityByUser entity.
 */
public class ProjectAuthorityByUserDTO implements Serializable {

    private Long id;

    private String description;

    private Long userId;

    private String userLogin;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long tenantUserId) {
        this.userId = tenantUserId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String tenantUserLogin) {
        this.userLogin = tenantUserLogin;
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

        ProjectAuthorityByUserDTO projectAuthorityByUserDTO = (ProjectAuthorityByUserDTO) o;
        if(projectAuthorityByUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectAuthorityByUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectAuthorityByUserDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
