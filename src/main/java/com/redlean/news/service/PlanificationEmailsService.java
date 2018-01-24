package com.redlean.news.service;
import com.redlean.news.domain.Planification_emails;
import com.redlean.news.repository.Planification_emailsRepository;
import com.redlean.news.web.rest.util.HeaderUtil;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
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
    private Planification_emailsRepository planificationEmailsRepository;


   // @Autowired
    public PlanificationEmailsService(Planification_emailsRepository planificationEmailsRepository) {
        this.planificationEmailsRepository = planificationEmailsRepository;
    }

    /**
     * Save a planificationEmails.
     *
     * @param planificationEmails the entity to save
     * @return the persisted entity
     */
    public Planification_emails save(Planification_emails planificationEmails) {
        log.debug("Request to save PlanificationEmails : {}", planificationEmails);
        return planificationEmailsRepository.save(planificationEmails);
    }

    @Scheduled(cron = "0 * * ? * *",zone = "Europe/Paris")
    public void SendMailInDate() {
        System.out.println("---Done---");
        List<Planification_emails> ListePlanif = planificationEmailsRepository.findAll();
        System.out.println(ListePlanif);
        for (Planification_emails planificationEmails : ListePlanif) {
            String date = planificationEmails.getDatePlanif();

            //DateTimeFormatter formatter = DateTimeFormatter.forPattern("yyyy-MM-dd'T'HH:mm");
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//            String text = date.format(String.valueOf(formatter));
//            LocalDateTime parsedDate = LocalDateTime.parse(text, formatter);
            LocalDateTime dateTime = LocalDateTime.parse(date);
            System.out.println("date d'envoi" + planificationEmails.getPlanifName() + dateTime);

            LocalDateTime today=LocalDateTime.now();
            ZoneId timeZone=ZoneId.of("Europe/Paris");
            ZonedDateTime todayWithTimeZone=ZonedDateTime.of(today, timeZone);
            ZonedDateTime dateTimeWithTimeZone=ZonedDateTime.of(dateTime, timeZone);
            // DateTime now = org.joda.time.DateTime.now();
            System.out.println("date d'envoi avec timezone" + planificationEmails.getPlanifName() + dateTimeWithTimeZone);
            System.out.println("Current time with timezone" + planificationEmails.getPlanifName() + todayWithTimeZone);

            if ((dateTimeWithTimeZone.isBefore(todayWithTimeZone)) && (planificationEmails.getStatus().equals("Non envoyée"))) {
                System.out.println(planificationEmails.getStatus());
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                props.put("mail.debug", "true");
                Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("******","*****");
                        }
                    });

                try {
                    MimeMessage msg = new MimeMessage(session);
                    msg.setFrom(planificationEmails.getExpediteur());
                    msg.setRecipients(Message.RecipientType.TO, planificationEmails.getDestinataire());
                    msg.setSubject(planificationEmails.getPlanifForEmail().getObjet());
                   // msg.setSentDate(DateTime);

                    Multipart multipart = new MimeMultipart();

                    MimeBodyPart htmlPart = new MimeBodyPart();
                    String htmlContent = planificationEmails.getPlanifForEmail().getContenu();
                    htmlPart.setContent(htmlContent, "text/html");
                    multipart.addBodyPart(htmlPart);

//                    MimeBodyPart attachementPart = new MimeBodyPart();
//                    attachementPart.attachFile(new File("/home/redlean/boitier.png"));
//                    multipart.addBodyPart(attachementPart);

                    msg.setContent(multipart);
                    Transport.send(msg);
                    System.out.println("---Done---");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                planificationEmails.setStatus("Envoyée");
                planificationEmailsRepository.save(planificationEmails);
            }
                //     mailMsg.setSentDate(planificationEmails.getDatePlanif().);
                //FileSystemResource object for Attachment


//            System.out.println("---Done---");
//            return ResponseEntity.ok(planificationEmails);
                // return mimeMessage;
            }
        }
    /**
     * Get all the planificationEmails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */


    @Transactional(readOnly = true)
    public Page<Planification_emails> findAll(Pageable pageable) {
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
    public Planification_emails findOne(Long id) {
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
  /* AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
                ctx.register(AppConfig.class);
                ctx.refresh();
                JavaMailSenderImpl mailSender = ctx.getBean(JavaMailSenderImpl.class);

                try {
                    MimeMessage mimeMessage = mailSender.createMimeMessage();
                    //Pass true flag for multipart message
                    MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage,
                        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name());
                    mailMsg.setFrom(planificationEmails.getExpediteur());
                    mailMsg.setTo(planificationEmails.getDestinataire());
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
                }*/

