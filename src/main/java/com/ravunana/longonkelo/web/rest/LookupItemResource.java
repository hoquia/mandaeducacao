package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.LookupItemRepository;
import com.ravunana.longonkelo.service.LookupItemQueryService;
import com.ravunana.longonkelo.service.LookupItemService;
import com.ravunana.longonkelo.service.criteria.LookupItemCriteria;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.LookupItem}.
 */
@RestController
@RequestMapping("/api")
public class LookupItemResource {

    private final Logger log = LoggerFactory.getLogger(LookupItemResource.class);

    private static final String ENTITY_NAME = "lookupItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LookupItemService lookupItemService;

    private final LookupItemRepository lookupItemRepository;

    private final LookupItemQueryService lookupItemQueryService;

    public LookupItemResource(
        LookupItemService lookupItemService,
        LookupItemRepository lookupItemRepository,
        LookupItemQueryService lookupItemQueryService
    ) {
        this.lookupItemService = lookupItemService;
        this.lookupItemRepository = lookupItemRepository;
        this.lookupItemQueryService = lookupItemQueryService;
    }

    /**
     * {@code POST  /lookup-items} : Create a new lookupItem.
     *
     * @param lookupItemDTO the lookupItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lookupItemDTO, or with status {@code 400 (Bad Request)} if the lookupItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lookup-items")
    public ResponseEntity<LookupItemDTO> createLookupItem(@Valid @RequestBody LookupItemDTO lookupItemDTO) throws URISyntaxException {
        log.debug("REST request to save LookupItem : {}", lookupItemDTO);
        if (lookupItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new lookupItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LookupItemDTO result = lookupItemService.save(lookupItemDTO);
        return ResponseEntity
            .created(new URI("/api/lookup-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lookup-items/:id} : Updates an existing lookupItem.
     *
     * @param id the id of the lookupItemDTO to save.
     * @param lookupItemDTO the lookupItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lookupItemDTO,
     * or with status {@code 400 (Bad Request)} if the lookupItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lookupItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lookup-items/{id}")
    public ResponseEntity<LookupItemDTO> updateLookupItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LookupItemDTO lookupItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LookupItem : {}, {}", id, lookupItemDTO);
        if (lookupItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lookupItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lookupItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LookupItemDTO result = lookupItemService.update(lookupItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lookupItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lookup-items/:id} : Partial updates given fields of an existing lookupItem, field will ignore if it is null
     *
     * @param id the id of the lookupItemDTO to save.
     * @param lookupItemDTO the lookupItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lookupItemDTO,
     * or with status {@code 400 (Bad Request)} if the lookupItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the lookupItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the lookupItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lookup-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LookupItemDTO> partialUpdateLookupItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LookupItemDTO lookupItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LookupItem partially : {}, {}", id, lookupItemDTO);
        if (lookupItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lookupItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lookupItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LookupItemDTO> result = lookupItemService.partialUpdate(lookupItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lookupItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /lookup-items} : get all the lookupItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lookupItems in body.
     */
    @GetMapping("/lookup-items")
    public ResponseEntity<List<LookupItemDTO>> getAllLookupItems(
        LookupItemCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LookupItems by criteria: {}", criteria);
        Page<LookupItemDTO> page = lookupItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lookup-items/count} : count all the lookupItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/lookup-items/count")
    public ResponseEntity<Long> countLookupItems(LookupItemCriteria criteria) {
        log.debug("REST request to count LookupItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(lookupItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /lookup-items/:id} : get the "id" lookupItem.
     *
     * @param id the id of the lookupItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lookupItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lookup-items/{id}")
    public ResponseEntity<LookupItemDTO> getLookupItem(@PathVariable Long id) {
        log.debug("REST request to get LookupItem : {}", id);
        Optional<LookupItemDTO> lookupItemDTO = lookupItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lookupItemDTO);
    }

    /**
     * {@code DELETE  /lookup-items/:id} : delete the "id" lookupItem.
     *
     * @param id the id of the lookupItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lookup-items/{id}")
    public ResponseEntity<Void> deleteLookupItem(@PathVariable Long id) {
        log.debug("REST request to delete LookupItem : {}", id);
        lookupItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
