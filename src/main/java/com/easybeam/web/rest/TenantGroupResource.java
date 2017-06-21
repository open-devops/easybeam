package com.easybeam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.easybeam.service.TenantGroupService;
import com.easybeam.web.rest.util.HeaderUtil;
import com.easybeam.web.rest.util.PaginationUtil;
import com.easybeam.service.dto.TenantGroupDTO;
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
 * REST controller for managing TenantGroup.
 */
@RestController
@RequestMapping("/api")
public class TenantGroupResource {

    private final Logger log = LoggerFactory.getLogger(TenantGroupResource.class);

    private static final String ENTITY_NAME = "tenantGroup";

    private final TenantGroupService tenantGroupService;

    public TenantGroupResource(TenantGroupService tenantGroupService) {
        this.tenantGroupService = tenantGroupService;
    }

    /**
     * POST  /tenant-groups : Create a new tenantGroup.
     *
     * @param tenantGroupDTO the tenantGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tenantGroupDTO, or with status 400 (Bad Request) if the tenantGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tenant-groups")
    @Timed
    public ResponseEntity<TenantGroupDTO> createTenantGroup(@Valid @RequestBody TenantGroupDTO tenantGroupDTO) throws URISyntaxException {
        log.debug("REST request to save TenantGroup : {}", tenantGroupDTO);
        if (tenantGroupDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tenantGroup cannot already have an ID")).body(null);
        }
        TenantGroupDTO result = tenantGroupService.save(tenantGroupDTO);
        return ResponseEntity.created(new URI("/api/tenant-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tenant-groups : Updates an existing tenantGroup.
     *
     * @param tenantGroupDTO the tenantGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tenantGroupDTO,
     * or with status 400 (Bad Request) if the tenantGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the tenantGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tenant-groups")
    @Timed
    public ResponseEntity<TenantGroupDTO> updateTenantGroup(@Valid @RequestBody TenantGroupDTO tenantGroupDTO) throws URISyntaxException {
        log.debug("REST request to update TenantGroup : {}", tenantGroupDTO);
        if (tenantGroupDTO.getId() == null) {
            return createTenantGroup(tenantGroupDTO);
        }
        TenantGroupDTO result = tenantGroupService.save(tenantGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tenantGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tenant-groups : get all the tenantGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tenantGroups in body
     */
    @GetMapping("/tenant-groups")
    @Timed
    public ResponseEntity<List<TenantGroupDTO>> getAllTenantGroups(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TenantGroups");
        Page<TenantGroupDTO> page = tenantGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tenant-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tenant-groups/:id : get the "id" tenantGroup.
     *
     * @param id the id of the tenantGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tenantGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tenant-groups/{id}")
    @Timed
    public ResponseEntity<TenantGroupDTO> getTenantGroup(@PathVariable Long id) {
        log.debug("REST request to get TenantGroup : {}", id);
        TenantGroupDTO tenantGroupDTO = tenantGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tenantGroupDTO));
    }

    /**
     * DELETE  /tenant-groups/:id : delete the "id" tenantGroup.
     *
     * @param id the id of the tenantGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tenant-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteTenantGroup(@PathVariable Long id) {
        log.debug("REST request to delete TenantGroup : {}", id);
        tenantGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
