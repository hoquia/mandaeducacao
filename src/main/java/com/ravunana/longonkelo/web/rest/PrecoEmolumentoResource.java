package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.PrecoEmolumentoRepository;
import com.ravunana.longonkelo.service.PrecoEmolumentoQueryService;
import com.ravunana.longonkelo.service.PrecoEmolumentoService;
import com.ravunana.longonkelo.service.criteria.PrecoEmolumentoCriteria;
import com.ravunana.longonkelo.service.dto.PrecoEmolumentoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.PrecoEmolumento}.
 */
@RestController
@RequestMapping("/api")
public class PrecoEmolumentoResource {

    private final Logger log = LoggerFactory.getLogger(PrecoEmolumentoResource.class);

    private static final String ENTITY_NAME = "precoEmolumento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrecoEmolumentoService precoEmolumentoService;

    private final PrecoEmolumentoRepository precoEmolumentoRepository;

    private final PrecoEmolumentoQueryService precoEmolumentoQueryService;

    public PrecoEmolumentoResource(
        PrecoEmolumentoService precoEmolumentoService,
        PrecoEmolumentoRepository precoEmolumentoRepository,
        PrecoEmolumentoQueryService precoEmolumentoQueryService
    ) {
        this.precoEmolumentoService = precoEmolumentoService;
        this.precoEmolumentoRepository = precoEmolumentoRepository;
        this.precoEmolumentoQueryService = precoEmolumentoQueryService;
    }

    /**
     * {@code POST  /preco-emolumentos} : Create a new precoEmolumento.
     *
     * @param precoEmolumentoDTO the precoEmolumentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new precoEmolumentoDTO, or with status {@code 400 (Bad Request)} if the precoEmolumento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/preco-emolumentos")
    public ResponseEntity<PrecoEmolumentoDTO> createPrecoEmolumento(@Valid @RequestBody PrecoEmolumentoDTO precoEmolumentoDTO)
        throws URISyntaxException {
        log.debug("REST request to save PrecoEmolumento : {}", precoEmolumentoDTO);
        if (precoEmolumentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new precoEmolumento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrecoEmolumentoDTO result = precoEmolumentoService.save(precoEmolumentoDTO);
        return ResponseEntity
            .created(new URI("/api/preco-emolumentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /preco-emolumentos/:id} : Updates an existing precoEmolumento.
     *
     * @param id the id of the precoEmolumentoDTO to save.
     * @param precoEmolumentoDTO the precoEmolumentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated precoEmolumentoDTO,
     * or with status {@code 400 (Bad Request)} if the precoEmolumentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the precoEmolumentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/preco-emolumentos/{id}")
    public ResponseEntity<PrecoEmolumentoDTO> updatePrecoEmolumento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PrecoEmolumentoDTO precoEmolumentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PrecoEmolumento : {}, {}", id, precoEmolumentoDTO);
        if (precoEmolumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, precoEmolumentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!precoEmolumentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrecoEmolumentoDTO result = precoEmolumentoService.update(precoEmolumentoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, precoEmolumentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /preco-emolumentos/:id} : Partial updates given fields of an existing precoEmolumento, field will ignore if it is null
     *
     * @param id the id of the precoEmolumentoDTO to save.
     * @param precoEmolumentoDTO the precoEmolumentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated precoEmolumentoDTO,
     * or with status {@code 400 (Bad Request)} if the precoEmolumentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the precoEmolumentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the precoEmolumentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/preco-emolumentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrecoEmolumentoDTO> partialUpdatePrecoEmolumento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PrecoEmolumentoDTO precoEmolumentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PrecoEmolumento partially : {}, {}", id, precoEmolumentoDTO);
        if (precoEmolumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, precoEmolumentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!precoEmolumentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrecoEmolumentoDTO> result = precoEmolumentoService.partialUpdate(precoEmolumentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, precoEmolumentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /preco-emolumentos} : get all the precoEmolumentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of precoEmolumentos in body.
     */
    @GetMapping("/preco-emolumentos")
    public ResponseEntity<List<PrecoEmolumentoDTO>> getAllPrecoEmolumentos(
        PrecoEmolumentoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PrecoEmolumentos by criteria: {}", criteria);
        Page<PrecoEmolumentoDTO> page = precoEmolumentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /preco-emolumentos/count} : count all the precoEmolumentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/preco-emolumentos/count")
    public ResponseEntity<Long> countPrecoEmolumentos(PrecoEmolumentoCriteria criteria) {
        log.debug("REST request to count PrecoEmolumentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(precoEmolumentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /preco-emolumentos/:id} : get the "id" precoEmolumento.
     *
     * @param id the id of the precoEmolumentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the precoEmolumentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preco-emolumentos/{id}")
    public ResponseEntity<PrecoEmolumentoDTO> getPrecoEmolumento(@PathVariable Long id) {
        log.debug("REST request to get PrecoEmolumento : {}", id);
        Optional<PrecoEmolumentoDTO> precoEmolumentoDTO = precoEmolumentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(precoEmolumentoDTO);
    }

    /**
     * {@code DELETE  /preco-emolumentos/:id} : delete the "id" precoEmolumento.
     *
     * @param id the id of the precoEmolumentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/preco-emolumentos/{id}")
    public ResponseEntity<Void> deletePrecoEmolumento(@PathVariable Long id) {
        log.debug("REST request to delete PrecoEmolumento : {}", id);
        precoEmolumentoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
