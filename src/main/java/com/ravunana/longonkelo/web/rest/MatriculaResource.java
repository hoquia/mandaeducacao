package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.MatriculaRepository;
import com.ravunana.longonkelo.service.MatriculaQueryService;
import com.ravunana.longonkelo.service.MatriculaService;
import com.ravunana.longonkelo.service.criteria.MatriculaCriteria;
import com.ravunana.longonkelo.service.dto.MatriculaDTO;
import com.ravunana.longonkelo.service.report.BoletimNotasServiceReport;
import com.ravunana.longonkelo.service.report.CertificadoServiceReport;
import com.ravunana.longonkelo.service.report.DeclaracaoNotasServiceReport;
import com.ravunana.longonkelo.service.report.DeclaracaoSemNotasServiceReport;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.Matricula}.
 */
@RestController
@RequestMapping("/api")
public class MatriculaResource {

    private final Logger log = LoggerFactory.getLogger(MatriculaResource.class);

    private static final String ENTITY_NAME = "matricula";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatriculaService matriculaService;

    private final MatriculaRepository matriculaRepository;

    private final MatriculaQueryService matriculaQueryService;
    private final BoletimNotasServiceReport boletimNotasServiceReport;
    private final DeclaracaoNotasServiceReport declaracaoNotasServiceReport;
    private final DeclaracaoSemNotasServiceReport declaracaoSemNotasServiceReport;
    private final CertificadoServiceReport certificadoServiceReport;

    public MatriculaResource(
        MatriculaService matriculaService,
        MatriculaRepository matriculaRepository,
        MatriculaQueryService matriculaQueryService,
        BoletimNotasServiceReport boletimNotasServiceReport,
        DeclaracaoNotasServiceReport declaracaoSemNotasServiceReport,
        DeclaracaoSemNotasServiceReport declaracaoSemNotasServiceReport1,
        CertificadoServiceReport certificadoServiceReport
    ) {
        this.matriculaService = matriculaService;
        this.matriculaRepository = matriculaRepository;
        this.matriculaQueryService = matriculaQueryService;
        this.boletimNotasServiceReport = boletimNotasServiceReport;
        this.declaracaoNotasServiceReport = declaracaoSemNotasServiceReport;
        this.declaracaoSemNotasServiceReport = declaracaoSemNotasServiceReport1;
        this.certificadoServiceReport = certificadoServiceReport;
    }

    /**
     * {@code POST  /matriculas} : Create a new matricula.
     *
     * @param matriculaDTO the matriculaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matriculaDTO, or with status {@code 400 (Bad Request)} if the matricula has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/matriculas")
    public ResponseEntity<MatriculaDTO> createMatricula(@Valid @RequestBody MatriculaDTO matriculaDTO) throws URISyntaxException {
        log.debug("REST request to save Matricula : {}", matriculaDTO);
        if (matriculaDTO.getId() != null) {
            throw new BadRequestAlertException("A new matricula cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatriculaDTO result = matriculaService.save(matriculaDTO);
        return ResponseEntity
            .created(new URI("/api/matriculas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /matriculas/:id} : Updates an existing matricula.
     *
     * @param id the id of the matriculaDTO to save.
     * @param matriculaDTO the matriculaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matriculaDTO,
     * or with status {@code 400 (Bad Request)} if the matriculaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matriculaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/matriculas/{id}")
    public ResponseEntity<MatriculaDTO> updateMatricula(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MatriculaDTO matriculaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Matricula : {}, {}", id, matriculaDTO);
        if (matriculaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matriculaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matriculaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MatriculaDTO result = matriculaService.update(matriculaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matriculaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /matriculas/:id} : Partial updates given fields of an existing matricula, field will ignore if it is null
     *
     * @param id the id of the matriculaDTO to save.
     * @param matriculaDTO the matriculaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matriculaDTO,
     * or with status {@code 400 (Bad Request)} if the matriculaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matriculaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matriculaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/matriculas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatriculaDTO> partialUpdateMatricula(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MatriculaDTO matriculaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Matricula partially : {}, {}", id, matriculaDTO);
        if (matriculaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matriculaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matriculaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatriculaDTO> result = matriculaService.partialUpdate(matriculaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matriculaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /matriculas} : get all the matriculas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matriculas in body.
     */
    @GetMapping("/matriculas")
    public ResponseEntity<List<MatriculaDTO>> getAllMatriculas(
        MatriculaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Matriculas by criteria: {}", criteria);
        Page<MatriculaDTO> page = matriculaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /matriculas/count} : count all the matriculas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/matriculas/count")
    public ResponseEntity<Long> countMatriculas(MatriculaCriteria criteria) {
        log.debug("REST request to count Matriculas by criteria: {}", criteria);
        return ResponseEntity.ok().body(matriculaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /matriculas/:id} : get the "id" matricula.
     *
     * @param id the id of the matriculaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matriculaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/matriculas/{id}")
    public ResponseEntity<MatriculaDTO> getMatricula(@PathVariable Long id) {
        log.debug("REST request to get Matricula : {}", id);
        Optional<MatriculaDTO> matriculaDTO = matriculaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matriculaDTO);
    }

    /**
     * {@code DELETE  /matriculas/:id} : delete the "id" matricula.
     *
     * @param id the id of the matriculaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/matriculas/{id}")
    public ResponseEntity<Void> deleteMatricula(@PathVariable Long id) {
        log.debug("REST request to delete Matricula : {}", id);
        matriculaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/matriculas/boletim-notas/{matriculaID}/{periodo}")
    public ResponseEntity<Resource> gerarBoletimNotas(@PathVariable Long matriculaID, @PathVariable Integer periodo) throws IOException {
        var filePath = boletimNotasServiceReport.gerarBoletimNotasPdf(matriculaID, periodo);
        File file = new File(filePath);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.APPLICATION_PDF).body(resource);
    }

    @GetMapping("/matriculas/declaracao-notas/{matriculaID}")
    public ResponseEntity<Resource> gerarDeclaracaoNotas(@PathVariable Long matriculaID) throws IOException {
        var filePath = declaracaoNotasServiceReport.gerarDeclaracaoNotasPdf(matriculaID);
        File file = new File(filePath);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.APPLICATION_PDF).body(resource);
    }

    @GetMapping("/matriculas/declaracao/{matriculaID}")
    public ResponseEntity<Resource> gerarDeclaracaoSemNotas(@PathVariable Long matriculaID) throws IOException {
        var filePath = declaracaoSemNotasServiceReport.gerarDeclaracaoSemNotasPdf(matriculaID);
        File file = new File(filePath);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.APPLICATION_PDF).body(resource);
    }

    @GetMapping("/matriculas/certificado/{matriculaID}")
    public ResponseEntity<Resource> gerarCertificado(@PathVariable Long matriculaID) throws IOException {
        var filePath = certificadoServiceReport.gerarCertificadoPdf(matriculaID);
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
