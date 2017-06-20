package com.easybeam.web.rest;

import com.easybeam.EasyBeamApp;

import com.easybeam.domain.TenantPolicy;
import com.easybeam.repository.TenantPolicyRepository;
import com.easybeam.service.TenantPolicyService;
import com.easybeam.service.dto.TenantPolicyDTO;
import com.easybeam.service.mapper.TenantPolicyMapper;
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

import com.easybeam.domain.enumeration.PolicyType;
/**
 * Test class for the TenantPolicyResource REST controller.
 *
 * @see TenantPolicyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyBeamApp.class)
public class TenantPolicyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final PolicyType DEFAULT_TYPE = PolicyType.PLATFORM_MANAGED;
    private static final PolicyType UPDATED_TYPE = PolicyType.CUSTOMER_MANAGED;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TenantPolicyRepository tenantPolicyRepository;

    @Autowired
    private TenantPolicyMapper tenantPolicyMapper;

    @Autowired
    private TenantPolicyService tenantPolicyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTenantPolicyMockMvc;

    private TenantPolicy tenantPolicy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TenantPolicyResource tenantPolicyResource = new TenantPolicyResource(tenantPolicyService);
        this.restTenantPolicyMockMvc = MockMvcBuilders.standaloneSetup(tenantPolicyResource)
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
    public static TenantPolicy createEntity(EntityManager em) {
        TenantPolicy tenantPolicy = new TenantPolicy()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return tenantPolicy;
    }

    @Before
    public void initTest() {
        tenantPolicy = createEntity(em);
    }

    @Test
    @Transactional
    public void createTenantPolicy() throws Exception {
        int databaseSizeBeforeCreate = tenantPolicyRepository.findAll().size();

        // Create the TenantPolicy
        TenantPolicyDTO tenantPolicyDTO = tenantPolicyMapper.toDto(tenantPolicy);
        restTenantPolicyMockMvc.perform(post("/api/tenant-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantPolicyDTO)))
            .andExpect(status().isCreated());

        // Validate the TenantPolicy in the database
        List<TenantPolicy> tenantPolicyList = tenantPolicyRepository.findAll();
        assertThat(tenantPolicyList).hasSize(databaseSizeBeforeCreate + 1);
        TenantPolicy testTenantPolicy = tenantPolicyList.get(tenantPolicyList.size() - 1);
        assertThat(testTenantPolicy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTenantPolicy.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTenantPolicy.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTenantPolicyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tenantPolicyRepository.findAll().size();

        // Create the TenantPolicy with an existing ID
        tenantPolicy.setId(1L);
        TenantPolicyDTO tenantPolicyDTO = tenantPolicyMapper.toDto(tenantPolicy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTenantPolicyMockMvc.perform(post("/api/tenant-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantPolicyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TenantPolicy> tenantPolicyList = tenantPolicyRepository.findAll();
        assertThat(tenantPolicyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tenantPolicyRepository.findAll().size();
        // set the field null
        tenantPolicy.setName(null);

        // Create the TenantPolicy, which fails.
        TenantPolicyDTO tenantPolicyDTO = tenantPolicyMapper.toDto(tenantPolicy);

        restTenantPolicyMockMvc.perform(post("/api/tenant-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantPolicyDTO)))
            .andExpect(status().isBadRequest());

        List<TenantPolicy> tenantPolicyList = tenantPolicyRepository.findAll();
        assertThat(tenantPolicyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTenantPolicies() throws Exception {
        // Initialize the database
        tenantPolicyRepository.saveAndFlush(tenantPolicy);

        // Get all the tenantPolicyList
        restTenantPolicyMockMvc.perform(get("/api/tenant-policies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenantPolicy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTenantPolicy() throws Exception {
        // Initialize the database
        tenantPolicyRepository.saveAndFlush(tenantPolicy);

        // Get the tenantPolicy
        restTenantPolicyMockMvc.perform(get("/api/tenant-policies/{id}", tenantPolicy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tenantPolicy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTenantPolicy() throws Exception {
        // Get the tenantPolicy
        restTenantPolicyMockMvc.perform(get("/api/tenant-policies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTenantPolicy() throws Exception {
        // Initialize the database
        tenantPolicyRepository.saveAndFlush(tenantPolicy);
        int databaseSizeBeforeUpdate = tenantPolicyRepository.findAll().size();

        // Update the tenantPolicy
        TenantPolicy updatedTenantPolicy = tenantPolicyRepository.findOne(tenantPolicy.getId());
        updatedTenantPolicy
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION);
        TenantPolicyDTO tenantPolicyDTO = tenantPolicyMapper.toDto(updatedTenantPolicy);

        restTenantPolicyMockMvc.perform(put("/api/tenant-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantPolicyDTO)))
            .andExpect(status().isOk());

        // Validate the TenantPolicy in the database
        List<TenantPolicy> tenantPolicyList = tenantPolicyRepository.findAll();
        assertThat(tenantPolicyList).hasSize(databaseSizeBeforeUpdate);
        TenantPolicy testTenantPolicy = tenantPolicyList.get(tenantPolicyList.size() - 1);
        assertThat(testTenantPolicy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTenantPolicy.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTenantPolicy.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTenantPolicy() throws Exception {
        int databaseSizeBeforeUpdate = tenantPolicyRepository.findAll().size();

        // Create the TenantPolicy
        TenantPolicyDTO tenantPolicyDTO = tenantPolicyMapper.toDto(tenantPolicy);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTenantPolicyMockMvc.perform(put("/api/tenant-policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantPolicyDTO)))
            .andExpect(status().isCreated());

        // Validate the TenantPolicy in the database
        List<TenantPolicy> tenantPolicyList = tenantPolicyRepository.findAll();
        assertThat(tenantPolicyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTenantPolicy() throws Exception {
        // Initialize the database
        tenantPolicyRepository.saveAndFlush(tenantPolicy);
        int databaseSizeBeforeDelete = tenantPolicyRepository.findAll().size();

        // Get the tenantPolicy
        restTenantPolicyMockMvc.perform(delete("/api/tenant-policies/{id}", tenantPolicy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TenantPolicy> tenantPolicyList = tenantPolicyRepository.findAll();
        assertThat(tenantPolicyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenantPolicy.class);
        TenantPolicy tenantPolicy1 = new TenantPolicy();
        tenantPolicy1.setId(1L);
        TenantPolicy tenantPolicy2 = new TenantPolicy();
        tenantPolicy2.setId(tenantPolicy1.getId());
        assertThat(tenantPolicy1).isEqualTo(tenantPolicy2);
        tenantPolicy2.setId(2L);
        assertThat(tenantPolicy1).isNotEqualTo(tenantPolicy2);
        tenantPolicy1.setId(null);
        assertThat(tenantPolicy1).isNotEqualTo(tenantPolicy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenantPolicyDTO.class);
        TenantPolicyDTO tenantPolicyDTO1 = new TenantPolicyDTO();
        tenantPolicyDTO1.setId(1L);
        TenantPolicyDTO tenantPolicyDTO2 = new TenantPolicyDTO();
        assertThat(tenantPolicyDTO1).isNotEqualTo(tenantPolicyDTO2);
        tenantPolicyDTO2.setId(tenantPolicyDTO1.getId());
        assertThat(tenantPolicyDTO1).isEqualTo(tenantPolicyDTO2);
        tenantPolicyDTO2.setId(2L);
        assertThat(tenantPolicyDTO1).isNotEqualTo(tenantPolicyDTO2);
        tenantPolicyDTO1.setId(null);
        assertThat(tenantPolicyDTO1).isNotEqualTo(tenantPolicyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tenantPolicyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tenantPolicyMapper.fromId(null)).isNull();
    }
}
