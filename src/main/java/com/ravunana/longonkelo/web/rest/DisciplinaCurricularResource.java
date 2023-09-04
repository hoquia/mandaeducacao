package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.DisciplinaCurricularRepository;
import com.ravunana.longonkelo.service.DisciplinaCurricularQueryService;
import com.ravunana.longonkelo.service.DisciplinaCurricularService;
import com.ravunana.longonkelo.service.criteria.DisciplinaCurricularCriteria;
import com.ravunana.longonkelo.service.dto.DisciplinaCurricularDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.DisciplinaCurricular}.
 */
@RestController
@RequestMapping("/api")
public class DisciplinaCurricularResource {

    private final Logger log = LoggerFactory.getLogger(DisciplinaCurricularResource.class);

    private static final String ENTITY_NAME = "disciplinaCurricular";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisciplinaCurricularService disciplinaCurricularService;

    private final DisciplinaCurricularRepository disciplinaCurricularRepository;

    private final DisciplinaCurricularQueryService disciplinaCurricularQueryService;

    public DisciplinaCurricularResource(
        DisciplinaCurricularService disciplinaCurricularService,
        DisciplinaCurricularRepository disciplinaCurricularRepository,
        DisciplinaCurricularQueryService disciplinaCurricularQueryService
    ) {
        this.disciplinaCurricularService = disciplinaCurricularService;
        this.disciplinaCurricularRepository = disciplinaCurricularRepository;
        this.disciplinaCurricularQueryService = disciplinaCurricularQueryService;
    }

    /**
     * {@code POST  /disciplina-curriculars} : Create a new disciplinaCurricular.
     *
     * @param disciplinaCurricularDTO the disciplinaCurricularDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disciplinaCurricularDTO, or with status {@code 400 (Bad Request)} if the disciplinaCurricular has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/disciplina-curriculars")
    public ResponseEntity<DisciplinaCurricularDTO> createDisciplinaCurricular(
        @Valid @RequestBody DisciplinaCurricularDTO disciplinaCurricularDTO
    ) throws URISyntaxException {
        log.debug("REST request to save DisciplinaCurricular : {}", disciplinaCurricularDTO);
        if (disciplinaCurricularDTO.getId() != null) {
            throw new BadRequestAlertException("A new disciplinaCurricular cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisciplinaCurricularDTO result = disciplinaCurricularService.save(disciplinaCurricularDTO);
        return ResponseEntity
            .created(new URI("/api/disciplina-curriculars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /disciplina-curriculars/:id} : Updates an existing disciplinaCurricular.
     *
     * @param id the id of the disciplinaCurricularDTO to save.
     * @param disciplinaCurricularDTO the disciplinaCurricularDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplinaCurricularDTO,
     * or with status {@code 400 (Bad Request)} if the disciplinaCurricularDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disciplinaCurricularDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/disciplina-curriculars/{id}")
    public ResponseEntity<DisciplinaCurricularDTO> updateDisciplinaCurricular(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DisciplinaCurricularDTO disciplinaCurricularDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DisciplinaCurricular : {}, {}", id, disciplinaCurricularDTO);
        if (disciplinaCurricularDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disciplinaCurricularDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disciplinaCurricularRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DisciplinaCurricularDTO result = disciplinaCurricularService.update(disciplinaCurricularDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplinaCurricularDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /disciplina-curriculars/:id} : Partial updates given fields of an existing disciplinaCurricular, field will ignore if it is null
     *
     * @param id the id of the disciplinaCurricularDTO to save.
     * @param disciplinaCurricularDTO the disciplinaCurricularDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplinaCurricularDTO,
     * or with status {@code 400 (Bad Request)} if the disciplinaCurricularDTO is not valid,
     * or with status {@code 404 (Not Found)} if the disciplinaCurricularDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the disciplinaCurricularDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/disciplina-curriculars/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DisciplinaCurricularDTO> partialUpdateDisciplinaCurricular(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DisciplinaCurricularDTO disciplinaCurricularDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DisciplinaCurricular partially : {}, {}", id, disciplinaCurricularDTO);
        if (disciplinaCurricularDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disciplinaCurricularDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disciplinaCurricularRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DisciplinaCurricularDTO> result = disciplinaCurricularService.partialUpdate(disciplinaCurricularDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplinaCurricularDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /disciplina-curriculars} : get all the disciplinaCurriculars.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disciplinaCurriculars in body.
     */
    @GetMapping("/disciplina-curriculars")
    public ResponseEntity<List<DisciplinaCurricularDTO>> getAllDisciplinaCurriculars(
        DisciplinaCurricularCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DisciplinaCurriculars by criteria: {}", criteria);
        Page<DisciplinaCurricularDTO> page = disciplinaCurricularQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /disciplina-curriculars/count} : count all the disciplinaCurriculars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/disciplina-curriculars/count")
    public ResponseEntity<Long> countDisciplinaCurriculars(DisciplinaCurricularCriteria criteria) {
        log.debug("REST request to count DisciplinaCurriculars by criteria: {}", criteria);
        return ResponseEntity.ok().body(disciplinaCurricularQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /disciplina-curriculars/:id} : get the "id" disciplinaCurricular.
     *
     * @param id the id of the disciplinaCurricularDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disciplinaCurricularDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/disciplina-curriculars/{id}")
    public ResponseEntity<DisciplinaCurricularDTO> getDisciplinaCurricular(@PathVariable Long id) {
        log.debug("REST request to get DisciplinaCurricular : {}", id);
        Optional<DisciplinaCurricularDTO> disciplinaCurricularDTO = disciplinaCurricularService.findOne(id);
        return ResponseUtil.wrapOrNotFound(disciplinaCurricularDTO);
    }

    /**
     * {@code DELETE  /disciplina-curriculars/:id} : delete the "id" disciplinaCurricular.
     *
     * @param id the id of the disciplinaCurricularDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/disciplina-curriculars/{id}")
    public ResponseEntity<Void> deleteDisciplinaCurricular(@PathVariable Long id) {
        log.debug("REST request to delete DisciplinaCurricular : {}", id);
        disciplinaCurricularService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
