package com.easybeam.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TenantUser entity.
 */
public class TenantUserDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String login;

    @NotNull
    @Size(max = 60)
    private String password;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Size(max = 100)
    private String email;

    @Size(min = 2, max = 5)
    private String langKey;

    private Long accountId;

    private String accountLogin;

    private Set<TenantGroupDTO> groups = new HashSet<>();

    private Set<TenantRoleDTO> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
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

    public Set<TenantGroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(Set<TenantGroupDTO> tenantGroups) {
        this.groups = tenantGroups;
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

        TenantUserDTO tenantUserDTO = (TenantUserDTO) o;
        if(tenantUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tenantUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TenantUserDTO{" +
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
