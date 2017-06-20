package com.easybeam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.easybeam.service.TenantPermissionService;
import com.easybeam.web.rest.util.HeaderUtil;
import com.easybeam.web.rest.util.PaginationUtil;
import com.easybeam.service.dto.TenantPermissionDTO;
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
 * REST controller for managing TenantPermission.
 */
@RestController
@RequestMapping("/api")
public class TenantPermissionResource {

    private final Logger log = LoggerFactory.getLogger(TenantPermissionResource.class);

    private static final String ENTITY_NAME = "tenantPermission";
        
    private final TenantPermissionService tenantPermissionService;

    public TenantPermissionResource(TenantPermissionService tenantPermissionService) {
        this.tenantPermissionService = tenantPermissionService;
    }

    /**
     * POST  /tenant-permissions : Create a new tenantPermission.
     *
     * @param tenantPermissionDTO the tenantPermissionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tenantPermissionDTO, or with status 400 (Bad Request) if the tenantPermission has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tenant-permissions")
    @Timed
    public ResponseEntity<TenantPermissionDTO> createTenantPermission(@Valid @RequestBody TenantPermissionDTO tenantPermissionDTO) throws URISyntaxException {
        log.debug("REST request to save TenantPermission : {}", tenantPermissionDTO);
        if (tenantPermissionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tenantPermission cannot already have an ID")).body(null);
        }
        TenantPermissionDTO result = tenantPermissionService.save(tenantPermissionDTO);
        return ResponseEntity.created(new URI("/api/tenant-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tenant-permissions : Updates an existing tenantPermission.
     *
     * @param tenantPermissionDTO the tenantPermissionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tenantPermissionDTO,
     * or with status 400 (Bad Request) if the tenantPermissionDTO is not valid,
     * or with status 500 (Internal Server Error) if the tenantPermissionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tenant-permissions")
    @Timed
    public ResponseEntity<TenantPermissionDTO> updateTenantPermission(@Valid @RequestBody TenantPermissionDTO tenantPermissionDTO) throws URISyntaxException {
        log.debug("REST request to update TenantPermission : {}", tenantPermissionDTO);
        if (tenantPermissionDTO.getId() == null) {
            return createTenantPermission(tenantPermissionDTO);
        }
        TenantPermissionDTO result = tenantPermissionService.save(tenantPermissionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tenantPermissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tenant-permissions : get all the tenantPermissions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tenantPermissions in body
     */
    @GetMapping("/tenant-permissions")
    @Timed
    public ResponseEntity<List<TenantPermissionDTO>> getAllTenantPermissions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TenantPermissions");
        Page<TenantPermissionDTO> page = tenantPermissionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tenant-permissions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tenant-permissions/:id : get the "id" tenantPermission.
     *
     * @param id the id of the tenantPermissionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tenantPermissionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tenant-permissions/{id}")
    @Timed
    public ResponseEntity<TenantPermissionDTO> getTenantPermission(@PathVariable Long id) {
        log.debug("REST request to get TenantPermission : {}", id);
        TenantPermissionDTO tenantPermissionDTO = tenantPermissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tenantPermissionDTO));
    }

    /**
     * DELETE  /tenant-permissions/:id : delete the "id" tenantPermission.
     *
     * @param id the id of the tenantPermissionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tenant-permissions/{id}")
    @Timed
    public ResponseEntity<Void> deleteTenantPermission(@PathVariable Long id) {
        log.debug("REST request to delete TenantPermission : {}", id);
        tenantPermissionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
