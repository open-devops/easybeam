package com.easybeam.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.easybeam.domain.enumeration.PlatformService;

import com.easybeam.domain.enumeration.ServiceAccessLevel;

/**
 * A TenantPermission.
 */
@Entity
@Table(name = "tenant_permission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TenantPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "service_name", nullable = false)
    private PlatformService serviceName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "service_access_level", nullable = false)
    private ServiceAccessLevel serviceAccessLevel;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TenantPolicy> policies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TenantPermission name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlatformService getServiceName() {
        return serviceName;
    }

    public TenantPermission serviceName(PlatformService serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public void setServiceName(PlatformService serviceName) {
        this.serviceName = serviceName;
    }

    public ServiceAccessLevel getServiceAccessLevel() {
        return serviceAccessLevel;
    }

    public TenantPermission serviceAccessLevel(ServiceAccessLevel serviceAccessLevel) {
        this.serviceAccessLevel = serviceAccessLevel;
        return this;
    }

    public void setServiceAccessLevel(ServiceAccessLevel serviceAccessLevel) {
        this.serviceAccessLevel = serviceAccessLevel;
    }

    public String getDescription() {
        return description;
    }

    public TenantPermission description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TenantPolicy> getPolicies() {
        return policies;
    }

    public TenantPermission policies(Set<TenantPolicy> tenantPolicies) {
        this.policies = tenantPolicies;
        return this;
    }

    public TenantPermission addPolicies(TenantPolicy tenantPolicy) {
        this.policies.add(tenantPolicy);
        tenantPolicy.getPermissions().add(this);
        return this;
    }

    public TenantPermission removePolicies(TenantPolicy tenantPolicy) {
        this.policies.remove(tenantPolicy);
        tenantPolicy.getPermissions().remove(this);
        return this;
    }

    public void setPolicies(Set<TenantPolicy> tenantPolicies) {
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
        TenantPermission tenantPermission = (TenantPermission) o;
        if (tenantPermission.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tenantPermission.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TenantPermission{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", serviceAccessLevel='" + getServiceAccessLevel() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
