package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.TurmaRepository;
import com.ravunana.longonkelo.service.TurmaQueryService;
import com.ravunana.longonkelo.service.TurmaService;
import com.ravunana.longonkelo.service.criteria.TurmaCriteria;
import com.ravunana.longonkelo.service.dto.TurmaDTO;
import com.ravunana.longonkelo.service.report.HorarioDiscenteServiceImpl;
import com.ravunana.longonkelo.service.report.ListaPresencaTurmaServiceImpl;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.Turma}.
 */
@RestController
@RequestMapping("/api")
public class TurmaResource {

    private final Logger log = LoggerFactory.getLogger(TurmaResource.class);

    private static final String ENTITY_NAME = "turma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TurmaService turmaService;

    private final TurmaRepository turmaRepository;

    private final TurmaQueryService turmaQueryService;

    private final ListaPresencaTurmaServiceImpl listaPresencaTurmaService;
    private final HorarioDiscenteServiceImpl horarioDiscenteService;

    public TurmaResource(
        TurmaService turmaService,
        TurmaRepository turmaRepository,
        TurmaQueryService turmaQueryService,
        ListaPresencaTurmaServiceImpl listaPresencaTurmaService,
        HorarioDiscenteServiceImpl horarioDiscenteService
    ) {
        this.turmaService = turmaService;
        this.turmaRepository = turmaRepository;
        this.turmaQueryService = turmaQueryService;
        this.listaPresencaTurmaService = listaPresencaTurmaService;
        this.horarioDiscenteService = horarioDiscenteService;
    }

    /**
     * {@code POST  /turmas} : Create a new turma.
     *
     * @param turmaDTO the turmaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new turmaDTO, or with status {@code 400 (Bad Request)} if the turma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/turmas")
    public ResponseEntity<TurmaDTO> createTurma(@Valid @RequestBody TurmaDTO turmaDTO) throws URISyntaxException {
        log.debug("REST request to save Turma : {}", turmaDTO);
        if (turmaDTO.getId() != null) {
            throw new BadRequestAlertException("A new turma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TurmaDTO result = turmaService.save(turmaDTO);
        return ResponseEntity
            .created(new URI("/api/turmas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /turmas/:id} : Updates an existing turma.
     *
     * @param id the id of the turmaDTO to save.
     * @param turmaDTO the turmaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated turmaDTO,
     * or with status {@code 400 (Bad Request)} if the turmaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the turmaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/turmas/{id}")
    public ResponseEntity<TurmaDTO> updateTurma(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TurmaDTO turmaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Turma : {}, {}", id, turmaDTO);
        if (turmaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, turmaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!turmaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TurmaDTO result = turmaService.update(turmaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, turmaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /turmas/:id} : Partial updates given fields of an existing turma, field will ignore if it is null
     *
     * @param id the id of the turmaDTO to save.
     * @param turmaDTO the turmaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated turmaDTO,
     * or with status {@code 400 (Bad Request)} if the turmaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the turmaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the turmaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/turmas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TurmaDTO> partialUpdateTurma(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TurmaDTO turmaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Turma partially : {}, {}", id, turmaDTO);
        if (turmaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, turmaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!turmaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TurmaDTO> result = turmaService.partialUpdate(turmaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, turmaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /turmas} : get all the turmas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of turmas in body.
     */
    @GetMapping("/turmas")
    public ResponseEntity<List<TurmaDTO>> getAllTurmas(
        TurmaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Turmas by criteria: {}", criteria);
        Page<TurmaDTO> page = turmaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /turmas/count} : count all the turmas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/turmas/count")
    public ResponseEntity<Long> countTurmas(TurmaCriteria criteria) {
        log.debug("REST request to count Turmas by criteria: {}", criteria);
        return ResponseEntity.ok().body(turmaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /turmas/:id} : get the "id" turma.
     *
     * @param id the id of the turmaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the turmaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/turmas/{id}")
    public ResponseEntity<TurmaDTO> getTurma(@PathVariable Long id) {
        log.debug("REST request to get Turma : {}", id);
        Optional<TurmaDTO> turmaDTO = turmaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(turmaDTO);
    }

    /**
     * {@code DELETE  /turmas/:id} : delete the "id" turma.
     *
     * @param id the id of the turmaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/turmas/{id}")
    public ResponseEntity<Void> deleteTurma(@PathVariable Long id) {
        log.debug("REST request to delete Turma : {}", id);
        turmaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/turmas/lista-presenca/{turmaID}")
    public ResponseEntity<Resource> getReciboSalario(@PathVariable Long turmaID) throws IOException {
        var filePath = listaPresencaTurmaService.gerarPdf(turmaID);
        File file = new File(filePath);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.APPLICATION_PDF).body(resource);
    }

    @GetMapping("/turmas/horario-discente/{turmaID}")
    public ResponseEntity<Resource> getHorarioDiscente(@PathVariable Long turmaID) throws IOException {
        var filePath = horarioDiscenteService.gerarPdf(turmaID);
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
