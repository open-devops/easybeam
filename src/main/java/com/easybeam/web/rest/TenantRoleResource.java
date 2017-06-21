package com.easybeam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.easybeam.service.TenantRoleService;
import com.easybeam.web.rest.util.HeaderUtil;
import com.easybeam.web.rest.util.PaginationUtil;
import com.easybeam.service.dto.TenantRoleDTO;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TenantRole.
 */
@RestController
@RequestMapping("/api")
public class TenantRoleResource {

    private final Logger log = LoggerFactory.getLogger(TenantRoleResource.class);

    private static final String ENTITY_NAME = "tenantRole";

    private final TenantRoleService tenantRoleService;

    public TenantRoleResource(TenantRoleService tenantRoleService) {
        this.tenantRoleService = tenantRoleService;
    }

    /**
     * POST  /tenant-roles : Create a new tenantRole.
     *
     * @param tenantRoleDTO the tenantRoleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tenantRoleDTO, or with status 400 (Bad Request) if the tenantRole has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tenant-roles")
    @Timed
    public ResponseEntity<TenantRoleDTO> createTenantRole(@Valid @RequestBody TenantRoleDTO tenantRoleDTO) throws URISyntaxException {
        log.debug("REST request to save TenantRole : {}", tenantRoleDTO);
        if (tenantRoleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tenantRole cannot already have an ID")).body(null);
        }
        TenantRoleDTO result = tenantRoleService.save(tenantRoleDTO);
        return ResponseEntity.created(new URI("/api/tenant-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tenant-roles : Updates an existing tenantRole.
     *
     * @param tenantRoleDTO the tenantRoleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tenantRoleDTO,
     * or with status 400 (Bad Request) if the tenantRoleDTO is not valid,
     * or with status 500 (Internal Server Error) if the tenantRoleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tenant-roles")
    @Timed
    public ResponseEntity<TenantRoleDTO> updateTenantRole(@Valid @RequestBody TenantRoleDTO tenantRoleDTO) throws URISyntaxException {
        log.debug("REST request to update TenantRole : {}", tenantRoleDTO);
        if (tenantRoleDTO.getId() == null) {
            return createTenantRole(tenantRoleDTO);
        }
        TenantRoleDTO result = tenantRoleService.save(tenantRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tenantRoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tenant-roles : get all the tenantRoles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tenantRoles in body
     */
    @GetMapping("/tenant-roles")
    @Timed
    public ResponseEntity<List<TenantRoleDTO>> getAllTenantRoles(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TenantRoles");
        Page<TenantRoleDTO> page = tenantRoleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tenant-roles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tenant-roles/:id : get the "id" tenantRole.
     *
     * @param id the id of the tenantRoleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tenantRoleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tenant-roles/{id}")
    @Timed
    public ResponseEntity<TenantRoleDTO> getTenantRole(@PathVariable Long id) {
        log.debug("REST request to get TenantRole : {}", id);
        TenantRoleDTO tenantRoleDTO = tenantRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tenantRoleDTO));
    }

    /**
     * DELETE  /tenant-roles/:id : delete the "id" tenantRole.
     *
     * @param id the id of the tenantRoleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tenant-roles/{id}")
    @Timed
    public ResponseEntity<Void> deleteTenantRole(@PathVariable Long id) {
        log.debug("REST request to delete TenantRole : {}", id);
        tenantRoleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
