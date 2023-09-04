package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.InstituicaoEnsinoRepository;
import com.ravunana.longonkelo.service.InstituicaoEnsinoQueryService;
import com.ravunana.longonkelo.service.InstituicaoEnsinoService;
import com.ravunana.longonkelo.service.criteria.InstituicaoEnsinoCriteria;
import com.ravunana.longonkelo.service.dto.InstituicaoEnsinoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.InstituicaoEnsino}.
 */
@RestController
@RequestMapping("/api")
public class InstituicaoEnsinoResource {

    private final Logger log = LoggerFactory.getLogger(InstituicaoEnsinoResource.class);

    private static final String ENTITY_NAME = "instituicaoEnsino";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstituicaoEnsinoService instituicaoEnsinoService;

    private final InstituicaoEnsinoRepository instituicaoEnsinoRepository;

    private final InstituicaoEnsinoQueryService instituicaoEnsinoQueryService;

    public InstituicaoEnsinoResource(
        InstituicaoEnsinoService instituicaoEnsinoService,
        InstituicaoEnsinoRepository instituicaoEnsinoRepository,
        InstituicaoEnsinoQueryService instituicaoEnsinoQueryService
    ) {
        this.instituicaoEnsinoService = instituicaoEnsinoService;
        this.instituicaoEnsinoRepository = instituicaoEnsinoRepository;
        this.instituicaoEnsinoQueryService = instituicaoEnsinoQueryService;
    }

    /**
     * {@code POST  /instituicao-ensinos} : Create a new instituicaoEnsino.
     *
     * @param instituicaoEnsinoDTO the instituicaoEnsinoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instituicaoEnsinoDTO, or with status {@code 400 (Bad Request)} if the instituicaoEnsino has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/instituicao-ensinos")
    public ResponseEntity<InstituicaoEnsinoDTO> createInstituicaoEnsino(@Valid @RequestBody InstituicaoEnsinoDTO instituicaoEnsinoDTO)
        throws URISyntaxException {
        log.debug("REST request to save InstituicaoEnsino : {}", instituicaoEnsinoDTO);
        if (instituicaoEnsinoDTO.getId() != null) {
            throw new BadRequestAlertException("A new instituicaoEnsino cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstituicaoEnsinoDTO result = instituicaoEnsinoService.save(instituicaoEnsinoDTO);
        return ResponseEntity
            .created(new URI("/api/instituicao-ensinos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /instituicao-ensinos/:id} : Updates an existing instituicaoEnsino.
     *
     * @param id the id of the instituicaoEnsinoDTO to save.
     * @param instituicaoEnsinoDTO the instituicaoEnsinoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instituicaoEnsinoDTO,
     * or with status {@code 400 (Bad Request)} if the instituicaoEnsinoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instituicaoEnsinoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/instituicao-ensinos/{id}")
    public ResponseEntity<InstituicaoEnsinoDTO> updateInstituicaoEnsino(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InstituicaoEnsinoDTO instituicaoEnsinoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InstituicaoEnsino : {}, {}", id, instituicaoEnsinoDTO);
        if (instituicaoEnsinoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, instituicaoEnsinoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!instituicaoEnsinoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InstituicaoEnsinoDTO result = instituicaoEnsinoService.update(instituicaoEnsinoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instituicaoEnsinoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /instituicao-ensinos/:id} : Partial updates given fields of an existing instituicaoEnsino, field will ignore if it is null
     *
     * @param id the id of the instituicaoEnsinoDTO to save.
     * @param instituicaoEnsinoDTO the instituicaoEnsinoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instituicaoEnsinoDTO,
     * or with status {@code 400 (Bad Request)} if the instituicaoEnsinoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the instituicaoEnsinoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the instituicaoEnsinoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/instituicao-ensinos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InstituicaoEnsinoDTO> partialUpdateInstituicaoEnsino(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InstituicaoEnsinoDTO instituicaoEnsinoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InstituicaoEnsino partially : {}, {}", id, instituicaoEnsinoDTO);
        if (instituicaoEnsinoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, instituicaoEnsinoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!instituicaoEnsinoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InstituicaoEnsinoDTO> result = instituicaoEnsinoService.partialUpdate(instituicaoEnsinoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instituicaoEnsinoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /instituicao-ensinos} : get all the instituicaoEnsinos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instituicaoEnsinos in body.
     */
    @GetMapping("/instituicao-ensinos")
    public ResponseEntity<List<InstituicaoEnsinoDTO>> getAllInstituicaoEnsinos(
        InstituicaoEnsinoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get InstituicaoEnsinos by criteria: {}", criteria);
        Page<InstituicaoEnsinoDTO> page = instituicaoEnsinoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /instituicao-ensinos/count} : count all the instituicaoEnsinos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/instituicao-ensinos/count")
    public ResponseEntity<Long> countInstituicaoEnsinos(InstituicaoEnsinoCriteria criteria) {
        log.debug("REST request to count InstituicaoEnsinos by criteria: {}", criteria);
        return ResponseEntity.ok().body(instituicaoEnsinoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /instituicao-ensinos/:id} : get the "id" instituicaoEnsino.
     *
     * @param id the id of the instituicaoEnsinoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instituicaoEnsinoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/instituicao-ensinos/{id}")
    public ResponseEntity<InstituicaoEnsinoDTO> getInstituicaoEnsino(@PathVariable Long id) {
        log.debug("REST request to get InstituicaoEnsino : {}", id);
        Optional<InstituicaoEnsinoDTO> instituicaoEnsinoDTO = instituicaoEnsinoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(instituicaoEnsinoDTO);
    }

    /**
     * {@code DELETE  /instituicao-ensinos/:id} : delete the "id" instituicaoEnsino.
     *
     * @param id the id of the instituicaoEnsinoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/instituicao-ensinos/{id}")
    public ResponseEntity<Void> deleteInstituicaoEnsino(@PathVariable Long id) {
        log.debug("REST request to delete InstituicaoEnsino : {}", id);
        instituicaoEnsinoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
