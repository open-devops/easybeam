package com.easybeam.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.easybeam.domain.enumeration.PolicyType;

/**
 * A DTO for the TenantPolicy entity.
 */
public class TenantPolicyDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    private PolicyType type;

    private String description;

    private Set<TenantPermissionDTO> permissions = new HashSet<>();

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

    public PolicyType getType() {
        return type;
    }

    public void setType(PolicyType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TenantPermissionDTO> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<TenantPermissionDTO> tenantPermissions) {
        this.permissions = tenantPermissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TenantPolicyDTO tenantPolicyDTO = (TenantPolicyDTO) o;
        if(tenantPolicyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tenantPolicyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TenantPolicyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
