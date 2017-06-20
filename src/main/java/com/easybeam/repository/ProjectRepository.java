package com.easybeam.repository;

import com.easybeam.domain.Project;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("select project from Project project where project.account.login = ?#{principal.username}")
    List<Project> findByAccountIsCurrentUser();

}
