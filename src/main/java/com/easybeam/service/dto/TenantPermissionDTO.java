package com.easybeam.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.easybeam.domain.enumeration.PlatformService;
import com.easybeam.domain.enumeration.ServiceAccessLevel;

/**
 * A DTO for the TenantPermission entity.
 */
public class TenantPermissionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    private PlatformService serviceName;

    @NotNull
    private ServiceAccessLevel serviceAccessLevel;

    private String description;

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

    public PlatformService getServiceName() {
        return serviceName;
    }

    public void setServiceName(PlatformService serviceName) {
        this.serviceName = serviceName;
    }

    public ServiceAccessLevel getServiceAccessLevel() {
        return serviceAccessLevel;
    }

    public void setServiceAccessLevel(ServiceAccessLevel serviceAccessLevel) {
        this.serviceAccessLevel = serviceAccessLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TenantPermissionDTO tenantPermissionDTO = (TenantPermissionDTO) o;
        if(tenantPermissionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tenantPermissionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TenantPermissionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", serviceAccessLevel='" + getServiceAccessLevel() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
