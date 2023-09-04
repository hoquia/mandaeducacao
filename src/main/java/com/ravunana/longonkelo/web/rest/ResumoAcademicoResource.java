package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.ResumoAcademicoRepository;
import com.ravunana.longonkelo.service.ResumoAcademicoQueryService;
import com.ravunana.longonkelo.service.ResumoAcademicoService;
import com.ravunana.longonkelo.service.criteria.ResumoAcademicoCriteria;
import com.ravunana.longonkelo.service.dto.ResumoAcademicoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.ResumoAcademico}.
 */
@RestController
@RequestMapping("/api")
public class ResumoAcademicoResource {

    private final Logger log = LoggerFactory.getLogger(ResumoAcademicoResource.class);

    private static final String ENTITY_NAME = "resumoAcademico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResumoAcademicoService resumoAcademicoService;

    private final ResumoAcademicoRepository resumoAcademicoRepository;

    private final ResumoAcademicoQueryService resumoAcademicoQueryService;

    public ResumoAcademicoResource(
        ResumoAcademicoService resumoAcademicoService,
        ResumoAcademicoRepository resumoAcademicoRepository,
        ResumoAcademicoQueryService resumoAcademicoQueryService
    ) {
        this.resumoAcademicoService = resumoAcademicoService;
        this.resumoAcademicoRepository = resumoAcademicoRepository;
        this.resumoAcademicoQueryService = resumoAcademicoQueryService;
    }

    /**
     * {@code POST  /resumo-academicos} : Create a new resumoAcademico.
     *
     * @param resumoAcademicoDTO the resumoAcademicoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resumoAcademicoDTO, or with status {@code 400 (Bad Request)} if the resumoAcademico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resumo-academicos")
    public ResponseEntity<ResumoAcademicoDTO> createResumoAcademico(@Valid @RequestBody ResumoAcademicoDTO resumoAcademicoDTO)
        throws URISyntaxException {
        log.debug("REST request to save ResumoAcademico : {}", resumoAcademicoDTO);
        if (resumoAcademicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new resumoAcademico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResumoAcademicoDTO result = resumoAcademicoService.save(resumoAcademicoDTO);
        return ResponseEntity
            .created(new URI("/api/resumo-academicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resumo-academicos/:id} : Updates an existing resumoAcademico.
     *
     * @param id the id of the resumoAcademicoDTO to save.
     * @param resumoAcademicoDTO the resumoAcademicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumoAcademicoDTO,
     * or with status {@code 400 (Bad Request)} if the resumoAcademicoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resumoAcademicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resumo-academicos/{id}")
    public ResponseEntity<ResumoAcademicoDTO> updateResumoAcademico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResumoAcademicoDTO resumoAcademicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ResumoAcademico : {}, {}", id, resumoAcademicoDTO);
        if (resumoAcademicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumoAcademicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumoAcademicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResumoAcademicoDTO result = resumoAcademicoService.update(resumoAcademicoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumoAcademicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /resumo-academicos/:id} : Partial updates given fields of an existing resumoAcademico, field will ignore if it is null
     *
     * @param id the id of the resumoAcademicoDTO to save.
     * @param resumoAcademicoDTO the resumoAcademicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumoAcademicoDTO,
     * or with status {@code 400 (Bad Request)} if the resumoAcademicoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the resumoAcademicoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the resumoAcademicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resumo-academicos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResumoAcademicoDTO> partialUpdateResumoAcademico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResumoAcademicoDTO resumoAcademicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResumoAcademico partially : {}, {}", id, resumoAcademicoDTO);
        if (resumoAcademicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumoAcademicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumoAcademicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResumoAcademicoDTO> result = resumoAcademicoService.partialUpdate(resumoAcademicoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumoAcademicoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /resumo-academicos} : get all the resumoAcademicos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resumoAcademicos in body.
     */
    @GetMapping("/resumo-academicos")
    public ResponseEntity<List<ResumoAcademicoDTO>> getAllResumoAcademicos(
        ResumoAcademicoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ResumoAcademicos by criteria: {}", criteria);
        Page<ResumoAcademicoDTO> page = resumoAcademicoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /resumo-academicos/count} : count all the resumoAcademicos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/resumo-academicos/count")
    public ResponseEntity<Long> countResumoAcademicos(ResumoAcademicoCriteria criteria) {
        log.debug("REST request to count ResumoAcademicos by criteria: {}", criteria);
        return ResponseEntity.ok().body(resumoAcademicoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /resumo-academicos/:id} : get the "id" resumoAcademico.
     *
     * @param id the id of the resumoAcademicoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resumoAcademicoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resumo-academicos/{id}")
    public ResponseEntity<ResumoAcademicoDTO> getResumoAcademico(@PathVariable Long id) {
        log.debug("REST request to get ResumoAcademico : {}", id);
        Optional<ResumoAcademicoDTO> resumoAcademicoDTO = resumoAcademicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resumoAcademicoDTO);
    }

    /**
     * {@code DELETE  /resumo-academicos/:id} : delete the "id" resumoAcademico.
     *
     * @param id the id of the resumoAcademicoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resumo-academicos/{id}")
    public ResponseEntity<Void> deleteResumoAcademico(@PathVariable Long id) {
        log.debug("REST request to delete ResumoAcademico : {}", id);
        resumoAcademicoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
