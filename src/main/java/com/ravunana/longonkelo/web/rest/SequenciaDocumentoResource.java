package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.SequenciaDocumentoRepository;
import com.ravunana.longonkelo.service.SequenciaDocumentoQueryService;
import com.ravunana.longonkelo.service.SequenciaDocumentoService;
import com.ravunana.longonkelo.service.criteria.SequenciaDocumentoCriteria;
import com.ravunana.longonkelo.service.dto.SequenciaDocumentoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.SequenciaDocumento}.
 */
@RestController
@RequestMapping("/api")
public class SequenciaDocumentoResource {

    private final Logger log = LoggerFactory.getLogger(SequenciaDocumentoResource.class);

    private static final String ENTITY_NAME = "sequenciaDocumento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequenciaDocumentoService sequenciaDocumentoService;

    private final SequenciaDocumentoRepository sequenciaDocumentoRepository;

    private final SequenciaDocumentoQueryService sequenciaDocumentoQueryService;

    public SequenciaDocumentoResource(
        SequenciaDocumentoService sequenciaDocumentoService,
        SequenciaDocumentoRepository sequenciaDocumentoRepository,
        SequenciaDocumentoQueryService sequenciaDocumentoQueryService
    ) {
        this.sequenciaDocumentoService = sequenciaDocumentoService;
        this.sequenciaDocumentoRepository = sequenciaDocumentoRepository;
        this.sequenciaDocumentoQueryService = sequenciaDocumentoQueryService;
    }

    /**
     * {@code POST  /sequencia-documentos} : Create a new sequenciaDocumento.
     *
     * @param sequenciaDocumentoDTO the sequenciaDocumentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequenciaDocumentoDTO, or with status {@code 400 (Bad Request)} if the sequenciaDocumento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sequencia-documentos")
    public ResponseEntity<SequenciaDocumentoDTO> createSequenciaDocumento(@Valid @RequestBody SequenciaDocumentoDTO sequenciaDocumentoDTO)
        throws URISyntaxException {
        log.debug("REST request to save SequenciaDocumento : {}", sequenciaDocumentoDTO);
        if (sequenciaDocumentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new sequenciaDocumento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SequenciaDocumentoDTO result = sequenciaDocumentoService.save(sequenciaDocumentoDTO);
        return ResponseEntity
            .created(new URI("/api/sequencia-documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequencia-documentos/:id} : Updates an existing sequenciaDocumento.
     *
     * @param id the id of the sequenciaDocumentoDTO to save.
     * @param sequenciaDocumentoDTO the sequenciaDocumentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenciaDocumentoDTO,
     * or with status {@code 400 (Bad Request)} if the sequenciaDocumentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequenciaDocumentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sequencia-documentos/{id}")
    public ResponseEntity<SequenciaDocumentoDTO> updateSequenciaDocumento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SequenciaDocumentoDTO sequenciaDocumentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SequenciaDocumento : {}, {}", id, sequenciaDocumentoDTO);
        if (sequenciaDocumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sequenciaDocumentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sequenciaDocumentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SequenciaDocumentoDTO result = sequenciaDocumentoService.update(sequenciaDocumentoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenciaDocumentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sequencia-documentos/:id} : Partial updates given fields of an existing sequenciaDocumento, field will ignore if it is null
     *
     * @param id the id of the sequenciaDocumentoDTO to save.
     * @param sequenciaDocumentoDTO the sequenciaDocumentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenciaDocumentoDTO,
     * or with status {@code 400 (Bad Request)} if the sequenciaDocumentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sequenciaDocumentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sequenciaDocumentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sequencia-documentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SequenciaDocumentoDTO> partialUpdateSequenciaDocumento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SequenciaDocumentoDTO sequenciaDocumentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SequenciaDocumento partially : {}, {}", id, sequenciaDocumentoDTO);
        if (sequenciaDocumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sequenciaDocumentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sequenciaDocumentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SequenciaDocumentoDTO> result = sequenciaDocumentoService.partialUpdate(sequenciaDocumentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenciaDocumentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sequencia-documentos} : get all the sequenciaDocumentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequenciaDocumentos in body.
     */
    @GetMapping("/sequencia-documentos")
    public ResponseEntity<List<SequenciaDocumentoDTO>> getAllSequenciaDocumentos(
        SequenciaDocumentoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SequenciaDocumentos by criteria: {}", criteria);
        Page<SequenciaDocumentoDTO> page = sequenciaDocumentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequencia-documentos/count} : count all the sequenciaDocumentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sequencia-documentos/count")
    public ResponseEntity<Long> countSequenciaDocumentos(SequenciaDocumentoCriteria criteria) {
        log.debug("REST request to count SequenciaDocumentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequenciaDocumentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequencia-documentos/:id} : get the "id" sequenciaDocumento.
     *
     * @param id the id of the sequenciaDocumentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequenciaDocumentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sequencia-documentos/{id}")
    public ResponseEntity<SequenciaDocumentoDTO> getSequenciaDocumento(@PathVariable Long id) {
        log.debug("REST request to get SequenciaDocumento : {}", id);
        Optional<SequenciaDocumentoDTO> sequenciaDocumentoDTO = sequenciaDocumentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequenciaDocumentoDTO);
    }

    /**
     * {@code DELETE  /sequencia-documentos/:id} : delete the "id" sequenciaDocumento.
     *
     * @param id the id of the sequenciaDocumentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sequencia-documentos/{id}")
    public ResponseEntity<Void> deleteSequenciaDocumento(@PathVariable Long id) {
        log.debug("REST request to delete SequenciaDocumento : {}", id);
        sequenciaDocumentoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
