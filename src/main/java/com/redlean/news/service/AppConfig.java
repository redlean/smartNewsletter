package com.redlean.news.service;

import com.redlean.news.domain.CompteConfig;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

import static com.redlean.news.service.PlanificationEmailsService.decrypt;
import static com.redlean.news.service.PlanificationEmailsService.encrypt;

@Configurable
public class AppConfig {
    @Bean
    public Session getAppconfig(CompteConfig compteConfig ) {
        String key = "Bar12345Bar12345"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.debug", "true");
        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(compteConfig.getEmail(), decrypt(key, initVector, compteConfig.getPassword()));
                }
            });
        return session;
    }
}
