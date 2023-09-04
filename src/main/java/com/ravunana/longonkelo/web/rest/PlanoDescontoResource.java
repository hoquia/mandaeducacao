package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.PlanoDescontoRepository;
import com.ravunana.longonkelo.service.PlanoDescontoQueryService;
import com.ravunana.longonkelo.service.PlanoDescontoService;
import com.ravunana.longonkelo.service.criteria.PlanoDescontoCriteria;
import com.ravunana.longonkelo.service.dto.PlanoDescontoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.PlanoDesconto}.
 */
@RestController
@RequestMapping("/api")
public class PlanoDescontoResource {

    private final Logger log = LoggerFactory.getLogger(PlanoDescontoResource.class);

    private static final String ENTITY_NAME = "planoDesconto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoDescontoService planoDescontoService;

    private final PlanoDescontoRepository planoDescontoRepository;

    private final PlanoDescontoQueryService planoDescontoQueryService;

    public PlanoDescontoResource(
        PlanoDescontoService planoDescontoService,
        PlanoDescontoRepository planoDescontoRepository,
        PlanoDescontoQueryService planoDescontoQueryService
    ) {
        this.planoDescontoService = planoDescontoService;
        this.planoDescontoRepository = planoDescontoRepository;
        this.planoDescontoQueryService = planoDescontoQueryService;
    }

    /**
     * {@code POST  /plano-descontos} : Create a new planoDesconto.
     *
     * @param planoDescontoDTO the planoDescontoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoDescontoDTO, or with status {@code 400 (Bad Request)} if the planoDesconto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plano-descontos")
    public ResponseEntity<PlanoDescontoDTO> createPlanoDesconto(@Valid @RequestBody PlanoDescontoDTO planoDescontoDTO)
        throws URISyntaxException {
        log.debug("REST request to save PlanoDesconto : {}", planoDescontoDTO);
        if (planoDescontoDTO.getId() != null) {
            throw new BadRequestAlertException("A new planoDesconto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoDescontoDTO result = planoDescontoService.save(planoDescontoDTO);
        return ResponseEntity
            .created(new URI("/api/plano-descontos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plano-descontos/:id} : Updates an existing planoDesconto.
     *
     * @param id the id of the planoDescontoDTO to save.
     * @param planoDescontoDTO the planoDescontoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoDescontoDTO,
     * or with status {@code 400 (Bad Request)} if the planoDescontoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoDescontoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plano-descontos/{id}")
    public ResponseEntity<PlanoDescontoDTO> updatePlanoDesconto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlanoDescontoDTO planoDescontoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlanoDesconto : {}, {}", id, planoDescontoDTO);
        if (planoDescontoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoDescontoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoDescontoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlanoDescontoDTO result = planoDescontoService.update(planoDescontoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoDescontoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plano-descontos/:id} : Partial updates given fields of an existing planoDesconto, field will ignore if it is null
     *
     * @param id the id of the planoDescontoDTO to save.
     * @param planoDescontoDTO the planoDescontoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoDescontoDTO,
     * or with status {@code 400 (Bad Request)} if the planoDescontoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the planoDescontoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the planoDescontoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plano-descontos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlanoDescontoDTO> partialUpdatePlanoDesconto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlanoDescontoDTO planoDescontoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlanoDesconto partially : {}, {}", id, planoDescontoDTO);
        if (planoDescontoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoDescontoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoDescontoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlanoDescontoDTO> result = planoDescontoService.partialUpdate(planoDescontoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoDescontoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /plano-descontos} : get all the planoDescontos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoDescontos in body.
     */
    @GetMapping("/plano-descontos")
    public ResponseEntity<List<PlanoDescontoDTO>> getAllPlanoDescontos(
        PlanoDescontoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PlanoDescontos by criteria: {}", criteria);
        Page<PlanoDescontoDTO> page = planoDescontoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plano-descontos/count} : count all the planoDescontos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/plano-descontos/count")
    public ResponseEntity<Long> countPlanoDescontos(PlanoDescontoCriteria criteria) {
        log.debug("REST request to count PlanoDescontos by criteria: {}", criteria);
        return ResponseEntity.ok().body(planoDescontoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plano-descontos/:id} : get the "id" planoDesconto.
     *
     * @param id the id of the planoDescontoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoDescontoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plano-descontos/{id}")
    public ResponseEntity<PlanoDescontoDTO> getPlanoDesconto(@PathVariable Long id) {
        log.debug("REST request to get PlanoDesconto : {}", id);
        Optional<PlanoDescontoDTO> planoDescontoDTO = planoDescontoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planoDescontoDTO);
    }

    /**
     * {@code DELETE  /plano-descontos/:id} : delete the "id" planoDesconto.
     *
     * @param id the id of the planoDescontoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plano-descontos/{id}")
    public ResponseEntity<Void> deletePlanoDesconto(@PathVariable Long id) {
        log.debug("REST request to delete PlanoDesconto : {}", id);
        planoDescontoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
