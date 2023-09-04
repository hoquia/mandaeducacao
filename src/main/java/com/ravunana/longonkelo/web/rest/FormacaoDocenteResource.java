package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.FormacaoDocenteRepository;
import com.ravunana.longonkelo.service.FormacaoDocenteQueryService;
import com.ravunana.longonkelo.service.FormacaoDocenteService;
import com.ravunana.longonkelo.service.criteria.FormacaoDocenteCriteria;
import com.ravunana.longonkelo.service.dto.FormacaoDocenteDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.FormacaoDocente}.
 */
@RestController
@RequestMapping("/api")
public class FormacaoDocenteResource {

    private final Logger log = LoggerFactory.getLogger(FormacaoDocenteResource.class);

    private static final String ENTITY_NAME = "formacaoDocente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormacaoDocenteService formacaoDocenteService;

    private final FormacaoDocenteRepository formacaoDocenteRepository;

    private final FormacaoDocenteQueryService formacaoDocenteQueryService;

    public FormacaoDocenteResource(
        FormacaoDocenteService formacaoDocenteService,
        FormacaoDocenteRepository formacaoDocenteRepository,
        FormacaoDocenteQueryService formacaoDocenteQueryService
    ) {
        this.formacaoDocenteService = formacaoDocenteService;
        this.formacaoDocenteRepository = formacaoDocenteRepository;
        this.formacaoDocenteQueryService = formacaoDocenteQueryService;
    }

    /**
     * {@code POST  /formacao-docentes} : Create a new formacaoDocente.
     *
     * @param formacaoDocenteDTO the formacaoDocenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formacaoDocenteDTO, or with status {@code 400 (Bad Request)} if the formacaoDocente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/formacao-docentes")
    public ResponseEntity<FormacaoDocenteDTO> createFormacaoDocente(@Valid @RequestBody FormacaoDocenteDTO formacaoDocenteDTO)
        throws URISyntaxException {
        log.debug("REST request to save FormacaoDocente : {}", formacaoDocenteDTO);
        if (formacaoDocenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new formacaoDocente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormacaoDocenteDTO result = formacaoDocenteService.save(formacaoDocenteDTO);
        return ResponseEntity
            .created(new URI("/api/formacao-docentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /formacao-docentes/:id} : Updates an existing formacaoDocente.
     *
     * @param id the id of the formacaoDocenteDTO to save.
     * @param formacaoDocenteDTO the formacaoDocenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formacaoDocenteDTO,
     * or with status {@code 400 (Bad Request)} if the formacaoDocenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formacaoDocenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/formacao-docentes/{id}")
    public ResponseEntity<FormacaoDocenteDTO> updateFormacaoDocente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FormacaoDocenteDTO formacaoDocenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FormacaoDocente : {}, {}", id, formacaoDocenteDTO);
        if (formacaoDocenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formacaoDocenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formacaoDocenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FormacaoDocenteDTO result = formacaoDocenteService.update(formacaoDocenteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formacaoDocenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /formacao-docentes/:id} : Partial updates given fields of an existing formacaoDocente, field will ignore if it is null
     *
     * @param id the id of the formacaoDocenteDTO to save.
     * @param formacaoDocenteDTO the formacaoDocenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formacaoDocenteDTO,
     * or with status {@code 400 (Bad Request)} if the formacaoDocenteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the formacaoDocenteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the formacaoDocenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/formacao-docentes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FormacaoDocenteDTO> partialUpdateFormacaoDocente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FormacaoDocenteDTO formacaoDocenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FormacaoDocente partially : {}, {}", id, formacaoDocenteDTO);
        if (formacaoDocenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formacaoDocenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formacaoDocenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FormacaoDocenteDTO> result = formacaoDocenteService.partialUpdate(formacaoDocenteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formacaoDocenteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /formacao-docentes} : get all the formacaoDocentes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formacaoDocentes in body.
     */
    @GetMapping("/formacao-docentes")
    public ResponseEntity<List<FormacaoDocenteDTO>> getAllFormacaoDocentes(
        FormacaoDocenteCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FormacaoDocentes by criteria: {}", criteria);
        Page<FormacaoDocenteDTO> page = formacaoDocenteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /formacao-docentes/count} : count all the formacaoDocentes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/formacao-docentes/count")
    public ResponseEntity<Long> countFormacaoDocentes(FormacaoDocenteCriteria criteria) {
        log.debug("REST request to count FormacaoDocentes by criteria: {}", criteria);
        return ResponseEntity.ok().body(formacaoDocenteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /formacao-docentes/:id} : get the "id" formacaoDocente.
     *
     * @param id the id of the formacaoDocenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formacaoDocenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/formacao-docentes/{id}")
    public ResponseEntity<FormacaoDocenteDTO> getFormacaoDocente(@PathVariable Long id) {
        log.debug("REST request to get FormacaoDocente : {}", id);
        Optional<FormacaoDocenteDTO> formacaoDocenteDTO = formacaoDocenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formacaoDocenteDTO);
    }

    /**
     * {@code DELETE  /formacao-docentes/:id} : delete the "id" formacaoDocente.
     *
     * @param id the id of the formacaoDocenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/formacao-docentes/{id}")
    public ResponseEntity<Void> deleteFormacaoDocente(@PathVariable Long id) {
        log.debug("REST request to delete FormacaoDocente : {}", id);
        formacaoDocenteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
