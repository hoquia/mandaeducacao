package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.CategoriaOcorrenciaRepository;
import com.ravunana.longonkelo.service.CategoriaOcorrenciaQueryService;
import com.ravunana.longonkelo.service.CategoriaOcorrenciaService;
import com.ravunana.longonkelo.service.criteria.CategoriaOcorrenciaCriteria;
import com.ravunana.longonkelo.service.dto.CategoriaOcorrenciaDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.CategoriaOcorrencia}.
 */
@RestController
@RequestMapping("/api")
public class CategoriaOcorrenciaResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaOcorrenciaResource.class);

    private static final String ENTITY_NAME = "categoriaOcorrencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriaOcorrenciaService categoriaOcorrenciaService;

    private final CategoriaOcorrenciaRepository categoriaOcorrenciaRepository;

    private final CategoriaOcorrenciaQueryService categoriaOcorrenciaQueryService;

    public CategoriaOcorrenciaResource(
        CategoriaOcorrenciaService categoriaOcorrenciaService,
        CategoriaOcorrenciaRepository categoriaOcorrenciaRepository,
        CategoriaOcorrenciaQueryService categoriaOcorrenciaQueryService
    ) {
        this.categoriaOcorrenciaService = categoriaOcorrenciaService;
        this.categoriaOcorrenciaRepository = categoriaOcorrenciaRepository;
        this.categoriaOcorrenciaQueryService = categoriaOcorrenciaQueryService;
    }

    /**
     * {@code POST  /categoria-ocorrencias} : Create a new categoriaOcorrencia.
     *
     * @param categoriaOcorrenciaDTO the categoriaOcorrenciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriaOcorrenciaDTO, or with status {@code 400 (Bad Request)} if the categoriaOcorrencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categoria-ocorrencias")
    public ResponseEntity<CategoriaOcorrenciaDTO> createCategoriaOcorrencia(
        @Valid @RequestBody CategoriaOcorrenciaDTO categoriaOcorrenciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CategoriaOcorrencia : {}", categoriaOcorrenciaDTO);
        if (categoriaOcorrenciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoriaOcorrencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaOcorrenciaDTO result = categoriaOcorrenciaService.save(categoriaOcorrenciaDTO);
        return ResponseEntity
            .created(new URI("/api/categoria-ocorrencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categoria-ocorrencias/:id} : Updates an existing categoriaOcorrencia.
     *
     * @param id the id of the categoriaOcorrenciaDTO to save.
     * @param categoriaOcorrenciaDTO the categoriaOcorrenciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaOcorrenciaDTO,
     * or with status {@code 400 (Bad Request)} if the categoriaOcorrenciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriaOcorrenciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categoria-ocorrencias/{id}")
    public ResponseEntity<CategoriaOcorrenciaDTO> updateCategoriaOcorrencia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CategoriaOcorrenciaDTO categoriaOcorrenciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CategoriaOcorrencia : {}, {}", id, categoriaOcorrenciaDTO);
        if (categoriaOcorrenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriaOcorrenciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriaOcorrenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategoriaOcorrenciaDTO result = categoriaOcorrenciaService.update(categoriaOcorrenciaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaOcorrenciaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categoria-ocorrencias/:id} : Partial updates given fields of an existing categoriaOcorrencia, field will ignore if it is null
     *
     * @param id the id of the categoriaOcorrenciaDTO to save.
     * @param categoriaOcorrenciaDTO the categoriaOcorrenciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaOcorrenciaDTO,
     * or with status {@code 400 (Bad Request)} if the categoriaOcorrenciaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categoriaOcorrenciaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoriaOcorrenciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categoria-ocorrencias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategoriaOcorrenciaDTO> partialUpdateCategoriaOcorrencia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CategoriaOcorrenciaDTO categoriaOcorrenciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategoriaOcorrencia partially : {}, {}", id, categoriaOcorrenciaDTO);
        if (categoriaOcorrenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriaOcorrenciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriaOcorrenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategoriaOcorrenciaDTO> result = categoriaOcorrenciaService.partialUpdate(categoriaOcorrenciaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaOcorrenciaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /categoria-ocorrencias} : get all the categoriaOcorrencias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriaOcorrencias in body.
     */
    @GetMapping("/categoria-ocorrencias")
    public ResponseEntity<List<CategoriaOcorrenciaDTO>> getAllCategoriaOcorrencias(
        CategoriaOcorrenciaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CategoriaOcorrencias by criteria: {}", criteria);
        Page<CategoriaOcorrenciaDTO> page = categoriaOcorrenciaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categoria-ocorrencias/count} : count all the categoriaOcorrencias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/categoria-ocorrencias/count")
    public ResponseEntity<Long> countCategoriaOcorrencias(CategoriaOcorrenciaCriteria criteria) {
        log.debug("REST request to count CategoriaOcorrencias by criteria: {}", criteria);
        return ResponseEntity.ok().body(categoriaOcorrenciaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categoria-ocorrencias/:id} : get the "id" categoriaOcorrencia.
     *
     * @param id the id of the categoriaOcorrenciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriaOcorrenciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categoria-ocorrencias/{id}")
    public ResponseEntity<CategoriaOcorrenciaDTO> getCategoriaOcorrencia(@PathVariable Long id) {
        log.debug("REST request to get CategoriaOcorrencia : {}", id);
        Optional<CategoriaOcorrenciaDTO> categoriaOcorrenciaDTO = categoriaOcorrenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriaOcorrenciaDTO);
    }

    /**
     * {@code DELETE  /categoria-ocorrencias/:id} : delete the "id" categoriaOcorrencia.
     *
     * @param id the id of the categoriaOcorrenciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categoria-ocorrencias/{id}")
    public ResponseEntity<Void> deleteCategoriaOcorrencia(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaOcorrencia : {}", id);
        categoriaOcorrenciaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
