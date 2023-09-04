package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.DissertacaoFinalCursoRepository;
import com.ravunana.longonkelo.service.DissertacaoFinalCursoQueryService;
import com.ravunana.longonkelo.service.DissertacaoFinalCursoService;
import com.ravunana.longonkelo.service.criteria.DissertacaoFinalCursoCriteria;
import com.ravunana.longonkelo.service.dto.DissertacaoFinalCursoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.DissertacaoFinalCurso}.
 */
@RestController
@RequestMapping("/api")
public class DissertacaoFinalCursoResource {

    private final Logger log = LoggerFactory.getLogger(DissertacaoFinalCursoResource.class);

    private static final String ENTITY_NAME = "dissertacaoFinalCurso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DissertacaoFinalCursoService dissertacaoFinalCursoService;

    private final DissertacaoFinalCursoRepository dissertacaoFinalCursoRepository;

    private final DissertacaoFinalCursoQueryService dissertacaoFinalCursoQueryService;

    public DissertacaoFinalCursoResource(
        DissertacaoFinalCursoService dissertacaoFinalCursoService,
        DissertacaoFinalCursoRepository dissertacaoFinalCursoRepository,
        DissertacaoFinalCursoQueryService dissertacaoFinalCursoQueryService
    ) {
        this.dissertacaoFinalCursoService = dissertacaoFinalCursoService;
        this.dissertacaoFinalCursoRepository = dissertacaoFinalCursoRepository;
        this.dissertacaoFinalCursoQueryService = dissertacaoFinalCursoQueryService;
    }

    /**
     * {@code POST  /dissertacao-final-cursos} : Create a new dissertacaoFinalCurso.
     *
     * @param dissertacaoFinalCursoDTO the dissertacaoFinalCursoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dissertacaoFinalCursoDTO, or with status {@code 400 (Bad Request)} if the dissertacaoFinalCurso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dissertacao-final-cursos")
    public ResponseEntity<DissertacaoFinalCursoDTO> createDissertacaoFinalCurso(
        @Valid @RequestBody DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save DissertacaoFinalCurso : {}", dissertacaoFinalCursoDTO);
        if (dissertacaoFinalCursoDTO.getId() != null) {
            throw new BadRequestAlertException("A new dissertacaoFinalCurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DissertacaoFinalCursoDTO result = dissertacaoFinalCursoService.save(dissertacaoFinalCursoDTO);
        return ResponseEntity
            .created(new URI("/api/dissertacao-final-cursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dissertacao-final-cursos/:id} : Updates an existing dissertacaoFinalCurso.
     *
     * @param id the id of the dissertacaoFinalCursoDTO to save.
     * @param dissertacaoFinalCursoDTO the dissertacaoFinalCursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dissertacaoFinalCursoDTO,
     * or with status {@code 400 (Bad Request)} if the dissertacaoFinalCursoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dissertacaoFinalCursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dissertacao-final-cursos/{id}")
    public ResponseEntity<DissertacaoFinalCursoDTO> updateDissertacaoFinalCurso(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DissertacaoFinalCurso : {}, {}", id, dissertacaoFinalCursoDTO);
        if (dissertacaoFinalCursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dissertacaoFinalCursoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dissertacaoFinalCursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DissertacaoFinalCursoDTO result = dissertacaoFinalCursoService.update(dissertacaoFinalCursoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dissertacaoFinalCursoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dissertacao-final-cursos/:id} : Partial updates given fields of an existing dissertacaoFinalCurso, field will ignore if it is null
     *
     * @param id the id of the dissertacaoFinalCursoDTO to save.
     * @param dissertacaoFinalCursoDTO the dissertacaoFinalCursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dissertacaoFinalCursoDTO,
     * or with status {@code 400 (Bad Request)} if the dissertacaoFinalCursoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dissertacaoFinalCursoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dissertacaoFinalCursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dissertacao-final-cursos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DissertacaoFinalCursoDTO> partialUpdateDissertacaoFinalCurso(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DissertacaoFinalCurso partially : {}, {}", id, dissertacaoFinalCursoDTO);
        if (dissertacaoFinalCursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dissertacaoFinalCursoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dissertacaoFinalCursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DissertacaoFinalCursoDTO> result = dissertacaoFinalCursoService.partialUpdate(dissertacaoFinalCursoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dissertacaoFinalCursoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dissertacao-final-cursos} : get all the dissertacaoFinalCursos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dissertacaoFinalCursos in body.
     */
    @GetMapping("/dissertacao-final-cursos")
    public ResponseEntity<List<DissertacaoFinalCursoDTO>> getAllDissertacaoFinalCursos(
        DissertacaoFinalCursoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DissertacaoFinalCursos by criteria: {}", criteria);
        Page<DissertacaoFinalCursoDTO> page = dissertacaoFinalCursoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dissertacao-final-cursos/count} : count all the dissertacaoFinalCursos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/dissertacao-final-cursos/count")
    public ResponseEntity<Long> countDissertacaoFinalCursos(DissertacaoFinalCursoCriteria criteria) {
        log.debug("REST request to count DissertacaoFinalCursos by criteria: {}", criteria);
        return ResponseEntity.ok().body(dissertacaoFinalCursoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dissertacao-final-cursos/:id} : get the "id" dissertacaoFinalCurso.
     *
     * @param id the id of the dissertacaoFinalCursoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dissertacaoFinalCursoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dissertacao-final-cursos/{id}")
    public ResponseEntity<DissertacaoFinalCursoDTO> getDissertacaoFinalCurso(@PathVariable Long id) {
        log.debug("REST request to get DissertacaoFinalCurso : {}", id);
        Optional<DissertacaoFinalCursoDTO> dissertacaoFinalCursoDTO = dissertacaoFinalCursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dissertacaoFinalCursoDTO);
    }

    /**
     * {@code DELETE  /dissertacao-final-cursos/:id} : delete the "id" dissertacaoFinalCurso.
     *
     * @param id the id of the dissertacaoFinalCursoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dissertacao-final-cursos/{id}")
    public ResponseEntity<Void> deleteDissertacaoFinalCurso(@PathVariable Long id) {
        log.debug("REST request to delete DissertacaoFinalCurso : {}", id);
        dissertacaoFinalCursoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
