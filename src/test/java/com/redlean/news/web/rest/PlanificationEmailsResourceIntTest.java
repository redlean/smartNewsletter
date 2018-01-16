package com.redlean.news.web.rest;

import com.redlean.news.SmartNewsApp;

import com.redlean.news.domain.PlanificationEmails;
import com.redlean.news.domain.Email;
import com.redlean.news.repository.PlanificationEmailsRepository;
import com.redlean.news.service.PlanificationEmailsService;
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
 * Test class for the PlanificationEmailsResource REST controller.
 *
 * @see PlanificationEmailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartNewsApp.class)
public class PlanificationEmailsResourceIntTest {

    private static final String DEFAULT_TACHE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TACHE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXPEDITEUR = "AAAAAAAAAA";
    private static final String UPDATED_EXPEDITEUR = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATAIRES = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATAIRES = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_PLANIF = "AAAAAAAAAA";
    private static final String UPDATED_DATE_PLANIF = "BBBBBBBBBB";

    @Autowired
    private PlanificationEmailsRepository planificationEmailsRepository;

    @Autowired
    private PlanificationEmailsService planificationEmailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlanificationEmailsMockMvc;

    private PlanificationEmails planificationEmails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanificationEmailsResource planificationEmailsResource = new PlanificationEmailsResource(planificationEmailsService);
        this.restPlanificationEmailsMockMvc = MockMvcBuilders.standaloneSetup(planificationEmailsResource)
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
    public static PlanificationEmails createEntity(EntityManager em) {
        PlanificationEmails planificationEmails = new PlanificationEmails()
            .tacheName(DEFAULT_TACHE_NAME)
            .expediteur(DEFAULT_EXPEDITEUR)
            .destinataires(DEFAULT_DESTINATAIRES)
            .status(DEFAULT_STATUS)
            .datePlanif(DEFAULT_DATE_PLANIF);
        // Add required entity
        Email planifForEmail = EmailResourceIntTest.createEntity(em);
        em.persist(planifForEmail);
        em.flush();
        planificationEmails.setPlanifForEmail(planifForEmail);
        return planificationEmails;
    }

