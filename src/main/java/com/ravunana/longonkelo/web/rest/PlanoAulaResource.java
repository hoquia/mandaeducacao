package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.PlanoAulaRepository;
import com.ravunana.longonkelo.service.PlanoAulaQueryService;
import com.ravunana.longonkelo.service.PlanoAulaService;
import com.ravunana.longonkelo.service.criteria.PlanoAulaCriteria;
import com.ravunana.longonkelo.service.dto.PlanoAulaDTO;
import com.ravunana.longonkelo.service.report.PlanoAulaReport;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.PlanoAula}.
 */
@RestController
@RequestMapping("/api")
public class PlanoAulaResource {

    private final Logger log = LoggerFactory.getLogger(PlanoAulaResource.class);

    private static final String ENTITY_NAME = "planoAula";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoAulaService planoAulaService;

    private final PlanoAulaRepository planoAulaRepository;

    private final PlanoAulaQueryService planoAulaQueryService;

    private final PlanoAulaReport pdfPlanoAulaService;

    public PlanoAulaResource(
        PlanoAulaService planoAulaService,
        PlanoAulaRepository planoAulaRepository,
        PlanoAulaQueryService planoAulaQueryService,
        PlanoAulaReport pdfPlanoAulaService
    ) {
        this.planoAulaService = planoAulaService;
        this.planoAulaRepository = planoAulaRepository;
        this.planoAulaQueryService = planoAulaQueryService;
        this.pdfPlanoAulaService = pdfPlanoAulaService;
    }

    /**
     * {@code POST  /plano-aulas} : Create a new planoAula.
     *
     * @param planoAulaDTO the planoAulaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoAulaDTO, or with status {@code 400 (Bad Request)} if the planoAula has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plano-aulas")
    public ResponseEntity<PlanoAulaDTO> createPlanoAula(@Valid @RequestBody PlanoAulaDTO planoAulaDTO) throws URISyntaxException {
        log.debug("REST request to save PlanoAula : {}", planoAulaDTO);
        if (planoAulaDTO.getId() != null) {
            throw new BadRequestAlertException("A new planoAula cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoAulaDTO result = planoAulaService.save(planoAulaDTO);
        return ResponseEntity
            .created(new URI("/api/plano-aulas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plano-aulas/:id} : Updates an existing planoAula.
     *
     * @param id the id of the planoAulaDTO to save.
     * @param planoAulaDTO the planoAulaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoAulaDTO,
     * or with status {@code 400 (Bad Request)} if the planoAulaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoAulaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plano-aulas/{id}")
    public ResponseEntity<PlanoAulaDTO> updatePlanoAula(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlanoAulaDTO planoAulaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlanoAula : {}, {}", id, planoAulaDTO);
        if (planoAulaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoAulaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoAulaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlanoAulaDTO result = planoAulaService.update(planoAulaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoAulaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plano-aulas/:id} : Partial updates given fields of an existing planoAula, field will ignore if it is null
     *
     * @param id the id of the planoAulaDTO to save.
     * @param planoAulaDTO the planoAulaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoAulaDTO,
     * or with status {@code 400 (Bad Request)} if the planoAulaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the planoAulaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the planoAulaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plano-aulas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlanoAulaDTO> partialUpdatePlanoAula(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlanoAulaDTO planoAulaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlanoAula partially : {}, {}", id, planoAulaDTO);
        if (planoAulaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoAulaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoAulaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlanoAulaDTO> result = planoAulaService.partialUpdate(planoAulaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoAulaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /plano-aulas} : get all the planoAulas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoAulas in body.
     */
    @GetMapping("/plano-aulas")
    public ResponseEntity<List<PlanoAulaDTO>> getAllPlanoAulas(
        PlanoAulaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PlanoAulas by criteria: {}", criteria);
        Page<PlanoAulaDTO> page = planoAulaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plano-aulas/count} : count all the planoAulas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/plano-aulas/count")
    public ResponseEntity<Long> countPlanoAulas(PlanoAulaCriteria criteria) {
        log.debug("REST request to count PlanoAulas by criteria: {}", criteria);
        return ResponseEntity.ok().body(planoAulaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plano-aulas/:id} : get the "id" planoAula.
     *
     * @param id the id of the planoAulaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoAulaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plano-aulas/{id}")
    public ResponseEntity<PlanoAulaDTO> getPlanoAula(@PathVariable Long id) {
        log.debug("REST request to get PlanoAula : {}", id);
        Optional<PlanoAulaDTO> planoAulaDTO = planoAulaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planoAulaDTO);
    }

    /**
     * {@code DELETE  /plano-aulas/:id} : delete the "id" planoAula.
     *
     * @param id the id of the planoAulaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plano-aulas/{id}")
    public ResponseEntity<Void> deletePlanoAula(@PathVariable Long id) {
        log.debug("REST request to delete PlanoAula : {}", id);
        planoAulaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/plano-aulas/diario/{planoAulaId}")
    public ResponseEntity<Resource> getReciboSalario(@PathVariable Long planoAulaId) throws IOException {
        var filePath = pdfPlanoAulaService.gerarPdf(planoAulaId);
        File file = new File(filePath);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.APPLICATION_PDF).body(resource);
    }
}
