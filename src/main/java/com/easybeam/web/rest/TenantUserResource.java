package com.easybeam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.easybeam.service.TenantUserService;
import com.easybeam.web.rest.util.HeaderUtil;
import com.easybeam.web.rest.util.PaginationUtil;
import com.easybeam.service.dto.TenantUserDTO;
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
 * REST controller for managing TenantUser.
 */
@RestController
@RequestMapping("/api")
public class TenantUserResource {

    private final Logger log = LoggerFactory.getLogger(TenantUserResource.class);

    private static final String ENTITY_NAME = "tenantUser";
        
    private final TenantUserService tenantUserService;

    public TenantUserResource(TenantUserService tenantUserService) {
        this.tenantUserService = tenantUserService;
    }

    /**
     * POST  /tenant-users : Create a new tenantUser.
     *
     * @param tenantUserDTO the tenantUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tenantUserDTO, or with status 400 (Bad Request) if the tenantUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tenant-users")
    @Timed
    public ResponseEntity<TenantUserDTO> createTenantUser(@Valid @RequestBody TenantUserDTO tenantUserDTO) throws URISyntaxException {
        log.debug("REST request to save TenantUser : {}", tenantUserDTO);
        if (tenantUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tenantUser cannot already have an ID")).body(null);
        }
        TenantUserDTO result = tenantUserService.save(tenantUserDTO);
        return ResponseEntity.created(new URI("/api/tenant-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tenant-users : Updates an existing tenantUser.
     *
     * @param tenantUserDTO the tenantUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tenantUserDTO,
     * or with status 400 (Bad Request) if the tenantUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the tenantUserDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tenant-users")
    @Timed
    public ResponseEntity<TenantUserDTO> updateTenantUser(@Valid @RequestBody TenantUserDTO tenantUserDTO) throws URISyntaxException {
        log.debug("REST request to update TenantUser : {}", tenantUserDTO);
        if (tenantUserDTO.getId() == null) {
            return createTenantUser(tenantUserDTO);
        }
        TenantUserDTO result = tenantUserService.save(tenantUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tenantUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tenant-users : get all the tenantUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tenantUsers in body
     */
    @GetMapping("/tenant-users")
    @Timed
    public ResponseEntity<List<TenantUserDTO>> getAllTenantUsers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TenantUsers");
        Page<TenantUserDTO> page = tenantUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tenant-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tenant-users/:id : get the "id" tenantUser.
     *
     * @param id the id of the tenantUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tenantUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tenant-users/{id}")
    @Timed
    public ResponseEntity<TenantUserDTO> getTenantUser(@PathVariable Long id) {
        log.debug("REST request to get TenantUser : {}", id);
        TenantUserDTO tenantUserDTO = tenantUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tenantUserDTO));
    }

    /**
     * DELETE  /tenant-users/:id : delete the "id" tenantUser.
     *
     * @param id the id of the tenantUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tenant-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteTenantUser(@PathVariable Long id) {
        log.debug("REST request to delete TenantUser : {}", id);
        tenantUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
