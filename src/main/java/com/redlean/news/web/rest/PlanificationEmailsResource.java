package com.redlean.news.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.redlean.news.domain.PlanificationEmails;
import com.redlean.news.service.AppConfig;
import com.redlean.news.service.PlanificationEmailsService;
import com.redlean.news.web.rest.errors.BadRequestAlertException;
import com.redlean.news.web.rest.util.HeaderUtil;
import com.redlean.news.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.FileSystemResource;
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
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;

/**
 * REST controller for managing PlanificationEmails.
 */

@RestController
@RequestMapping("/api")
public class PlanificationEmailsResource {

    private final Logger log = LoggerFactory.getLogger(PlanificationEmailsResource.class);

    private static final String ENTITY_NAME = "planificationEmails";

    private final PlanificationEmailsService planificationEmailsService;

    public PlanificationEmailsResource(PlanificationEmailsService planificationEmailsService) {
        this.planificationEmailsService = planificationEmailsService;
    }

    /**
     * POST  /planification-emails : Create a new planificationEmails.
     *
     * @param planificationEmails the planificationEmails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planificationEmails, or with status 400 (Bad Request) if the planificationEmails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/planification-emails")
    @Timed
    public ResponseEntity<PlanificationEmails> createPlanificationEmails(@Valid @RequestBody PlanificationEmails planificationEmails) throws URISyntaxException {
        log.debug("REST request to save PlanificationEmails : {}", planificationEmails);
        if (planificationEmails.getId() != null) {
            throw new BadRequestAlertException("A new planificationEmails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanificationEmails result = planificationEmailsService.save(planificationEmails);
        if (result != null){
            planificationEmailsService.SendMailInDate();
        }
        return ResponseEntity.created(new URI("/api/planification-emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    @PostMapping("/planification-emails/send")
//    @Timed
//@Scheduled(cron = "0 1 1 ? * *")
    public ResponseEntity<PlanificationEmails> sendPlanificationEmails(@Valid @RequestBody PlanificationEmails planificationEmails) throws URISyntaxException, MessagingException, SchedulerException, ParseException {
        System.out.println(planificationEmails);

//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//
//        JobDetail job = newJob(SendEmailJob.class)
//            .withIdentity("cronJob", "SendEmailJob")
//            .build();
//
//        String startDateStr = "2013-09-27 00:00:00.0";
//        String endDateStr = "2013-09-31 00:00:00.0";
//
//        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(startDateStr);
//        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(endDateStr);
//
//        CronTrigger cronTrigger = newTrigger()
//            .withIdentity("trigger1", "testJob")
//            .startAt(startDate)
//            .withSchedule(CronScheduleBuilder.cronSchedule("0 0 9-12 * * ?").withMisfireHandlingInstructionDoNothing())
//            .endAt(endDate)
//            .build();
//
//        scheduler.scheduleJob(job, cronTrigger);
//        scheduler.start();


        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        JavaMailSenderImpl mailSender = ctx.getBean(JavaMailSenderImpl.class);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //Pass true flag for multipart message
        MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage, true);
        mailMsg.setFrom(planificationEmails.getExpediteur());
        mailMsg.setTo(planificationEmails.getDestinataires());
        mailMsg.setSubject(planificationEmails.getPlanifForEmail().getObjet());
        mailMsg.setText(planificationEmails.getPlanifForEmail().getContenu());
   //     mailMsg.setSentDate(planificationEmails.getDatePlanif().);
        //FileSystemResource object for Attachment
        FileSystemResource file = new FileSystemResource(new File("/home/redlean/boitier.png"));
        mailMsg.addAttachment("boitier.png", file);
        mailSender.send(mimeMessage);
        System.out.println("---Done---");
        return ResponseEntity.ok(planificationEmails);
       // return mimeMessage;
    }


    /**
     * PUT  /planification-emails : Updates an existing planificationEmails.
     *
     * @param planificationEmails the planificationEmails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planificationEmails,
     * or with status 400 (Bad Request) if the planificationEmails is not valid,
     * or with status 500 (Internal Server Error) if the planificationEmails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/planification-emails")
    @Timed
    public ResponseEntity<PlanificationEmails> updatePlanificationEmails(@Valid @RequestBody PlanificationEmails planificationEmails) throws URISyntaxException {
        log.debug("REST request to update PlanificationEmails : {}", planificationEmails);
        if (planificationEmails.getId() == null) {
            return createPlanificationEmails(planificationEmails);
        }
        PlanificationEmails result = planificationEmailsService.save(planificationEmails);
        if (result != null){
            planificationEmailsService.SendMailInDate();
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planificationEmails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /planification-emails : get all the planificationEmails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of planificationEmails in body
     */
    @GetMapping("/planification-emails")
    @Timed
    public ResponseEntity<List<PlanificationEmails>> getAllPlanificationEmails(Pageable pageable) {
        log.debug("REST request to get a page of PlanificationEmails");
        Page<PlanificationEmails> page = planificationEmailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/planification-emails");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /planification-emails/:id : get the "id" planificationEmails.
     *
     * @param id the id of the planificationEmails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planificationEmails, or with status 404 (Not Found)
     */
    @GetMapping("/planification-emails/{id}")
    @Timed
    public ResponseEntity<PlanificationEmails> getPlanificationEmails(@PathVariable Long id) {
        log.debug("REST request to get PlanificationEmails : {}", id);
        PlanificationEmails planificationEmails = planificationEmailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(planificationEmails));
    }

    /**
     * DELETE  /planification-emails/:id : delete the "id" planificationEmails.
     *
     * @param id the id of the planificationEmails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/planification-emails/{id}")
    @Timed
    public ResponseEntity<Void> deletePlanificationEmails(@PathVariable Long id) {
        log.debug("REST request to delete PlanificationEmails : {}", id);
        planificationEmailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
//
//class SendEmailJob implements Job {
//    private final PlanificationEmailsService planificationEmailsService;
//    public SendEmailJob(PlanificationEmailsService planificationEmailsService) {
//        this.planificationEmailsService = planificationEmailsService;
//    }
//    @Override
//    @Transactional(readOnly = true)
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//         planificationEmailsService.
//    }
//}
