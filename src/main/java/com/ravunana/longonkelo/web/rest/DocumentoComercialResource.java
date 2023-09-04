package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.DocumentoComercialRepository;
import com.ravunana.longonkelo.service.DocumentoComercialQueryService;
import com.ravunana.longonkelo.service.DocumentoComercialService;
import com.ravunana.longonkelo.service.criteria.DocumentoComercialCriteria;
import com.ravunana.longonkelo.service.dto.DocumentoComercialDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.DocumentoComercial}.
 */
@RestController
@RequestMapping("/api")
public class DocumentoComercialResource {

    private final Logger log = LoggerFactory.getLogger(DocumentoComercialResource.class);

    private static final String ENTITY_NAME = "documentoComercial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentoComercialService documentoComercialService;

    private final DocumentoComercialRepository documentoComercialRepository;

    private final DocumentoComercialQueryService documentoComercialQueryService;

    public DocumentoComercialResource(
        DocumentoComercialService documentoComercialService,
        DocumentoComercialRepository documentoComercialRepository,
        DocumentoComercialQueryService documentoComercialQueryService
    ) {
        this.documentoComercialService = documentoComercialService;
        this.documentoComercialRepository = documentoComercialRepository;
        this.documentoComercialQueryService = documentoComercialQueryService;
    }

    /**
     * {@code POST  /documento-comercials} : Create a new documentoComercial.
     *
     * @param documentoComercialDTO the documentoComercialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentoComercialDTO, or with status {@code 400 (Bad Request)} if the documentoComercial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documento-comercials")
    public ResponseEntity<DocumentoComercialDTO> createDocumentoComercial(@Valid @RequestBody DocumentoComercialDTO documentoComercialDTO)
        throws URISyntaxException {
        log.debug("REST request to save DocumentoComercial : {}", documentoComercialDTO);
        if (documentoComercialDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentoComercial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentoComercialDTO result = documentoComercialService.save(documentoComercialDTO);
        return ResponseEntity
            .created(new URI("/api/documento-comercials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /documento-comercials/:id} : Updates an existing documentoComercial.
     *
     * @param id the id of the documentoComercialDTO to save.
     * @param documentoComercialDTO the documentoComercialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentoComercialDTO,
     * or with status {@code 400 (Bad Request)} if the documentoComercialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentoComercialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documento-comercials/{id}")
    public ResponseEntity<DocumentoComercialDTO> updateDocumentoComercial(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DocumentoComercialDTO documentoComercialDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DocumentoComercial : {}, {}", id, documentoComercialDTO);
        if (documentoComercialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentoComercialDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentoComercialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DocumentoComercialDTO result = documentoComercialService.update(documentoComercialDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentoComercialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /documento-comercials/:id} : Partial updates given fields of an existing documentoComercial, field will ignore if it is null
     *
     * @param id the id of the documentoComercialDTO to save.
     * @param documentoComercialDTO the documentoComercialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentoComercialDTO,
     * or with status {@code 400 (Bad Request)} if the documentoComercialDTO is not valid,
     * or with status {@code 404 (Not Found)} if the documentoComercialDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentoComercialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/documento-comercials/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentoComercialDTO> partialUpdateDocumentoComercial(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DocumentoComercialDTO documentoComercialDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocumentoComercial partially : {}, {}", id, documentoComercialDTO);
        if (documentoComercialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentoComercialDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentoComercialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentoComercialDTO> result = documentoComercialService.partialUpdate(documentoComercialDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentoComercialDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /documento-comercials} : get all the documentoComercials.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentoComercials in body.
     */
    @GetMapping("/documento-comercials")
    public ResponseEntity<List<DocumentoComercialDTO>> getAllDocumentoComercials(
        DocumentoComercialCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DocumentoComercials by criteria: {}", criteria);
        Page<DocumentoComercialDTO> page = documentoComercialQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /documento-comercials/count} : count all the documentoComercials.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/documento-comercials/count")
    public ResponseEntity<Long> countDocumentoComercials(DocumentoComercialCriteria criteria) {
        log.debug("REST request to count DocumentoComercials by criteria: {}", criteria);
        return ResponseEntity.ok().body(documentoComercialQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /documento-comercials/:id} : get the "id" documentoComercial.
     *
     * @param id the id of the documentoComercialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentoComercialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documento-comercials/{id}")
    public ResponseEntity<DocumentoComercialDTO> getDocumentoComercial(@PathVariable Long id) {
        log.debug("REST request to get DocumentoComercial : {}", id);
        Optional<DocumentoComercialDTO> documentoComercialDTO = documentoComercialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentoComercialDTO);
    }

    /**
     * {@code DELETE  /documento-comercials/:id} : delete the "id" documentoComercial.
     *
     * @param id the id of the documentoComercialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documento-comercials/{id}")
    public ResponseEntity<Void> deleteDocumentoComercial(@PathVariable Long id) {
        log.debug("REST request to delete DocumentoComercial : {}", id);
        documentoComercialService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
