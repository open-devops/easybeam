package com.easybeam.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TenantRole entity.
 */
public class TenantRoleDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    private String description;

    private Set<TenantPolicyDTO> policies = new HashSet<>();

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

    public Set<TenantPolicyDTO> getPolicies() {
        return policies;
    }

    public void setPolicies(Set<TenantPolicyDTO> tenantPolicies) {
        this.policies = tenantPolicies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TenantRoleDTO tenantRoleDTO = (TenantRoleDTO) o;
        if(tenantRoleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tenantRoleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TenantRoleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
