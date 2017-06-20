package com.easybeam.web.rest;

import com.easybeam.EasyBeamApp;

import com.easybeam.domain.ProjectAuthorityByGroup;
import com.easybeam.repository.ProjectAuthorityByGroupRepository;
import com.easybeam.service.ProjectAuthorityByGroupService;
import com.easybeam.service.dto.ProjectAuthorityByGroupDTO;
import com.easybeam.service.mapper.ProjectAuthorityByGroupMapper;
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
 * Test class for the ProjectAuthorityByGroupResource REST controller.
 *
 * @see ProjectAuthorityByGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyBeamApp.class)
public class ProjectAuthorityByGroupResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectAuthorityByGroupRepository projectAuthorityByGroupRepository;

    @Autowired
    private ProjectAuthorityByGroupMapper projectAuthorityByGroupMapper;

    @Autowired
    private ProjectAuthorityByGroupService projectAuthorityByGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjectAuthorityByGroupMockMvc;

    private ProjectAuthorityByGroup projectAuthorityByGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectAuthorityByGroupResource projectAuthorityByGroupResource = new ProjectAuthorityByGroupResource(projectAuthorityByGroupService);
        this.restProjectAuthorityByGroupMockMvc = MockMvcBuilders.standaloneSetup(projectAuthorityByGroupResource)
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
    public static ProjectAuthorityByGroup createEntity(EntityManager em) {
        ProjectAuthorityByGroup projectAuthorityByGroup = new ProjectAuthorityByGroup()
            .description(DEFAULT_DESCRIPTION);
        return projectAuthorityByGroup;
    }

    @Before
    public void initTest() {
        projectAuthorityByGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectAuthorityByGroup() throws Exception {
        int databaseSizeBeforeCreate = projectAuthorityByGroupRepository.findAll().size();

        // Create the ProjectAuthorityByGroup
        ProjectAuthorityByGroupDTO projectAuthorityByGroupDTO = projectAuthorityByGroupMapper.toDto(projectAuthorityByGroup);
        restProjectAuthorityByGroupMockMvc.perform(post("/api/project-authority-by-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectAuthorityByGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjectAuthorityByGroup in the database
        List<ProjectAuthorityByGroup> projectAuthorityByGroupList = projectAuthorityByGroupRepository.findAll();
        assertThat(projectAuthorityByGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectAuthorityByGroup testProjectAuthorityByGroup = projectAuthorityByGroupList.get(projectAuthorityByGroupList.size() - 1);
        assertThat(testProjectAuthorityByGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectAuthorityByGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectAuthorityByGroupRepository.findAll().size();

        // Create the ProjectAuthorityByGroup with an existing ID
        projectAuthorityByGroup.setId(1L);
        ProjectAuthorityByGroupDTO projectAuthorityByGroupDTO = projectAuthorityByGroupMapper.toDto(projectAuthorityByGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectAuthorityByGroupMockMvc.perform(post("/api/project-authority-by-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectAuthorityByGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProjectAuthorityByGroup> projectAuthorityByGroupList = projectAuthorityByGroupRepository.findAll();
        assertThat(projectAuthorityByGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProjectAuthorityByGroups() throws Exception {
        // Initialize the database
        projectAuthorityByGroupRepository.saveAndFlush(projectAuthorityByGroup);

        // Get all the projectAuthorityByGroupList
        restProjectAuthorityByGroupMockMvc.perform(get("/api/project-authority-by-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectAuthorityByGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getProjectAuthorityByGroup() throws Exception {
        // Initialize the database
        projectAuthorityByGroupRepository.saveAndFlush(projectAuthorityByGroup);

        // Get the projectAuthorityByGroup
        restProjectAuthorityByGroupMockMvc.perform(get("/api/project-authority-by-groups/{id}", projectAuthorityByGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectAuthorityByGroup.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectAuthorityByGroup() throws Exception {
        // Get the projectAuthorityByGroup
        restProjectAuthorityByGroupMockMvc.perform(get("/api/project-authority-by-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectAuthorityByGroup() throws Exception {
        // Initialize the database
        projectAuthorityByGroupRepository.saveAndFlush(projectAuthorityByGroup);
        int databaseSizeBeforeUpdate = projectAuthorityByGroupRepository.findAll().size();

        // Update the projectAuthorityByGroup
        ProjectAuthorityByGroup updatedProjectAuthorityByGroup = projectAuthorityByGroupRepository.findOne(projectAuthorityByGroup.getId());
        updatedProjectAuthorityByGroup
            .description(UPDATED_DESCRIPTION);
        ProjectAuthorityByGroupDTO projectAuthorityByGroupDTO = projectAuthorityByGroupMapper.toDto(updatedProjectAuthorityByGroup);

        restProjectAuthorityByGroupMockMvc.perform(put("/api/project-authority-by-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectAuthorityByGroupDTO)))
            .andExpect(status().isOk());

        // Validate the ProjectAuthorityByGroup in the database
        List<ProjectAuthorityByGroup> projectAuthorityByGroupList = projectAuthorityByGroupRepository.findAll();
        assertThat(projectAuthorityByGroupList).hasSize(databaseSizeBeforeUpdate);
        ProjectAuthorityByGroup testProjectAuthorityByGroup = projectAuthorityByGroupList.get(projectAuthorityByGroupList.size() - 1);
        assertThat(testProjectAuthorityByGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectAuthorityByGroup() throws Exception {
        int databaseSizeBeforeUpdate = projectAuthorityByGroupRepository.findAll().size();

        // Create the ProjectAuthorityByGroup
        ProjectAuthorityByGroupDTO projectAuthorityByGroupDTO = projectAuthorityByGroupMapper.toDto(projectAuthorityByGroup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectAuthorityByGroupMockMvc.perform(put("/api/project-authority-by-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectAuthorityByGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjectAuthorityByGroup in the database
        List<ProjectAuthorityByGroup> projectAuthorityByGroupList = projectAuthorityByGroupRepository.findAll();
        assertThat(projectAuthorityByGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjectAuthorityByGroup() throws Exception {
        // Initialize the database
        projectAuthorityByGroupRepository.saveAndFlush(projectAuthorityByGroup);
        int databaseSizeBeforeDelete = projectAuthorityByGroupRepository.findAll().size();

        // Get the projectAuthorityByGroup
        restProjectAuthorityByGroupMockMvc.perform(delete("/api/project-authority-by-groups/{id}", projectAuthorityByGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectAuthorityByGroup> projectAuthorityByGroupList = projectAuthorityByGroupRepository.findAll();
        assertThat(projectAuthorityByGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectAuthorityByGroup.class);
        ProjectAuthorityByGroup projectAuthorityByGroup1 = new ProjectAuthorityByGroup();
        projectAuthorityByGroup1.setId(1L);
        ProjectAuthorityByGroup projectAuthorityByGroup2 = new ProjectAuthorityByGroup();
        projectAuthorityByGroup2.setId(projectAuthorityByGroup1.getId());
        assertThat(projectAuthorityByGroup1).isEqualTo(projectAuthorityByGroup2);
        projectAuthorityByGroup2.setId(2L);
        assertThat(projectAuthorityByGroup1).isNotEqualTo(projectAuthorityByGroup2);
        projectAuthorityByGroup1.setId(null);
        assertThat(projectAuthorityByGroup1).isNotEqualTo(projectAuthorityByGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectAuthorityByGroupDTO.class);
        ProjectAuthorityByGroupDTO projectAuthorityByGroupDTO1 = new ProjectAuthorityByGroupDTO();
        projectAuthorityByGroupDTO1.setId(1L);
        ProjectAuthorityByGroupDTO projectAuthorityByGroupDTO2 = new ProjectAuthorityByGroupDTO();
        assertThat(projectAuthorityByGroupDTO1).isNotEqualTo(projectAuthorityByGroupDTO2);
        projectAuthorityByGroupDTO2.setId(projectAuthorityByGroupDTO1.getId());
        assertThat(projectAuthorityByGroupDTO1).isEqualTo(projectAuthorityByGroupDTO2);
        projectAuthorityByGroupDTO2.setId(2L);
        assertThat(projectAuthorityByGroupDTO1).isNotEqualTo(projectAuthorityByGroupDTO2);
        projectAuthorityByGroupDTO1.setId(null);
        assertThat(projectAuthorityByGroupDTO1).isNotEqualTo(projectAuthorityByGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(projectAuthorityByGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(projectAuthorityByGroupMapper.fromId(null)).isNull();
    }
}
