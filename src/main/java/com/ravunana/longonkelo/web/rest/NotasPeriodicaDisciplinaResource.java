package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.NotasPeriodicaDisciplinaRepository;
import com.ravunana.longonkelo.service.NotasPeriodicaDisciplinaQueryService;
import com.ravunana.longonkelo.service.NotasPeriodicaDisciplinaService;
import com.ravunana.longonkelo.service.criteria.NotasPeriodicaDisciplinaCriteria;
import com.ravunana.longonkelo.service.dto.NotasPeriodicaDisciplinaDTO;
import com.ravunana.longonkelo.service.report.BoletimNotasServiceReport;
import com.ravunana.longonkelo.web.rest.errors.BadRequestAlertException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina}.
 */
@RestController
@RequestMapping("/api")
public class NotasPeriodicaDisciplinaResource {

    private final Logger log = LoggerFactory.getLogger(NotasPeriodicaDisciplinaResource.class);

    private static final String ENTITY_NAME = "notasPeriodicaDisciplina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotasPeriodicaDisciplinaService notasPeriodicaDisciplinaService;

    private final NotasPeriodicaDisciplinaRepository notasPeriodicaDisciplinaRepository;

    private final NotasPeriodicaDisciplinaQueryService notasPeriodicaDisciplinaQueryService;

    private final BoletimNotasServiceReport boletimNotasServiceReport;

    public NotasPeriodicaDisciplinaResource(
        NotasPeriodicaDisciplinaService notasPeriodicaDisciplinaService,
        NotasPeriodicaDisciplinaRepository notasPeriodicaDisciplinaRepository,
        NotasPeriodicaDisciplinaQueryService notasPeriodicaDisciplinaQueryService,
        BoletimNotasServiceReport boletimNotasServiceReport
    ) {
        this.notasPeriodicaDisciplinaService = notasPeriodicaDisciplinaService;
        this.notasPeriodicaDisciplinaRepository = notasPeriodicaDisciplinaRepository;
        this.notasPeriodicaDisciplinaQueryService = notasPeriodicaDisciplinaQueryService;
        this.boletimNotasServiceReport = boletimNotasServiceReport;
    }

    /**
     * {@code POST  /notas-periodica-disciplinas} : Create a new notasPeriodicaDisciplina.
     *
     * @param notasPeriodicaDisciplinaDTO the notasPeriodicaDisciplinaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notasPeriodicaDisciplinaDTO, or with status {@code 400 (Bad Request)} if the notasPeriodicaDisciplina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notas-periodica-disciplinas")
    public ResponseEntity<NotasPeriodicaDisciplinaDTO> createNotasPeriodicaDisciplina(
        @Valid @RequestBody NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save NotasPeriodicaDisciplina : {}", notasPeriodicaDisciplinaDTO);
        if (notasPeriodicaDisciplinaDTO.getId() != null) {
            throw new BadRequestAlertException("A new notasPeriodicaDisciplina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotasPeriodicaDisciplinaDTO result = notasPeriodicaDisciplinaService.save(notasPeriodicaDisciplinaDTO);
        return ResponseEntity
            .created(new URI("/api/notas-periodica-disciplinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notas-periodica-disciplinas/:id} : Updates an existing notasPeriodicaDisciplina.
     *
     * @param id the id of the notasPeriodicaDisciplinaDTO to save.
     * @param notasPeriodicaDisciplinaDTO the notasPeriodicaDisciplinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notasPeriodicaDisciplinaDTO,
     * or with status {@code 400 (Bad Request)} if the notasPeriodicaDisciplinaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notasPeriodicaDisciplinaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notas-periodica-disciplinas/{id}")
    public ResponseEntity<NotasPeriodicaDisciplinaDTO> updateNotasPeriodicaDisciplina(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NotasPeriodicaDisciplina : {}, {}", id, notasPeriodicaDisciplinaDTO);
        if (notasPeriodicaDisciplinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notasPeriodicaDisciplinaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notasPeriodicaDisciplinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NotasPeriodicaDisciplinaDTO result = notasPeriodicaDisciplinaService.update(notasPeriodicaDisciplinaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notasPeriodicaDisciplinaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /notas-periodica-disciplinas/:id} : Partial updates given fields of an existing notasPeriodicaDisciplina, field will ignore if it is null
     *
     * @param id the id of the notasPeriodicaDisciplinaDTO to save.
     * @param notasPeriodicaDisciplinaDTO the notasPeriodicaDisciplinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notasPeriodicaDisciplinaDTO,
     * or with status {@code 400 (Bad Request)} if the notasPeriodicaDisciplinaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the notasPeriodicaDisciplinaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the notasPeriodicaDisciplinaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notas-periodica-disciplinas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NotasPeriodicaDisciplinaDTO> partialUpdateNotasPeriodicaDisciplina(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NotasPeriodicaDisciplina partially : {}, {}", id, notasPeriodicaDisciplinaDTO);
        if (notasPeriodicaDisciplinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notasPeriodicaDisciplinaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notasPeriodicaDisciplinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NotasPeriodicaDisciplinaDTO> result = notasPeriodicaDisciplinaService.partialUpdate(notasPeriodicaDisciplinaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notasPeriodicaDisciplinaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /notas-periodica-disciplinas} : get all the notasPeriodicaDisciplinas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notasPeriodicaDisciplinas in body.
     */
    @GetMapping("/notas-periodica-disciplinas")
    public ResponseEntity<List<NotasPeriodicaDisciplinaDTO>> getAllNotasPeriodicaDisciplinas(
        NotasPeriodicaDisciplinaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NotasPeriodicaDisciplinas by criteria: {}", criteria);
        Page<NotasPeriodicaDisciplinaDTO> page = notasPeriodicaDisciplinaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notas-periodica-disciplinas/count} : count all the notasPeriodicaDisciplinas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/notas-periodica-disciplinas/count")
    public ResponseEntity<Long> countNotasPeriodicaDisciplinas(NotasPeriodicaDisciplinaCriteria criteria) {
        log.debug("REST request to count NotasPeriodicaDisciplinas by criteria: {}", criteria);
        return ResponseEntity.ok().body(notasPeriodicaDisciplinaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /notas-periodica-disciplinas/:id} : get the "id" notasPeriodicaDisciplina.
     *
     * @param id the id of the notasPeriodicaDisciplinaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notasPeriodicaDisciplinaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notas-periodica-disciplinas/{id}")
    public ResponseEntity<NotasPeriodicaDisciplinaDTO> getNotasPeriodicaDisciplina(@PathVariable Long id) {
        log.debug("REST request to get NotasPeriodicaDisciplina : {}", id);
        Optional<NotasPeriodicaDisciplinaDTO> notasPeriodicaDisciplinaDTO = notasPeriodicaDisciplinaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notasPeriodicaDisciplinaDTO);
    }

    /**
     * {@code DELETE  /notas-periodica-disciplinas/:id} : delete the "id" notasPeriodicaDisciplina.
     *
     * @param id the id of the notasPeriodicaDisciplinaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notas-periodica-disciplinas/{id}")
    public ResponseEntity<Void> deleteNotasPeriodicaDisciplina(@PathVariable Long id) {
        log.debug("REST request to delete NotasPeriodicaDisciplina : {}", id);
        notasPeriodicaDisciplinaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
