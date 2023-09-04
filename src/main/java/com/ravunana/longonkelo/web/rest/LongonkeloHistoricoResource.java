package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.LongonkeloHistoricoRepository;
import com.ravunana.longonkelo.service.LongonkeloHistoricoQueryService;
import com.ravunana.longonkelo.service.LongonkeloHistoricoService;
import com.ravunana.longonkelo.service.criteria.LongonkeloHistoricoCriteria;
import com.ravunana.longonkelo.service.dto.LongonkeloHistoricoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.LongonkeloHistorico}.
 */
@RestController
@RequestMapping("/api")
public class LongonkeloHistoricoResource {

    private final Logger log = LoggerFactory.getLogger(LongonkeloHistoricoResource.class);

    private static final String ENTITY_NAME = "longonkeloHistorico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LongonkeloHistoricoService longonkeloHistoricoService;

    private final LongonkeloHistoricoRepository longonkeloHistoricoRepository;

    private final LongonkeloHistoricoQueryService longonkeloHistoricoQueryService;

    public LongonkeloHistoricoResource(
        LongonkeloHistoricoService longonkeloHistoricoService,
        LongonkeloHistoricoRepository longonkeloHistoricoRepository,
        LongonkeloHistoricoQueryService longonkeloHistoricoQueryService
    ) {
        this.longonkeloHistoricoService = longonkeloHistoricoService;
        this.longonkeloHistoricoRepository = longonkeloHistoricoRepository;
        this.longonkeloHistoricoQueryService = longonkeloHistoricoQueryService;
    }

    /**
     * {@code POST  /longonkelo-historicos} : Create a new longonkeloHistorico.
     *
     * @param longonkeloHistoricoDTO the longonkeloHistoricoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new longonkeloHistoricoDTO, or with status {@code 400 (Bad Request)} if the longonkeloHistorico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/longonkelo-historicos")
    public ResponseEntity<LongonkeloHistoricoDTO> createLongonkeloHistorico(
        @Valid @RequestBody LongonkeloHistoricoDTO longonkeloHistoricoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save LongonkeloHistorico : {}", longonkeloHistoricoDTO);
        if (longonkeloHistoricoDTO.getId() != null) {
            throw new BadRequestAlertException("A new longonkeloHistorico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LongonkeloHistoricoDTO result = longonkeloHistoricoService.save(longonkeloHistoricoDTO);
        return ResponseEntity
            .created(new URI("/api/longonkelo-historicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /longonkelo-historicos/:id} : Updates an existing longonkeloHistorico.
     *
     * @param id the id of the longonkeloHistoricoDTO to save.
     * @param longonkeloHistoricoDTO the longonkeloHistoricoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated longonkeloHistoricoDTO,
     * or with status {@code 400 (Bad Request)} if the longonkeloHistoricoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the longonkeloHistoricoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/longonkelo-historicos/{id}")
    public ResponseEntity<LongonkeloHistoricoDTO> updateLongonkeloHistorico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LongonkeloHistoricoDTO longonkeloHistoricoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LongonkeloHistorico : {}, {}", id, longonkeloHistoricoDTO);
        if (longonkeloHistoricoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, longonkeloHistoricoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!longonkeloHistoricoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LongonkeloHistoricoDTO result = longonkeloHistoricoService.update(longonkeloHistoricoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, longonkeloHistoricoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /longonkelo-historicos/:id} : Partial updates given fields of an existing longonkeloHistorico, field will ignore if it is null
     *
     * @param id the id of the longonkeloHistoricoDTO to save.
     * @param longonkeloHistoricoDTO the longonkeloHistoricoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated longonkeloHistoricoDTO,
     * or with status {@code 400 (Bad Request)} if the longonkeloHistoricoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the longonkeloHistoricoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the longonkeloHistoricoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/longonkelo-historicos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LongonkeloHistoricoDTO> partialUpdateLongonkeloHistorico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LongonkeloHistoricoDTO longonkeloHistoricoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LongonkeloHistorico partially : {}, {}", id, longonkeloHistoricoDTO);
        if (longonkeloHistoricoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, longonkeloHistoricoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!longonkeloHistoricoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LongonkeloHistoricoDTO> result = longonkeloHistoricoService.partialUpdate(longonkeloHistoricoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, longonkeloHistoricoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /longonkelo-historicos} : get all the longonkeloHistoricos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of longonkeloHistoricos in body.
     */
    @GetMapping("/longonkelo-historicos")
    public ResponseEntity<List<LongonkeloHistoricoDTO>> getAllLongonkeloHistoricos(
        LongonkeloHistoricoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LongonkeloHistoricos by criteria: {}", criteria);
        Page<LongonkeloHistoricoDTO> page = longonkeloHistoricoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /longonkelo-historicos/count} : count all the longonkeloHistoricos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/longonkelo-historicos/count")
    public ResponseEntity<Long> countLongonkeloHistoricos(LongonkeloHistoricoCriteria criteria) {
        log.debug("REST request to count LongonkeloHistoricos by criteria: {}", criteria);
        return ResponseEntity.ok().body(longonkeloHistoricoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /longonkelo-historicos/:id} : get the "id" longonkeloHistorico.
     *
     * @param id the id of the longonkeloHistoricoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the longonkeloHistoricoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/longonkelo-historicos/{id}")
    public ResponseEntity<LongonkeloHistoricoDTO> getLongonkeloHistorico(@PathVariable Long id) {
        log.debug("REST request to get LongonkeloHistorico : {}", id);
        Optional<LongonkeloHistoricoDTO> longonkeloHistoricoDTO = longonkeloHistoricoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(longonkeloHistoricoDTO);
    }

    /**
     * {@code DELETE  /longonkelo-historicos/:id} : delete the "id" longonkeloHistorico.
     *
     * @param id the id of the longonkeloHistoricoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/longonkelo-historicos/{id}")
    public ResponseEntity<Void> deleteLongonkeloHistorico(@PathVariable Long id) {
        log.debug("REST request to delete LongonkeloHistorico : {}", id);
        longonkeloHistoricoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
