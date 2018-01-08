package com.redlean.news.service;

import com.redlean.news.domain.Email;
import com.redlean.news.repository.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Email.
 */
@Service
@Transactional
public class EmailService {

    private final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    /**
     * Save a email.
     *
     * @param email the entity to save
     * @return the persisted entity
     */
    public Email save(Email email) {
        log.debug("Request to save Email : {}", email);
        return emailRepository.save(email);
    }

    /**
     * Get all the emails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Email> findAll(Pageable pageable) {
        log.debug("Request to get all Emails");
        return emailRepository.findAll(pageable);
    }

    /**
     * Get one email by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Email findOne(Long id) {
        log.debug("Request to get Email : {}", id);
        return emailRepository.findOne(id);
    }

    /**
     * Delete the email by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Email : {}", id);
        emailRepository.delete(id);
    }
}
