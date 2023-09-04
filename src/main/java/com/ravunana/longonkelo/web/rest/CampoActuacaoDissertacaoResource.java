package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.CampoActuacaoDissertacaoRepository;
import com.ravunana.longonkelo.service.CampoActuacaoDissertacaoQueryService;
import com.ravunana.longonkelo.service.CampoActuacaoDissertacaoService;
import com.ravunana.longonkelo.service.criteria.CampoActuacaoDissertacaoCriteria;
import com.ravunana.longonkelo.service.dto.CampoActuacaoDissertacaoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.CampoActuacaoDissertacao}.
 */
@RestController
@RequestMapping("/api")
public class CampoActuacaoDissertacaoResource {

    private final Logger log = LoggerFactory.getLogger(CampoActuacaoDissertacaoResource.class);

    private static final String ENTITY_NAME = "campoActuacaoDissertacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampoActuacaoDissertacaoService campoActuacaoDissertacaoService;

    private final CampoActuacaoDissertacaoRepository campoActuacaoDissertacaoRepository;

    private final CampoActuacaoDissertacaoQueryService campoActuacaoDissertacaoQueryService;

    public CampoActuacaoDissertacaoResource(
        CampoActuacaoDissertacaoService campoActuacaoDissertacaoService,
        CampoActuacaoDissertacaoRepository campoActuacaoDissertacaoRepository,
        CampoActuacaoDissertacaoQueryService campoActuacaoDissertacaoQueryService
    ) {
        this.campoActuacaoDissertacaoService = campoActuacaoDissertacaoService;
        this.campoActuacaoDissertacaoRepository = campoActuacaoDissertacaoRepository;
        this.campoActuacaoDissertacaoQueryService = campoActuacaoDissertacaoQueryService;
    }

    /**
     * {@code POST  /campo-actuacao-dissertacaos} : Create a new campoActuacaoDissertacao.
     *
     * @param campoActuacaoDissertacaoDTO the campoActuacaoDissertacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campoActuacaoDissertacaoDTO, or with status {@code 400 (Bad Request)} if the campoActuacaoDissertacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campo-actuacao-dissertacaos")
    public ResponseEntity<CampoActuacaoDissertacaoDTO> createCampoActuacaoDissertacao(
        @Valid @RequestBody CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CampoActuacaoDissertacao : {}", campoActuacaoDissertacaoDTO);
        if (campoActuacaoDissertacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new campoActuacaoDissertacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampoActuacaoDissertacaoDTO result = campoActuacaoDissertacaoService.save(campoActuacaoDissertacaoDTO);
        return ResponseEntity
            .created(new URI("/api/campo-actuacao-dissertacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /campo-actuacao-dissertacaos/:id} : Updates an existing campoActuacaoDissertacao.
     *
     * @param id the id of the campoActuacaoDissertacaoDTO to save.
     * @param campoActuacaoDissertacaoDTO the campoActuacaoDissertacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campoActuacaoDissertacaoDTO,
     * or with status {@code 400 (Bad Request)} if the campoActuacaoDissertacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campoActuacaoDissertacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/campo-actuacao-dissertacaos/{id}")
    public ResponseEntity<CampoActuacaoDissertacaoDTO> updateCampoActuacaoDissertacao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CampoActuacaoDissertacao : {}, {}", id, campoActuacaoDissertacaoDTO);
        if (campoActuacaoDissertacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, campoActuacaoDissertacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!campoActuacaoDissertacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CampoActuacaoDissertacaoDTO result = campoActuacaoDissertacaoService.update(campoActuacaoDissertacaoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campoActuacaoDissertacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /campo-actuacao-dissertacaos/:id} : Partial updates given fields of an existing campoActuacaoDissertacao, field will ignore if it is null
     *
     * @param id the id of the campoActuacaoDissertacaoDTO to save.
     * @param campoActuacaoDissertacaoDTO the campoActuacaoDissertacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campoActuacaoDissertacaoDTO,
     * or with status {@code 400 (Bad Request)} if the campoActuacaoDissertacaoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the campoActuacaoDissertacaoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the campoActuacaoDissertacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/campo-actuacao-dissertacaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CampoActuacaoDissertacaoDTO> partialUpdateCampoActuacaoDissertacao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CampoActuacaoDissertacao partially : {}, {}", id, campoActuacaoDissertacaoDTO);
        if (campoActuacaoDissertacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, campoActuacaoDissertacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!campoActuacaoDissertacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CampoActuacaoDissertacaoDTO> result = campoActuacaoDissertacaoService.partialUpdate(campoActuacaoDissertacaoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campoActuacaoDissertacaoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /campo-actuacao-dissertacaos} : get all the campoActuacaoDissertacaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campoActuacaoDissertacaos in body.
     */
    @GetMapping("/campo-actuacao-dissertacaos")
    public ResponseEntity<List<CampoActuacaoDissertacaoDTO>> getAllCampoActuacaoDissertacaos(
        CampoActuacaoDissertacaoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CampoActuacaoDissertacaos by criteria: {}", criteria);
        Page<CampoActuacaoDissertacaoDTO> page = campoActuacaoDissertacaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /campo-actuacao-dissertacaos/count} : count all the campoActuacaoDissertacaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/campo-actuacao-dissertacaos/count")
    public ResponseEntity<Long> countCampoActuacaoDissertacaos(CampoActuacaoDissertacaoCriteria criteria) {
        log.debug("REST request to count CampoActuacaoDissertacaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(campoActuacaoDissertacaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /campo-actuacao-dissertacaos/:id} : get the "id" campoActuacaoDissertacao.
     *
     * @param id the id of the campoActuacaoDissertacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campoActuacaoDissertacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campo-actuacao-dissertacaos/{id}")
    public ResponseEntity<CampoActuacaoDissertacaoDTO> getCampoActuacaoDissertacao(@PathVariable Long id) {
        log.debug("REST request to get CampoActuacaoDissertacao : {}", id);
        Optional<CampoActuacaoDissertacaoDTO> campoActuacaoDissertacaoDTO = campoActuacaoDissertacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campoActuacaoDissertacaoDTO);
    }

    /**
     * {@code DELETE  /campo-actuacao-dissertacaos/:id} : delete the "id" campoActuacaoDissertacao.
     *
     * @param id the id of the campoActuacaoDissertacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/campo-actuacao-dissertacaos/{id}")
    public ResponseEntity<Void> deleteCampoActuacaoDissertacao(@PathVariable Long id) {
        log.debug("REST request to delete CampoActuacaoDissertacao : {}", id);
        campoActuacaoDissertacaoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
