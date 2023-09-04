package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.ResponsavelAreaFormacaoRepository;
import com.ravunana.longonkelo.service.ResponsavelAreaFormacaoQueryService;
import com.ravunana.longonkelo.service.ResponsavelAreaFormacaoService;
import com.ravunana.longonkelo.service.criteria.ResponsavelAreaFormacaoCriteria;
import com.ravunana.longonkelo.service.dto.ResponsavelAreaFormacaoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.ResponsavelAreaFormacao}.
 */
@RestController
@RequestMapping("/api")
public class ResponsavelAreaFormacaoResource {

    private final Logger log = LoggerFactory.getLogger(ResponsavelAreaFormacaoResource.class);

    private static final String ENTITY_NAME = "responsavelAreaFormacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsavelAreaFormacaoService responsavelAreaFormacaoService;

    private final ResponsavelAreaFormacaoRepository responsavelAreaFormacaoRepository;

    private final ResponsavelAreaFormacaoQueryService responsavelAreaFormacaoQueryService;

    public ResponsavelAreaFormacaoResource(
        ResponsavelAreaFormacaoService responsavelAreaFormacaoService,
        ResponsavelAreaFormacaoRepository responsavelAreaFormacaoRepository,
        ResponsavelAreaFormacaoQueryService responsavelAreaFormacaoQueryService
    ) {
        this.responsavelAreaFormacaoService = responsavelAreaFormacaoService;
        this.responsavelAreaFormacaoRepository = responsavelAreaFormacaoRepository;
        this.responsavelAreaFormacaoQueryService = responsavelAreaFormacaoQueryService;
    }

    /**
     * {@code POST  /responsavel-area-formacaos} : Create a new responsavelAreaFormacao.
     *
     * @param responsavelAreaFormacaoDTO the responsavelAreaFormacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responsavelAreaFormacaoDTO, or with status {@code 400 (Bad Request)} if the responsavelAreaFormacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/responsavel-area-formacaos")
    public ResponseEntity<ResponsavelAreaFormacaoDTO> createResponsavelAreaFormacao(
        @Valid @RequestBody ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ResponsavelAreaFormacao : {}", responsavelAreaFormacaoDTO);
        if (responsavelAreaFormacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new responsavelAreaFormacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponsavelAreaFormacaoDTO result = responsavelAreaFormacaoService.save(responsavelAreaFormacaoDTO);
        return ResponseEntity
            .created(new URI("/api/responsavel-area-formacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /responsavel-area-formacaos/:id} : Updates an existing responsavelAreaFormacao.
     *
     * @param id the id of the responsavelAreaFormacaoDTO to save.
     * @param responsavelAreaFormacaoDTO the responsavelAreaFormacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavelAreaFormacaoDTO,
     * or with status {@code 400 (Bad Request)} if the responsavelAreaFormacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responsavelAreaFormacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/responsavel-area-formacaos/{id}")
    public ResponseEntity<ResponsavelAreaFormacaoDTO> updateResponsavelAreaFormacao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ResponsavelAreaFormacao : {}, {}", id, responsavelAreaFormacaoDTO);
        if (responsavelAreaFormacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsavelAreaFormacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsavelAreaFormacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResponsavelAreaFormacaoDTO result = responsavelAreaFormacaoService.update(responsavelAreaFormacaoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsavelAreaFormacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /responsavel-area-formacaos/:id} : Partial updates given fields of an existing responsavelAreaFormacao, field will ignore if it is null
     *
     * @param id the id of the responsavelAreaFormacaoDTO to save.
     * @param responsavelAreaFormacaoDTO the responsavelAreaFormacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavelAreaFormacaoDTO,
     * or with status {@code 400 (Bad Request)} if the responsavelAreaFormacaoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the responsavelAreaFormacaoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the responsavelAreaFormacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/responsavel-area-formacaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResponsavelAreaFormacaoDTO> partialUpdateResponsavelAreaFormacao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResponsavelAreaFormacao partially : {}, {}", id, responsavelAreaFormacaoDTO);
        if (responsavelAreaFormacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsavelAreaFormacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsavelAreaFormacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResponsavelAreaFormacaoDTO> result = responsavelAreaFormacaoService.partialUpdate(responsavelAreaFormacaoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsavelAreaFormacaoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /responsavel-area-formacaos} : get all the responsavelAreaFormacaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responsavelAreaFormacaos in body.
     */
    @GetMapping("/responsavel-area-formacaos")
    public ResponseEntity<List<ResponsavelAreaFormacaoDTO>> getAllResponsavelAreaFormacaos(
        ResponsavelAreaFormacaoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ResponsavelAreaFormacaos by criteria: {}", criteria);
        Page<ResponsavelAreaFormacaoDTO> page = responsavelAreaFormacaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /responsavel-area-formacaos/count} : count all the responsavelAreaFormacaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/responsavel-area-formacaos/count")
    public ResponseEntity<Long> countResponsavelAreaFormacaos(ResponsavelAreaFormacaoCriteria criteria) {
        log.debug("REST request to count ResponsavelAreaFormacaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(responsavelAreaFormacaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /responsavel-area-formacaos/:id} : get the "id" responsavelAreaFormacao.
     *
     * @param id the id of the responsavelAreaFormacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responsavelAreaFormacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/responsavel-area-formacaos/{id}")
    public ResponseEntity<ResponsavelAreaFormacaoDTO> getResponsavelAreaFormacao(@PathVariable Long id) {
        log.debug("REST request to get ResponsavelAreaFormacao : {}", id);
        Optional<ResponsavelAreaFormacaoDTO> responsavelAreaFormacaoDTO = responsavelAreaFormacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsavelAreaFormacaoDTO);
    }

    /**
     * {@code DELETE  /responsavel-area-formacaos/:id} : delete the "id" responsavelAreaFormacao.
     *
     * @param id the id of the responsavelAreaFormacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/responsavel-area-formacaos/{id}")
    public ResponseEntity<Void> deleteResponsavelAreaFormacao(@PathVariable Long id) {
        log.debug("REST request to delete ResponsavelAreaFormacao : {}", id);
        responsavelAreaFormacaoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
