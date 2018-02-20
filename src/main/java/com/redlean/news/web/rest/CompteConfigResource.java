package com.redlean.news.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.redlean.news.domain.CompteConfig;
import com.redlean.news.service.CompteConfigService;
import com.redlean.news.web.rest.errors.BadRequestAlertException;
import com.redlean.news.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CompteConfig.
 */
@RestController
@RequestMapping("/api")
public class CompteConfigResource {

    private final Logger log = LoggerFactory.getLogger(CompteConfigResource.class);

    private static final String ENTITY_NAME = "compteConfig";

    private final CompteConfigService compteConfigService;

    public CompteConfigResource(CompteConfigService compteConfigService) {
        this.compteConfigService = compteConfigService;
    }

    /**
     * POST  /compte-configs : Create a new compteConfig.
     *
     * @param compteConfig the compteConfig to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compteConfig, or with status 400 (Bad Request) if the compteConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compte-configs")
    @Timed
    public ResponseEntity<CompteConfig> createCompteConfig(@Valid @RequestBody CompteConfig compteConfig) throws URISyntaxException {
        log.debug("REST request to save CompteConfig : {}", compteConfig);
        if (compteConfig.getId() != null) {
            throw new BadRequestAlertException("A new compteConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompteConfig result = compteConfigService.save(compteConfig);
        return ResponseEntity.created(new URI("/api/compte-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /compte-configs : Updates an existing compteConfig.
     *
     * @param compteConfig the compteConfig to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compteConfig,
     * or with status 400 (Bad Request) if the compteConfig is not valid,
     * or with status 500 (Internal Server Error) if the compteConfig couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compte-configs")
    @Timed
    public ResponseEntity<CompteConfig> updateCompteConfig(@Valid @RequestBody CompteConfig compteConfig) throws URISyntaxException {
        log.debug("REST request to update CompteConfig : {}", compteConfig);
        if (compteConfig.getId() == null) {
            return createCompteConfig(compteConfig);
        }
        CompteConfig result = compteConfigService.save(compteConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compteConfig.getId().toString()))
            .body(result);
    }

    /**
     * GET  /compte-configs : get all the compteConfigs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of compteConfigs in body
     */
    @GetMapping("/compte-configs")
    @Timed
    public List<CompteConfig> getAllCompteConfigs() {
        log.debug("REST request to get all CompteConfigs");
        return compteConfigService.findAll();
        }

    /**
     * GET  /compte-configs/:id : get the "id" compteConfig.
     *
     * @param id the id of the compteConfig to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compteConfig, or with status 404 (Not Found)
     */
    @GetMapping("/compte-configs/{id}")
    @Timed
    public ResponseEntity<CompteConfig> getCompteConfig(@PathVariable Long id) {
        log.debug("REST request to get CompteConfig : {}", id);
        CompteConfig compteConfig = compteConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(compteConfig));
    }

    /**
     * DELETE  /compte-configs/:id : delete the "id" compteConfig.
     *
     * @param id the id of the compteConfig to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compte-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompteConfig(@PathVariable Long id) {
        log.debug("REST request to delete CompteConfig : {}", id);
        compteConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
