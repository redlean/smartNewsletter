package com.redlean.news.service;

import com.redlean.news.domain.Tache;
import com.redlean.news.repository.TacheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Tache.
 */
@Service
@Transactional
public class TacheService {

    private final Logger log = LoggerFactory.getLogger(TacheService.class);

    private final TacheRepository tacheRepository;

    public TacheService(TacheRepository tacheRepository) {
        this.tacheRepository = tacheRepository;
    }

    /**
     * Save a tache.
     *
     * @param tache the entity to save
     * @return the persisted entity
     */
    public Tache save(Tache tache) {
        log.debug("Request to save Tache : {}", tache);
        return tacheRepository.save(tache);
    }

    /**
     * Get all the taches.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tache> findAll(Pageable pageable) {
        log.debug("Request to get all Taches");
        return tacheRepository.findAll(pageable);
    }

    /**
     * Get one tache by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Tache findOne(Long id) {
        log.debug("Request to get Tache : {}", id);
        return tacheRepository.findOne(id);
    }

    /**
     * Delete the tache by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tache : {}", id);
        tacheRepository.delete(id);
    }
}
