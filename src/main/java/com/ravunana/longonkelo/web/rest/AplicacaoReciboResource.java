package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.AplicacaoReciboRepository;
import com.ravunana.longonkelo.service.AplicacaoReciboService;
import com.ravunana.longonkelo.service.dto.AplicacaoReciboDTO;
import com.ravunana.longonkelo.service.report.ReciboPagamentoReport;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.AplicacaoRecibo}.
 */
@RestController
@RequestMapping("/api")
public class AplicacaoReciboResource {

    private final Logger log = LoggerFactory.getLogger(AplicacaoReciboResource.class);

    private static final String ENTITY_NAME = "aplicacaoRecibo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AplicacaoReciboService aplicacaoReciboService;

    private final AplicacaoReciboRepository aplicacaoReciboRepository;

    private final ReciboPagamentoReport reciboPagamentoReport;

    public AplicacaoReciboResource(
        AplicacaoReciboService aplicacaoReciboService,
        AplicacaoReciboRepository aplicacaoReciboRepository,
        ReciboPagamentoReport reciboPagamentoReport
    ) {
        this.aplicacaoReciboService = aplicacaoReciboService;
        this.aplicacaoReciboRepository = aplicacaoReciboRepository;
        this.reciboPagamentoReport = reciboPagamentoReport;
    }

    /**
     * {@code POST  /aplicacao-recibos} : Create a new aplicacaoRecibo.
     *
     * @param aplicacaoReciboDTO the aplicacaoReciboDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aplicacaoReciboDTO, or with status {@code 400 (Bad Request)} if the aplicacaoRecibo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aplicacao-recibos")
    public ResponseEntity<AplicacaoReciboDTO> createAplicacaoRecibo(@Valid @RequestBody AplicacaoReciboDTO aplicacaoReciboDTO)
        throws URISyntaxException {
        log.debug("REST request to save AplicacaoRecibo : {}", aplicacaoReciboDTO);
        if (aplicacaoReciboDTO.getId() != null) {
            throw new BadRequestAlertException("A new aplicacaoRecibo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AplicacaoReciboDTO result = aplicacaoReciboService.save(aplicacaoReciboDTO);
        return ResponseEntity
            .created(new URI("/api/aplicacao-recibos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aplicacao-recibos/:id} : Updates an existing aplicacaoRecibo.
     *
     * @param id the id of the aplicacaoReciboDTO to save.
     * @param aplicacaoReciboDTO the aplicacaoReciboDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aplicacaoReciboDTO,
     * or with status {@code 400 (Bad Request)} if the aplicacaoReciboDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aplicacaoReciboDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aplicacao-recibos/{id}")
    public ResponseEntity<AplicacaoReciboDTO> updateAplicacaoRecibo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AplicacaoReciboDTO aplicacaoReciboDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AplicacaoRecibo : {}, {}", id, aplicacaoReciboDTO);
        if (aplicacaoReciboDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aplicacaoReciboDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aplicacaoReciboRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AplicacaoReciboDTO result = aplicacaoReciboService.update(aplicacaoReciboDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aplicacaoReciboDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /aplicacao-recibos/:id} : Partial updates given fields of an existing aplicacaoRecibo, field will ignore if it is null
     *
     * @param id the id of the aplicacaoReciboDTO to save.
     * @param aplicacaoReciboDTO the aplicacaoReciboDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aplicacaoReciboDTO,
     * or with status {@code 400 (Bad Request)} if the aplicacaoReciboDTO is not valid,
     * or with status {@code 404 (Not Found)} if the aplicacaoReciboDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the aplicacaoReciboDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/aplicacao-recibos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AplicacaoReciboDTO> partialUpdateAplicacaoRecibo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AplicacaoReciboDTO aplicacaoReciboDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AplicacaoRecibo partially : {}, {}", id, aplicacaoReciboDTO);
        if (aplicacaoReciboDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aplicacaoReciboDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aplicacaoReciboRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AplicacaoReciboDTO> result = aplicacaoReciboService.partialUpdate(aplicacaoReciboDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aplicacaoReciboDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /aplicacao-recibos} : get all the aplicacaoRecibos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aplicacaoRecibos in body.
     */
    @GetMapping("/aplicacao-recibos")
    public ResponseEntity<List<AplicacaoReciboDTO>> getAllAplicacaoRecibos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of AplicacaoRecibos");
        Page<AplicacaoReciboDTO> page;
        if (eagerload) {
            page = aplicacaoReciboService.findAllWithEagerRelationships(pageable);
        } else {
            page = aplicacaoReciboService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aplicacao-recibos/:id} : get the "id" aplicacaoRecibo.
     *
     * @param id the id of the aplicacaoReciboDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aplicacaoReciboDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aplicacao-recibos/{id}")
    public ResponseEntity<AplicacaoReciboDTO> getAplicacaoRecibo(@PathVariable Long id) {
        log.debug("REST request to get AplicacaoRecibo : {}", id);
        Optional<AplicacaoReciboDTO> aplicacaoReciboDTO = aplicacaoReciboService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aplicacaoReciboDTO);
    }

    /**
     * {@code DELETE  /aplicacao-recibos/:id} : delete the "id" aplicacaoRecibo.
     *
     * @param id the id of the aplicacaoReciboDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aplicacao-recibos/{id}")
    public ResponseEntity<Void> deleteAplicacaoRecibo(@PathVariable Long id) {
        log.debug("REST request to delete AplicacaoRecibo : {}", id);
        aplicacaoReciboService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
