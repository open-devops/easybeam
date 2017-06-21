package com.easybeam.repository;

import com.easybeam.domain.ProjectAuthorityByGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProjectAuthorityByGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectAuthorityByGroupRepository extends JpaRepository<ProjectAuthorityByGroup,Long> {
    
}
