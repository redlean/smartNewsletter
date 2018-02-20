package com.redlean.news.web.rest;

import com.redlean.news.SmartNewsletterApp;

import com.redlean.news.domain.CompteConfig;
import com.redlean.news.repository.CompteConfigRepository;
import com.redlean.news.service.CompteConfigService;
import com.redlean.news.web.rest.errors.ExceptionTranslator;

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

import static com.redlean.news.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CompteConfigResource REST controller.
 *
 * @see CompteConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartNewsletterApp.class)
public class CompteConfigResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private CompteConfigRepository compteConfigRepository;

    @Autowired
    private CompteConfigService compteConfigService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompteConfigMockMvc;

    private CompteConfig compteConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompteConfigResource compteConfigResource = new CompteConfigResource(compteConfigService);
        this.restCompteConfigMockMvc = MockMvcBuilders.standaloneSetup(compteConfigResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompteConfig createEntity(EntityManager em) {
        CompteConfig compteConfig = new CompteConfig()
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD);
        return compteConfig;
    }

    @Before
    public void initTest() {
        compteConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompteConfig() throws Exception {
        int databaseSizeBeforeCreate = compteConfigRepository.findAll().size();

        // Create the CompteConfig
        restCompteConfigMockMvc.perform(post("/api/compte-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteConfig)))
            .andExpect(status().isCreated());

        // Validate the CompteConfig in the database
        List<CompteConfig> compteConfigList = compteConfigRepository.findAll();
        assertThat(compteConfigList).hasSize(databaseSizeBeforeCreate + 1);
        CompteConfig testCompteConfig = compteConfigList.get(compteConfigList.size() - 1);
        assertThat(testCompteConfig.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompteConfig.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createCompteConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compteConfigRepository.findAll().size();

        // Create the CompteConfig with an existing ID
        compteConfig.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompteConfigMockMvc.perform(post("/api/compte-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteConfig)))
            .andExpect(status().isBadRequest());

        // Validate the CompteConfig in the database
        List<CompteConfig> compteConfigList = compteConfigRepository.findAll();
        assertThat(compteConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = compteConfigRepository.findAll().size();
        // set the field null
        compteConfig.setEmail(null);

        // Create the CompteConfig, which fails.

        restCompteConfigMockMvc.perform(post("/api/compte-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteConfig)))
            .andExpect(status().isBadRequest());

        List<CompteConfig> compteConfigList = compteConfigRepository.findAll();
        assertThat(compteConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = compteConfigRepository.findAll().size();
        // set the field null
        compteConfig.setPassword(null);

        // Create the CompteConfig, which fails.

        restCompteConfigMockMvc.perform(post("/api/compte-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteConfig)))
            .andExpect(status().isBadRequest());

        List<CompteConfig> compteConfigList = compteConfigRepository.findAll();
        assertThat(compteConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompteConfigs() throws Exception {
        // Initialize the database
        compteConfigRepository.saveAndFlush(compteConfig);

        // Get all the compteConfigList
        restCompteConfigMockMvc.perform(get("/api/compte-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compteConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    @Transactional
    public void getCompteConfig() throws Exception {
        // Initialize the database
        compteConfigRepository.saveAndFlush(compteConfig);

        // Get the compteConfig
        restCompteConfigMockMvc.perform(get("/api/compte-configs/{id}", compteConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compteConfig.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompteConfig() throws Exception {
        // Get the compteConfig
        restCompteConfigMockMvc.perform(get("/api/compte-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompteConfig() throws Exception {
        // Initialize the database
        compteConfigService.save(compteConfig);

        int databaseSizeBeforeUpdate = compteConfigRepository.findAll().size();

        // Update the compteConfig
        CompteConfig updatedCompteConfig = compteConfigRepository.findOne(compteConfig.getId());
        // Disconnect from session so that the updates on updatedCompteConfig are not directly saved in db
        em.detach(updatedCompteConfig);
        updatedCompteConfig
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD);

        restCompteConfigMockMvc.perform(put("/api/compte-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompteConfig)))
            .andExpect(status().isOk());

        // Validate the CompteConfig in the database
        List<CompteConfig> compteConfigList = compteConfigRepository.findAll();
        assertThat(compteConfigList).hasSize(databaseSizeBeforeUpdate);
        CompteConfig testCompteConfig = compteConfigList.get(compteConfigList.size() - 1);
        assertThat(testCompteConfig.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompteConfig.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingCompteConfig() throws Exception {
        int databaseSizeBeforeUpdate = compteConfigRepository.findAll().size();

        // Create the CompteConfig

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompteConfigMockMvc.perform(put("/api/compte-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteConfig)))
            .andExpect(status().isCreated());

        // Validate the CompteConfig in the database
        List<CompteConfig> compteConfigList = compteConfigRepository.findAll();
        assertThat(compteConfigList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompteConfig() throws Exception {
        // Initialize the database
        compteConfigService.save(compteConfig);

        int databaseSizeBeforeDelete = compteConfigRepository.findAll().size();

        // Get the compteConfig
        restCompteConfigMockMvc.perform(delete("/api/compte-configs/{id}", compteConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompteConfig> compteConfigList = compteConfigRepository.findAll();
        assertThat(compteConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompteConfig.class);
        CompteConfig compteConfig1 = new CompteConfig();
        compteConfig1.setId(1L);
        CompteConfig compteConfig2 = new CompteConfig();
        compteConfig2.setId(compteConfig1.getId());
        assertThat(compteConfig1).isEqualTo(compteConfig2);
        compteConfig2.setId(2L);
        assertThat(compteConfig1).isNotEqualTo(compteConfig2);
        compteConfig1.setId(null);
        assertThat(compteConfig1).isNotEqualTo(compteConfig2);
    }
}
