package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.EstadoDissertacaoRepository;
import com.ravunana.longonkelo.service.EstadoDissertacaoQueryService;
import com.ravunana.longonkelo.service.EstadoDissertacaoService;
import com.ravunana.longonkelo.service.criteria.EstadoDissertacaoCriteria;
import com.ravunana.longonkelo.service.dto.EstadoDissertacaoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.EstadoDissertacao}.
 */
@RestController
@RequestMapping("/api")
public class EstadoDissertacaoResource {

    private final Logger log = LoggerFactory.getLogger(EstadoDissertacaoResource.class);

    private static final String ENTITY_NAME = "estadoDissertacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoDissertacaoService estadoDissertacaoService;

    private final EstadoDissertacaoRepository estadoDissertacaoRepository;

    private final EstadoDissertacaoQueryService estadoDissertacaoQueryService;

    public EstadoDissertacaoResource(
        EstadoDissertacaoService estadoDissertacaoService,
        EstadoDissertacaoRepository estadoDissertacaoRepository,
        EstadoDissertacaoQueryService estadoDissertacaoQueryService
    ) {
        this.estadoDissertacaoService = estadoDissertacaoService;
        this.estadoDissertacaoRepository = estadoDissertacaoRepository;
        this.estadoDissertacaoQueryService = estadoDissertacaoQueryService;
    }

    /**
     * {@code POST  /estado-dissertacaos} : Create a new estadoDissertacao.
     *
     * @param estadoDissertacaoDTO the estadoDissertacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoDissertacaoDTO, or with status {@code 400 (Bad Request)} if the estadoDissertacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estado-dissertacaos")
    public ResponseEntity<EstadoDissertacaoDTO> createEstadoDissertacao(@Valid @RequestBody EstadoDissertacaoDTO estadoDissertacaoDTO)
        throws URISyntaxException {
        log.debug("REST request to save EstadoDissertacao : {}", estadoDissertacaoDTO);
        if (estadoDissertacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estadoDissertacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoDissertacaoDTO result = estadoDissertacaoService.save(estadoDissertacaoDTO);
        return ResponseEntity
            .created(new URI("/api/estado-dissertacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-dissertacaos/:id} : Updates an existing estadoDissertacao.
     *
     * @param id the id of the estadoDissertacaoDTO to save.
     * @param estadoDissertacaoDTO the estadoDissertacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoDissertacaoDTO,
     * or with status {@code 400 (Bad Request)} if the estadoDissertacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoDissertacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estado-dissertacaos/{id}")
    public ResponseEntity<EstadoDissertacaoDTO> updateEstadoDissertacao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EstadoDissertacaoDTO estadoDissertacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EstadoDissertacao : {}, {}", id, estadoDissertacaoDTO);
        if (estadoDissertacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoDissertacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoDissertacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstadoDissertacaoDTO result = estadoDissertacaoService.update(estadoDissertacaoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoDissertacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estado-dissertacaos/:id} : Partial updates given fields of an existing estadoDissertacao, field will ignore if it is null
     *
     * @param id the id of the estadoDissertacaoDTO to save.
     * @param estadoDissertacaoDTO the estadoDissertacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoDissertacaoDTO,
     * or with status {@code 400 (Bad Request)} if the estadoDissertacaoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the estadoDissertacaoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the estadoDissertacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estado-dissertacaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstadoDissertacaoDTO> partialUpdateEstadoDissertacao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EstadoDissertacaoDTO estadoDissertacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EstadoDissertacao partially : {}, {}", id, estadoDissertacaoDTO);
        if (estadoDissertacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoDissertacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoDissertacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstadoDissertacaoDTO> result = estadoDissertacaoService.partialUpdate(estadoDissertacaoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoDissertacaoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /estado-dissertacaos} : get all the estadoDissertacaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadoDissertacaos in body.
     */
    @GetMapping("/estado-dissertacaos")
    public ResponseEntity<List<EstadoDissertacaoDTO>> getAllEstadoDissertacaos(
        EstadoDissertacaoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EstadoDissertacaos by criteria: {}", criteria);
        Page<EstadoDissertacaoDTO> page = estadoDissertacaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estado-dissertacaos/count} : count all the estadoDissertacaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/estado-dissertacaos/count")
    public ResponseEntity<Long> countEstadoDissertacaos(EstadoDissertacaoCriteria criteria) {
        log.debug("REST request to count EstadoDissertacaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(estadoDissertacaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /estado-dissertacaos/:id} : get the "id" estadoDissertacao.
     *
     * @param id the id of the estadoDissertacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoDissertacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estado-dissertacaos/{id}")
    public ResponseEntity<EstadoDissertacaoDTO> getEstadoDissertacao(@PathVariable Long id) {
        log.debug("REST request to get EstadoDissertacao : {}", id);
        Optional<EstadoDissertacaoDTO> estadoDissertacaoDTO = estadoDissertacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estadoDissertacaoDTO);
    }

    /**
     * {@code DELETE  /estado-dissertacaos/:id} : delete the "id" estadoDissertacao.
     *
     * @param id the id of the estadoDissertacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estado-dissertacaos/{id}")
    public ResponseEntity<Void> deleteEstadoDissertacao(@PathVariable Long id) {
        log.debug("REST request to delete EstadoDissertacao : {}", id);
        estadoDissertacaoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
