package com.redlean.news.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.redlean.news.domain.Planification_emails;

import com.redlean.news.repository.Planification_emailsRepository;
import com.redlean.news.service.AppConfig;
import com.redlean.news.service.PlanificationEmailsService;
import com.redlean.news.web.rest.errors.BadRequestAlertException;
import com.redlean.news.web.rest.util.HeaderUtil;
import com.redlean.news.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Planification_emails.
 */
@RestController
@RequestMapping("/api")
public class Planification_emailsResource {

//    private final Logger log = LoggerFactory.getLogger(Planification_emailsResource.class);
//
//    private static final String ENTITY_NAME = "planification_emails";
//    private final Planification_emailsRepository planification_emailsRepository;
//
//    public Planification_emailsResource(Planification_emailsRepository planification_emailsRepository ) {
//        this.planification_emailsRepository = planification_emailsRepository;
//    }
    private final Logger log = LoggerFactory.getLogger(Planification_emailsResource.class);

    private static final String ENTITY_NAME = "planificationEmails";

    private final PlanificationEmailsService planificationEmailsService;

    public Planification_emailsResource(PlanificationEmailsService planificationEmailsService) {
        this.planificationEmailsService = planificationEmailsService;
    }
    /**
     * POST  /planification-emails : Create a new planification_emails.
     *
     * @param planification_emails the planification_emails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planification_emails, or with status 400 (Bad Request) if the planification_emails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/planification-emails")
    @Timed
    public ResponseEntity<Planification_emails> createPlanification_emails(@Valid @RequestBody Planification_emails planification_emails) throws URISyntaxException {
        log.debug("REST request to save Planification_emails : {}", planification_emails);
        if (planification_emails.getId() != null) {
            throw new BadRequestAlertException("A new planification_emails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Planification_emails result = planificationEmailsService.save(planification_emails);
      //SendMailInDate();
        return ResponseEntity.created(new URI("/api/planification-emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /planification-emails : Updates an existing planification_emails.
     *
     * @param planification_emails the planification_emails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planification_emails,
     * or with status 400 (Bad Request) if the planification_emails is not valid,
     * or with status 500 (Internal Server Error) if the planification_emails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/planification-emails")
    @Timed
    public ResponseEntity<Planification_emails> updatePlanification_emails(@Valid @RequestBody Planification_emails planification_emails) throws URISyntaxException {
        log.debug("REST request to update Planification_emails : {}", planification_emails);
        if (planification_emails.getId() == null) {
            return createPlanification_emails(planification_emails);
        }
        Planification_emails result = planificationEmailsService.save(planification_emails);
       //this.SendMailInDate();
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planification_emails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /planification-emails : get all the planification_emails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of planification_emails in body
     */
    @GetMapping("/planification-emails")
    @Timed
    public ResponseEntity<List<Planification_emails>> getAllPlanification_emails(Pageable pageable) {
        log.debug("REST request to get a page of Planification_emails");
        Page<Planification_emails> page = planificationEmailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/planification-emails");
        //this.SendMailInDate();
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /planification-emails/:id : get the "id" planification_emails.
     *
     * @param id the id of the planification_emails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planification_emails, or with status 404 (Not Found)
     */
    @GetMapping("/planification-emails/{id}")
    @Timed
    public ResponseEntity<Planification_emails> getPlanification_emails(@PathVariable Long id) {
        log.debug("REST request to get Planification_emails : {}", id);
      //  this.SendMailInDate();
        Planification_emails planification_emails = planificationEmailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(planification_emails));
    }

    /**
     * DELETE  /planification-emails/:id : delete the "id" planification_emails.
     *
     * @param id the id of the planification_emails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/planification-emails/{id}")
    @Timed
    public ResponseEntity<Void> deletePlanification_emails(@PathVariable Long id) {
        log.debug("REST request to delete Planification_emails : {}", id);
        planificationEmailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


}
