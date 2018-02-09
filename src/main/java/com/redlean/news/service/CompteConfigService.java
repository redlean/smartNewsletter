package com.redlean.news.service;

import com.redlean.news.domain.CompteConfig;
import com.redlean.news.repository.CompteConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.Convert;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Service Implementation for managing CompteConfig.
 */
@Service
@Transactional
public class CompteConfigService {

    private final Logger log = LoggerFactory.getLogger(CompteConfigService.class);

    private final CompteConfigRepository compteConfigRepository;

    public CompteConfigService(CompteConfigRepository compteConfigRepository) {
        this.compteConfigRepository = compteConfigRepository;

    }

    /**
     * Save a compteConfig.
     *
     * @param compteConfig the entity to save
     * @return the persisted entity
     */
    public CompteConfig save(CompteConfig compteConfig) {
        log.debug("Request to save CompteConfig : {}", compteConfig);
        if (this.ConfirmationDeConfiguration(compteConfig) == true) {
            return compteConfigRepository.save(compteConfig);
        }
        else return null;
    }

    /**
     * Get all the compteConfigs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CompteConfig> findAll() {
        log.debug("Request to get all CompteConfigs");
        return compteConfigRepository.findAll();
    }

    /**
     * Get one compteConfig by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CompteConfig findOne(Long id) {
        log.debug("Request to get CompteConfig : {}", id);
        return compteConfigRepository.findOne(id);
    }

    /**
     * Delete the compteConfig by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CompteConfig : {}", id);
        compteConfigRepository.delete(id);
    }


    public boolean ConfirmationDeConfiguration(CompteConfig compteConfig) {

       // CompteConfig compteConfig = compteConfigRepository.findAll().get(0);
        Session session = new AppConfig().getAppconfig(compteConfig);
        System.out.println("session data" + session);
        boolean sendFailed = false;
        boolean done = false;
        try {
            MimeMessage msg = new MimeMessage(session);
          //  msg.setFrom(planificationEmails.getExpediteur());
            msg.setRecipients(Message.RecipientType.TO, compteConfig.getEmail());
            msg.setSubject("Validation de la configuration de l'application Smart Newsletter par votre compte");
            Multipart multipart = new MimeMultipart();

            MimeBodyPart htmlPart = new MimeBodyPart();
            String htmlContent = "<p>Madame, Monsieur,</p>\n" +
                "\n" +
                "<p>Vous avez configurez l&#39;envoi des e-mails de l&#39;application<span style=\"color:#0000ff\"><span style=\"font-size:16px\"><strong> </strong></span><span style=\"font-size:14px\"><strong>&quot;Smart Newsletter&quot;</strong></span><span style=\"font-size:16px\"><strong> </strong></span></span>par votre compte.</p>\n" +
                "\n" +
                "<p>Veuillez cliquer sur le lien suivant pour acc&eacute;der &agrave; l&#39;application<strong><span style=\"color:#0000ff\"><span style=\"font-size:14px\"> &quot;Smart Newsletter&quot;</span></span></strong> :&nbsp;<a href=\"https://smart-newsletter.herokuapp.com\">https://smart-newsletter.herokuapp.com</a></p>\n" +
                "\n" +
                "<p>Cordialement,<br />\n" +
                "\n" +
                "L&#39;&eacute;quipe de <strong><span style=\"color:#ff0000\">Red</span>lean</strong>.</p>\n" +
                "\n" +
                "<p><img alt=\"\" src=\"http://redlean.io/wp-content/uploads/2017/02/logo-Redlean-Services.png\" style=\"height:25px; width:100px\" /></p>\n";


            htmlPart.setContent(htmlContent, "text/html");
            multipart.addBodyPart(htmlPart);
            msg.setContent(multipart);
            Transport.send(msg);
            System.out.println("---Done---");
            done = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            sendFailed = true;
        }

        if ((sendFailed == false)&&( done == true)){
            return true;
        }
        else {
            return false;
        }
    }

    public String digestMd5(final String password) {
        String base64;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            base64 = new BASE64Encoder().encode(digest.digest());
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "{MD5}" + base64;
    }
}
