package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.AnexoDiscenteRepository;
import com.ravunana.longonkelo.service.AnexoDiscenteService;
import com.ravunana.longonkelo.service.dto.AnexoDiscenteDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ravunana.longonkelo.domain.AnexoDiscente}.
 */
@RestController
@RequestMapping("/api")
public class AnexoDiscenteResource {

    private final Logger log = LoggerFactory.getLogger(AnexoDiscenteResource.class);

    private static final String ENTITY_NAME = "anexoDiscente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnexoDiscenteService anexoDiscenteService;

    private final AnexoDiscenteRepository anexoDiscenteRepository;

    public AnexoDiscenteResource(AnexoDiscenteService anexoDiscenteService, AnexoDiscenteRepository anexoDiscenteRepository) {
        this.anexoDiscenteService = anexoDiscenteService;
        this.anexoDiscenteRepository = anexoDiscenteRepository;
    }

    /**
     * {@code POST  /anexo-discentes} : Create a new anexoDiscente.
     *
     * @param anexoDiscenteDTO the anexoDiscenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anexoDiscenteDTO, or with status {@code 400 (Bad Request)} if the anexoDiscente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/anexo-discentes")
    public ResponseEntity<AnexoDiscenteDTO> createAnexoDiscente(@Valid @RequestBody AnexoDiscenteDTO anexoDiscenteDTO)
        throws URISyntaxException {
        log.debug("REST request to save AnexoDiscente : {}", anexoDiscenteDTO);
        if (anexoDiscenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new anexoDiscente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnexoDiscenteDTO result = anexoDiscenteService.save(anexoDiscenteDTO);
        return ResponseEntity
            .created(new URI("/api/anexo-discentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /anexo-discentes/:id} : Updates an existing anexoDiscente.
     *
     * @param id the id of the anexoDiscenteDTO to save.
     * @param anexoDiscenteDTO the anexoDiscenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoDiscenteDTO,
     * or with status {@code 400 (Bad Request)} if the anexoDiscenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anexoDiscenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/anexo-discentes/{id}")
    public ResponseEntity<AnexoDiscenteDTO> updateAnexoDiscente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnexoDiscenteDTO anexoDiscenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AnexoDiscente : {}, {}", id, anexoDiscenteDTO);
        if (anexoDiscenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoDiscenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoDiscenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnexoDiscenteDTO result = anexoDiscenteService.update(anexoDiscenteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoDiscenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /anexo-discentes/:id} : Partial updates given fields of an existing anexoDiscente, field will ignore if it is null
     *
     * @param id the id of the anexoDiscenteDTO to save.
     * @param anexoDiscenteDTO the anexoDiscenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoDiscenteDTO,
     * or with status {@code 400 (Bad Request)} if the anexoDiscenteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the anexoDiscenteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the anexoDiscenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/anexo-discentes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnexoDiscenteDTO> partialUpdateAnexoDiscente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnexoDiscenteDTO anexoDiscenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnexoDiscente partially : {}, {}", id, anexoDiscenteDTO);
        if (anexoDiscenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoDiscenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoDiscenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnexoDiscenteDTO> result = anexoDiscenteService.partialUpdate(anexoDiscenteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoDiscenteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /anexo-discentes} : get all the anexoDiscentes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anexoDiscentes in body.
     */
    @GetMapping("/anexo-discentes")
    public ResponseEntity<List<AnexoDiscenteDTO>> getAllAnexoDiscentes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of AnexoDiscentes");
        Page<AnexoDiscenteDTO> page;
        if (eagerload) {
            page = anexoDiscenteService.findAllWithEagerRelationships(pageable);
        } else {
            page = anexoDiscenteService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /anexo-discentes/:id} : get the "id" anexoDiscente.
     *
     * @param id the id of the anexoDiscenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anexoDiscenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/anexo-discentes/{id}")
    public ResponseEntity<AnexoDiscenteDTO> getAnexoDiscente(@PathVariable Long id) {
        log.debug("REST request to get AnexoDiscente : {}", id);
        Optional<AnexoDiscenteDTO> anexoDiscenteDTO = anexoDiscenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anexoDiscenteDTO);
    }

    /**
     * {@code DELETE  /anexo-discentes/:id} : delete the "id" anexoDiscente.
     *
     * @param id the id of the anexoDiscenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/anexo-discentes/{id}")
    public ResponseEntity<Void> deleteAnexoDiscente(@PathVariable Long id) {
        log.debug("REST request to delete AnexoDiscente : {}", id);
        anexoDiscenteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
