package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.PlanoCurricularRepository;
import com.ravunana.longonkelo.service.PlanoCurricularQueryService;
import com.ravunana.longonkelo.service.PlanoCurricularService;
import com.ravunana.longonkelo.service.criteria.PlanoCurricularCriteria;
import com.ravunana.longonkelo.service.dto.PlanoCurricularDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.PlanoCurricular}.
 */
@RestController
@RequestMapping("/api")
public class PlanoCurricularResource {

    private final Logger log = LoggerFactory.getLogger(PlanoCurricularResource.class);

    private static final String ENTITY_NAME = "planoCurricular";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoCurricularService planoCurricularService;

    private final PlanoCurricularRepository planoCurricularRepository;

    private final PlanoCurricularQueryService planoCurricularQueryService;

    public PlanoCurricularResource(
        PlanoCurricularService planoCurricularService,
        PlanoCurricularRepository planoCurricularRepository,
        PlanoCurricularQueryService planoCurricularQueryService
    ) {
        this.planoCurricularService = planoCurricularService;
        this.planoCurricularRepository = planoCurricularRepository;
        this.planoCurricularQueryService = planoCurricularQueryService;
    }

    /**
     * {@code POST  /plano-curriculars} : Create a new planoCurricular.
     *
     * @param planoCurricularDTO the planoCurricularDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoCurricularDTO, or with status {@code 400 (Bad Request)} if the planoCurricular has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plano-curriculars")
    public ResponseEntity<PlanoCurricularDTO> createPlanoCurricular(@Valid @RequestBody PlanoCurricularDTO planoCurricularDTO)
        throws URISyntaxException {
        log.debug("REST request to save PlanoCurricular : {}", planoCurricularDTO);
        if (planoCurricularDTO.getId() != null) {
            throw new BadRequestAlertException("A new planoCurricular cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoCurricularDTO result = planoCurricularService.save(planoCurricularDTO);
        return ResponseEntity
            .created(new URI("/api/plano-curriculars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plano-curriculars/:id} : Updates an existing planoCurricular.
     *
     * @param id the id of the planoCurricularDTO to save.
     * @param planoCurricularDTO the planoCurricularDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoCurricularDTO,
     * or with status {@code 400 (Bad Request)} if the planoCurricularDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoCurricularDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plano-curriculars/{id}")
    public ResponseEntity<PlanoCurricularDTO> updatePlanoCurricular(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlanoCurricularDTO planoCurricularDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlanoCurricular : {}, {}", id, planoCurricularDTO);
        if (planoCurricularDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoCurricularDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoCurricularRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlanoCurricularDTO result = planoCurricularService.update(planoCurricularDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoCurricularDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plano-curriculars/:id} : Partial updates given fields of an existing planoCurricular, field will ignore if it is null
     *
     * @param id the id of the planoCurricularDTO to save.
     * @param planoCurricularDTO the planoCurricularDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoCurricularDTO,
     * or with status {@code 400 (Bad Request)} if the planoCurricularDTO is not valid,
     * or with status {@code 404 (Not Found)} if the planoCurricularDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the planoCurricularDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plano-curriculars/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlanoCurricularDTO> partialUpdatePlanoCurricular(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlanoCurricularDTO planoCurricularDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlanoCurricular partially : {}, {}", id, planoCurricularDTO);
        if (planoCurricularDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoCurricularDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoCurricularRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlanoCurricularDTO> result = planoCurricularService.partialUpdate(planoCurricularDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoCurricularDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /plano-curriculars} : get all the planoCurriculars.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoCurriculars in body.
     */
    @GetMapping("/plano-curriculars")
    public ResponseEntity<List<PlanoCurricularDTO>> getAllPlanoCurriculars(
        PlanoCurricularCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PlanoCurriculars by criteria: {}", criteria);
        Page<PlanoCurricularDTO> page = planoCurricularQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plano-curriculars/count} : count all the planoCurriculars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/plano-curriculars/count")
    public ResponseEntity<Long> countPlanoCurriculars(PlanoCurricularCriteria criteria) {
        log.debug("REST request to count PlanoCurriculars by criteria: {}", criteria);
        return ResponseEntity.ok().body(planoCurricularQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plano-curriculars/:id} : get the "id" planoCurricular.
     *
     * @param id the id of the planoCurricularDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoCurricularDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plano-curriculars/{id}")
    public ResponseEntity<PlanoCurricularDTO> getPlanoCurricular(@PathVariable Long id) {
        log.debug("REST request to get PlanoCurricular : {}", id);
        Optional<PlanoCurricularDTO> planoCurricularDTO = planoCurricularService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planoCurricularDTO);
    }

    /**
     * {@code DELETE  /plano-curriculars/:id} : delete the "id" planoCurricular.
     *
     * @param id the id of the planoCurricularDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plano-curriculars/{id}")
    public ResponseEntity<Void> deletePlanoCurricular(@PathVariable Long id) {
        log.debug("REST request to delete PlanoCurricular : {}", id);
        planoCurricularService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
