package com.redlean.news.web.rest;

import com.redlean.news.SmartNewsApp;

import com.redlean.news.domain.Tache;
import com.redlean.news.repository.TacheRepository;
import com.redlean.news.service.TacheService;
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
 * Test class for the TacheResource REST controller.
 *
 * @see TacheResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartNewsApp.class)
public class TacheResourceIntTest {

    private static final String DEFAULT_DATE_ENVOIE = "AAAAAAAAAA";
    private static final String UPDATED_DATE_ENVOIE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_LOG = "AAAAAAAAAA";
    private static final String UPDATED_LOG = "BBBBBBBBBB";

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private TacheService tacheService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTacheMockMvc;

    private Tache tache;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TacheResource tacheResource = new TacheResource(tacheService);
        this.restTacheMockMvc = MockMvcBuilders.standaloneSetup(tacheResource)
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
    public static Tache createEntity(EntityManager em) {
        Tache tache = new Tache()
            .dateEnvoie(DEFAULT_DATE_ENVOIE)
            .status(DEFAULT_STATUS)
            .log(DEFAULT_LOG);
        return tache;
    }

    @Before
    public void initTest() {
        tache = createEntity(em);
    }

    @Test
    @Transactional
    public void createTache() throws Exception {
        int databaseSizeBeforeCreate = tacheRepository.findAll().size();

        // Create the Tache
        restTacheMockMvc.perform(post("/api/taches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tache)))
            .andExpect(status().isCreated());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeCreate + 1);
        Tache testTache = tacheList.get(tacheList.size() - 1);
        assertThat(testTache.getDateEnvoie()).isEqualTo(DEFAULT_DATE_ENVOIE);
        assertThat(testTache.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTache.getLog()).isEqualTo(DEFAULT_LOG);
    }

    @Test
    @Transactional
    public void createTacheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tacheRepository.findAll().size();

        // Create the Tache with an existing ID
        tache.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTacheMockMvc.perform(post("/api/taches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tache)))
            .andExpect(status().isBadRequest());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateEnvoieIsRequired() throws Exception {
        int databaseSizeBeforeTest = tacheRepository.findAll().size();
        // set the field null
        tache.setDateEnvoie(null);

        // Create the Tache, which fails.

        restTacheMockMvc.perform(post("/api/taches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tache)))
            .andExpect(status().isBadRequest());

        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = tacheRepository.findAll().size();
        // set the field null
        tache.setStatus(null);

        // Create the Tache, which fails.

        restTacheMockMvc.perform(post("/api/taches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tache)))
            .andExpect(status().isBadRequest());

        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaches() throws Exception {
        // Initialize the database
        tacheRepository.saveAndFlush(tache);

        // Get all the tacheList
        restTacheMockMvc.perform(get("/api/taches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tache.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateEnvoie").value(hasItem(DEFAULT_DATE_ENVOIE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].log").value(hasItem(DEFAULT_LOG.toString())));
    }

    @Test
    @Transactional
    public void getTache() throws Exception {
        // Initialize the database
        tacheRepository.saveAndFlush(tache);

        // Get the tache
        restTacheMockMvc.perform(get("/api/taches/{id}", tache.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tache.getId().intValue()))
            .andExpect(jsonPath("$.dateEnvoie").value(DEFAULT_DATE_ENVOIE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.log").value(DEFAULT_LOG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTache() throws Exception {
        // Get the tache
        restTacheMockMvc.perform(get("/api/taches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTache() throws Exception {
        // Initialize the database
        tacheService.save(tache);

        int databaseSizeBeforeUpdate = tacheRepository.findAll().size();

        // Update the tache
        Tache updatedTache = tacheRepository.findOne(tache.getId());
        // Disconnect from session so that the updates on updatedTache are not directly saved in db
        em.detach(updatedTache);
        updatedTache
            .dateEnvoie(UPDATED_DATE_ENVOIE)
            .status(UPDATED_STATUS)
            .log(UPDATED_LOG);

        restTacheMockMvc.perform(put("/api/taches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTache)))
            .andExpect(status().isOk());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeUpdate);
        Tache testTache = tacheList.get(tacheList.size() - 1);
        assertThat(testTache.getDateEnvoie()).isEqualTo(UPDATED_DATE_ENVOIE);
        assertThat(testTache.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTache.getLog()).isEqualTo(UPDATED_LOG);
    }

    @Test
    @Transactional
    public void updateNonExistingTache() throws Exception {
        int databaseSizeBeforeUpdate = tacheRepository.findAll().size();

        // Create the Tache

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTacheMockMvc.perform(put("/api/taches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tache)))
            .andExpect(status().isCreated());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTache() throws Exception {
        // Initialize the database
        tacheService.save(tache);

        int databaseSizeBeforeDelete = tacheRepository.findAll().size();

        // Get the tache
        restTacheMockMvc.perform(delete("/api/taches/{id}", tache.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tache.class);
        Tache tache1 = new Tache();
        tache1.setId(1L);
        Tache tache2 = new Tache();
        tache2.setId(tache1.getId());
        assertThat(tache1).isEqualTo(tache2);
        tache2.setId(2L);
        assertThat(tache1).isNotEqualTo(tache2);
        tache1.setId(null);
        assertThat(tache1).isNotEqualTo(tache2);
    }
}
