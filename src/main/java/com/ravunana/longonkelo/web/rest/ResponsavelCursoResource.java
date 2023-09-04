package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.ResponsavelCursoRepository;
import com.ravunana.longonkelo.service.ResponsavelCursoQueryService;
import com.ravunana.longonkelo.service.ResponsavelCursoService;
import com.ravunana.longonkelo.service.criteria.ResponsavelCursoCriteria;
import com.ravunana.longonkelo.service.dto.ResponsavelCursoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.ResponsavelCurso}.
 */
@RestController
@RequestMapping("/api")
public class ResponsavelCursoResource {

    private final Logger log = LoggerFactory.getLogger(ResponsavelCursoResource.class);

    private static final String ENTITY_NAME = "responsavelCurso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsavelCursoService responsavelCursoService;

    private final ResponsavelCursoRepository responsavelCursoRepository;

    private final ResponsavelCursoQueryService responsavelCursoQueryService;

    public ResponsavelCursoResource(
        ResponsavelCursoService responsavelCursoService,
        ResponsavelCursoRepository responsavelCursoRepository,
        ResponsavelCursoQueryService responsavelCursoQueryService
    ) {
        this.responsavelCursoService = responsavelCursoService;
        this.responsavelCursoRepository = responsavelCursoRepository;
        this.responsavelCursoQueryService = responsavelCursoQueryService;
    }

    /**
     * {@code POST  /responsavel-cursos} : Create a new responsavelCurso.
     *
     * @param responsavelCursoDTO the responsavelCursoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responsavelCursoDTO, or with status {@code 400 (Bad Request)} if the responsavelCurso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/responsavel-cursos")
    public ResponseEntity<ResponsavelCursoDTO> createResponsavelCurso(@Valid @RequestBody ResponsavelCursoDTO responsavelCursoDTO)
        throws URISyntaxException {
        log.debug("REST request to save ResponsavelCurso : {}", responsavelCursoDTO);
        if (responsavelCursoDTO.getId() != null) {
            throw new BadRequestAlertException("A new responsavelCurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponsavelCursoDTO result = responsavelCursoService.save(responsavelCursoDTO);
        return ResponseEntity
            .created(new URI("/api/responsavel-cursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /responsavel-cursos/:id} : Updates an existing responsavelCurso.
     *
     * @param id the id of the responsavelCursoDTO to save.
     * @param responsavelCursoDTO the responsavelCursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavelCursoDTO,
     * or with status {@code 400 (Bad Request)} if the responsavelCursoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responsavelCursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/responsavel-cursos/{id}")
    public ResponseEntity<ResponsavelCursoDTO> updateResponsavelCurso(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResponsavelCursoDTO responsavelCursoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ResponsavelCurso : {}, {}", id, responsavelCursoDTO);
        if (responsavelCursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsavelCursoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsavelCursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResponsavelCursoDTO result = responsavelCursoService.update(responsavelCursoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsavelCursoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /responsavel-cursos/:id} : Partial updates given fields of an existing responsavelCurso, field will ignore if it is null
     *
     * @param id the id of the responsavelCursoDTO to save.
     * @param responsavelCursoDTO the responsavelCursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavelCursoDTO,
     * or with status {@code 400 (Bad Request)} if the responsavelCursoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the responsavelCursoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the responsavelCursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/responsavel-cursos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResponsavelCursoDTO> partialUpdateResponsavelCurso(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResponsavelCursoDTO responsavelCursoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResponsavelCurso partially : {}, {}", id, responsavelCursoDTO);
        if (responsavelCursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsavelCursoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsavelCursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResponsavelCursoDTO> result = responsavelCursoService.partialUpdate(responsavelCursoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsavelCursoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /responsavel-cursos} : get all the responsavelCursos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responsavelCursos in body.
     */
    @GetMapping("/responsavel-cursos")
    public ResponseEntity<List<ResponsavelCursoDTO>> getAllResponsavelCursos(
        ResponsavelCursoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ResponsavelCursos by criteria: {}", criteria);
        Page<ResponsavelCursoDTO> page = responsavelCursoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /responsavel-cursos/count} : count all the responsavelCursos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/responsavel-cursos/count")
    public ResponseEntity<Long> countResponsavelCursos(ResponsavelCursoCriteria criteria) {
        log.debug("REST request to count ResponsavelCursos by criteria: {}", criteria);
        return ResponseEntity.ok().body(responsavelCursoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /responsavel-cursos/:id} : get the "id" responsavelCurso.
     *
     * @param id the id of the responsavelCursoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responsavelCursoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/responsavel-cursos/{id}")
    public ResponseEntity<ResponsavelCursoDTO> getResponsavelCurso(@PathVariable Long id) {
        log.debug("REST request to get ResponsavelCurso : {}", id);
        Optional<ResponsavelCursoDTO> responsavelCursoDTO = responsavelCursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsavelCursoDTO);
    }

    /**
     * {@code DELETE  /responsavel-cursos/:id} : delete the "id" responsavelCurso.
     *
     * @param id the id of the responsavelCursoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/responsavel-cursos/{id}")
    public ResponseEntity<Void> deleteResponsavelCurso(@PathVariable Long id) {
        log.debug("REST request to delete ResponsavelCurso : {}", id);
        responsavelCursoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
