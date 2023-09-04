package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.NotasGeralDisciplinaRepository;
import com.ravunana.longonkelo.service.NotasGeralDisciplinaQueryService;
import com.ravunana.longonkelo.service.NotasGeralDisciplinaService;
import com.ravunana.longonkelo.service.criteria.NotasGeralDisciplinaCriteria;
import com.ravunana.longonkelo.service.dto.NotasGeralDisciplinaDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.NotasGeralDisciplina}.
 */
@RestController
@RequestMapping("/api")
public class NotasGeralDisciplinaResource {

    private final Logger log = LoggerFactory.getLogger(NotasGeralDisciplinaResource.class);

    private static final String ENTITY_NAME = "notasGeralDisciplina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotasGeralDisciplinaService notasGeralDisciplinaService;

    private final NotasGeralDisciplinaRepository notasGeralDisciplinaRepository;

    private final NotasGeralDisciplinaQueryService notasGeralDisciplinaQueryService;

    public NotasGeralDisciplinaResource(
        NotasGeralDisciplinaService notasGeralDisciplinaService,
        NotasGeralDisciplinaRepository notasGeralDisciplinaRepository,
        NotasGeralDisciplinaQueryService notasGeralDisciplinaQueryService
    ) {
        this.notasGeralDisciplinaService = notasGeralDisciplinaService;
        this.notasGeralDisciplinaRepository = notasGeralDisciplinaRepository;
        this.notasGeralDisciplinaQueryService = notasGeralDisciplinaQueryService;
    }

    /**
     * {@code POST  /notas-geral-disciplinas} : Create a new notasGeralDisciplina.
     *
     * @param notasGeralDisciplinaDTO the notasGeralDisciplinaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notasGeralDisciplinaDTO, or with status {@code 400 (Bad Request)} if the notasGeralDisciplina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notas-geral-disciplinas")
    public ResponseEntity<NotasGeralDisciplinaDTO> createNotasGeralDisciplina(
        @Valid @RequestBody NotasGeralDisciplinaDTO notasGeralDisciplinaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save NotasGeralDisciplina : {}", notasGeralDisciplinaDTO);
        if (notasGeralDisciplinaDTO.getId() != null) {
            throw new BadRequestAlertException("A new notasGeralDisciplina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotasGeralDisciplinaDTO result = notasGeralDisciplinaService.save(notasGeralDisciplinaDTO);
        return ResponseEntity
            .created(new URI("/api/notas-geral-disciplinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notas-geral-disciplinas/:id} : Updates an existing notasGeralDisciplina.
     *
     * @param id the id of the notasGeralDisciplinaDTO to save.
     * @param notasGeralDisciplinaDTO the notasGeralDisciplinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notasGeralDisciplinaDTO,
     * or with status {@code 400 (Bad Request)} if the notasGeralDisciplinaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notasGeralDisciplinaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notas-geral-disciplinas/{id}")
    public ResponseEntity<NotasGeralDisciplinaDTO> updateNotasGeralDisciplina(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NotasGeralDisciplinaDTO notasGeralDisciplinaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NotasGeralDisciplina : {}, {}", id, notasGeralDisciplinaDTO);
        if (notasGeralDisciplinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notasGeralDisciplinaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notasGeralDisciplinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NotasGeralDisciplinaDTO result = notasGeralDisciplinaService.update(notasGeralDisciplinaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notasGeralDisciplinaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /notas-geral-disciplinas/:id} : Partial updates given fields of an existing notasGeralDisciplina, field will ignore if it is null
     *
     * @param id the id of the notasGeralDisciplinaDTO to save.
     * @param notasGeralDisciplinaDTO the notasGeralDisciplinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notasGeralDisciplinaDTO,
     * or with status {@code 400 (Bad Request)} if the notasGeralDisciplinaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the notasGeralDisciplinaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the notasGeralDisciplinaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notas-geral-disciplinas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NotasGeralDisciplinaDTO> partialUpdateNotasGeralDisciplina(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NotasGeralDisciplinaDTO notasGeralDisciplinaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NotasGeralDisciplina partially : {}, {}", id, notasGeralDisciplinaDTO);
        if (notasGeralDisciplinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notasGeralDisciplinaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notasGeralDisciplinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NotasGeralDisciplinaDTO> result = notasGeralDisciplinaService.partialUpdate(notasGeralDisciplinaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notasGeralDisciplinaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /notas-geral-disciplinas} : get all the notasGeralDisciplinas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notasGeralDisciplinas in body.
     */
    @GetMapping("/notas-geral-disciplinas")
    public ResponseEntity<List<NotasGeralDisciplinaDTO>> getAllNotasGeralDisciplinas(
        NotasGeralDisciplinaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NotasGeralDisciplinas by criteria: {}", criteria);
        Page<NotasGeralDisciplinaDTO> page = notasGeralDisciplinaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notas-geral-disciplinas/count} : count all the notasGeralDisciplinas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/notas-geral-disciplinas/count")
    public ResponseEntity<Long> countNotasGeralDisciplinas(NotasGeralDisciplinaCriteria criteria) {
        log.debug("REST request to count NotasGeralDisciplinas by criteria: {}", criteria);
        return ResponseEntity.ok().body(notasGeralDisciplinaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /notas-geral-disciplinas/:id} : get the "id" notasGeralDisciplina.
     *
     * @param id the id of the notasGeralDisciplinaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notasGeralDisciplinaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notas-geral-disciplinas/{id}")
    public ResponseEntity<NotasGeralDisciplinaDTO> getNotasGeralDisciplina(@PathVariable Long id) {
        log.debug("REST request to get NotasGeralDisciplina : {}", id);
        Optional<NotasGeralDisciplinaDTO> notasGeralDisciplinaDTO = notasGeralDisciplinaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notasGeralDisciplinaDTO);
    }

    /**
     * {@code DELETE  /notas-geral-disciplinas/:id} : delete the "id" notasGeralDisciplina.
     *
     * @param id the id of the notasGeralDisciplinaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notas-geral-disciplinas/{id}")
    public ResponseEntity<Void> deleteNotasGeralDisciplina(@PathVariable Long id) {
        log.debug("REST request to delete NotasGeralDisciplina : {}", id);
        notasGeralDisciplinaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
