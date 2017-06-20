package com.easybeam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.easybeam.service.TenantPolicyService;
import com.easybeam.web.rest.util.HeaderUtil;
import com.easybeam.web.rest.util.PaginationUtil;
import com.easybeam.service.dto.TenantPolicyDTO;
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
 * REST controller for managing TenantPolicy.
 */
@RestController
@RequestMapping("/api")
public class TenantPolicyResource {

    private final Logger log = LoggerFactory.getLogger(TenantPolicyResource.class);

    private static final String ENTITY_NAME = "tenantPolicy";
        
    private final TenantPolicyService tenantPolicyService;

    public TenantPolicyResource(TenantPolicyService tenantPolicyService) {
        this.tenantPolicyService = tenantPolicyService;
    }

    /**
     * POST  /tenant-policies : Create a new tenantPolicy.
     *
     * @param tenantPolicyDTO the tenantPolicyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tenantPolicyDTO, or with status 400 (Bad Request) if the tenantPolicy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tenant-policies")
    @Timed
    public ResponseEntity<TenantPolicyDTO> createTenantPolicy(@Valid @RequestBody TenantPolicyDTO tenantPolicyDTO) throws URISyntaxException {
        log.debug("REST request to save TenantPolicy : {}", tenantPolicyDTO);
        if (tenantPolicyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tenantPolicy cannot already have an ID")).body(null);
        }
        TenantPolicyDTO result = tenantPolicyService.save(tenantPolicyDTO);
        return ResponseEntity.created(new URI("/api/tenant-policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tenant-policies : Updates an existing tenantPolicy.
     *
     * @param tenantPolicyDTO the tenantPolicyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tenantPolicyDTO,
     * or with status 400 (Bad Request) if the tenantPolicyDTO is not valid,
     * or with status 500 (Internal Server Error) if the tenantPolicyDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tenant-policies")
    @Timed
    public ResponseEntity<TenantPolicyDTO> updateTenantPolicy(@Valid @RequestBody TenantPolicyDTO tenantPolicyDTO) throws URISyntaxException {
        log.debug("REST request to update TenantPolicy : {}", tenantPolicyDTO);
        if (tenantPolicyDTO.getId() == null) {
            return createTenantPolicy(tenantPolicyDTO);
        }
        TenantPolicyDTO result = tenantPolicyService.save(tenantPolicyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tenantPolicyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tenant-policies : get all the tenantPolicies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tenantPolicies in body
     */
    @GetMapping("/tenant-policies")
    @Timed
    public ResponseEntity<List<TenantPolicyDTO>> getAllTenantPolicies(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TenantPolicies");
        Page<TenantPolicyDTO> page = tenantPolicyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tenant-policies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tenant-policies/:id : get the "id" tenantPolicy.
     *
     * @param id the id of the tenantPolicyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tenantPolicyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tenant-policies/{id}")
    @Timed
    public ResponseEntity<TenantPolicyDTO> getTenantPolicy(@PathVariable Long id) {
        log.debug("REST request to get TenantPolicy : {}", id);
        TenantPolicyDTO tenantPolicyDTO = tenantPolicyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tenantPolicyDTO));
    }

    /**
     * DELETE  /tenant-policies/:id : delete the "id" tenantPolicy.
     *
     * @param id the id of the tenantPolicyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tenant-policies/{id}")
    @Timed
    public ResponseEntity<Void> deleteTenantPolicy(@PathVariable Long id) {
        log.debug("REST request to delete TenantPolicy : {}", id);
        tenantPolicyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
