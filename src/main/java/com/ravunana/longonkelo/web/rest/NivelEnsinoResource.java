package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.NivelEnsinoRepository;
import com.ravunana.longonkelo.service.NivelEnsinoQueryService;
import com.ravunana.longonkelo.service.NivelEnsinoService;
import com.ravunana.longonkelo.service.criteria.NivelEnsinoCriteria;
import com.ravunana.longonkelo.service.dto.NivelEnsinoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.NivelEnsino}.
 */
@RestController
@RequestMapping("/api")
public class NivelEnsinoResource {

    private final Logger log = LoggerFactory.getLogger(NivelEnsinoResource.class);

    private static final String ENTITY_NAME = "nivelEnsino";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NivelEnsinoService nivelEnsinoService;

    private final NivelEnsinoRepository nivelEnsinoRepository;

    private final NivelEnsinoQueryService nivelEnsinoQueryService;

    public NivelEnsinoResource(
        NivelEnsinoService nivelEnsinoService,
        NivelEnsinoRepository nivelEnsinoRepository,
        NivelEnsinoQueryService nivelEnsinoQueryService
    ) {
        this.nivelEnsinoService = nivelEnsinoService;
        this.nivelEnsinoRepository = nivelEnsinoRepository;
        this.nivelEnsinoQueryService = nivelEnsinoQueryService;
    }

    /**
     * {@code POST  /nivel-ensinos} : Create a new nivelEnsino.
     *
     * @param nivelEnsinoDTO the nivelEnsinoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nivelEnsinoDTO, or with status {@code 400 (Bad Request)} if the nivelEnsino has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nivel-ensinos")
    public ResponseEntity<NivelEnsinoDTO> createNivelEnsino(@Valid @RequestBody NivelEnsinoDTO nivelEnsinoDTO) throws URISyntaxException {
        log.debug("REST request to save NivelEnsino : {}", nivelEnsinoDTO);
        if (nivelEnsinoDTO.getId() != null) {
            throw new BadRequestAlertException("A new nivelEnsino cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NivelEnsinoDTO result = nivelEnsinoService.save(nivelEnsinoDTO);
        return ResponseEntity
            .created(new URI("/api/nivel-ensinos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nivel-ensinos/:id} : Updates an existing nivelEnsino.
     *
     * @param id the id of the nivelEnsinoDTO to save.
     * @param nivelEnsinoDTO the nivelEnsinoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nivelEnsinoDTO,
     * or with status {@code 400 (Bad Request)} if the nivelEnsinoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nivelEnsinoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nivel-ensinos/{id}")
    public ResponseEntity<NivelEnsinoDTO> updateNivelEnsino(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NivelEnsinoDTO nivelEnsinoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NivelEnsino : {}, {}", id, nivelEnsinoDTO);
        if (nivelEnsinoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nivelEnsinoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nivelEnsinoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NivelEnsinoDTO result = nivelEnsinoService.update(nivelEnsinoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nivelEnsinoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nivel-ensinos/:id} : Partial updates given fields of an existing nivelEnsino, field will ignore if it is null
     *
     * @param id the id of the nivelEnsinoDTO to save.
     * @param nivelEnsinoDTO the nivelEnsinoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nivelEnsinoDTO,
     * or with status {@code 400 (Bad Request)} if the nivelEnsinoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nivelEnsinoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nivelEnsinoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nivel-ensinos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NivelEnsinoDTO> partialUpdateNivelEnsino(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NivelEnsinoDTO nivelEnsinoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NivelEnsino partially : {}, {}", id, nivelEnsinoDTO);
        if (nivelEnsinoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nivelEnsinoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nivelEnsinoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NivelEnsinoDTO> result = nivelEnsinoService.partialUpdate(nivelEnsinoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nivelEnsinoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nivel-ensinos} : get all the nivelEnsinos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nivelEnsinos in body.
     */
    @GetMapping("/nivel-ensinos")
    public ResponseEntity<List<NivelEnsinoDTO>> getAllNivelEnsinos(
        NivelEnsinoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NivelEnsinos by criteria: {}", criteria);
        Page<NivelEnsinoDTO> page = nivelEnsinoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nivel-ensinos/count} : count all the nivelEnsinos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/nivel-ensinos/count")
    public ResponseEntity<Long> countNivelEnsinos(NivelEnsinoCriteria criteria) {
        log.debug("REST request to count NivelEnsinos by criteria: {}", criteria);
        return ResponseEntity.ok().body(nivelEnsinoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nivel-ensinos/:id} : get the "id" nivelEnsino.
     *
     * @param id the id of the nivelEnsinoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nivelEnsinoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nivel-ensinos/{id}")
    public ResponseEntity<NivelEnsinoDTO> getNivelEnsino(@PathVariable Long id) {
        log.debug("REST request to get NivelEnsino : {}", id);
        Optional<NivelEnsinoDTO> nivelEnsinoDTO = nivelEnsinoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nivelEnsinoDTO);
    }

    /**
     * {@code DELETE  /nivel-ensinos/:id} : delete the "id" nivelEnsino.
     *
     * @param id the id of the nivelEnsinoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nivel-ensinos/{id}")
    public ResponseEntity<Void> deleteNivelEnsino(@PathVariable Long id) {
        log.debug("REST request to delete NivelEnsino : {}", id);
        nivelEnsinoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
