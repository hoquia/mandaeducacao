package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.PeriodoLancamentoNotaRepository;
import com.ravunana.longonkelo.service.PeriodoLancamentoNotaQueryService;
import com.ravunana.longonkelo.service.PeriodoLancamentoNotaService;
import com.ravunana.longonkelo.service.criteria.PeriodoLancamentoNotaCriteria;
import com.ravunana.longonkelo.service.dto.PeriodoLancamentoNotaDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.PeriodoLancamentoNota}.
 */
@RestController
@RequestMapping("/api")
public class PeriodoLancamentoNotaResource {

    private final Logger log = LoggerFactory.getLogger(PeriodoLancamentoNotaResource.class);

    private static final String ENTITY_NAME = "periodoLancamentoNota";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeriodoLancamentoNotaService periodoLancamentoNotaService;

    private final PeriodoLancamentoNotaRepository periodoLancamentoNotaRepository;

    private final PeriodoLancamentoNotaQueryService periodoLancamentoNotaQueryService;

    public PeriodoLancamentoNotaResource(
        PeriodoLancamentoNotaService periodoLancamentoNotaService,
        PeriodoLancamentoNotaRepository periodoLancamentoNotaRepository,
        PeriodoLancamentoNotaQueryService periodoLancamentoNotaQueryService
    ) {
        this.periodoLancamentoNotaService = periodoLancamentoNotaService;
        this.periodoLancamentoNotaRepository = periodoLancamentoNotaRepository;
        this.periodoLancamentoNotaQueryService = periodoLancamentoNotaQueryService;
    }

    /**
     * {@code POST  /periodo-lancamento-notas} : Create a new periodoLancamentoNota.
     *
     * @param periodoLancamentoNotaDTO the periodoLancamentoNotaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new periodoLancamentoNotaDTO, or with status {@code 400 (Bad Request)} if the periodoLancamentoNota has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/periodo-lancamento-notas")
    public ResponseEntity<PeriodoLancamentoNotaDTO> createPeriodoLancamentoNota(
        @Valid @RequestBody PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save PeriodoLancamentoNota : {}", periodoLancamentoNotaDTO);
        if (periodoLancamentoNotaDTO.getId() != null) {
            throw new BadRequestAlertException("A new periodoLancamentoNota cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodoLancamentoNotaDTO result = periodoLancamentoNotaService.save(periodoLancamentoNotaDTO);
        return ResponseEntity
            .created(new URI("/api/periodo-lancamento-notas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /periodo-lancamento-notas/:id} : Updates an existing periodoLancamentoNota.
     *
     * @param id the id of the periodoLancamentoNotaDTO to save.
     * @param periodoLancamentoNotaDTO the periodoLancamentoNotaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodoLancamentoNotaDTO,
     * or with status {@code 400 (Bad Request)} if the periodoLancamentoNotaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the periodoLancamentoNotaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/periodo-lancamento-notas/{id}")
    public ResponseEntity<PeriodoLancamentoNotaDTO> updatePeriodoLancamentoNota(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PeriodoLancamentoNota : {}, {}", id, periodoLancamentoNotaDTO);
        if (periodoLancamentoNotaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodoLancamentoNotaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodoLancamentoNotaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PeriodoLancamentoNotaDTO result = periodoLancamentoNotaService.update(periodoLancamentoNotaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodoLancamentoNotaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /periodo-lancamento-notas/:id} : Partial updates given fields of an existing periodoLancamentoNota, field will ignore if it is null
     *
     * @param id the id of the periodoLancamentoNotaDTO to save.
     * @param periodoLancamentoNotaDTO the periodoLancamentoNotaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodoLancamentoNotaDTO,
     * or with status {@code 400 (Bad Request)} if the periodoLancamentoNotaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the periodoLancamentoNotaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the periodoLancamentoNotaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/periodo-lancamento-notas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PeriodoLancamentoNotaDTO> partialUpdatePeriodoLancamentoNota(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PeriodoLancamentoNota partially : {}, {}", id, periodoLancamentoNotaDTO);
        if (periodoLancamentoNotaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodoLancamentoNotaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodoLancamentoNotaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PeriodoLancamentoNotaDTO> result = periodoLancamentoNotaService.partialUpdate(periodoLancamentoNotaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodoLancamentoNotaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /periodo-lancamento-notas} : get all the periodoLancamentoNotas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of periodoLancamentoNotas in body.
     */
    @GetMapping("/periodo-lancamento-notas")
    public ResponseEntity<List<PeriodoLancamentoNotaDTO>> getAllPeriodoLancamentoNotas(
        PeriodoLancamentoNotaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PeriodoLancamentoNotas by criteria: {}", criteria);
        Page<PeriodoLancamentoNotaDTO> page = periodoLancamentoNotaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /periodo-lancamento-notas/count} : count all the periodoLancamentoNotas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/periodo-lancamento-notas/count")
    public ResponseEntity<Long> countPeriodoLancamentoNotas(PeriodoLancamentoNotaCriteria criteria) {
        log.debug("REST request to count PeriodoLancamentoNotas by criteria: {}", criteria);
        return ResponseEntity.ok().body(periodoLancamentoNotaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /periodo-lancamento-notas/:id} : get the "id" periodoLancamentoNota.
     *
     * @param id the id of the periodoLancamentoNotaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the periodoLancamentoNotaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/periodo-lancamento-notas/{id}")
    public ResponseEntity<PeriodoLancamentoNotaDTO> getPeriodoLancamentoNota(@PathVariable Long id) {
        log.debug("REST request to get PeriodoLancamentoNota : {}", id);
        Optional<PeriodoLancamentoNotaDTO> periodoLancamentoNotaDTO = periodoLancamentoNotaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(periodoLancamentoNotaDTO);
    }

    /**
     * {@code DELETE  /periodo-lancamento-notas/:id} : delete the "id" periodoLancamentoNota.
     *
     * @param id the id of the periodoLancamentoNotaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/periodo-lancamento-notas/{id}")
    public ResponseEntity<Void> deletePeriodoLancamentoNota(@PathVariable Long id) {
        log.debug("REST request to delete PeriodoLancamentoNota : {}", id);
        periodoLancamentoNotaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
