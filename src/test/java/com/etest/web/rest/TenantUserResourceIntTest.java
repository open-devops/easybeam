package com.easybeam.web.rest;

import com.easybeam.EasyBeamApp;

import com.easybeam.domain.TenantUser;
import com.easybeam.repository.TenantUserRepository;
import com.easybeam.service.TenantUserService;
import com.easybeam.service.dto.TenantUserDTO;
import com.easybeam.service.mapper.TenantUserMapper;
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

import com.easybeam.domain.enumeration.LanguageKey;
/**
 * Test class for the TenantUserResource REST controller.
 *
 * @see TenantUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyBeamApp.class)
public class TenantUserResourceIntTest {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final LanguageKey DEFAULT_LANG_KEY = LanguageKey.ENGLISH;
    private static final LanguageKey UPDATED_LANG_KEY = LanguageKey.CHINESE;

    @Autowired
    private TenantUserRepository tenantUserRepository;

    @Autowired
    private TenantUserMapper tenantUserMapper;

    @Autowired
    private TenantUserService tenantUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTenantUserMockMvc;

    private TenantUser tenantUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TenantUserResource tenantUserResource = new TenantUserResource(tenantUserService);
        this.restTenantUserMockMvc = MockMvcBuilders.standaloneSetup(tenantUserResource)
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
    public static TenantUser createEntity(EntityManager em) {
        TenantUser tenantUser = new TenantUser()
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .langKey(DEFAULT_LANG_KEY);
        return tenantUser;
    }

    @Before
    public void initTest() {
        tenantUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createTenantUser() throws Exception {
        int databaseSizeBeforeCreate = tenantUserRepository.findAll().size();

        // Create the TenantUser
        TenantUserDTO tenantUserDTO = tenantUserMapper.toDto(tenantUser);
        restTenantUserMockMvc.perform(post("/api/tenant-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantUserDTO)))
            .andExpect(status().isCreated());

        // Validate the TenantUser in the database
        List<TenantUser> tenantUserList = tenantUserRepository.findAll();
        assertThat(tenantUserList).hasSize(databaseSizeBeforeCreate + 1);
        TenantUser testTenantUser = tenantUserList.get(tenantUserList.size() - 1);
        assertThat(testTenantUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testTenantUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testTenantUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTenantUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testTenantUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTenantUser.getLangKey()).isEqualTo(DEFAULT_LANG_KEY);
    }

    @Test
    @Transactional
    public void createTenantUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tenantUserRepository.findAll().size();

        // Create the TenantUser with an existing ID
        tenantUser.setId(1L);
        TenantUserDTO tenantUserDTO = tenantUserMapper.toDto(tenantUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTenantUserMockMvc.perform(post("/api/tenant-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TenantUser> tenantUserList = tenantUserRepository.findAll();
        assertThat(tenantUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = tenantUserRepository.findAll().size();
        // set the field null
        tenantUser.setLogin(null);

        // Create the TenantUser, which fails.
        TenantUserDTO tenantUserDTO = tenantUserMapper.toDto(tenantUser);

        restTenantUserMockMvc.perform(post("/api/tenant-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantUserDTO)))
            .andExpect(status().isBadRequest());

        List<TenantUser> tenantUserList = tenantUserRepository.findAll();
        assertThat(tenantUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = tenantUserRepository.findAll().size();
        // set the field null
        tenantUser.setPassword(null);

        // Create the TenantUser, which fails.
        TenantUserDTO tenantUserDTO = tenantUserMapper.toDto(tenantUser);

        restTenantUserMockMvc.perform(post("/api/tenant-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantUserDTO)))
            .andExpect(status().isBadRequest());

        List<TenantUser> tenantUserList = tenantUserRepository.findAll();
        assertThat(tenantUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTenantUsers() throws Exception {
        // Initialize the database
        tenantUserRepository.saveAndFlush(tenantUser);

        // Get all the tenantUserList
        restTenantUserMockMvc.perform(get("/api/tenant-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenantUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].langKey").value(hasItem(DEFAULT_LANG_KEY.toString())));
    }

    @Test
    @Transactional
    public void getTenantUser() throws Exception {
        // Initialize the database
        tenantUserRepository.saveAndFlush(tenantUser);

        // Get the tenantUser
        restTenantUserMockMvc.perform(get("/api/tenant-users/{id}", tenantUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tenantUser.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.langKey").value(DEFAULT_LANG_KEY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTenantUser() throws Exception {
        // Get the tenantUser
        restTenantUserMockMvc.perform(get("/api/tenant-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTenantUser() throws Exception {
        // Initialize the database
        tenantUserRepository.saveAndFlush(tenantUser);
        int databaseSizeBeforeUpdate = tenantUserRepository.findAll().size();

        // Update the tenantUser
        TenantUser updatedTenantUser = tenantUserRepository.findOne(tenantUser.getId());
        updatedTenantUser
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .langKey(UPDATED_LANG_KEY);
        TenantUserDTO tenantUserDTO = tenantUserMapper.toDto(updatedTenantUser);

        restTenantUserMockMvc.perform(put("/api/tenant-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantUserDTO)))
            .andExpect(status().isOk());

        // Validate the TenantUser in the database
        List<TenantUser> tenantUserList = tenantUserRepository.findAll();
        assertThat(tenantUserList).hasSize(databaseSizeBeforeUpdate);
        TenantUser testTenantUser = tenantUserList.get(tenantUserList.size() - 1);
        assertThat(testTenantUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testTenantUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testTenantUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTenantUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTenantUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTenantUser.getLangKey()).isEqualTo(UPDATED_LANG_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingTenantUser() throws Exception {
        int databaseSizeBeforeUpdate = tenantUserRepository.findAll().size();

        // Create the TenantUser
        TenantUserDTO tenantUserDTO = tenantUserMapper.toDto(tenantUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTenantUserMockMvc.perform(put("/api/tenant-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantUserDTO)))
            .andExpect(status().isCreated());

        // Validate the TenantUser in the database
        List<TenantUser> tenantUserList = tenantUserRepository.findAll();
        assertThat(tenantUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTenantUser() throws Exception {
        // Initialize the database
        tenantUserRepository.saveAndFlush(tenantUser);
        int databaseSizeBeforeDelete = tenantUserRepository.findAll().size();

        // Get the tenantUser
        restTenantUserMockMvc.perform(delete("/api/tenant-users/{id}", tenantUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TenantUser> tenantUserList = tenantUserRepository.findAll();
        assertThat(tenantUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenantUser.class);
        TenantUser tenantUser1 = new TenantUser();
        tenantUser1.setId(1L);
        TenantUser tenantUser2 = new TenantUser();
        tenantUser2.setId(tenantUser1.getId());
        assertThat(tenantUser1).isEqualTo(tenantUser2);
        tenantUser2.setId(2L);
        assertThat(tenantUser1).isNotEqualTo(tenantUser2);
        tenantUser1.setId(null);
        assertThat(tenantUser1).isNotEqualTo(tenantUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenantUserDTO.class);
        TenantUserDTO tenantUserDTO1 = new TenantUserDTO();
        tenantUserDTO1.setId(1L);
        TenantUserDTO tenantUserDTO2 = new TenantUserDTO();
        assertThat(tenantUserDTO1).isNotEqualTo(tenantUserDTO2);
        tenantUserDTO2.setId(tenantUserDTO1.getId());
        assertThat(tenantUserDTO1).isEqualTo(tenantUserDTO2);
        tenantUserDTO2.setId(2L);
        assertThat(tenantUserDTO1).isNotEqualTo(tenantUserDTO2);
        tenantUserDTO1.setId(null);
        assertThat(tenantUserDTO1).isNotEqualTo(tenantUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tenantUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tenantUserMapper.fromId(null)).isNull();
    }
}
