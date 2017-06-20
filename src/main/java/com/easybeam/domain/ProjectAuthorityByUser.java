package com.easybeam.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProjectAuthorityByUser.
 */
@Entity
@Table(name = "project_authority_by_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjectAuthorityByUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private TenantUser user;

    @OneToOne
    @JoinColumn(unique = true)
    private TenantRole role;

    @ManyToOne
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public ProjectAuthorityByUser description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TenantUser getUser() {
        return user;
    }

    public ProjectAuthorityByUser user(TenantUser tenantUser) {
        this.user = tenantUser;
        return this;
    }

    public void setUser(TenantUser tenantUser) {
        this.user = tenantUser;
    }

    public TenantRole getRole() {
        return role;
    }

    public ProjectAuthorityByUser role(TenantRole tenantRole) {
        this.role = tenantRole;
        return this;
    }

    public void setRole(TenantRole tenantRole) {
        this.role = tenantRole;
    }

    public Project getProject() {
        return project;
    }

    public ProjectAuthorityByUser project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectAuthorityByUser projectAuthorityByUser = (ProjectAuthorityByUser) o;
        if (projectAuthorityByUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectAuthorityByUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectAuthorityByUser{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
