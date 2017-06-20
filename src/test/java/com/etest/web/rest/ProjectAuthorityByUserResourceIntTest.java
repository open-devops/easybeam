package com.easybeam.web.rest;

import com.easybeam.EasyBeamApp;

import com.easybeam.domain.ProjectAuthorityByUser;
import com.easybeam.repository.ProjectAuthorityByUserRepository;
import com.easybeam.service.ProjectAuthorityByUserService;
import com.easybeam.service.dto.ProjectAuthorityByUserDTO;
import com.easybeam.service.mapper.ProjectAuthorityByUserMapper;
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
 * Test class for the ProjectAuthorityByUserResource REST controller.
 *
 * @see ProjectAuthorityByUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyBeamApp.class)
public class ProjectAuthorityByUserResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectAuthorityByUserRepository projectAuthorityByUserRepository;

    @Autowired
    private ProjectAuthorityByUserMapper projectAuthorityByUserMapper;

    @Autowired
    private ProjectAuthorityByUserService projectAuthorityByUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjectAuthorityByUserMockMvc;

    private ProjectAuthorityByUser projectAuthorityByUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectAuthorityByUserResource projectAuthorityByUserResource = new ProjectAuthorityByUserResource(projectAuthorityByUserService);
        this.restProjectAuthorityByUserMockMvc = MockMvcBuilders.standaloneSetup(projectAuthorityByUserResource)
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
    public static ProjectAuthorityByUser createEntity(EntityManager em) {
        ProjectAuthorityByUser projectAuthorityByUser = new ProjectAuthorityByUser()
            .description(DEFAULT_DESCRIPTION);
        return projectAuthorityByUser;
    }

    @Before
    public void initTest() {
        projectAuthorityByUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectAuthorityByUser() throws Exception {
        int databaseSizeBeforeCreate = projectAuthorityByUserRepository.findAll().size();

        // Create the ProjectAuthorityByUser
        ProjectAuthorityByUserDTO projectAuthorityByUserDTO = projectAuthorityByUserMapper.toDto(projectAuthorityByUser);
        restProjectAuthorityByUserMockMvc.perform(post("/api/project-authority-by-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectAuthorityByUserDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjectAuthorityByUser in the database
        List<ProjectAuthorityByUser> projectAuthorityByUserList = projectAuthorityByUserRepository.findAll();
        assertThat(projectAuthorityByUserList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectAuthorityByUser testProjectAuthorityByUser = projectAuthorityByUserList.get(projectAuthorityByUserList.size() - 1);
        assertThat(testProjectAuthorityByUser.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectAuthorityByUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectAuthorityByUserRepository.findAll().size();

        // Create the ProjectAuthorityByUser with an existing ID
        projectAuthorityByUser.setId(1L);
        ProjectAuthorityByUserDTO projectAuthorityByUserDTO = projectAuthorityByUserMapper.toDto(projectAuthorityByUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectAuthorityByUserMockMvc.perform(post("/api/project-authority-by-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectAuthorityByUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProjectAuthorityByUser> projectAuthorityByUserList = projectAuthorityByUserRepository.findAll();
        assertThat(projectAuthorityByUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProjectAuthorityByUsers() throws Exception {
        // Initialize the database
        projectAuthorityByUserRepository.saveAndFlush(projectAuthorityByUser);

        // Get all the projectAuthorityByUserList
        restProjectAuthorityByUserMockMvc.perform(get("/api/project-authority-by-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectAuthorityByUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getProjectAuthorityByUser() throws Exception {
        // Initialize the database
        projectAuthorityByUserRepository.saveAndFlush(projectAuthorityByUser);

        // Get the projectAuthorityByUser
        restProjectAuthorityByUserMockMvc.perform(get("/api/project-authority-by-users/{id}", projectAuthorityByUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectAuthorityByUser.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectAuthorityByUser() throws Exception {
        // Get the projectAuthorityByUser
        restProjectAuthorityByUserMockMvc.perform(get("/api/project-authority-by-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectAuthorityByUser() throws Exception {
        // Initialize the database
        projectAuthorityByUserRepository.saveAndFlush(projectAuthorityByUser);
        int databaseSizeBeforeUpdate = projectAuthorityByUserRepository.findAll().size();

        // Update the projectAuthorityByUser
        ProjectAuthorityByUser updatedProjectAuthorityByUser = projectAuthorityByUserRepository.findOne(projectAuthorityByUser.getId());
        updatedProjectAuthorityByUser
            .description(UPDATED_DESCRIPTION);
        ProjectAuthorityByUserDTO projectAuthorityByUserDTO = projectAuthorityByUserMapper.toDto(updatedProjectAuthorityByUser);

        restProjectAuthorityByUserMockMvc.perform(put("/api/project-authority-by-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectAuthorityByUserDTO)))
            .andExpect(status().isOk());

        // Validate the ProjectAuthorityByUser in the database
        List<ProjectAuthorityByUser> projectAuthorityByUserList = projectAuthorityByUserRepository.findAll();
        assertThat(projectAuthorityByUserList).hasSize(databaseSizeBeforeUpdate);
        ProjectAuthorityByUser testProjectAuthorityByUser = projectAuthorityByUserList.get(projectAuthorityByUserList.size() - 1);
        assertThat(testProjectAuthorityByUser.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectAuthorityByUser() throws Exception {
        int databaseSizeBeforeUpdate = projectAuthorityByUserRepository.findAll().size();

        // Create the ProjectAuthorityByUser
        ProjectAuthorityByUserDTO projectAuthorityByUserDTO = projectAuthorityByUserMapper.toDto(projectAuthorityByUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectAuthorityByUserMockMvc.perform(put("/api/project-authority-by-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectAuthorityByUserDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjectAuthorityByUser in the database
        List<ProjectAuthorityByUser> projectAuthorityByUserList = projectAuthorityByUserRepository.findAll();
        assertThat(projectAuthorityByUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjectAuthorityByUser() throws Exception {
        // Initialize the database
        projectAuthorityByUserRepository.saveAndFlush(projectAuthorityByUser);
        int databaseSizeBeforeDelete = projectAuthorityByUserRepository.findAll().size();

        // Get the projectAuthorityByUser
        restProjectAuthorityByUserMockMvc.perform(delete("/api/project-authority-by-users/{id}", projectAuthorityByUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectAuthorityByUser> projectAuthorityByUserList = projectAuthorityByUserRepository.findAll();
        assertThat(projectAuthorityByUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectAuthorityByUser.class);
        ProjectAuthorityByUser projectAuthorityByUser1 = new ProjectAuthorityByUser();
        projectAuthorityByUser1.setId(1L);
        ProjectAuthorityByUser projectAuthorityByUser2 = new ProjectAuthorityByUser();
        projectAuthorityByUser2.setId(projectAuthorityByUser1.getId());
        assertThat(projectAuthorityByUser1).isEqualTo(projectAuthorityByUser2);
        projectAuthorityByUser2.setId(2L);
        assertThat(projectAuthorityByUser1).isNotEqualTo(projectAuthorityByUser2);
        projectAuthorityByUser1.setId(null);
        assertThat(projectAuthorityByUser1).isNotEqualTo(projectAuthorityByUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectAuthorityByUserDTO.class);
        ProjectAuthorityByUserDTO projectAuthorityByUserDTO1 = new ProjectAuthorityByUserDTO();
        projectAuthorityByUserDTO1.setId(1L);
        ProjectAuthorityByUserDTO projectAuthorityByUserDTO2 = new ProjectAuthorityByUserDTO();
        assertThat(projectAuthorityByUserDTO1).isNotEqualTo(projectAuthorityByUserDTO2);
        projectAuthorityByUserDTO2.setId(projectAuthorityByUserDTO1.getId());
        assertThat(projectAuthorityByUserDTO1).isEqualTo(projectAuthorityByUserDTO2);
        projectAuthorityByUserDTO2.setId(2L);
        assertThat(projectAuthorityByUserDTO1).isNotEqualTo(projectAuthorityByUserDTO2);
        projectAuthorityByUserDTO1.setId(null);
        assertThat(projectAuthorityByUserDTO1).isNotEqualTo(projectAuthorityByUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(projectAuthorityByUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(projectAuthorityByUserMapper.fromId(null)).isNull();
    }
}
