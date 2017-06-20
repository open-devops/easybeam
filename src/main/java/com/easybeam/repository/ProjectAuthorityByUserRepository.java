package com.easybeam.repository;

import com.easybeam.domain.ProjectAuthorityByUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProjectAuthorityByUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectAuthorityByUserRepository extends JpaRepository<ProjectAuthorityByUser,Long> {

}
