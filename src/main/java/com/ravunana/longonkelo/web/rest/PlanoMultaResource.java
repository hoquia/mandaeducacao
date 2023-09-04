package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.PlanoMultaRepository;
import com.ravunana.longonkelo.service.PlanoMultaQueryService;
import com.ravunana.longonkelo.service.PlanoMultaService;
import com.ravunana.longonkelo.service.criteria.PlanoMultaCriteria;
import com.ravunana.longonkelo.service.dto.PlanoMultaDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.PlanoMulta}.
 */
@RestController
@RequestMapping("/api")
public class PlanoMultaResource {

    private final Logger log = LoggerFactory.getLogger(PlanoMultaResource.class);

    private static final String ENTITY_NAME = "planoMulta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoMultaService planoMultaService;

    private final PlanoMultaRepository planoMultaRepository;

    private final PlanoMultaQueryService planoMultaQueryService;

    public PlanoMultaResource(
        PlanoMultaService planoMultaService,
        PlanoMultaRepository planoMultaRepository,
        PlanoMultaQueryService planoMultaQueryService
    ) {
        this.planoMultaService = planoMultaService;
        this.planoMultaRepository = planoMultaRepository;
        this.planoMultaQueryService = planoMultaQueryService;
    }

    /**
     * {@code POST  /plano-multas} : Create a new planoMulta.
     *
     * @param planoMultaDTO the planoMultaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoMultaDTO, or with status {@code 400 (Bad Request)} if the planoMulta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plano-multas")
    public ResponseEntity<PlanoMultaDTO> createPlanoMulta(@Valid @RequestBody PlanoMultaDTO planoMultaDTO) throws URISyntaxException {
        log.debug("REST request to save PlanoMulta : {}", planoMultaDTO);
        if (planoMultaDTO.getId() != null) {
            throw new BadRequestAlertException("A new planoMulta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoMultaDTO result = planoMultaService.save(planoMultaDTO);
        return ResponseEntity
            .created(new URI("/api/plano-multas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plano-multas/:id} : Updates an existing planoMulta.
     *
     * @param id the id of the planoMultaDTO to save.
     * @param planoMultaDTO the planoMultaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoMultaDTO,
     * or with status {@code 400 (Bad Request)} if the planoMultaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoMultaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plano-multas/{id}")
    public ResponseEntity<PlanoMultaDTO> updatePlanoMulta(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlanoMultaDTO planoMultaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlanoMulta : {}, {}", id, planoMultaDTO);
        if (planoMultaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoMultaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoMultaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlanoMultaDTO result = planoMultaService.update(planoMultaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoMultaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plano-multas/:id} : Partial updates given fields of an existing planoMulta, field will ignore if it is null
     *
     * @param id the id of the planoMultaDTO to save.
     * @param planoMultaDTO the planoMultaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoMultaDTO,
     * or with status {@code 400 (Bad Request)} if the planoMultaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the planoMultaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the planoMultaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plano-multas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlanoMultaDTO> partialUpdatePlanoMulta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlanoMultaDTO planoMultaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlanoMulta partially : {}, {}", id, planoMultaDTO);
        if (planoMultaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoMultaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoMultaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlanoMultaDTO> result = planoMultaService.partialUpdate(planoMultaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoMultaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /plano-multas} : get all the planoMultas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoMultas in body.
     */
    @GetMapping("/plano-multas")
    public ResponseEntity<List<PlanoMultaDTO>> getAllPlanoMultas(
        PlanoMultaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PlanoMultas by criteria: {}", criteria);
        Page<PlanoMultaDTO> page = planoMultaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plano-multas/count} : count all the planoMultas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/plano-multas/count")
    public ResponseEntity<Long> countPlanoMultas(PlanoMultaCriteria criteria) {
        log.debug("REST request to count PlanoMultas by criteria: {}", criteria);
        return ResponseEntity.ok().body(planoMultaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plano-multas/:id} : get the "id" planoMulta.
     *
     * @param id the id of the planoMultaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoMultaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plano-multas/{id}")
    public ResponseEntity<PlanoMultaDTO> getPlanoMulta(@PathVariable Long id) {
        log.debug("REST request to get PlanoMulta : {}", id);
        Optional<PlanoMultaDTO> planoMultaDTO = planoMultaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planoMultaDTO);
    }

    /**
     * {@code DELETE  /plano-multas/:id} : delete the "id" planoMulta.
     *
     * @param id the id of the planoMultaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plano-multas/{id}")
    public ResponseEntity<Void> deletePlanoMulta(@PathVariable Long id) {
        log.debug("REST request to delete PlanoMulta : {}", id);
        planoMultaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
