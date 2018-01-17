package com.redlean.news.service;
import com.redlean.news.domain.PlanificationEmails;
import com.redlean.news.repository.PlanificationEmailsRepository;
import org.joda.time.DateTime;

import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;



/**
 * Service Implementation for managing PlanificationEmails.
 */
@Service
@Transactional
//@Configuration
//@EnableAsync
@EnableScheduling
public class PlanificationEmailsService {

    private final Logger log = LoggerFactory.getLogger(PlanificationEmailsService.class);
    private PlanificationEmailsRepository planificationEmailsRepository;



    public PlanificationEmailsService(PlanificationEmailsRepository planificationEmailsRepository) {
        this.planificationEmailsRepository = planificationEmailsRepository;
    }

    /**
     * Save a planificationEmails.
     *
     * @param planificationEmails the entity to save
     * @return the persisted entity
     */
    public PlanificationEmails save(PlanificationEmails planificationEmails) {
        log.debug("Request to save PlanificationEmails : {}", planificationEmails);
        return planificationEmailsRepository.save(planificationEmails);
    }

  // @Scheduled(cron = "0 0 10 * * ?")
    public void SendMailInDate() {
        List<PlanificationEmails> ListePlanif = planificationEmailsRepository.findAll();
        System.out.println(ListePlanif);
        for (PlanificationEmails planificationEmails : ListePlanif) {
            String date = planificationEmails.getDatePlanif();
            System.out.println(date);
            org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm");
            DateTime DateTime = formatter.parseDateTime(date);
            System.out.println(DateTime);
            DateTime now = org.joda.time.DateTime.now();
            if (DateTime.isBefore(now)) {
                System.out.println(DateTime.isBefore(now));
                AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
                ctx.register(AppConfig.class);
                ctx.refresh();
                JavaMailSenderImpl mailSender = ctx.getBean(JavaMailSenderImpl.class);

                try {
                    MimeMessage mimeMessage = mailSender.createMimeMessage();
                    //Pass true flag for multipart message
                    MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage, true);
                    mailMsg.setFrom(planificationEmails.getExpediteur());
                    mailMsg.setTo(planificationEmails.getDestinataires());
                    mailMsg.setSubject(planificationEmails.getPlanifForEmail().getObjet());
                    String Contenu;
                    Contenu = planificationEmails.getPlanifForEmail().getContenu().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
                    System.out.println(Contenu.toString());
                    mailMsg.setText(Contenu.toString());
//                    FileSystemResource file = new FileSystemResource(new File("/home/redlean/boitier.png"));
//                    mailMsg.addAttachment("boitier.png", file);
                    mailSender.send(mimeMessage);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                //     mailMsg.setSentDate(planificationEmails.getDatePlanif().);
                //FileSystemResource object for Attachment

//            System.out.println("---Done---");
//            return ResponseEntity.ok(planificationEmails);
                // return mimeMessage;
            }
        }
    }

    /**
     * Get all the planificationEmails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */


    @Transactional(readOnly = true)
    public Page<PlanificationEmails> findAll(Pageable pageable) {
        log.debug("Request to get all PlanificationEmails");
        return planificationEmailsRepository.findAll(pageable);
    }

    /**
     * Get one planificationEmails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PlanificationEmails findOne(Long id) {
        log.debug("Request to get PlanificationEmails : {}", id);
        return planificationEmailsRepository.findOne(id);
    }

    /**
     * Delete the planificationEmails by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PlanificationEmails : {}", id);
        planificationEmailsRepository.delete(id);
    }

//    public void SendMailwithAttachment() throws MessagingException {
//            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//            ctx.register(AppConfig.class);
//            ctx.refresh();
//            JavaMailSenderImpl mailSender = ctx.getBean(JavaMailSenderImpl.class);
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            //Pass true flag for multipart message
//            MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage, true);
//            mailMsg.setFrom("arvindraivns02@gmail.com");
//            mailMsg.setTo("arvindraivns03@gmail.com");
//            mailMsg.setSubject("Test mail with Attachment");
//            mailMsg.setText("Please find Attachment.");
//            //FileSystemResource object for Attachment
//            FileSystemResource file = new FileSystemResource(new File("D:/cp/pic.jpg"));
//            mailMsg.addAttachment("myPic.jpg", file);
//            mailSender.send(mimeMessage);
//            System.out.println("---Done---");
//        }

}
