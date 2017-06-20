package com.easybeam.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProjectAuthorityByGroup.
 */
@Entity
@Table(name = "project_authority_by_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjectAuthorityByGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private TenantGroup group;

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

    public ProjectAuthorityByGroup description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TenantGroup getGroup() {
        return group;
    }

    public ProjectAuthorityByGroup group(TenantGroup tenantGroup) {
        this.group = tenantGroup;
        return this;
    }

    public void setGroup(TenantGroup tenantGroup) {
        this.group = tenantGroup;
    }

    public TenantRole getRole() {
        return role;
    }

    public ProjectAuthorityByGroup role(TenantRole tenantRole) {
        this.role = tenantRole;
        return this;
    }

    public void setRole(TenantRole tenantRole) {
        this.role = tenantRole;
    }

    public Project getProject() {
        return project;
    }

    public ProjectAuthorityByGroup project(Project project) {
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
        ProjectAuthorityByGroup projectAuthorityByGroup = (ProjectAuthorityByGroup) o;
        if (projectAuthorityByGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectAuthorityByGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectAuthorityByGroup{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
