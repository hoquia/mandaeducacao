package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.CategoriaEmolumentoRepository;
import com.ravunana.longonkelo.service.CategoriaEmolumentoQueryService;
import com.ravunana.longonkelo.service.CategoriaEmolumentoService;
import com.ravunana.longonkelo.service.criteria.CategoriaEmolumentoCriteria;
import com.ravunana.longonkelo.service.dto.CategoriaEmolumentoDTO;
import com.ravunana.longonkelo.service.report.FluxoCaixaCategoriaEmolumentoReport;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.CategoriaEmolumento}.
 */
@RestController
@RequestMapping("/api")
public class CategoriaEmolumentoResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaEmolumentoResource.class);

    private static final String ENTITY_NAME = "categoriaEmolumento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriaEmolumentoService categoriaEmolumentoService;

    private final CategoriaEmolumentoRepository categoriaEmolumentoRepository;

    private final CategoriaEmolumentoQueryService categoriaEmolumentoQueryService;
    private final FluxoCaixaCategoriaEmolumentoReport fluxoCaixaCategoriaEmolumentoReport;

    public CategoriaEmolumentoResource(
        CategoriaEmolumentoService categoriaEmolumentoService,
        CategoriaEmolumentoRepository categoriaEmolumentoRepository,
        CategoriaEmolumentoQueryService categoriaEmolumentoQueryService,
        FluxoCaixaCategoriaEmolumentoReport fluxoCaixaCategoriaEmolumentoReport
    ) {
        this.categoriaEmolumentoService = categoriaEmolumentoService;
        this.categoriaEmolumentoRepository = categoriaEmolumentoRepository;
        this.categoriaEmolumentoQueryService = categoriaEmolumentoQueryService;
        this.fluxoCaixaCategoriaEmolumentoReport = fluxoCaixaCategoriaEmolumentoReport;
    }

    /**
     * {@code POST  /categoria-emolumentos} : Create a new categoriaEmolumento.
     *
     * @param categoriaEmolumentoDTO the categoriaEmolumentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriaEmolumentoDTO, or with status {@code 400 (Bad Request)} if the categoriaEmolumento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categoria-emolumentos")
    public ResponseEntity<CategoriaEmolumentoDTO> createCategoriaEmolumento(
        @Valid @RequestBody CategoriaEmolumentoDTO categoriaEmolumentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CategoriaEmolumento : {}", categoriaEmolumentoDTO);
        if (categoriaEmolumentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoriaEmolumento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaEmolumentoDTO result = categoriaEmolumentoService.save(categoriaEmolumentoDTO);
        return ResponseEntity
            .created(new URI("/api/categoria-emolumentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categoria-emolumentos/:id} : Updates an existing categoriaEmolumento.
     *
     * @param id the id of the categoriaEmolumentoDTO to save.
     * @param categoriaEmolumentoDTO the categoriaEmolumentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaEmolumentoDTO,
     * or with status {@code 400 (Bad Request)} if the categoriaEmolumentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriaEmolumentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categoria-emolumentos/{id}")
    public ResponseEntity<CategoriaEmolumentoDTO> updateCategoriaEmolumento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CategoriaEmolumentoDTO categoriaEmolumentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CategoriaEmolumento : {}, {}", id, categoriaEmolumentoDTO);
        if (categoriaEmolumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriaEmolumentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriaEmolumentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategoriaEmolumentoDTO result = categoriaEmolumentoService.update(categoriaEmolumentoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaEmolumentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categoria-emolumentos/:id} : Partial updates given fields of an existing categoriaEmolumento, field will ignore if it is null
     *
     * @param id the id of the categoriaEmolumentoDTO to save.
     * @param categoriaEmolumentoDTO the categoriaEmolumentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaEmolumentoDTO,
     * or with status {@code 400 (Bad Request)} if the categoriaEmolumentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categoriaEmolumentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoriaEmolumentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categoria-emolumentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategoriaEmolumentoDTO> partialUpdateCategoriaEmolumento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CategoriaEmolumentoDTO categoriaEmolumentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategoriaEmolumento partially : {}, {}", id, categoriaEmolumentoDTO);
        if (categoriaEmolumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriaEmolumentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriaEmolumentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategoriaEmolumentoDTO> result = categoriaEmolumentoService.partialUpdate(categoriaEmolumentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaEmolumentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /categoria-emolumentos} : get all the categoriaEmolumentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriaEmolumentos in body.
     */
    @GetMapping("/categoria-emolumentos")
    public ResponseEntity<List<CategoriaEmolumentoDTO>> getAllCategoriaEmolumentos(
        CategoriaEmolumentoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CategoriaEmolumentos by criteria: {}", criteria);
        Page<CategoriaEmolumentoDTO> page = categoriaEmolumentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categoria-emolumentos/count} : count all the categoriaEmolumentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/categoria-emolumentos/count")
    public ResponseEntity<Long> countCategoriaEmolumentos(CategoriaEmolumentoCriteria criteria) {
        log.debug("REST request to count CategoriaEmolumentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(categoriaEmolumentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categoria-emolumentos/:id} : get the "id" categoriaEmolumento.
     *
     * @param id the id of the categoriaEmolumentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriaEmolumentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categoria-emolumentos/{id}")
    public ResponseEntity<CategoriaEmolumentoDTO> getCategoriaEmolumento(@PathVariable Long id) {
        log.debug("REST request to get CategoriaEmolumento : {}", id);
        Optional<CategoriaEmolumentoDTO> categoriaEmolumentoDTO = categoriaEmolumentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriaEmolumentoDTO);
    }

    /**
     * {@code DELETE  /categoria-emolumentos/:id} : delete the "id" categoriaEmolumento.
     *
     * @param id the id of the categoriaEmolumentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categoria-emolumentos/{id}")
    public ResponseEntity<Void> deleteCategoriaEmolumento(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaEmolumento : {}", id);
        categoriaEmolumentoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/turmas/categoria-emolumentos/{categoriaEmolumentosID}")
    public ResponseEntity<Resource> getEstratoFinanceiro(@PathVariable Long categoriaEmolumentosID) throws IOException {
        var filePath = fluxoCaixaCategoriaEmolumentoReport.gerarPdf(categoriaEmolumentosID);
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
