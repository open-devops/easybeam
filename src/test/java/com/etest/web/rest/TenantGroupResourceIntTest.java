package com.easybeam.web.rest;

import com.easybeam.EasyBeamApp;

import com.easybeam.domain.TenantGroup;
import com.easybeam.repository.TenantGroupRepository;
import com.easybeam.service.TenantGroupService;
import com.easybeam.service.dto.TenantGroupDTO;
import com.easybeam.service.mapper.TenantGroupMapper;
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
 * Test class for the TenantGroupResource REST controller.
 *
 * @see TenantGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyBeamApp.class)
public class TenantGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TenantGroupRepository tenantGroupRepository;

    @Autowired
    private TenantGroupMapper tenantGroupMapper;

    @Autowired
    private TenantGroupService tenantGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTenantGroupMockMvc;

    private TenantGroup tenantGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TenantGroupResource tenantGroupResource = new TenantGroupResource(tenantGroupService);
        this.restTenantGroupMockMvc = MockMvcBuilders.standaloneSetup(tenantGroupResource)
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
    public static TenantGroup createEntity(EntityManager em) {
        TenantGroup tenantGroup = new TenantGroup()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return tenantGroup;
    }

    @Before
    public void initTest() {
        tenantGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createTenantGroup() throws Exception {
        int databaseSizeBeforeCreate = tenantGroupRepository.findAll().size();

        // Create the TenantGroup
        TenantGroupDTO tenantGroupDTO = tenantGroupMapper.toDto(tenantGroup);
        restTenantGroupMockMvc.perform(post("/api/tenant-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the TenantGroup in the database
        List<TenantGroup> tenantGroupList = tenantGroupRepository.findAll();
        assertThat(tenantGroupList).hasSize(databaseSizeBeforeCreate + 1);
        TenantGroup testTenantGroup = tenantGroupList.get(tenantGroupList.size() - 1);
        assertThat(testTenantGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTenantGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTenantGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tenantGroupRepository.findAll().size();

        // Create the TenantGroup with an existing ID
        tenantGroup.setId(1L);
        TenantGroupDTO tenantGroupDTO = tenantGroupMapper.toDto(tenantGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTenantGroupMockMvc.perform(post("/api/tenant-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TenantGroup> tenantGroupList = tenantGroupRepository.findAll();
        assertThat(tenantGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tenantGroupRepository.findAll().size();
        // set the field null
        tenantGroup.setName(null);

        // Create the TenantGroup, which fails.
        TenantGroupDTO tenantGroupDTO = tenantGroupMapper.toDto(tenantGroup);

        restTenantGroupMockMvc.perform(post("/api/tenant-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantGroupDTO)))
            .andExpect(status().isBadRequest());

        List<TenantGroup> tenantGroupList = tenantGroupRepository.findAll();
        assertThat(tenantGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTenantGroups() throws Exception {
        // Initialize the database
        tenantGroupRepository.saveAndFlush(tenantGroup);

        // Get all the tenantGroupList
        restTenantGroupMockMvc.perform(get("/api/tenant-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenantGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTenantGroup() throws Exception {
        // Initialize the database
        tenantGroupRepository.saveAndFlush(tenantGroup);

        // Get the tenantGroup
        restTenantGroupMockMvc.perform(get("/api/tenant-groups/{id}", tenantGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tenantGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTenantGroup() throws Exception {
        // Get the tenantGroup
        restTenantGroupMockMvc.perform(get("/api/tenant-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTenantGroup() throws Exception {
        // Initialize the database
        tenantGroupRepository.saveAndFlush(tenantGroup);
        int databaseSizeBeforeUpdate = tenantGroupRepository.findAll().size();

        // Update the tenantGroup
        TenantGroup updatedTenantGroup = tenantGroupRepository.findOne(tenantGroup.getId());
        updatedTenantGroup
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        TenantGroupDTO tenantGroupDTO = tenantGroupMapper.toDto(updatedTenantGroup);

        restTenantGroupMockMvc.perform(put("/api/tenant-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantGroupDTO)))
            .andExpect(status().isOk());

        // Validate the TenantGroup in the database
        List<TenantGroup> tenantGroupList = tenantGroupRepository.findAll();
        assertThat(tenantGroupList).hasSize(databaseSizeBeforeUpdate);
        TenantGroup testTenantGroup = tenantGroupList.get(tenantGroupList.size() - 1);
        assertThat(testTenantGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTenantGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTenantGroup() throws Exception {
        int databaseSizeBeforeUpdate = tenantGroupRepository.findAll().size();

        // Create the TenantGroup
        TenantGroupDTO tenantGroupDTO = tenantGroupMapper.toDto(tenantGroup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTenantGroupMockMvc.perform(put("/api/tenant-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the TenantGroup in the database
        List<TenantGroup> tenantGroupList = tenantGroupRepository.findAll();
        assertThat(tenantGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTenantGroup() throws Exception {
        // Initialize the database
        tenantGroupRepository.saveAndFlush(tenantGroup);
        int databaseSizeBeforeDelete = tenantGroupRepository.findAll().size();

        // Get the tenantGroup
        restTenantGroupMockMvc.perform(delete("/api/tenant-groups/{id}", tenantGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TenantGroup> tenantGroupList = tenantGroupRepository.findAll();
        assertThat(tenantGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenantGroup.class);
        TenantGroup tenantGroup1 = new TenantGroup();
        tenantGroup1.setId(1L);
        TenantGroup tenantGroup2 = new TenantGroup();
        tenantGroup2.setId(tenantGroup1.getId());
        assertThat(tenantGroup1).isEqualTo(tenantGroup2);
        tenantGroup2.setId(2L);
        assertThat(tenantGroup1).isNotEqualTo(tenantGroup2);
        tenantGroup1.setId(null);
        assertThat(tenantGroup1).isNotEqualTo(tenantGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenantGroupDTO.class);
        TenantGroupDTO tenantGroupDTO1 = new TenantGroupDTO();
        tenantGroupDTO1.setId(1L);
        TenantGroupDTO tenantGroupDTO2 = new TenantGroupDTO();
        assertThat(tenantGroupDTO1).isNotEqualTo(tenantGroupDTO2);
        tenantGroupDTO2.setId(tenantGroupDTO1.getId());
        assertThat(tenantGroupDTO1).isEqualTo(tenantGroupDTO2);
        tenantGroupDTO2.setId(2L);
        assertThat(tenantGroupDTO1).isNotEqualTo(tenantGroupDTO2);
        tenantGroupDTO1.setId(null);
        assertThat(tenantGroupDTO1).isNotEqualTo(tenantGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tenantGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tenantGroupMapper.fromId(null)).isNull();
    }
}
