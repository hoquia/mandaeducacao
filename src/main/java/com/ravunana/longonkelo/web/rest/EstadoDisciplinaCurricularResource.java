package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.EstadoDisciplinaCurricularRepository;
import com.ravunana.longonkelo.service.EstadoDisciplinaCurricularQueryService;
import com.ravunana.longonkelo.service.EstadoDisciplinaCurricularService;
import com.ravunana.longonkelo.service.criteria.EstadoDisciplinaCurricularCriteria;
import com.ravunana.longonkelo.service.dto.EstadoDisciplinaCurricularDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular}.
 */
@RestController
@RequestMapping("/api")
public class EstadoDisciplinaCurricularResource {

    private final Logger log = LoggerFactory.getLogger(EstadoDisciplinaCurricularResource.class);

    private static final String ENTITY_NAME = "estadoDisciplinaCurricular";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoDisciplinaCurricularService estadoDisciplinaCurricularService;

    private final EstadoDisciplinaCurricularRepository estadoDisciplinaCurricularRepository;

    private final EstadoDisciplinaCurricularQueryService estadoDisciplinaCurricularQueryService;

    public EstadoDisciplinaCurricularResource(
        EstadoDisciplinaCurricularService estadoDisciplinaCurricularService,
        EstadoDisciplinaCurricularRepository estadoDisciplinaCurricularRepository,
        EstadoDisciplinaCurricularQueryService estadoDisciplinaCurricularQueryService
    ) {
        this.estadoDisciplinaCurricularService = estadoDisciplinaCurricularService;
        this.estadoDisciplinaCurricularRepository = estadoDisciplinaCurricularRepository;
        this.estadoDisciplinaCurricularQueryService = estadoDisciplinaCurricularQueryService;
    }

    /**
     * {@code POST  /estado-disciplina-curriculars} : Create a new estadoDisciplinaCurricular.
     *
     * @param estadoDisciplinaCurricularDTO the estadoDisciplinaCurricularDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoDisciplinaCurricularDTO, or with status {@code 400 (Bad Request)} if the estadoDisciplinaCurricular has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estado-disciplina-curriculars")
    public ResponseEntity<EstadoDisciplinaCurricularDTO> createEstadoDisciplinaCurricular(
        @Valid @RequestBody EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO
    ) throws URISyntaxException {
        log.debug("REST request to save EstadoDisciplinaCurricular : {}", estadoDisciplinaCurricularDTO);
        if (estadoDisciplinaCurricularDTO.getId() != null) {
            throw new BadRequestAlertException("A new estadoDisciplinaCurricular cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoDisciplinaCurricularDTO result = estadoDisciplinaCurricularService.save(estadoDisciplinaCurricularDTO);
        return ResponseEntity
            .created(new URI("/api/estado-disciplina-curriculars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-disciplina-curriculars/:id} : Updates an existing estadoDisciplinaCurricular.
     *
     * @param id the id of the estadoDisciplinaCurricularDTO to save.
     * @param estadoDisciplinaCurricularDTO the estadoDisciplinaCurricularDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoDisciplinaCurricularDTO,
     * or with status {@code 400 (Bad Request)} if the estadoDisciplinaCurricularDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoDisciplinaCurricularDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estado-disciplina-curriculars/{id}")
    public ResponseEntity<EstadoDisciplinaCurricularDTO> updateEstadoDisciplinaCurricular(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EstadoDisciplinaCurricular : {}, {}", id, estadoDisciplinaCurricularDTO);
        if (estadoDisciplinaCurricularDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoDisciplinaCurricularDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoDisciplinaCurricularRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstadoDisciplinaCurricularDTO result = estadoDisciplinaCurricularService.update(estadoDisciplinaCurricularDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoDisciplinaCurricularDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /estado-disciplina-curriculars/:id} : Partial updates given fields of an existing estadoDisciplinaCurricular, field will ignore if it is null
     *
     * @param id the id of the estadoDisciplinaCurricularDTO to save.
     * @param estadoDisciplinaCurricularDTO the estadoDisciplinaCurricularDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoDisciplinaCurricularDTO,
     * or with status {@code 400 (Bad Request)} if the estadoDisciplinaCurricularDTO is not valid,
     * or with status {@code 404 (Not Found)} if the estadoDisciplinaCurricularDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the estadoDisciplinaCurricularDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estado-disciplina-curriculars/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstadoDisciplinaCurricularDTO> partialUpdateEstadoDisciplinaCurricular(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EstadoDisciplinaCurricular partially : {}, {}", id, estadoDisciplinaCurricularDTO);
        if (estadoDisciplinaCurricularDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoDisciplinaCurricularDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoDisciplinaCurricularRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstadoDisciplinaCurricularDTO> result = estadoDisciplinaCurricularService.partialUpdate(estadoDisciplinaCurricularDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoDisciplinaCurricularDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /estado-disciplina-curriculars} : get all the estadoDisciplinaCurriculars.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadoDisciplinaCurriculars in body.
     */
    @GetMapping("/estado-disciplina-curriculars")
    public ResponseEntity<List<EstadoDisciplinaCurricularDTO>> getAllEstadoDisciplinaCurriculars(
        EstadoDisciplinaCurricularCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EstadoDisciplinaCurriculars by criteria: {}", criteria);
        Page<EstadoDisciplinaCurricularDTO> page = estadoDisciplinaCurricularQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estado-disciplina-curriculars/count} : count all the estadoDisciplinaCurriculars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/estado-disciplina-curriculars/count")
    public ResponseEntity<Long> countEstadoDisciplinaCurriculars(EstadoDisciplinaCurricularCriteria criteria) {
        log.debug("REST request to count EstadoDisciplinaCurriculars by criteria: {}", criteria);
        return ResponseEntity.ok().body(estadoDisciplinaCurricularQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /estado-disciplina-curriculars/:id} : get the "id" estadoDisciplinaCurricular.
     *
     * @param id the id of the estadoDisciplinaCurricularDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoDisciplinaCurricularDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estado-disciplina-curriculars/{id}")
    public ResponseEntity<EstadoDisciplinaCurricularDTO> getEstadoDisciplinaCurricular(@PathVariable Long id) {
        log.debug("REST request to get EstadoDisciplinaCurricular : {}", id);
        Optional<EstadoDisciplinaCurricularDTO> estadoDisciplinaCurricularDTO = estadoDisciplinaCurricularService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estadoDisciplinaCurricularDTO);
    }

    /**
     * {@code DELETE  /estado-disciplina-curriculars/:id} : delete the "id" estadoDisciplinaCurricular.
     *
     * @param id the id of the estadoDisciplinaCurricularDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estado-disciplina-curriculars/{id}")
    public ResponseEntity<Void> deleteEstadoDisciplinaCurricular(@PathVariable Long id) {
        log.debug("REST request to delete EstadoDisciplinaCurricular : {}", id);
        estadoDisciplinaCurricularService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
