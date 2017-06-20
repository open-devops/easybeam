package com.easybeam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.easybeam.service.ProjectAuthorityByUserService;
import com.easybeam.web.rest.util.HeaderUtil;
import com.easybeam.web.rest.util.PaginationUtil;
import com.easybeam.service.dto.ProjectAuthorityByUserDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProjectAuthorityByUser.
 */
@RestController
@RequestMapping("/api")
public class ProjectAuthorityByUserResource {

    private final Logger log = LoggerFactory.getLogger(ProjectAuthorityByUserResource.class);

    private static final String ENTITY_NAME = "projectAuthorityByUser";
        
    private final ProjectAuthorityByUserService projectAuthorityByUserService;

    public ProjectAuthorityByUserResource(ProjectAuthorityByUserService projectAuthorityByUserService) {
        this.projectAuthorityByUserService = projectAuthorityByUserService;
    }

    /**
     * POST  /project-authority-by-users : Create a new projectAuthorityByUser.
     *
     * @param projectAuthorityByUserDTO the projectAuthorityByUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectAuthorityByUserDTO, or with status 400 (Bad Request) if the projectAuthorityByUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/project-authority-by-users")
    @Timed
    public ResponseEntity<ProjectAuthorityByUserDTO> createProjectAuthorityByUser(@RequestBody ProjectAuthorityByUserDTO projectAuthorityByUserDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectAuthorityByUser : {}", projectAuthorityByUserDTO);
        if (projectAuthorityByUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new projectAuthorityByUser cannot already have an ID")).body(null);
        }
        ProjectAuthorityByUserDTO result = projectAuthorityByUserService.save(projectAuthorityByUserDTO);
        return ResponseEntity.created(new URI("/api/project-authority-by-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /project-authority-by-users : Updates an existing projectAuthorityByUser.
     *
     * @param projectAuthorityByUserDTO the projectAuthorityByUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectAuthorityByUserDTO,
     * or with status 400 (Bad Request) if the projectAuthorityByUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the projectAuthorityByUserDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/project-authority-by-users")
    @Timed
    public ResponseEntity<ProjectAuthorityByUserDTO> updateProjectAuthorityByUser(@RequestBody ProjectAuthorityByUserDTO projectAuthorityByUserDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectAuthorityByUser : {}", projectAuthorityByUserDTO);
        if (projectAuthorityByUserDTO.getId() == null) {
            return createProjectAuthorityByUser(projectAuthorityByUserDTO);
        }
        ProjectAuthorityByUserDTO result = projectAuthorityByUserService.save(projectAuthorityByUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectAuthorityByUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /project-authority-by-users : get all the projectAuthorityByUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projectAuthorityByUsers in body
     */
    @GetMapping("/project-authority-by-users")
    @Timed
    public ResponseEntity<List<ProjectAuthorityByUserDTO>> getAllProjectAuthorityByUsers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ProjectAuthorityByUsers");
        Page<ProjectAuthorityByUserDTO> page = projectAuthorityByUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/project-authority-by-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /project-authority-by-users/:id : get the "id" projectAuthorityByUser.
     *
     * @param id the id of the projectAuthorityByUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectAuthorityByUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/project-authority-by-users/{id}")
    @Timed
    public ResponseEntity<ProjectAuthorityByUserDTO> getProjectAuthorityByUser(@PathVariable Long id) {
        log.debug("REST request to get ProjectAuthorityByUser : {}", id);
        ProjectAuthorityByUserDTO projectAuthorityByUserDTO = projectAuthorityByUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(projectAuthorityByUserDTO));
    }

    /**
     * DELETE  /project-authority-by-users/:id : delete the "id" projectAuthorityByUser.
     *
     * @param id the id of the projectAuthorityByUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/project-authority-by-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjectAuthorityByUser(@PathVariable Long id) {
        log.debug("REST request to delete ProjectAuthorityByUser : {}", id);
        projectAuthorityByUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
