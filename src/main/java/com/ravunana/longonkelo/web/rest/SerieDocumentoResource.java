package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.SerieDocumentoRepository;
import com.ravunana.longonkelo.service.SerieDocumentoQueryService;
import com.ravunana.longonkelo.service.SerieDocumentoService;
import com.ravunana.longonkelo.service.criteria.SerieDocumentoCriteria;
import com.ravunana.longonkelo.service.dto.SerieDocumentoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.SerieDocumento}.
 */
@RestController
@RequestMapping("/api")
public class SerieDocumentoResource {

    private final Logger log = LoggerFactory.getLogger(SerieDocumentoResource.class);

    private static final String ENTITY_NAME = "serieDocumento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SerieDocumentoService serieDocumentoService;

    private final SerieDocumentoRepository serieDocumentoRepository;

    private final SerieDocumentoQueryService serieDocumentoQueryService;

    public SerieDocumentoResource(
        SerieDocumentoService serieDocumentoService,
        SerieDocumentoRepository serieDocumentoRepository,
        SerieDocumentoQueryService serieDocumentoQueryService
    ) {
        this.serieDocumentoService = serieDocumentoService;
        this.serieDocumentoRepository = serieDocumentoRepository;
        this.serieDocumentoQueryService = serieDocumentoQueryService;
    }

    /**
     * {@code POST  /serie-documentos} : Create a new serieDocumento.
     *
     * @param serieDocumentoDTO the serieDocumentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serieDocumentoDTO, or with status {@code 400 (Bad Request)} if the serieDocumento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/serie-documentos")
    public ResponseEntity<SerieDocumentoDTO> createSerieDocumento(@Valid @RequestBody SerieDocumentoDTO serieDocumentoDTO)
        throws URISyntaxException {
        log.debug("REST request to save SerieDocumento : {}", serieDocumentoDTO);
        if (serieDocumentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new serieDocumento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SerieDocumentoDTO result = serieDocumentoService.save(serieDocumentoDTO);
        return ResponseEntity
            .created(new URI("/api/serie-documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /serie-documentos/:id} : Updates an existing serieDocumento.
     *
     * @param id the id of the serieDocumentoDTO to save.
     * @param serieDocumentoDTO the serieDocumentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serieDocumentoDTO,
     * or with status {@code 400 (Bad Request)} if the serieDocumentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serieDocumentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/serie-documentos/{id}")
    public ResponseEntity<SerieDocumentoDTO> updateSerieDocumento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SerieDocumentoDTO serieDocumentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SerieDocumento : {}, {}", id, serieDocumentoDTO);
        if (serieDocumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serieDocumentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serieDocumentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SerieDocumentoDTO result = serieDocumentoService.update(serieDocumentoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serieDocumentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /serie-documentos/:id} : Partial updates given fields of an existing serieDocumento, field will ignore if it is null
     *
     * @param id the id of the serieDocumentoDTO to save.
     * @param serieDocumentoDTO the serieDocumentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serieDocumentoDTO,
     * or with status {@code 400 (Bad Request)} if the serieDocumentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the serieDocumentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the serieDocumentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/serie-documentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SerieDocumentoDTO> partialUpdateSerieDocumento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SerieDocumentoDTO serieDocumentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SerieDocumento partially : {}, {}", id, serieDocumentoDTO);
        if (serieDocumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serieDocumentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serieDocumentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SerieDocumentoDTO> result = serieDocumentoService.partialUpdate(serieDocumentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serieDocumentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /serie-documentos} : get all the serieDocumentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serieDocumentos in body.
     */
    @GetMapping("/serie-documentos")
    public ResponseEntity<List<SerieDocumentoDTO>> getAllSerieDocumentos(
        SerieDocumentoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SerieDocumentos by criteria: {}", criteria);
        Page<SerieDocumentoDTO> page = serieDocumentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /serie-documentos/count} : count all the serieDocumentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/serie-documentos/count")
    public ResponseEntity<Long> countSerieDocumentos(SerieDocumentoCriteria criteria) {
        log.debug("REST request to count SerieDocumentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(serieDocumentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /serie-documentos/:id} : get the "id" serieDocumento.
     *
     * @param id the id of the serieDocumentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serieDocumentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/serie-documentos/{id}")
    public ResponseEntity<SerieDocumentoDTO> getSerieDocumento(@PathVariable Long id) {
        log.debug("REST request to get SerieDocumento : {}", id);
        Optional<SerieDocumentoDTO> serieDocumentoDTO = serieDocumentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serieDocumentoDTO);
    }

    /**
     * {@code DELETE  /serie-documentos/:id} : delete the "id" serieDocumento.
     *
     * @param id the id of the serieDocumentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/serie-documentos/{id}")
    public ResponseEntity<Void> deleteSerieDocumento(@PathVariable Long id) {
        log.debug("REST request to delete SerieDocumento : {}", id);
        serieDocumentoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
