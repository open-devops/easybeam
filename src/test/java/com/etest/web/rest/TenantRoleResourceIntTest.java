package com.easybeam.web.rest;

import com.easybeam.EasyBeamApp;

import com.easybeam.domain.TenantRole;
import com.easybeam.repository.TenantRoleRepository;
import com.easybeam.service.TenantRoleService;
import com.easybeam.service.dto.TenantRoleDTO;
import com.easybeam.service.mapper.TenantRoleMapper;
import com.easybeam.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TenantRoleResource REST controller.
 *
 * @see TenantRoleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyBeamApp.class)
public class TenantRoleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TenantRoleRepository tenantRoleRepository;

    @Autowired
    private TenantRoleMapper tenantRoleMapper;

    @Autowired
    private TenantRoleService tenantRoleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTenantRoleMockMvc;

    private TenantRole tenantRole;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TenantRoleResource tenantRoleResource = new TenantRoleResource(tenantRoleService);
        this.restTenantRoleMockMvc = MockMvcBuilders.standaloneSetup(tenantRoleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TenantRole createEntity(EntityManager em) {
        TenantRole tenantRole = new TenantRole()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return tenantRole;
    }

    @Before
    public void initTest() {
        tenantRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createTenantRole() throws Exception {
        int databaseSizeBeforeCreate = tenantRoleRepository.findAll().size();

        // Create the TenantRole
        TenantRoleDTO tenantRoleDTO = tenantRoleMapper.toDto(tenantRole);
        restTenantRoleMockMvc.perform(post("/api/tenant-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the TenantRole in the database
        List<TenantRole> tenantRoleList = tenantRoleRepository.findAll();
        assertThat(tenantRoleList).hasSize(databaseSizeBeforeCreate + 1);
        TenantRole testTenantRole = tenantRoleList.get(tenantRoleList.size() - 1);
        assertThat(testTenantRole.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTenantRole.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTenantRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tenantRoleRepository.findAll().size();

        // Create the TenantRole with an existing ID
        tenantRole.setId(1L);
        TenantRoleDTO tenantRoleDTO = tenantRoleMapper.toDto(tenantRole);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTenantRoleMockMvc.perform(post("/api/tenant-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TenantRole> tenantRoleList = tenantRoleRepository.findAll();
        assertThat(tenantRoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tenantRoleRepository.findAll().size();
        // set the field null
        tenantRole.setName(null);

        // Create the TenantRole, which fails.
        TenantRoleDTO tenantRoleDTO = tenantRoleMapper.toDto(tenantRole);

        restTenantRoleMockMvc.perform(post("/api/tenant-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantRoleDTO)))
            .andExpect(status().isBadRequest());

        List<TenantRole> tenantRoleList = tenantRoleRepository.findAll();
        assertThat(tenantRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTenantRoles() throws Exception {
        // Initialize the database
        tenantRoleRepository.saveAndFlush(tenantRole);

        // Get all the tenantRoleList
        restTenantRoleMockMvc.perform(get("/api/tenant-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenantRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTenantRole() throws Exception {
        // Initialize the database
        tenantRoleRepository.saveAndFlush(tenantRole);

        // Get the tenantRole
        restTenantRoleMockMvc.perform(get("/api/tenant-roles/{id}", tenantRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tenantRole.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTenantRole() throws Exception {
        // Get the tenantRole
        restTenantRoleMockMvc.perform(get("/api/tenant-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTenantRole() throws Exception {
        // Initialize the database
        tenantRoleRepository.saveAndFlush(tenantRole);
        int databaseSizeBeforeUpdate = tenantRoleRepository.findAll().size();

        // Update the tenantRole
        TenantRole updatedTenantRole = tenantRoleRepository.findOne(tenantRole.getId());
        updatedTenantRole
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        TenantRoleDTO tenantRoleDTO = tenantRoleMapper.toDto(updatedTenantRole);

        restTenantRoleMockMvc.perform(put("/api/tenant-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantRoleDTO)))
            .andExpect(status().isOk());

        // Validate the TenantRole in the database
        List<TenantRole> tenantRoleList = tenantRoleRepository.findAll();
        assertThat(tenantRoleList).hasSize(databaseSizeBeforeUpdate);
        TenantRole testTenantRole = tenantRoleList.get(tenantRoleList.size() - 1);
        assertThat(testTenantRole.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTenantRole.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTenantRole() throws Exception {
        int databaseSizeBeforeUpdate = tenantRoleRepository.findAll().size();

        // Create the TenantRole
        TenantRoleDTO tenantRoleDTO = tenantRoleMapper.toDto(tenantRole);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTenantRoleMockMvc.perform(put("/api/tenant-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the TenantRole in the database
        List<TenantRole> tenantRoleList = tenantRoleRepository.findAll();
        assertThat(tenantRoleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTenantRole() throws Exception {
        // Initialize the database
        tenantRoleRepository.saveAndFlush(tenantRole);
        int databaseSizeBeforeDelete = tenantRoleRepository.findAll().size();

        // Get the tenantRole
        restTenantRoleMockMvc.perform(delete("/api/tenant-roles/{id}", tenantRole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TenantRole> tenantRoleList = tenantRoleRepository.findAll();
        assertThat(tenantRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenantRole.class);
        TenantRole tenantRole1 = new TenantRole();
        tenantRole1.setId(1L);
        TenantRole tenantRole2 = new TenantRole();
        tenantRole2.setId(tenantRole1.getId());
        assertThat(tenantRole1).isEqualTo(tenantRole2);
        tenantRole2.setId(2L);
        assertThat(tenantRole1).isNotEqualTo(tenantRole2);
        tenantRole1.setId(null);
        assertThat(tenantRole1).isNotEqualTo(tenantRole2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenantRoleDTO.class);
        TenantRoleDTO tenantRoleDTO1 = new TenantRoleDTO();
        tenantRoleDTO1.setId(1L);
        TenantRoleDTO tenantRoleDTO2 = new TenantRoleDTO();
        assertThat(tenantRoleDTO1).isNotEqualTo(tenantRoleDTO2);
        tenantRoleDTO2.setId(tenantRoleDTO1.getId());
        assertThat(tenantRoleDTO1).isEqualTo(tenantRoleDTO2);
        tenantRoleDTO2.setId(2L);
        assertThat(tenantRoleDTO1).isNotEqualTo(tenantRoleDTO2);
        tenantRoleDTO1.setId(null);
        assertThat(tenantRoleDTO1).isNotEqualTo(tenantRoleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tenantRoleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tenantRoleMapper.fromId(null)).isNull();
    }
}
