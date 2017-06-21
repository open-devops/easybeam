package com.easybeam.web.rest;

import com.easybeam.EasyBeamApp;

import com.easybeam.domain.TenantPermission;
import com.easybeam.repository.TenantPermissionRepository;
import com.easybeam.service.TenantPermissionService;
import com.easybeam.service.dto.TenantPermissionDTO;
import com.easybeam.service.mapper.TenantPermissionMapper;
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

import com.easybeam.domain.enumeration.PlatformService;
import com.easybeam.domain.enumeration.ServiceAccessLevel;
/**
 * Test class for the TenantPermissionResource REST controller.
 *
 * @see TenantPermissionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyBeamApp.class)
public class TenantPermissionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final PlatformService DEFAULT_SERVICE_NAME = PlatformService.IAM;
    private static final PlatformService UPDATED_SERVICE_NAME = PlatformService.IAM_GROUP;

    private static final ServiceAccessLevel DEFAULT_SERVICE_ACCESS_LEVEL = ServiceAccessLevel.FULL_ACCESS;
    private static final ServiceAccessLevel UPDATED_SERVICE_ACCESS_LEVEL = ServiceAccessLevel.LIMITED;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TenantPermissionRepository tenantPermissionRepository;

    @Autowired
    private TenantPermissionMapper tenantPermissionMapper;

    @Autowired
    private TenantPermissionService tenantPermissionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTenantPermissionMockMvc;

    private TenantPermission tenantPermission;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TenantPermissionResource tenantPermissionResource = new TenantPermissionResource(tenantPermissionService);
        this.restTenantPermissionMockMvc = MockMvcBuilders.standaloneSetup(tenantPermissionResource)
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
    public static TenantPermission createEntity(EntityManager em) {
        TenantPermission tenantPermission = new TenantPermission()
            .name(DEFAULT_NAME)
            .serviceName(DEFAULT_SERVICE_NAME)
            .serviceAccessLevel(DEFAULT_SERVICE_ACCESS_LEVEL)
            .description(DEFAULT_DESCRIPTION);
        return tenantPermission;
    }

    @Before
    public void initTest() {
        tenantPermission = createEntity(em);
    }

    @Test
    @Transactional
    public void createTenantPermission() throws Exception {
        int databaseSizeBeforeCreate = tenantPermissionRepository.findAll().size();

        // Create the TenantPermission
        TenantPermissionDTO tenantPermissionDTO = tenantPermissionMapper.toDto(tenantPermission);
        restTenantPermissionMockMvc.perform(post("/api/tenant-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantPermissionDTO)))
            .andExpect(status().isCreated());

        // Validate the TenantPermission in the database
        List<TenantPermission> tenantPermissionList = tenantPermissionRepository.findAll();
        assertThat(tenantPermissionList).hasSize(databaseSizeBeforeCreate + 1);
        TenantPermission testTenantPermission = tenantPermissionList.get(tenantPermissionList.size() - 1);
        assertThat(testTenantPermission.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTenantPermission.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testTenantPermission.getServiceAccessLevel()).isEqualTo(DEFAULT_SERVICE_ACCESS_LEVEL);
        assertThat(testTenantPermission.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTenantPermissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tenantPermissionRepository.findAll().size();

        // Create the TenantPermission with an existing ID
        tenantPermission.setId(1L);
        TenantPermissionDTO tenantPermissionDTO = tenantPermissionMapper.toDto(tenantPermission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTenantPermissionMockMvc.perform(post("/api/tenant-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantPermissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TenantPermission> tenantPermissionList = tenantPermissionRepository.findAll();
        assertThat(tenantPermissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tenantPermissionRepository.findAll().size();
        // set the field null
        tenantPermission.setName(null);

        // Create the TenantPermission, which fails.
        TenantPermissionDTO tenantPermissionDTO = tenantPermissionMapper.toDto(tenantPermission);

        restTenantPermissionMockMvc.perform(post("/api/tenant-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantPermissionDTO)))
            .andExpect(status().isBadRequest());

        List<TenantPermission> tenantPermissionList = tenantPermissionRepository.findAll();
        assertThat(tenantPermissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tenantPermissionRepository.findAll().size();
        // set the field null
        tenantPermission.setServiceName(null);

        // Create the TenantPermission, which fails.
        TenantPermissionDTO tenantPermissionDTO = tenantPermissionMapper.toDto(tenantPermission);

        restTenantPermissionMockMvc.perform(post("/api/tenant-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantPermissionDTO)))
            .andExpect(status().isBadRequest());

        List<TenantPermission> tenantPermissionList = tenantPermissionRepository.findAll();
        assertThat(tenantPermissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceAccessLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = tenantPermissionRepository.findAll().size();
        // set the field null
        tenantPermission.setServiceAccessLevel(null);

        // Create the TenantPermission, which fails.
        TenantPermissionDTO tenantPermissionDTO = tenantPermissionMapper.toDto(tenantPermission);

        restTenantPermissionMockMvc.perform(post("/api/tenant-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantPermissionDTO)))
            .andExpect(status().isBadRequest());

        List<TenantPermission> tenantPermissionList = tenantPermissionRepository.findAll();
        assertThat(tenantPermissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTenantPermissions() throws Exception {
        // Initialize the database
        tenantPermissionRepository.saveAndFlush(tenantPermission);

        // Get all the tenantPermissionList
        restTenantPermissionMockMvc.perform(get("/api/tenant-permissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenantPermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].serviceAccessLevel").value(hasItem(DEFAULT_SERVICE_ACCESS_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTenantPermission() throws Exception {
        // Initialize the database
        tenantPermissionRepository.saveAndFlush(tenantPermission);

        // Get the tenantPermission
        restTenantPermissionMockMvc.perform(get("/api/tenant-permissions/{id}", tenantPermission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tenantPermission.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME.toString()))
            .andExpect(jsonPath("$.serviceAccessLevel").value(DEFAULT_SERVICE_ACCESS_LEVEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTenantPermission() throws Exception {
        // Get the tenantPermission
        restTenantPermissionMockMvc.perform(get("/api/tenant-permissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTenantPermission() throws Exception {
        // Initialize the database
        tenantPermissionRepository.saveAndFlush(tenantPermission);
        int databaseSizeBeforeUpdate = tenantPermissionRepository.findAll().size();

        // Update the tenantPermission
        TenantPermission updatedTenantPermission = tenantPermissionRepository.findOne(tenantPermission.getId());
        updatedTenantPermission
            .name(UPDATED_NAME)
            .serviceName(UPDATED_SERVICE_NAME)
            .serviceAccessLevel(UPDATED_SERVICE_ACCESS_LEVEL)
            .description(UPDATED_DESCRIPTION);
        TenantPermissionDTO tenantPermissionDTO = tenantPermissionMapper.toDto(updatedTenantPermission);

        restTenantPermissionMockMvc.perform(put("/api/tenant-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantPermissionDTO)))
            .andExpect(status().isOk());

        // Validate the TenantPermission in the database
        List<TenantPermission> tenantPermissionList = tenantPermissionRepository.findAll();
        assertThat(tenantPermissionList).hasSize(databaseSizeBeforeUpdate);
        TenantPermission testTenantPermission = tenantPermissionList.get(tenantPermissionList.size() - 1);
        assertThat(testTenantPermission.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTenantPermission.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testTenantPermission.getServiceAccessLevel()).isEqualTo(UPDATED_SERVICE_ACCESS_LEVEL);
        assertThat(testTenantPermission.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTenantPermission() throws Exception {
        int databaseSizeBeforeUpdate = tenantPermissionRepository.findAll().size();

        // Create the TenantPermission
        TenantPermissionDTO tenantPermissionDTO = tenantPermissionMapper.toDto(tenantPermission);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTenantPermissionMockMvc.perform(put("/api/tenant-permissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantPermissionDTO)))
            .andExpect(status().isCreated());

        // Validate the TenantPermission in the database
        List<TenantPermission> tenantPermissionList = tenantPermissionRepository.findAll();
        assertThat(tenantPermissionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTenantPermission() throws Exception {
        // Initialize the database
        tenantPermissionRepository.saveAndFlush(tenantPermission);
        int databaseSizeBeforeDelete = tenantPermissionRepository.findAll().size();

        // Get the tenantPermission
        restTenantPermissionMockMvc.perform(delete("/api/tenant-permissions/{id}", tenantPermission.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TenantPermission> tenantPermissionList = tenantPermissionRepository.findAll();
        assertThat(tenantPermissionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenantPermission.class);
        TenantPermission tenantPermission1 = new TenantPermission();
        tenantPermission1.setId(1L);
        TenantPermission tenantPermission2 = new TenantPermission();
        tenantPermission2.setId(tenantPermission1.getId());
        assertThat(tenantPermission1).isEqualTo(tenantPermission2);
        tenantPermission2.setId(2L);
        assertThat(tenantPermission1).isNotEqualTo(tenantPermission2);
        tenantPermission1.setId(null);
        assertThat(tenantPermission1).isNotEqualTo(tenantPermission2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenantPermissionDTO.class);
        TenantPermissionDTO tenantPermissionDTO1 = new TenantPermissionDTO();
        tenantPermissionDTO1.setId(1L);
        TenantPermissionDTO tenantPermissionDTO2 = new TenantPermissionDTO();
        assertThat(tenantPermissionDTO1).isNotEqualTo(tenantPermissionDTO2);
        tenantPermissionDTO2.setId(tenantPermissionDTO1.getId());
        assertThat(tenantPermissionDTO1).isEqualTo(tenantPermissionDTO2);
        tenantPermissionDTO2.setId(2L);
        assertThat(tenantPermissionDTO1).isNotEqualTo(tenantPermissionDTO2);
        tenantPermissionDTO1.setId(null);
        assertThat(tenantPermissionDTO1).isNotEqualTo(tenantPermissionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tenantPermissionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tenantPermissionMapper.fromId(null)).isNull();
    }
}
