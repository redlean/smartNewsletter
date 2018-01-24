//package com.redlean.news.web.rest;
//
//import com.redlean.news.SmartNewsletterApp;
//
//import com.redlean.news.domain.Planification_emails;
//import com.redlean.news.domain.Email;
//import com.redlean.news.repository.Planification_emailsRepository;
//import com.redlean.news.service.PlanificationEmailsService;
//import com.redlean.news.web.rest.errors.ExceptionTranslator;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//import static com.redlean.news.web.rest.TestUtil.createFormattingConversionService;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Test class for the Planification_emailsResource REST controller.
// *
// * @see Planification_emailsResource
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = SmartNewsletterApp.class)
//public class Planification_emailsResourceIntTest {
//
//    private static final String DEFAULT_PLANIF_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_PLANIF_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_EXPEDITEUR = "AAAAAAAAAA";
//    private static final String UPDATED_EXPEDITEUR = "BBBBBBBBBB";
//
//    private static final String DEFAULT_DESTINATAIRE = "AAAAAAAAAA";
//    private static final String UPDATED_DESTINATAIRE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
//    private static final String UPDATED_STATUS = "BBBBBBBBBB";
//
//    private static final String DEFAULT_DATE_PLANIF = "AAAAAAAAAA";
//    private static final String UPDATED_DATE_PLANIF = "BBBBBBBBBB";
//
//    /**
//     *
//     */
//    @Autowired
//    private Planification_emailsRepository planification_emailsRepository;
////    @Autowired
////    private PlanificationEmailsService planification_emailsRepository;
//
//    @Autowired
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//    @Autowired
//    private ExceptionTranslator exceptionTranslator;
//
//    @Autowired
//    private EntityManager em;
//
//    private MockMvc restPlanification_emailsMockMvc;
//
//    private Planification_emails planification_emails;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final Planification_emailsRepository planification_emailsResource;
//        planification_emailsResource = new Planification_emailsRepository(planification_emailsRepository);
//        this.restPlanification_emailsMockMvc = MockMvcBuilders.standaloneSetup(planification_emailsResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setConversionService(createFormattingConversionService())
//            .setMessageConverters(jacksonMessageConverter).build();
//    }
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Planification_emails createEntity(EntityManager em) {
//        Planification_emails planification_emails = new Planification_emails()
//            .planifName(DEFAULT_PLANIF_NAME)
//            .expediteur(DEFAULT_EXPEDITEUR)
//            .destinataire(DEFAULT_DESTINATAIRE)
//            .status(DEFAULT_STATUS)
//            .datePlanif(DEFAULT_DATE_PLANIF);
//        // Add required entity
//        Email planifForEmail = EmailResourceIntTest.createEntity(em);
//        em.persist(planifForEmail);
//        em.flush();
//        planification_emails.setPlanifForEmail(planifForEmail);
//        return planification_emails;
//    }
//
//    @Before
//    public void initTest() {
//        planification_emails = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createPlanification_emails() throws Exception {
//        int databaseSizeBeforeCreate = planification_emailsRepository.findAll().size();
//
//        // Create the Planification_emails
//        restPlanification_emailsMockMvc.perform(post("/api/planification-emails")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(planification_emails)))
//            .andExpect(status().isCreated());
//
//        // Validate the Planification_emails in the database
//        List<Planification_emails> planification_emailsList = planification_emailsRepository.findAll();
//        assertThat(planification_emailsList).hasSize(databaseSizeBeforeCreate + 1);
//        Planification_emails testPlanification_emails = planification_emailsList.get(planification_emailsList.size() - 1);
//        assertThat(testPlanification_emails.getPlanifName()).isEqualTo(DEFAULT_PLANIF_NAME);
//        assertThat(testPlanification_emails.getExpediteur()).isEqualTo(DEFAULT_EXPEDITEUR);
//        assertThat(testPlanification_emails.getDestinataire()).isEqualTo(DEFAULT_DESTINATAIRE);
//        assertThat(testPlanification_emails.getStatus()).isEqualTo(DEFAULT_STATUS);
//        assertThat(testPlanification_emails.getDatePlanif()).isEqualTo(DEFAULT_DATE_PLANIF);
//    }
//
//    @Test
//    @Transactional
//    public void createPlanification_emailsWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = planification_emailsRepository.findAll().size();
//
//        // Create the Planification_emails with an existing ID
//        planification_emails.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restPlanification_emailsMockMvc.perform(post("/api/planification-emails")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(planification_emails)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Planification_emails in the database
//        List<Planification_emails> planification_emailsList = planification_emailsRepository.findAll();
//        assertThat(planification_emailsList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    public void checkPlanifNameIsRequired() throws Exception {
//        int databaseSizeBeforeTest = planification_emailsRepository.findAll().size();
//        // set the field null
//        planification_emails.setPlanifName(null);
//
//        // Create the Planification_emails, which fails.
//
//        restPlanification_emailsMockMvc.perform(post("/api/planification-emails")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(planification_emails)))
//            .andExpect(status().isBadRequest());
//
//        List<Planification_emails> planification_emailsList = planification_emailsRepository.findAll();
//        assertThat(planification_emailsList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkExpediteurIsRequired() throws Exception {
//        int databaseSizeBeforeTest = planification_emailsRepository.findAll().size();
//        // set the field null
//        planification_emails.setExpediteur(null);
//
//        // Create the Planification_emails, which fails.
//
//        restPlanification_emailsMockMvc.perform(post("/api/planification-emails")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(planification_emails)))
//            .andExpect(status().isBadRequest());
//
//        List<Planification_emails> planification_emailsList = planification_emailsRepository.findAll();
//        assertThat(planification_emailsList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkDestinataireIsRequired() throws Exception {
//        int databaseSizeBeforeTest = planification_emailsRepository.findAll().size();
//        // set the field null
//        planification_emails.setDestinataire(null);
//
//        // Create the Planification_emails, which fails.
//
//        restPlanification_emailsMockMvc.perform(post("/api/planification-emails")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(planification_emails)))
//            .andExpect(status().isBadRequest());
//
//        List<Planification_emails> planification_emailsList = planification_emailsRepository.findAll();
//        assertThat(planification_emailsList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkStatusIsRequired() throws Exception {
//        int databaseSizeBeforeTest = planification_emailsRepository.findAll().size();
//        // set the field null
//        planification_emails.setStatus(null);
//
//        // Create the Planification_emails, which fails.
//
//        restPlanification_emailsMockMvc.perform(post("/api/planification-emails")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(planification_emails)))
//            .andExpect(status().isBadRequest());
//
//        List<Planification_emails> planification_emailsList = planification_emailsRepository.findAll();
//        assertThat(planification_emailsList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkDatePlanifIsRequired() throws Exception {
//        int databaseSizeBeforeTest = planification_emailsRepository.findAll().size();
//        // set the field null
//        planification_emails.setDatePlanif(null);
//
//        // Create the Planification_emails, which fails.
//
//        restPlanification_emailsMockMvc.perform(post("/api/planification-emails")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(planification_emails)))
//            .andExpect(status().isBadRequest());
//
//        List<Planification_emails> planification_emailsList = planification_emailsRepository.findAll();
//        assertThat(planification_emailsList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllPlanification_emails() throws Exception {
//        // Initialize the database
//        planification_emailsRepository.saveAndFlush(planification_emails);
//
//        // Get all the planification_emailsList
//        restPlanification_emailsMockMvc.perform(get("/api/planification-emails?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(planification_emails.getId().intValue())))
//            .andExpect(jsonPath("$.[*].planifName").value(hasItem(DEFAULT_PLANIF_NAME.toString())))
//            .andExpect(jsonPath("$.[*].expediteur").value(hasItem(DEFAULT_EXPEDITEUR.toString())))
//            .andExpect(jsonPath("$.[*].destinataire").value(hasItem(DEFAULT_DESTINATAIRE.toString())))
//            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
//            .andExpect(jsonPath("$.[*].datePlanif").value(hasItem(DEFAULT_DATE_PLANIF.toString())));
//    }
//
//    @Test
//    @Transactional
//    public void getPlanification_emails() throws Exception {
//        // Initialize the database
//        planification_emailsRepository.saveAndFlush(planification_emails);
//
//        // Get the planification_emails
//        restPlanification_emailsMockMvc.perform(get("/api/planification-emails/{id}", planification_emails.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(planification_emails.getId().intValue()))
//            .andExpect(jsonPath("$.planifName").value(DEFAULT_PLANIF_NAME.toString()))
//            .andExpect(jsonPath("$.expediteur").value(DEFAULT_EXPEDITEUR.toString()))
//            .andExpect(jsonPath("$.destinataire").value(DEFAULT_DESTINATAIRE.toString()))
//            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
//            .andExpect(jsonPath("$.datePlanif").value(DEFAULT_DATE_PLANIF.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingPlanification_emails() throws Exception {
//        // Get the planification_emails
//        restPlanification_emailsMockMvc.perform(get("/api/planification-emails/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updatePlanification_emails() throws Exception {
//        // Initialize the database
//        planification_emailsRepository.saveAndFlush(planification_emails);
//        int databaseSizeBeforeUpdate = planification_emailsRepository.findAll().size();
//
//        // Update the planification_emails
//        Planification_emails updatedPlanification_emails = planification_emailsRepository.findOne(planification_emails.getId());
//        // Disconnect from session so that the updates on updatedPlanification_emails are not directly saved in db
//        em.detach(updatedPlanification_emails);
//        updatedPlanification_emails
//            .planifName(UPDATED_PLANIF_NAME)
//            .expediteur(UPDATED_EXPEDITEUR)
//            .destinataire(UPDATED_DESTINATAIRE)
//            .status(UPDATED_STATUS)
//            .datePlanif(UPDATED_DATE_PLANIF);
//
//        restPlanification_emailsMockMvc.perform(put("/api/planification-emails")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedPlanification_emails)))
//            .andExpect(status().isOk());
//
//        // Validate the Planification_emails in the database
//        List<Planification_emails> planification_emailsList = planification_emailsRepository.findAll();
//        assertThat(planification_emailsList).hasSize(databaseSizeBeforeUpdate);
//        Planification_emails testPlanification_emails = planification_emailsList.get(planification_emailsList.size() - 1);
//        assertThat(testPlanification_emails.getPlanifName()).isEqualTo(UPDATED_PLANIF_NAME);
//        assertThat(testPlanification_emails.getExpediteur()).isEqualTo(UPDATED_EXPEDITEUR);
//        assertThat(testPlanification_emails.getDestinataire()).isEqualTo(UPDATED_DESTINATAIRE);
//        assertThat(testPlanification_emails.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testPlanification_emails.getDatePlanif()).isEqualTo(UPDATED_DATE_PLANIF);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingPlanification_emails() throws Exception {
//        int databaseSizeBeforeUpdate = planification_emailsRepository.findAll().size();
//
//        // Create the Planification_emails
//
//        // If the entity doesn't have an ID, it will be created instead of just being updated
//        restPlanification_emailsMockMvc.perform(put("/api/planification-emails")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(planification_emails)))
//            .andExpect(status().isCreated());
//
//        // Validate the Planification_emails in the database
//        List<Planification_emails> planification_emailsList = planification_emailsRepository.findAll();
//        assertThat(planification_emailsList).hasSize(databaseSizeBeforeUpdate + 1);
//    }
//
//    @Test
//    @Transactional
//    public void deletePlanification_emails() throws Exception {
//        // Initialize the database
//        planification_emailsRepository.saveAndFlush(planification_emails);
//        int databaseSizeBeforeDelete = planification_emailsRepository.findAll().size();
//
//        // Get the planification_emails
//        restPlanification_emailsMockMvc.perform(delete("/api/planification-emails/{id}", planification_emails.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<Planification_emails> planification_emailsList = planification_emailsRepository.findAll();
//        assertThat(planification_emailsList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Planification_emails.class);
//        Planification_emails planification_emails1 = new Planification_emails();
//        planification_emails1.setId(1L);
//        Planification_emails planification_emails2 = new Planification_emails();
//        planification_emails2.setId(planification_emails1.getId());
//        assertThat(planification_emails1).isEqualTo(planification_emails2);
//        planification_emails2.setId(2L);
//        assertThat(planification_emails1).isNotEqualTo(planification_emails2);
//        planification_emails1.setId(null);
//        assertThat(planification_emails1).isNotEqualTo(planification_emails2);
//    }
//}
