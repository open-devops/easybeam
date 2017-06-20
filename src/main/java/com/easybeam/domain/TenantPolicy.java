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

import com.easybeam.domain.enumeration.PolicyType;

/**
 * A TenantPolicy.
 */
@Entity
@Table(name = "tenant_policy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TenantPolicy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private PolicyType type;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tenant_policy_permissions",
               joinColumns = @JoinColumn(name="tenant_policies_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="permissions_id", referencedColumnName="id"))
    private Set<TenantPermission> permissions = new HashSet<>();

    @ManyToMany(mappedBy = "policies")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TenantRole> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TenantPolicy name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PolicyType getType() {
        return type;
    }

    public TenantPolicy type(PolicyType type) {
        this.type = type;
        return this;
    }

    public void setType(PolicyType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public TenantPolicy description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TenantPermission> getPermissions() {
        return permissions;
    }

    public TenantPolicy permissions(Set<TenantPermission> tenantPermissions) {
        this.permissions = tenantPermissions;
        return this;
    }

    public TenantPolicy addPermissions(TenantPermission tenantPermission) {
        this.permissions.add(tenantPermission);
        tenantPermission.getPolicies().add(this);
        return this;
    }

    public TenantPolicy removePermissions(TenantPermission tenantPermission) {
        this.permissions.remove(tenantPermission);
        tenantPermission.getPolicies().remove(this);
        return this;
    }

    public void setPermissions(Set<TenantPermission> tenantPermissions) {
        this.permissions = tenantPermissions;
    }

    public Set<TenantRole> getRoles() {
        return roles;
    }

    public TenantPolicy roles(Set<TenantRole> tenantRoles) {
        this.roles = tenantRoles;
        return this;
    }

    public TenantPolicy addRoles(TenantRole tenantRole) {
        this.roles.add(tenantRole);
        tenantRole.getPolicies().add(this);
        return this;
    }

    public TenantPolicy removeRoles(TenantRole tenantRole) {
        this.roles.remove(tenantRole);
        tenantRole.getPolicies().remove(this);
        return this;
    }

    public void setRoles(Set<TenantRole> tenantRoles) {
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
        TenantPolicy tenantPolicy = (TenantPolicy) o;
        if (tenantPolicy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tenantPolicy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TenantPolicy{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
