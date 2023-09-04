package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.ProcessoSelectivoMatriculaRepository;
import com.ravunana.longonkelo.service.ProcessoSelectivoMatriculaQueryService;
import com.ravunana.longonkelo.service.ProcessoSelectivoMatriculaService;
import com.ravunana.longonkelo.service.criteria.ProcessoSelectivoMatriculaCriteria;
import com.ravunana.longonkelo.service.dto.ProcessoSelectivoMatriculaDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.ProcessoSelectivoMatricula}.
 */
@RestController
@RequestMapping("/api")
public class ProcessoSelectivoMatriculaResource {

    private final Logger log = LoggerFactory.getLogger(ProcessoSelectivoMatriculaResource.class);

    private static final String ENTITY_NAME = "processoSelectivoMatricula";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessoSelectivoMatriculaService processoSelectivoMatriculaService;

    private final ProcessoSelectivoMatriculaRepository processoSelectivoMatriculaRepository;

    private final ProcessoSelectivoMatriculaQueryService processoSelectivoMatriculaQueryService;

    public ProcessoSelectivoMatriculaResource(
        ProcessoSelectivoMatriculaService processoSelectivoMatriculaService,
        ProcessoSelectivoMatriculaRepository processoSelectivoMatriculaRepository,
        ProcessoSelectivoMatriculaQueryService processoSelectivoMatriculaQueryService
    ) {
        this.processoSelectivoMatriculaService = processoSelectivoMatriculaService;
        this.processoSelectivoMatriculaRepository = processoSelectivoMatriculaRepository;
        this.processoSelectivoMatriculaQueryService = processoSelectivoMatriculaQueryService;
    }

    /**
     * {@code POST  /processo-selectivo-matriculas} : Create a new processoSelectivoMatricula.
     *
     * @param processoSelectivoMatriculaDTO the processoSelectivoMatriculaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processoSelectivoMatriculaDTO, or with status {@code 400 (Bad Request)} if the processoSelectivoMatricula has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/processo-selectivo-matriculas")
    public ResponseEntity<ProcessoSelectivoMatriculaDTO> createProcessoSelectivoMatricula(
        @Valid @RequestBody ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProcessoSelectivoMatricula : {}", processoSelectivoMatriculaDTO);
        if (processoSelectivoMatriculaDTO.getId() != null) {
            throw new BadRequestAlertException("A new processoSelectivoMatricula cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessoSelectivoMatriculaDTO result = processoSelectivoMatriculaService.save(processoSelectivoMatriculaDTO);
        return ResponseEntity
            .created(new URI("/api/processo-selectivo-matriculas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /processo-selectivo-matriculas/:id} : Updates an existing processoSelectivoMatricula.
     *
     * @param id the id of the processoSelectivoMatriculaDTO to save.
     * @param processoSelectivoMatriculaDTO the processoSelectivoMatriculaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processoSelectivoMatriculaDTO,
     * or with status {@code 400 (Bad Request)} if the processoSelectivoMatriculaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processoSelectivoMatriculaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/processo-selectivo-matriculas/{id}")
    public ResponseEntity<ProcessoSelectivoMatriculaDTO> updateProcessoSelectivoMatricula(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProcessoSelectivoMatricula : {}, {}", id, processoSelectivoMatriculaDTO);
        if (processoSelectivoMatriculaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processoSelectivoMatriculaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processoSelectivoMatriculaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcessoSelectivoMatriculaDTO result = processoSelectivoMatriculaService.update(processoSelectivoMatriculaDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processoSelectivoMatriculaDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /processo-selectivo-matriculas/:id} : Partial updates given fields of an existing processoSelectivoMatricula, field will ignore if it is null
     *
     * @param id the id of the processoSelectivoMatriculaDTO to save.
     * @param processoSelectivoMatriculaDTO the processoSelectivoMatriculaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processoSelectivoMatriculaDTO,
     * or with status {@code 400 (Bad Request)} if the processoSelectivoMatriculaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the processoSelectivoMatriculaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the processoSelectivoMatriculaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/processo-selectivo-matriculas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProcessoSelectivoMatriculaDTO> partialUpdateProcessoSelectivoMatricula(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProcessoSelectivoMatricula partially : {}, {}", id, processoSelectivoMatriculaDTO);
        if (processoSelectivoMatriculaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processoSelectivoMatriculaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processoSelectivoMatriculaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProcessoSelectivoMatriculaDTO> result = processoSelectivoMatriculaService.partialUpdate(processoSelectivoMatriculaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processoSelectivoMatriculaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /processo-selectivo-matriculas} : get all the processoSelectivoMatriculas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processoSelectivoMatriculas in body.
     */
    @GetMapping("/processo-selectivo-matriculas")
    public ResponseEntity<List<ProcessoSelectivoMatriculaDTO>> getAllProcessoSelectivoMatriculas(
        ProcessoSelectivoMatriculaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ProcessoSelectivoMatriculas by criteria: {}", criteria);
        Page<ProcessoSelectivoMatriculaDTO> page = processoSelectivoMatriculaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /processo-selectivo-matriculas/count} : count all the processoSelectivoMatriculas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/processo-selectivo-matriculas/count")
    public ResponseEntity<Long> countProcessoSelectivoMatriculas(ProcessoSelectivoMatriculaCriteria criteria) {
        log.debug("REST request to count ProcessoSelectivoMatriculas by criteria: {}", criteria);
        return ResponseEntity.ok().body(processoSelectivoMatriculaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /processo-selectivo-matriculas/:id} : get the "id" processoSelectivoMatricula.
     *
     * @param id the id of the processoSelectivoMatriculaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processoSelectivoMatriculaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/processo-selectivo-matriculas/{id}")
    public ResponseEntity<ProcessoSelectivoMatriculaDTO> getProcessoSelectivoMatricula(@PathVariable Long id) {
        log.debug("REST request to get ProcessoSelectivoMatricula : {}", id);
        Optional<ProcessoSelectivoMatriculaDTO> processoSelectivoMatriculaDTO = processoSelectivoMatriculaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processoSelectivoMatriculaDTO);
    }

    /**
     * {@code DELETE  /processo-selectivo-matriculas/:id} : delete the "id" processoSelectivoMatricula.
     *
     * @param id the id of the processoSelectivoMatriculaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/processo-selectivo-matriculas/{id}")
    public ResponseEntity<Void> deleteProcessoSelectivoMatricula(@PathVariable Long id) {
        log.debug("REST request to delete ProcessoSelectivoMatricula : {}", id);
        processoSelectivoMatriculaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
