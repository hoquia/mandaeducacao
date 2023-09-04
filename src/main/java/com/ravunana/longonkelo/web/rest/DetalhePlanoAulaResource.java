package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.DetalhePlanoAulaRepository;
import com.ravunana.longonkelo.service.DetalhePlanoAulaQueryService;
import com.ravunana.longonkelo.service.DetalhePlanoAulaService;
import com.ravunana.longonkelo.service.criteria.DetalhePlanoAulaCriteria;
import com.ravunana.longonkelo.service.dto.DetalhePlanoAulaDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.DetalhePlanoAula}.
 */
@RestController
@RequestMapping("/api")
public class DetalhePlanoAulaResource {

    private final Logger log = LoggerFactory.getLogger(DetalhePlanoAulaResource.class);

    private static final String ENTITY_NAME = "detalhePlanoAula";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetalhePlanoAulaService detalhePlanoAulaService;

    private final DetalhePlanoAulaRepository detalhePlanoAulaRepository;

    private final DetalhePlanoAulaQueryService detalhePlanoAulaQueryService;

    public DetalhePlanoAulaResource(
        DetalhePlanoAulaService detalhePlanoAulaService,
        DetalhePlanoAulaRepository detalhePlanoAulaRepository,
        DetalhePlanoAulaQueryService detalhePlanoAulaQueryService
    ) {
        this.detalhePlanoAulaService = detalhePlanoAulaService;
        this.detalhePlanoAulaRepository = detalhePlanoAulaRepository;
        this.detalhePlanoAulaQueryService = detalhePlanoAulaQueryService;
    }

    /**
     * {@code POST  /detalhe-plano-aulas} : Create a new detalhePlanoAula.
     *
     * @param detalhePlanoAulaDTO the detalhePlanoAulaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detalhePlanoAulaDTO, or with status {@code 400 (Bad Request)} if the detalhePlanoAula has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detalhe-plano-aulas")
    public ResponseEntity<DetalhePlanoAulaDTO> createDetalhePlanoAula(@Valid @RequestBody DetalhePlanoAulaDTO detalhePlanoAulaDTO)
        throws URISyntaxException {
        log.debug("REST request to save DetalhePlanoAula : {}", detalhePlanoAulaDTO);
        if (detalhePlanoAulaDTO.getId() != null) {
            throw new BadRequestAlertException("A new detalhePlanoAula cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetalhePlanoAulaDTO result = detalhePlanoAulaService.save(detalhePlanoAulaDTO);
        return ResponseEntity
            .created(new URI("/api/detalhe-plano-aulas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detalhe-plano-aulas/:id} : Updates an existing detalhePlanoAula.
     *
     * @param id the id of the detalhePlanoAulaDTO to save.
     * @param detalhePlanoAulaDTO the detalhePlanoAulaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalhePlanoAulaDTO,
     * or with status {@code 400 (Bad Request)} if the detalhePlanoAulaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detalhePlanoAulaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detalhe-plano-aulas/{id}")
    public ResponseEntity<DetalhePlanoAulaDTO> updateDetalhePlanoAula(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DetalhePlanoAulaDTO detalhePlanoAulaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DetalhePlanoAula : {}, {}", id, detalhePlanoAulaDTO);
        if (detalhePlanoAulaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detalhePlanoAulaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detalhePlanoAulaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetalhePlanoAulaDTO result = detalhePlanoAulaService.update(detalhePlanoAulaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detalhePlanoAulaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detalhe-plano-aulas/:id} : Partial updates given fields of an existing detalhePlanoAula, field will ignore if it is null
     *
     * @param id the id of the detalhePlanoAulaDTO to save.
     * @param detalhePlanoAulaDTO the detalhePlanoAulaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalhePlanoAulaDTO,
     * or with status {@code 400 (Bad Request)} if the detalhePlanoAulaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the detalhePlanoAulaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the detalhePlanoAulaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/detalhe-plano-aulas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DetalhePlanoAulaDTO> partialUpdateDetalhePlanoAula(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DetalhePlanoAulaDTO detalhePlanoAulaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetalhePlanoAula partially : {}, {}", id, detalhePlanoAulaDTO);
        if (detalhePlanoAulaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detalhePlanoAulaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detalhePlanoAulaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetalhePlanoAulaDTO> result = detalhePlanoAulaService.partialUpdate(detalhePlanoAulaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detalhePlanoAulaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /detalhe-plano-aulas} : get all the detalhePlanoAulas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detalhePlanoAulas in body.
     */
    @GetMapping("/detalhe-plano-aulas")
    public ResponseEntity<List<DetalhePlanoAulaDTO>> getAllDetalhePlanoAulas(
        DetalhePlanoAulaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DetalhePlanoAulas by criteria: {}", criteria);
        Page<DetalhePlanoAulaDTO> page = detalhePlanoAulaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detalhe-plano-aulas/count} : count all the detalhePlanoAulas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/detalhe-plano-aulas/count")
    public ResponseEntity<Long> countDetalhePlanoAulas(DetalhePlanoAulaCriteria criteria) {
        log.debug("REST request to count DetalhePlanoAulas by criteria: {}", criteria);
        return ResponseEntity.ok().body(detalhePlanoAulaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /detalhe-plano-aulas/:id} : get the "id" detalhePlanoAula.
     *
     * @param id the id of the detalhePlanoAulaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detalhePlanoAulaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detalhe-plano-aulas/{id}")
    public ResponseEntity<DetalhePlanoAulaDTO> getDetalhePlanoAula(@PathVariable Long id) {
        log.debug("REST request to get DetalhePlanoAula : {}", id);
        Optional<DetalhePlanoAulaDTO> detalhePlanoAulaDTO = detalhePlanoAulaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detalhePlanoAulaDTO);
    }

    /**
     * {@code DELETE  /detalhe-plano-aulas/:id} : delete the "id" detalhePlanoAula.
     *
     * @param id the id of the detalhePlanoAulaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detalhe-plano-aulas/{id}")
    public ResponseEntity<Void> deleteDetalhePlanoAula(@PathVariable Long id) {
        log.debug("REST request to delete DetalhePlanoAula : {}", id);
        detalhePlanoAulaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
