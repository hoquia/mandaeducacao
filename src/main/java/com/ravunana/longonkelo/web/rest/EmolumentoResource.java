package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.EmolumentoRepository;
import com.ravunana.longonkelo.service.EmolumentoQueryService;
import com.ravunana.longonkelo.service.EmolumentoService;
import com.ravunana.longonkelo.service.criteria.EmolumentoCriteria;
import com.ravunana.longonkelo.service.dto.EmolumentoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.Emolumento}.
 */
@RestController
@RequestMapping("/api")
public class EmolumentoResource {

    private final Logger log = LoggerFactory.getLogger(EmolumentoResource.class);

    private static final String ENTITY_NAME = "emolumento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmolumentoService emolumentoService;

    private final EmolumentoRepository emolumentoRepository;

    private final EmolumentoQueryService emolumentoQueryService;

    public EmolumentoResource(
        EmolumentoService emolumentoService,
        EmolumentoRepository emolumentoRepository,
        EmolumentoQueryService emolumentoQueryService
    ) {
        this.emolumentoService = emolumentoService;
        this.emolumentoRepository = emolumentoRepository;
        this.emolumentoQueryService = emolumentoQueryService;
    }

    /**
     * {@code POST  /emolumentos} : Create a new emolumento.
     *
     * @param emolumentoDTO the emolumentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emolumentoDTO, or with status {@code 400 (Bad Request)} if the emolumento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emolumentos")
    public ResponseEntity<EmolumentoDTO> createEmolumento(@Valid @RequestBody EmolumentoDTO emolumentoDTO) throws URISyntaxException {
        log.debug("REST request to save Emolumento : {}", emolumentoDTO);
        if (emolumentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new emolumento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmolumentoDTO result = emolumentoService.save(emolumentoDTO);
        return ResponseEntity
            .created(new URI("/api/emolumentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emolumentos/:id} : Updates an existing emolumento.
     *
     * @param id the id of the emolumentoDTO to save.
     * @param emolumentoDTO the emolumentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emolumentoDTO,
     * or with status {@code 400 (Bad Request)} if the emolumentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emolumentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emolumentos/{id}")
    public ResponseEntity<EmolumentoDTO> updateEmolumento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmolumentoDTO emolumentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Emolumento : {}, {}", id, emolumentoDTO);
        if (emolumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emolumentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emolumentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmolumentoDTO result = emolumentoService.update(emolumentoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emolumentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /emolumentos/:id} : Partial updates given fields of an existing emolumento, field will ignore if it is null
     *
     * @param id the id of the emolumentoDTO to save.
     * @param emolumentoDTO the emolumentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emolumentoDTO,
     * or with status {@code 400 (Bad Request)} if the emolumentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the emolumentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the emolumentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/emolumentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmolumentoDTO> partialUpdateEmolumento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmolumentoDTO emolumentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Emolumento partially : {}, {}", id, emolumentoDTO);
        if (emolumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emolumentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emolumentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmolumentoDTO> result = emolumentoService.partialUpdate(emolumentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emolumentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /emolumentos} : get all the emolumentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emolumentos in body.
     */
    @GetMapping("/emolumentos")
    public ResponseEntity<List<EmolumentoDTO>> getAllEmolumentos(
        EmolumentoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Emolumentos by criteria: {}", criteria);
        Page<EmolumentoDTO> page = emolumentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emolumentos/count} : count all the emolumentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/emolumentos/count")
    public ResponseEntity<Long> countEmolumentos(EmolumentoCriteria criteria) {
        log.debug("REST request to count Emolumentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(emolumentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /emolumentos/:id} : get the "id" emolumento.
     *
     * @param id the id of the emolumentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emolumentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emolumentos/{id}")
    public ResponseEntity<EmolumentoDTO> getEmolumento(@PathVariable Long id) {
        log.debug("REST request to get Emolumento : {}", id);
        Optional<EmolumentoDTO> emolumentoDTO = emolumentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emolumentoDTO);
    }

    /**
     * {@code DELETE  /emolumentos/:id} : delete the "id" emolumento.
     *
     * @param id the id of the emolumentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emolumentos/{id}")
    public ResponseEntity<Void> deleteEmolumento(@PathVariable Long id) {
        log.debug("REST request to delete Emolumento : {}", id);
        emolumentoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
