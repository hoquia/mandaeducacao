package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.PeriodoHorarioRepository;
import com.ravunana.longonkelo.service.PeriodoHorarioQueryService;
import com.ravunana.longonkelo.service.PeriodoHorarioService;
import com.ravunana.longonkelo.service.criteria.PeriodoHorarioCriteria;
import com.ravunana.longonkelo.service.dto.PeriodoHorarioDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.PeriodoHorario}.
 */
@RestController
@RequestMapping("/api")
public class PeriodoHorarioResource {

    private final Logger log = LoggerFactory.getLogger(PeriodoHorarioResource.class);

    private static final String ENTITY_NAME = "periodoHorario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeriodoHorarioService periodoHorarioService;

    private final PeriodoHorarioRepository periodoHorarioRepository;

    private final PeriodoHorarioQueryService periodoHorarioQueryService;

    public PeriodoHorarioResource(
        PeriodoHorarioService periodoHorarioService,
        PeriodoHorarioRepository periodoHorarioRepository,
        PeriodoHorarioQueryService periodoHorarioQueryService
    ) {
        this.periodoHorarioService = periodoHorarioService;
        this.periodoHorarioRepository = periodoHorarioRepository;
        this.periodoHorarioQueryService = periodoHorarioQueryService;
    }

    /**
     * {@code POST  /periodo-horarios} : Create a new periodoHorario.
     *
     * @param periodoHorarioDTO the periodoHorarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new periodoHorarioDTO, or with status {@code 400 (Bad Request)} if the periodoHorario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/periodo-horarios")
    public ResponseEntity<PeriodoHorarioDTO> createPeriodoHorario(@Valid @RequestBody PeriodoHorarioDTO periodoHorarioDTO)
        throws URISyntaxException {
        log.debug("REST request to save PeriodoHorario : {}", periodoHorarioDTO);
        if (periodoHorarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new periodoHorario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodoHorarioDTO result = periodoHorarioService.save(periodoHorarioDTO);
        return ResponseEntity
            .created(new URI("/api/periodo-horarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /periodo-horarios/:id} : Updates an existing periodoHorario.
     *
     * @param id the id of the periodoHorarioDTO to save.
     * @param periodoHorarioDTO the periodoHorarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodoHorarioDTO,
     * or with status {@code 400 (Bad Request)} if the periodoHorarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the periodoHorarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/periodo-horarios/{id}")
    public ResponseEntity<PeriodoHorarioDTO> updatePeriodoHorario(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PeriodoHorarioDTO periodoHorarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PeriodoHorario : {}, {}", id, periodoHorarioDTO);
        if (periodoHorarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodoHorarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodoHorarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PeriodoHorarioDTO result = periodoHorarioService.update(periodoHorarioDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodoHorarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /periodo-horarios/:id} : Partial updates given fields of an existing periodoHorario, field will ignore if it is null
     *
     * @param id the id of the periodoHorarioDTO to save.
     * @param periodoHorarioDTO the periodoHorarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodoHorarioDTO,
     * or with status {@code 400 (Bad Request)} if the periodoHorarioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the periodoHorarioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the periodoHorarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/periodo-horarios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PeriodoHorarioDTO> partialUpdatePeriodoHorario(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PeriodoHorarioDTO periodoHorarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PeriodoHorario partially : {}, {}", id, periodoHorarioDTO);
        if (periodoHorarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodoHorarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodoHorarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PeriodoHorarioDTO> result = periodoHorarioService.partialUpdate(periodoHorarioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodoHorarioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /periodo-horarios} : get all the periodoHorarios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of periodoHorarios in body.
     */
    @GetMapping("/periodo-horarios")
    public ResponseEntity<List<PeriodoHorarioDTO>> getAllPeriodoHorarios(
        PeriodoHorarioCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PeriodoHorarios by criteria: {}", criteria);
        Page<PeriodoHorarioDTO> page = periodoHorarioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /periodo-horarios/count} : count all the periodoHorarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/periodo-horarios/count")
    public ResponseEntity<Long> countPeriodoHorarios(PeriodoHorarioCriteria criteria) {
        log.debug("REST request to count PeriodoHorarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(periodoHorarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /periodo-horarios/:id} : get the "id" periodoHorario.
     *
     * @param id the id of the periodoHorarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the periodoHorarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/periodo-horarios/{id}")
    public ResponseEntity<PeriodoHorarioDTO> getPeriodoHorario(@PathVariable Long id) {
        log.debug("REST request to get PeriodoHorario : {}", id);
        Optional<PeriodoHorarioDTO> periodoHorarioDTO = periodoHorarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(periodoHorarioDTO);
    }

    /**
     * {@code DELETE  /periodo-horarios/:id} : delete the "id" periodoHorario.
     *
     * @param id the id of the periodoHorarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/periodo-horarios/{id}")
    public ResponseEntity<Void> deletePeriodoHorario(@PathVariable Long id) {
        log.debug("REST request to delete PeriodoHorario : {}", id);
        periodoHorarioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
