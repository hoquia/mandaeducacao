package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.EncarregadoEducacaoRepository;
import com.ravunana.longonkelo.service.EncarregadoEducacaoQueryService;
import com.ravunana.longonkelo.service.EncarregadoEducacaoService;
import com.ravunana.longonkelo.service.criteria.EncarregadoEducacaoCriteria;
import com.ravunana.longonkelo.service.dto.EncarregadoEducacaoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.EncarregadoEducacao}.
 */
@RestController
@RequestMapping("/api")
public class EncarregadoEducacaoResource {

    private final Logger log = LoggerFactory.getLogger(EncarregadoEducacaoResource.class);

    private static final String ENTITY_NAME = "encarregadoEducacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EncarregadoEducacaoService encarregadoEducacaoService;

    private final EncarregadoEducacaoRepository encarregadoEducacaoRepository;

    private final EncarregadoEducacaoQueryService encarregadoEducacaoQueryService;

    public EncarregadoEducacaoResource(
        EncarregadoEducacaoService encarregadoEducacaoService,
        EncarregadoEducacaoRepository encarregadoEducacaoRepository,
        EncarregadoEducacaoQueryService encarregadoEducacaoQueryService
    ) {
        this.encarregadoEducacaoService = encarregadoEducacaoService;
        this.encarregadoEducacaoRepository = encarregadoEducacaoRepository;
        this.encarregadoEducacaoQueryService = encarregadoEducacaoQueryService;
    }

    /**
     * {@code POST  /encarregado-educacaos} : Create a new encarregadoEducacao.
     *
     * @param encarregadoEducacaoDTO the encarregadoEducacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new encarregadoEducacaoDTO, or with status {@code 400 (Bad Request)} if the encarregadoEducacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/encarregado-educacaos")
    public ResponseEntity<EncarregadoEducacaoDTO> createEncarregadoEducacao(
        @Valid @RequestBody EncarregadoEducacaoDTO encarregadoEducacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save EncarregadoEducacao : {}", encarregadoEducacaoDTO);
        if (encarregadoEducacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new encarregadoEducacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EncarregadoEducacaoDTO result = encarregadoEducacaoService.save(encarregadoEducacaoDTO);
        return ResponseEntity
            .created(new URI("/api/encarregado-educacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /encarregado-educacaos/:id} : Updates an existing encarregadoEducacao.
     *
     * @param id the id of the encarregadoEducacaoDTO to save.
     * @param encarregadoEducacaoDTO the encarregadoEducacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated encarregadoEducacaoDTO,
     * or with status {@code 400 (Bad Request)} if the encarregadoEducacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the encarregadoEducacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/encarregado-educacaos/{id}")
    public ResponseEntity<EncarregadoEducacaoDTO> updateEncarregadoEducacao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EncarregadoEducacaoDTO encarregadoEducacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EncarregadoEducacao : {}, {}", id, encarregadoEducacaoDTO);
        if (encarregadoEducacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, encarregadoEducacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!encarregadoEducacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EncarregadoEducacaoDTO result = encarregadoEducacaoService.update(encarregadoEducacaoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, encarregadoEducacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /encarregado-educacaos/:id} : Partial updates given fields of an existing encarregadoEducacao, field will ignore if it is null
     *
     * @param id the id of the encarregadoEducacaoDTO to save.
     * @param encarregadoEducacaoDTO the encarregadoEducacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated encarregadoEducacaoDTO,
     * or with status {@code 400 (Bad Request)} if the encarregadoEducacaoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the encarregadoEducacaoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the encarregadoEducacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/encarregado-educacaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EncarregadoEducacaoDTO> partialUpdateEncarregadoEducacao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EncarregadoEducacaoDTO encarregadoEducacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EncarregadoEducacao partially : {}, {}", id, encarregadoEducacaoDTO);
        if (encarregadoEducacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, encarregadoEducacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!encarregadoEducacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EncarregadoEducacaoDTO> result = encarregadoEducacaoService.partialUpdate(encarregadoEducacaoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, encarregadoEducacaoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /encarregado-educacaos} : get all the encarregadoEducacaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of encarregadoEducacaos in body.
     */
    @GetMapping("/encarregado-educacaos")
    public ResponseEntity<List<EncarregadoEducacaoDTO>> getAllEncarregadoEducacaos(
        EncarregadoEducacaoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EncarregadoEducacaos by criteria: {}", criteria);
        Page<EncarregadoEducacaoDTO> page = encarregadoEducacaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /encarregado-educacaos/count} : count all the encarregadoEducacaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/encarregado-educacaos/count")
    public ResponseEntity<Long> countEncarregadoEducacaos(EncarregadoEducacaoCriteria criteria) {
        log.debug("REST request to count EncarregadoEducacaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(encarregadoEducacaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /encarregado-educacaos/:id} : get the "id" encarregadoEducacao.
     *
     * @param id the id of the encarregadoEducacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the encarregadoEducacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/encarregado-educacaos/{id}")
    public ResponseEntity<EncarregadoEducacaoDTO> getEncarregadoEducacao(@PathVariable Long id) {
        log.debug("REST request to get EncarregadoEducacao : {}", id);
        Optional<EncarregadoEducacaoDTO> encarregadoEducacaoDTO = encarregadoEducacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(encarregadoEducacaoDTO);
    }

    /**
     * {@code DELETE  /encarregado-educacaos/:id} : delete the "id" encarregadoEducacao.
     *
     * @param id the id of the encarregadoEducacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/encarregado-educacaos/{id}")
    public ResponseEntity<Void> deleteEncarregadoEducacao(@PathVariable Long id) {
        log.debug("REST request to delete EncarregadoEducacao : {}", id);
        encarregadoEducacaoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
