package com.redlean.news.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.redlean.news.domain.Tache;
import com.redlean.news.service.TacheService;
import com.redlean.news.web.rest.errors.BadRequestAlertException;
import com.redlean.news.web.rest.util.HeaderUtil;
import com.redlean.news.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tache.
 */
@RestController
@RequestMapping("/api")
public class TacheResource {

    private final Logger log = LoggerFactory.getLogger(TacheResource.class);

    private static final String ENTITY_NAME = "tache";

    private final TacheService tacheService;

    public TacheResource(TacheService tacheService) {
        this.tacheService = tacheService;
    }

    /**
     * POST  /taches : Create a new tache.
     *
     * @param tache the tache to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tache, or with status 400 (Bad Request) if the tache has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/taches")
    @Timed
    public ResponseEntity<Tache> createTache(@Valid @RequestBody Tache tache) throws URISyntaxException {
        log.debug("REST request to save Tache : {}", tache);
        if (tache.getId() != null) {
            throw new BadRequestAlertException("A new tache cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tache result = tacheService.save(tache);
        return ResponseEntity.created(new URI("/api/taches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taches : Updates an existing tache.
     *
     * @param tache the tache to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tache,
     * or with status 400 (Bad Request) if the tache is not valid,
     * or with status 500 (Internal Server Error) if the tache couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/taches")
    @Timed
    public ResponseEntity<Tache> updateTache(@Valid @RequestBody Tache tache) throws URISyntaxException {
        log.debug("REST request to update Tache : {}", tache);
        if (tache.getId() == null) {
            return createTache(tache);
        }
        Tache result = tacheService.save(tache);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tache.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taches : get all the taches.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taches in body
     */
    @GetMapping("/taches")
    @Timed
    public ResponseEntity<List<Tache>> getAllTaches(Pageable pageable) {
        log.debug("REST request to get a page of Taches");
        Page<Tache> page = tacheService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taches");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /taches/:id : get the "id" tache.
     *
     * @param id the id of the tache to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tache, or with status 404 (Not Found)
     */
    @GetMapping("/taches/{id}")
    @Timed
    public ResponseEntity<Tache> getTache(@PathVariable Long id) {
        log.debug("REST request to get Tache : {}", id);
        Tache tache = tacheService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tache));
    }

    /**
     * DELETE  /taches/:id : delete the "id" tache.
     *
     * @param id the id of the tache to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/taches/{id}")
    @Timed
    public ResponseEntity<Void> deleteTache(@PathVariable Long id) {
        log.debug("REST request to delete Tache : {}", id);
        tacheService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
