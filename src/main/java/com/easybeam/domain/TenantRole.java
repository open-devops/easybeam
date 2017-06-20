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

/**
 * A TenantRole.
 */
@Entity
@Table(name = "tenant_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TenantRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tenant_role_policies",
               joinColumns = @JoinColumn(name="tenant_roles_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="policies_id", referencedColumnName="id"))
    private Set<TenantPolicy> policies = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TenantGroup> groups = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TenantUser> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TenantRole name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public TenantRole description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TenantPolicy> getPolicies() {
        return policies;
    }

    public TenantRole policies(Set<TenantPolicy> tenantPolicies) {
        this.policies = tenantPolicies;
        return this;
    }

    public TenantRole addPolicies(TenantPolicy tenantPolicy) {
        this.policies.add(tenantPolicy);
        tenantPolicy.getRoles().add(this);
        return this;
    }

    public TenantRole removePolicies(TenantPolicy tenantPolicy) {
        this.policies.remove(tenantPolicy);
        tenantPolicy.getRoles().remove(this);
        return this;
    }

    public void setPolicies(Set<TenantPolicy> tenantPolicies) {
        this.policies = tenantPolicies;
    }

    public Set<TenantGroup> getGroups() {
        return groups;
    }

    public TenantRole groups(Set<TenantGroup> tenantGroups) {
        this.groups = tenantGroups;
        return this;
    }

    public TenantRole addGroups(TenantGroup tenantGroup) {
        this.groups.add(tenantGroup);
        tenantGroup.getRoles().add(this);
        return this;
    }

    public TenantRole removeGroups(TenantGroup tenantGroup) {
        this.groups.remove(tenantGroup);
        tenantGroup.getRoles().remove(this);
        return this;
    }

    public void setGroups(Set<TenantGroup> tenantGroups) {
        this.groups = tenantGroups;
    }

    public Set<TenantUser> getUsers() {
        return users;
    }

    public TenantRole users(Set<TenantUser> tenantUsers) {
        this.users = tenantUsers;
        return this;
    }

    public TenantRole addUsers(TenantUser tenantUser) {
        this.users.add(tenantUser);
        tenantUser.getRoles().add(this);
        return this;
    }

    public TenantRole removeUsers(TenantUser tenantUser) {
        this.users.remove(tenantUser);
        tenantUser.getRoles().remove(this);
        return this;
    }

    public void setUsers(Set<TenantUser> tenantUsers) {
        this.users = tenantUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TenantRole tenantRole = (TenantRole) o;
        if (tenantRole.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tenantRole.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TenantRole{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
