package com.easybeam.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TenantGroup entity.
 */
public class TenantGroupDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    private String description;

    private Long accountId;

    private String accountLogin;

    private Set<TenantRoleDTO> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long userId) {
        this.accountId = userId;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String userLogin) {
        this.accountLogin = userLogin;
    }

    public Set<TenantRoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<TenantRoleDTO> tenantRoles) {
        this.roles = tenantRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TenantGroupDTO tenantGroupDTO = (TenantGroupDTO) o;
        if(tenantGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tenantGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TenantGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
