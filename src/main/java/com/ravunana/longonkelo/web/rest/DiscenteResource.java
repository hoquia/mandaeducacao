package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.DiscenteRepository;
import com.ravunana.longonkelo.service.DiscenteQueryService;
import com.ravunana.longonkelo.service.DiscenteService;
import com.ravunana.longonkelo.service.criteria.DiscenteCriteria;
import com.ravunana.longonkelo.service.dto.DiscenteDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.Discente}.
 */
@RestController
@RequestMapping("/api")
public class DiscenteResource {

    private final Logger log = LoggerFactory.getLogger(DiscenteResource.class);

    private static final String ENTITY_NAME = "discente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiscenteService discenteService;

    private final DiscenteRepository discenteRepository;

    private final DiscenteQueryService discenteQueryService;

    public DiscenteResource(
        DiscenteService discenteService,
        DiscenteRepository discenteRepository,
        DiscenteQueryService discenteQueryService
    ) {
        this.discenteService = discenteService;
        this.discenteRepository = discenteRepository;
        this.discenteQueryService = discenteQueryService;
    }

    /**
     * {@code POST  /discentes} : Create a new discente.
     *
     * @param discenteDTO the discenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new discenteDTO, or with status {@code 400 (Bad Request)} if the discente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/discentes")
    public ResponseEntity<DiscenteDTO> createDiscente(@Valid @RequestBody DiscenteDTO discenteDTO) throws URISyntaxException {
        log.debug("REST request to save Discente : {}", discenteDTO);
        if (discenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new discente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiscenteDTO result = discenteService.save(discenteDTO);
        return ResponseEntity
            .created(new URI("/api/discentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /discentes/:id} : Updates an existing discente.
     *
     * @param id the id of the discenteDTO to save.
     * @param discenteDTO the discenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discenteDTO,
     * or with status {@code 400 (Bad Request)} if the discenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the discenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/discentes/{id}")
    public ResponseEntity<DiscenteDTO> updateDiscente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DiscenteDTO discenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Discente : {}, {}", id, discenteDTO);
        if (discenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, discenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!discenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DiscenteDTO result = discenteService.update(discenteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, discenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /discentes/:id} : Partial updates given fields of an existing discente, field will ignore if it is null
     *
     * @param id the id of the discenteDTO to save.
     * @param discenteDTO the discenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discenteDTO,
     * or with status {@code 400 (Bad Request)} if the discenteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the discenteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the discenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/discentes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DiscenteDTO> partialUpdateDiscente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DiscenteDTO discenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Discente partially : {}, {}", id, discenteDTO);
        if (discenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, discenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!discenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DiscenteDTO> result = discenteService.partialUpdate(discenteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, discenteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /discentes} : get all the discentes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of discentes in body.
     */
    @GetMapping("/discentes")
    public ResponseEntity<List<DiscenteDTO>> getAllDiscentes(
        DiscenteCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Discentes by criteria: {}", criteria);
        Page<DiscenteDTO> page = discenteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /discentes/count} : count all the discentes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/discentes/count")
    public ResponseEntity<Long> countDiscentes(DiscenteCriteria criteria) {
        log.debug("REST request to count Discentes by criteria: {}", criteria);
        return ResponseEntity.ok().body(discenteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /discentes/:id} : get the "id" discente.
     *
     * @param id the id of the discenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the discenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/discentes/{id}")
    public ResponseEntity<DiscenteDTO> getDiscente(@PathVariable Long id) {
        log.debug("REST request to get Discente : {}", id);
        Optional<DiscenteDTO> discenteDTO = discenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(discenteDTO);
    }

    /**
     * {@code DELETE  /discentes/:id} : delete the "id" discente.
     *
     * @param id the id of the discenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/discentes/{id}")
    public ResponseEntity<Void> deleteDiscente(@PathVariable Long id) {
        log.debug("REST request to delete Discente : {}", id);
        discenteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
