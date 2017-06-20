package com.easybeam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.easybeam.service.ProjectAuthorityByGroupService;
import com.easybeam.web.rest.util.HeaderUtil;
import com.easybeam.web.rest.util.PaginationUtil;
import com.easybeam.service.dto.ProjectAuthorityByGroupDTO;
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
 * REST controller for managing ProjectAuthorityByGroup.
 */
@RestController
@RequestMapping("/api")
public class ProjectAuthorityByGroupResource {

    private final Logger log = LoggerFactory.getLogger(ProjectAuthorityByGroupResource.class);

    private static final String ENTITY_NAME = "projectAuthorityByGroup";
        
    private final ProjectAuthorityByGroupService projectAuthorityByGroupService;

    public ProjectAuthorityByGroupResource(ProjectAuthorityByGroupService projectAuthorityByGroupService) {
        this.projectAuthorityByGroupService = projectAuthorityByGroupService;
    }

    /**
     * POST  /project-authority-by-groups : Create a new projectAuthorityByGroup.
     *
     * @param projectAuthorityByGroupDTO the projectAuthorityByGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectAuthorityByGroupDTO, or with status 400 (Bad Request) if the projectAuthorityByGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/project-authority-by-groups")
    @Timed
    public ResponseEntity<ProjectAuthorityByGroupDTO> createProjectAuthorityByGroup(@RequestBody ProjectAuthorityByGroupDTO projectAuthorityByGroupDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectAuthorityByGroup : {}", projectAuthorityByGroupDTO);
        if (projectAuthorityByGroupDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new projectAuthorityByGroup cannot already have an ID")).body(null);
        }
        ProjectAuthorityByGroupDTO result = projectAuthorityByGroupService.save(projectAuthorityByGroupDTO);
        return ResponseEntity.created(new URI("/api/project-authority-by-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /project-authority-by-groups : Updates an existing projectAuthorityByGroup.
     *
     * @param projectAuthorityByGroupDTO the projectAuthorityByGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectAuthorityByGroupDTO,
     * or with status 400 (Bad Request) if the projectAuthorityByGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the projectAuthorityByGroupDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/project-authority-by-groups")
    @Timed
    public ResponseEntity<ProjectAuthorityByGroupDTO> updateProjectAuthorityByGroup(@RequestBody ProjectAuthorityByGroupDTO projectAuthorityByGroupDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectAuthorityByGroup : {}", projectAuthorityByGroupDTO);
        if (projectAuthorityByGroupDTO.getId() == null) {
            return createProjectAuthorityByGroup(projectAuthorityByGroupDTO);
        }
        ProjectAuthorityByGroupDTO result = projectAuthorityByGroupService.save(projectAuthorityByGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectAuthorityByGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /project-authority-by-groups : get all the projectAuthorityByGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projectAuthorityByGroups in body
     */
    @GetMapping("/project-authority-by-groups")
    @Timed
    public ResponseEntity<List<ProjectAuthorityByGroupDTO>> getAllProjectAuthorityByGroups(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ProjectAuthorityByGroups");
        Page<ProjectAuthorityByGroupDTO> page = projectAuthorityByGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/project-authority-by-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /project-authority-by-groups/:id : get the "id" projectAuthorityByGroup.
     *
     * @param id the id of the projectAuthorityByGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectAuthorityByGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/project-authority-by-groups/{id}")
    @Timed
    public ResponseEntity<ProjectAuthorityByGroupDTO> getProjectAuthorityByGroup(@PathVariable Long id) {
        log.debug("REST request to get ProjectAuthorityByGroup : {}", id);
        ProjectAuthorityByGroupDTO projectAuthorityByGroupDTO = projectAuthorityByGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(projectAuthorityByGroupDTO));
    }

    /**
     * DELETE  /project-authority-by-groups/:id : delete the "id" projectAuthorityByGroup.
     *
     * @param id the id of the projectAuthorityByGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/project-authority-by-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjectAuthorityByGroup(@PathVariable Long id) {
        log.debug("REST request to delete ProjectAuthorityByGroup : {}", id);
        projectAuthorityByGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
