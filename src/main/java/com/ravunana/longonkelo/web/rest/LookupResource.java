package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.LookupRepository;
import com.ravunana.longonkelo.service.LookupQueryService;
import com.ravunana.longonkelo.service.LookupService;
import com.ravunana.longonkelo.service.criteria.LookupCriteria;
import com.ravunana.longonkelo.service.dto.LookupDTO;
import com.ravunana.longonkelo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ravunana.longonkelo.domain.Lookup}.
 */
@RestController
@RequestMapping("/api")
public class LookupResource {

    private final Logger log = LoggerFactory.getLogger(LookupResource.class);

    private static final String ENTITY_NAME = "lookup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LookupService lookupService;

    private final LookupRepository lookupRepository;

    private final LookupQueryService lookupQueryService;

    public LookupResource(LookupService lookupService, LookupRepository lookupRepository, LookupQueryService lookupQueryService) {
        this.lookupService = lookupService;
        this.lookupRepository = lookupRepository;
        this.lookupQueryService = lookupQueryService;
    }

    /**
     * {@code POST  /lookups} : Create a new lookup.
     *
     * @param lookupDTO the lookupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lookupDTO, or with status {@code 400 (Bad Request)} if the lookup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lookups")
    public ResponseEntity<LookupDTO> createLookup(@Valid @RequestBody LookupDTO lookupDTO) throws URISyntaxException {
        log.debug("REST request to save Lookup : {}", lookupDTO);
        if (lookupDTO.getId() != null) {
            throw new BadRequestAlertException("A new lookup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LookupDTO result = lookupService.save(lookupDTO);
        return ResponseEntity
            .created(new URI("/api/lookups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lookups/:id} : Updates an existing lookup.
     *
     * @param id the id of the lookupDTO to save.
     * @param lookupDTO the lookupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lookupDTO,
     * or with status {@code 400 (Bad Request)} if the lookupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lookupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lookups/{id}")
    public ResponseEntity<LookupDTO> updateLookup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LookupDTO lookupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Lookup : {}, {}", id, lookupDTO);
        if (lookupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lookupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lookupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LookupDTO result = lookupService.update(lookupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lookupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lookups/:id} : Partial updates given fields of an existing lookup, field will ignore if it is null
     *
     * @param id the id of the lookupDTO to save.
     * @param lookupDTO the lookupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lookupDTO,
     * or with status {@code 400 (Bad Request)} if the lookupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the lookupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the lookupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lookups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LookupDTO> partialUpdateLookup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LookupDTO lookupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Lookup partially : {}, {}", id, lookupDTO);
        if (lookupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lookupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lookupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LookupDTO> result = lookupService.partialUpdate(lookupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lookupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /lookups} : get all the lookups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lookups in body.
     */
    @GetMapping("/lookups")
    public ResponseEntity<List<LookupDTO>> getAllLookups(
        LookupCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Lookups by criteria: {}", criteria);
        Page<LookupDTO> page = lookupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lookups/count} : count all the lookups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/lookups/count")
    public ResponseEntity<Long> countLookups(LookupCriteria criteria) {
        log.debug("REST request to count Lookups by criteria: {}", criteria);
        return ResponseEntity.ok().body(lookupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /lookups/:id} : get the "id" lookup.
     *
     * @param id the id of the lookupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lookupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lookups/{id}")
    public ResponseEntity<LookupDTO> getLookup(@PathVariable Long id) {
        log.debug("REST request to get Lookup : {}", id);
        Optional<LookupDTO> lookupDTO = lookupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lookupDTO);
    }

    /**
     * {@code DELETE  /lookups/:id} : delete the "id" lookup.
     *
     * @param id the id of the lookupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lookups/{id}")
    public ResponseEntity<Void> deleteLookup(@PathVariable Long id) {
        log.debug("REST request to delete Lookup : {}", id);
        lookupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
