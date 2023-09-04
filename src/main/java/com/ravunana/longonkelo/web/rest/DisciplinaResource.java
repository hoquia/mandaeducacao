package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.DisciplinaRepository;
import com.ravunana.longonkelo.service.DisciplinaQueryService;
import com.ravunana.longonkelo.service.DisciplinaService;
import com.ravunana.longonkelo.service.criteria.DisciplinaCriteria;
import com.ravunana.longonkelo.service.dto.DisciplinaDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.Disciplina}.
 */
@RestController
@RequestMapping("/api")
public class DisciplinaResource {

    private final Logger log = LoggerFactory.getLogger(DisciplinaResource.class);

    private static final String ENTITY_NAME = "disciplina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisciplinaService disciplinaService;

    private final DisciplinaRepository disciplinaRepository;

    private final DisciplinaQueryService disciplinaQueryService;

    public DisciplinaResource(
        DisciplinaService disciplinaService,
        DisciplinaRepository disciplinaRepository,
        DisciplinaQueryService disciplinaQueryService
    ) {
        this.disciplinaService = disciplinaService;
        this.disciplinaRepository = disciplinaRepository;
        this.disciplinaQueryService = disciplinaQueryService;
    }

    /**
     * {@code POST  /disciplinas} : Create a new disciplina.
     *
     * @param disciplinaDTO the disciplinaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disciplinaDTO, or with status {@code 400 (Bad Request)} if the disciplina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/disciplinas")
    public ResponseEntity<DisciplinaDTO> createDisciplina(@Valid @RequestBody DisciplinaDTO disciplinaDTO) throws URISyntaxException {
        log.debug("REST request to save Disciplina : {}", disciplinaDTO);
        if (disciplinaDTO.getId() != null) {
            throw new BadRequestAlertException("A new disciplina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisciplinaDTO result = disciplinaService.save(disciplinaDTO);
        return ResponseEntity
            .created(new URI("/api/disciplinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /disciplinas/:id} : Updates an existing disciplina.
     *
     * @param id the id of the disciplinaDTO to save.
     * @param disciplinaDTO the disciplinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplinaDTO,
     * or with status {@code 400 (Bad Request)} if the disciplinaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disciplinaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/disciplinas/{id}")
    public ResponseEntity<DisciplinaDTO> updateDisciplina(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DisciplinaDTO disciplinaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Disciplina : {}, {}", id, disciplinaDTO);
        if (disciplinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disciplinaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disciplinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DisciplinaDTO result = disciplinaService.update(disciplinaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplinaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /disciplinas/:id} : Partial updates given fields of an existing disciplina, field will ignore if it is null
     *
     * @param id the id of the disciplinaDTO to save.
     * @param disciplinaDTO the disciplinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplinaDTO,
     * or with status {@code 400 (Bad Request)} if the disciplinaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the disciplinaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the disciplinaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/disciplinas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DisciplinaDTO> partialUpdateDisciplina(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DisciplinaDTO disciplinaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Disciplina partially : {}, {}", id, disciplinaDTO);
        if (disciplinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disciplinaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disciplinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DisciplinaDTO> result = disciplinaService.partialUpdate(disciplinaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplinaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /disciplinas} : get all the disciplinas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disciplinas in body.
     */
    @GetMapping("/disciplinas")
    public ResponseEntity<List<DisciplinaDTO>> getAllDisciplinas(
        DisciplinaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Disciplinas by criteria: {}", criteria);
        Page<DisciplinaDTO> page = disciplinaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /disciplinas/count} : count all the disciplinas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/disciplinas/count")
    public ResponseEntity<Long> countDisciplinas(DisciplinaCriteria criteria) {
        log.debug("REST request to count Disciplinas by criteria: {}", criteria);
        return ResponseEntity.ok().body(disciplinaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /disciplinas/:id} : get the "id" disciplina.
     *
     * @param id the id of the disciplinaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disciplinaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/disciplinas/{id}")
    public ResponseEntity<DisciplinaDTO> getDisciplina(@PathVariable Long id) {
        log.debug("REST request to get Disciplina : {}", id);
        Optional<DisciplinaDTO> disciplinaDTO = disciplinaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(disciplinaDTO);
    }

    /**
     * {@code DELETE  /disciplinas/:id} : delete the "id" disciplina.
     *
     * @param id the id of the disciplinaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/disciplinas/{id}")
    public ResponseEntity<Void> deleteDisciplina(@PathVariable Long id) {
        log.debug("REST request to delete Disciplina : {}", id);
        disciplinaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