    @Before
    public void initTest() {
        planificationEmails = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanificationEmails() throws Exception {
        int databaseSizeBeforeCreate = planificationEmailsRepository.findAll().size();

        // Create the PlanificationEmails
        restPlanificationEmailsMockMvc.perform(post("/api/planification-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planificationEmails)))
            .andExpect(status().isCreated());

        // Validate the PlanificationEmails in the database
        List<PlanificationEmails> planificationEmailsList = planificationEmailsRepository.findAll();
        assertThat(planificationEmailsList).hasSize(databaseSizeBeforeCreate + 1);
        PlanificationEmails testPlanificationEmails = planificationEmailsList.get(planificationEmailsList.size() - 1);
        assertThat(testPlanificationEmails.getTacheName()).isEqualTo(DEFAULT_TACHE_NAME);
        assertThat(testPlanificationEmails.getExpediteur()).isEqualTo(DEFAULT_EXPEDITEUR);
        assertThat(testPlanificationEmails.getDestinataires()).isEqualTo(DEFAULT_DESTINATAIRES);
        assertThat(testPlanificationEmails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPlanificationEmails.getDatePlanif()).isEqualTo(DEFAULT_DATE_PLANIF);
    }

    @Test
    @Transactional
    public void createPlanificationEmailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planificationEmailsRepository.findAll().size();

        // Create the PlanificationEmails with an existing ID
        planificationEmails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanificationEmailsMockMvc.perform(post("/api/planification-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planificationEmails)))
            .andExpect(status().isBadRequest());

        // Validate the PlanificationEmails in the database
        List<PlanificationEmails> planificationEmailsList = planificationEmailsRepository.findAll();
        assertThat(planificationEmailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDatePlanifIsRequired() throws Exception {
        int databaseSizeBeforeTest = planificationEmailsRepository.findAll().size();
        // set the field null
        planificationEmails.setDatePlanif(null);

        // Create the PlanificationEmails, which fails.

        restPlanificationEmailsMockMvc.perform(post("/api/planification-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planificationEmails)))
            .andExpect(status().isBadRequest());

        List<PlanificationEmails> planificationEmailsList = planificationEmailsRepository.findAll();
        assertThat(planificationEmailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanificationEmails() throws Exception {
        // Initialize the database
        planificationEmailsRepository.saveAndFlush(planificationEmails);

        // Get all the planificationEmailsList
        restPlanificationEmailsMockMvc.perform(get("/api/planification-emails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planificationEmails.getId().intValue())))
            .andExpect(jsonPath("$.[*].tacheName").value(hasItem(DEFAULT_TACHE_NAME.toString())))
            .andExpect(jsonPath("$.[*].expediteur").value(hasItem(DEFAULT_EXPEDITEUR.toString())))
            .andExpect(jsonPath("$.[*].destinataires").value(hasItem(DEFAULT_DESTINATAIRES.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].datePlanif").value(hasItem(DEFAULT_DATE_PLANIF.toString())));
    }

    @Test
    @Transactional
    public void getPlanificationEmails() throws Exception {
        // Initialize the database
        planificationEmailsRepository.saveAndFlush(planificationEmails);

        // Get the planificationEmails
        restPlanificationEmailsMockMvc.perform(get("/api/planification-emails/{id}", planificationEmails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planificationEmails.getId().intValue()))
            .andExpect(jsonPath("$.tacheName").value(DEFAULT_TACHE_NAME.toString()))
            .andExpect(jsonPath("$.expediteur").value(DEFAULT_EXPEDITEUR.toString()))
            .andExpect(jsonPath("$.destinataires").value(DEFAULT_DESTINATAIRES.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.datePlanif").value(DEFAULT_DATE_PLANIF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlanificationEmails() throws Exception {
        // Get the planificationEmails
        restPlanificationEmailsMockMvc.perform(get("/api/planification-emails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanificationEmails() throws Exception {
        // Initialize the database
        planificationEmailsService.save(planificationEmails);

        int databaseSizeBeforeUpdate = planificationEmailsRepository.findAll().size();

        // Update the planificationEmails
        PlanificationEmails updatedPlanificationEmails = planificationEmailsRepository.findOne(planificationEmails.getId());
        // Disconnect from session so that the updates on updatedPlanificationEmails are not directly saved in db
        em.detach(updatedPlanificationEmails);
        updatedPlanificationEmails
            .tacheName(UPDATED_TACHE_NAME)
            .expediteur(UPDATED_EXPEDITEUR)
            .destinataires(UPDATED_DESTINATAIRES)
            .status(UPDATED_STATUS)
            .datePlanif(UPDATED_DATE_PLANIF);

        restPlanificationEmailsMockMvc.perform(put("/api/planification-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlanificationEmails)))
            .andExpect(status().isOk());

        // Validate the PlanificationEmails in the database
        List<PlanificationEmails> planificationEmailsList = planificationEmailsRepository.findAll();
        assertThat(planificationEmailsList).hasSize(databaseSizeBeforeUpdate);
        PlanificationEmails testPlanificationEmails = planificationEmailsList.get(planificationEmailsList.size() - 1);
        assertThat(testPlanificationEmails.getTacheName()).isEqualTo(UPDATED_TACHE_NAME);
        assertThat(testPlanificationEmails.getExpediteur()).isEqualTo(UPDATED_EXPEDITEUR);
        assertThat(testPlanificationEmails.getDestinataires()).isEqualTo(UPDATED_DESTINATAIRES);
        assertThat(testPlanificationEmails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPlanificationEmails.getDatePlanif()).isEqualTo(UPDATED_DATE_PLANIF);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanificationEmails() throws Exception {
        int databaseSizeBeforeUpdate = planificationEmailsRepository.findAll().size();

        // Create the PlanificationEmails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlanificationEmailsMockMvc.perform(put("/api/planification-emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planificationEmails)))
            .andExpect(status().isCreated());

        // Validate the PlanificationEmails in the database
        List<PlanificationEmails> planificationEmailsList = planificationEmailsRepository.findAll();
        assertThat(planificationEmailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlanificationEmails() throws Exception {
        // Initialize the database
        planificationEmailsService.save(planificationEmails);

        int databaseSizeBeforeDelete = planificationEmailsRepository.findAll().size();

        // Get the planificationEmails
        restPlanificationEmailsMockMvc.perform(delete("/api/planification-emails/{id}", planificationEmails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlanificationEmails> planificationEmailsList = planificationEmailsRepository.findAll();
        assertThat(planificationEmailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanificationEmails.class);
        PlanificationEmails planificationEmails1 = new PlanificationEmails();
        planificationEmails1.setId(1L);
        PlanificationEmails planificationEmails2 = new PlanificationEmails();
        planificationEmails2.setId(planificationEmails1.getId());
        assertThat(planificationEmails1).isEqualTo(planificationEmails2);
        planificationEmails2.setId(2L);
        assertThat(planificationEmails1).isNotEqualTo(planificationEmails2);
        planificationEmails1.setId(null);
        assertThat(planificationEmails1).isNotEqualTo(planificationEmails2);
    }
}
