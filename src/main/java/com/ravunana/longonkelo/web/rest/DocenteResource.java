package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.DocenteRepository;
import com.ravunana.longonkelo.service.DocenteQueryService;
import com.ravunana.longonkelo.service.DocenteService;
import com.ravunana.longonkelo.service.criteria.DocenteCriteria;
import com.ravunana.longonkelo.service.dto.DocenteDTO;
import com.ravunana.longonkelo.service.report.HorarioDocenteReport;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.Docente}.
 */
@RestController
@RequestMapping("/api")
public class DocenteResource {

    private final Logger log = LoggerFactory.getLogger(DocenteResource.class);

    private static final String ENTITY_NAME = "docente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocenteService docenteService;

    private final DocenteRepository docenteRepository;

    private final DocenteQueryService docenteQueryService;
    private final HorarioDocenteReport horarioDocenteService;

    public DocenteResource(
        DocenteService docenteService,
        DocenteRepository docenteRepository,
        DocenteQueryService docenteQueryService,
        HorarioDocenteReport horarioDocenteService
    ) {
        this.docenteService = docenteService;
        this.docenteRepository = docenteRepository;
        this.docenteQueryService = docenteQueryService;
        this.horarioDocenteService = horarioDocenteService;
    }

    /**
     * {@code POST  /docentes} : Create a new docente.
     *
     * @param docenteDTO the docenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new docenteDTO, or with status {@code 400 (Bad Request)} if the docente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/docentes")
    public ResponseEntity<DocenteDTO> createDocente(@Valid @RequestBody DocenteDTO docenteDTO) throws URISyntaxException {
        log.debug("REST request to save Docente : {}", docenteDTO);
        if (docenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new docente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocenteDTO result = docenteService.save(docenteDTO);
        return ResponseEntity
            .created(new URI("/api/docentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /docentes/:id} : Updates an existing docente.
     *
     * @param id the id of the docenteDTO to save.
     * @param docenteDTO the docenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docenteDTO,
     * or with status {@code 400 (Bad Request)} if the docenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the docenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/docentes/{id}")
    public ResponseEntity<DocenteDTO> updateDocente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DocenteDTO docenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Docente : {}, {}", id, docenteDTO);
        if (docenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, docenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!docenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DocenteDTO result = docenteService.update(docenteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, docenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /docentes/:id} : Partial updates given fields of an existing docente, field will ignore if it is null
     *
     * @param id the id of the docenteDTO to save.
     * @param docenteDTO the docenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docenteDTO,
     * or with status {@code 400 (Bad Request)} if the docenteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the docenteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the docenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/docentes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocenteDTO> partialUpdateDocente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DocenteDTO docenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Docente partially : {}, {}", id, docenteDTO);
        if (docenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, docenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!docenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocenteDTO> result = docenteService.partialUpdate(docenteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, docenteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /docentes} : get all the docentes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of docentes in body.
     */
    @GetMapping("/docentes")
    public ResponseEntity<List<DocenteDTO>> getAllDocentes(
        DocenteCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Docentes by criteria: {}", criteria);
        Page<DocenteDTO> page = docenteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /docentes/count} : count all the docentes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/docentes/count")
    public ResponseEntity<Long> countDocentes(DocenteCriteria criteria) {
        log.debug("REST request to count Docentes by criteria: {}", criteria);
        return ResponseEntity.ok().body(docenteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /docentes/:id} : get the "id" docente.
     *
     * @param id the id of the docenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the docenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/docentes/{id}")
    public ResponseEntity<DocenteDTO> getDocente(@PathVariable Long id) {
        log.debug("REST request to get Docente : {}", id);
        Optional<DocenteDTO> docenteDTO = docenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(docenteDTO);
    }

    /**
     * {@code DELETE  /docentes/:id} : delete the "id" docente.
     *
     * @param id the id of the docenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/docentes/{id}")
    public ResponseEntity<Void> deleteDocente(@PathVariable Long id) {
        log.debug("REST request to delete Docente : {}", id);
        docenteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/docentes/horario-docente/{docenteID}")
    public ResponseEntity<Resource> getHorarioDiscente(@PathVariable Long docenteID) throws IOException {
        var filePath = horarioDocenteService.gerarPdf(docenteID);
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
