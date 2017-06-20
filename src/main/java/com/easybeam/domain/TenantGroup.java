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
 * A TenantGroup.
 */
@Entity
@Table(name = "tenant_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TenantGroup implements Serializable {

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

    @ManyToOne
    private User account;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tenant_group_roles",
               joinColumns = @JoinColumn(name="tenant_groups_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="roles_id", referencedColumnName="id"))
    private Set<TenantRole> roles = new HashSet<>();

    @ManyToMany(mappedBy = "groups")
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

    public TenantGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public TenantGroup description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAccount() {
        return account;
    }

    public TenantGroup account(User user) {
        this.account = user;
        return this;
    }

    public void setAccount(User user) {
        this.account = user;
    }

    public Set<TenantRole> getRoles() {
        return roles;
    }

    public TenantGroup roles(Set<TenantRole> tenantRoles) {
        this.roles = tenantRoles;
        return this;
    }

    public TenantGroup addRoles(TenantRole tenantRole) {
        this.roles.add(tenantRole);
        tenantRole.getGroups().add(this);
        return this;
    }

    public TenantGroup removeRoles(TenantRole tenantRole) {
        this.roles.remove(tenantRole);
        tenantRole.getGroups().remove(this);
        return this;
    }

    public void setRoles(Set<TenantRole> tenantRoles) {
        this.roles = tenantRoles;
    }

    public Set<TenantUser> getUsers() {
        return users;
    }

    public TenantGroup users(Set<TenantUser> tenantUsers) {
        this.users = tenantUsers;
        return this;
    }

    public TenantGroup addUsers(TenantUser tenantUser) {
        this.users.add(tenantUser);
        tenantUser.getGroups().add(this);
        return this;
    }

    public TenantGroup removeUsers(TenantUser tenantUser) {
        this.users.remove(tenantUser);
        tenantUser.getGroups().remove(this);
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
        TenantGroup tenantGroup = (TenantGroup) o;
        if (tenantGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tenantGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TenantGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
