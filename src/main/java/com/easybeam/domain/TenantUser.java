package com.easybeam.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TenantUser.
 */
@Entity
@Table(name = "tenant_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TenantUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "login", length = 50, nullable = false)
    private String login;

    @NotNull
    @Size(max = 60)
    @Column(name = "jhi_password", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Size(min = 2, max = 5)
    @Column(name = "lang_key")
    private String langKey;

    @ManyToOne
    private User account;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tenant_user_groups",
               joinColumns = @JoinColumn(name="tenant_users_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="groups_id", referencedColumnName="id"))
    private Set<TenantGroup> groups = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tenant_user_roles",
               joinColumns = @JoinColumn(name="tenant_users_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="roles_id", referencedColumnName="id"))
    private Set<TenantRole> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public TenantUser login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public TenantUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public TenantUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public TenantUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public TenantUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLangKey() {
        return langKey;
    }

    public TenantUser langKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public User getAccount() {
        return account;
    }

    public TenantUser account(User user) {
        this.account = user;
        return this;
    }

    public void setAccount(User user) {
        this.account = user;
    }

    public Set<TenantGroup> getGroups() {
        return groups;
    }

    public TenantUser groups(Set<TenantGroup> tenantGroups) {
        this.groups = tenantGroups;
        return this;
    }

    public TenantUser addGroups(TenantGroup tenantGroup) {
        this.groups.add(tenantGroup);
        tenantGroup.getUsers().add(this);
        return this;
    }

    public TenantUser removeGroups(TenantGroup tenantGroup) {
        this.groups.remove(tenantGroup);
        tenantGroup.getUsers().remove(this);
        return this;
    }

    public void setGroups(Set<TenantGroup> tenantGroups) {
        this.groups = tenantGroups;
    }

    public Set<TenantRole> getRoles() {
        return roles;
    }

    public TenantUser roles(Set<TenantRole> tenantRoles) {
        this.roles = tenantRoles;
        return this;
    }

    public TenantUser addRoles(TenantRole tenantRole) {
        this.roles.add(tenantRole);
        tenantRole.getUsers().add(this);
        return this;
    }

    public TenantUser removeRoles(TenantRole tenantRole) {
        this.roles.remove(tenantRole);
        tenantRole.getUsers().remove(this);
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
        TenantUser tenantUser = (TenantUser) o;
        if (tenantUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tenantUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TenantUser{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", langKey='" + getLangKey() + "'" +
            "}";
    }
}
