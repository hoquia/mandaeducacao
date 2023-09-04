package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.ResumoImpostoFacturaRepository;
import com.ravunana.longonkelo.service.ResumoImpostoFacturaService;
import com.ravunana.longonkelo.service.dto.ResumoImpostoFacturaDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ravunana.longonkelo.domain.ResumoImpostoFactura}.
 */
@RestController
@RequestMapping("/api")
public class ResumoImpostoFacturaResource {

    private final Logger log = LoggerFactory.getLogger(ResumoImpostoFacturaResource.class);

    private static final String ENTITY_NAME = "resumoImpostoFactura";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResumoImpostoFacturaService resumoImpostoFacturaService;

    private final ResumoImpostoFacturaRepository resumoImpostoFacturaRepository;

    public ResumoImpostoFacturaResource(
        ResumoImpostoFacturaService resumoImpostoFacturaService,
        ResumoImpostoFacturaRepository resumoImpostoFacturaRepository
    ) {
        this.resumoImpostoFacturaService = resumoImpostoFacturaService;
        this.resumoImpostoFacturaRepository = resumoImpostoFacturaRepository;
    }

    /**
     * {@code POST  /resumo-imposto-facturas} : Create a new resumoImpostoFactura.
     *
     * @param resumoImpostoFacturaDTO the resumoImpostoFacturaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resumoImpostoFacturaDTO, or with status {@code 400 (Bad Request)} if the resumoImpostoFactura has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resumo-imposto-facturas")
    public ResponseEntity<ResumoImpostoFacturaDTO> createResumoImpostoFactura(
        @Valid @RequestBody ResumoImpostoFacturaDTO resumoImpostoFacturaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ResumoImpostoFactura : {}", resumoImpostoFacturaDTO);
        if (resumoImpostoFacturaDTO.getId() != null) {
            throw new BadRequestAlertException("A new resumoImpostoFactura cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResumoImpostoFacturaDTO result = resumoImpostoFacturaService.save(resumoImpostoFacturaDTO);
        return ResponseEntity
            .created(new URI("/api/resumo-imposto-facturas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resumo-imposto-facturas/:id} : Updates an existing resumoImpostoFactura.
     *
     * @param id the id of the resumoImpostoFacturaDTO to save.
     * @param resumoImpostoFacturaDTO the resumoImpostoFacturaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumoImpostoFacturaDTO,
     * or with status {@code 400 (Bad Request)} if the resumoImpostoFacturaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resumoImpostoFacturaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resumo-imposto-facturas/{id}")
    public ResponseEntity<ResumoImpostoFacturaDTO> updateResumoImpostoFactura(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResumoImpostoFacturaDTO resumoImpostoFacturaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ResumoImpostoFactura : {}, {}", id, resumoImpostoFacturaDTO);
        if (resumoImpostoFacturaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumoImpostoFacturaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumoImpostoFacturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResumoImpostoFacturaDTO result = resumoImpostoFacturaService.update(resumoImpostoFacturaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumoImpostoFacturaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /resumo-imposto-facturas/:id} : Partial updates given fields of an existing resumoImpostoFactura, field will ignore if it is null
     *
     * @param id the id of the resumoImpostoFacturaDTO to save.
     * @param resumoImpostoFacturaDTO the resumoImpostoFacturaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resumoImpostoFacturaDTO,
     * or with status {@code 400 (Bad Request)} if the resumoImpostoFacturaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the resumoImpostoFacturaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the resumoImpostoFacturaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resumo-imposto-facturas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResumoImpostoFacturaDTO> partialUpdateResumoImpostoFactura(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResumoImpostoFacturaDTO resumoImpostoFacturaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResumoImpostoFactura partially : {}, {}", id, resumoImpostoFacturaDTO);
        if (resumoImpostoFacturaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resumoImpostoFacturaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resumoImpostoFacturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResumoImpostoFacturaDTO> result = resumoImpostoFacturaService.partialUpdate(resumoImpostoFacturaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resumoImpostoFacturaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /resumo-imposto-facturas} : get all the resumoImpostoFacturas.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resumoImpostoFacturas in body.
     */
    @GetMapping("/resumo-imposto-facturas")
    public ResponseEntity<List<ResumoImpostoFacturaDTO>> getAllResumoImpostoFacturas(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of ResumoImpostoFacturas");
        Page<ResumoImpostoFacturaDTO> page;
        if (eagerload) {
            page = resumoImpostoFacturaService.findAllWithEagerRelationships(pageable);
        } else {
            page = resumoImpostoFacturaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /resumo-imposto-facturas/:id} : get the "id" resumoImpostoFactura.
     *
     * @param id the id of the resumoImpostoFacturaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resumoImpostoFacturaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resumo-imposto-facturas/{id}")
    public ResponseEntity<ResumoImpostoFacturaDTO> getResumoImpostoFactura(@PathVariable Long id) {
        log.debug("REST request to get ResumoImpostoFactura : {}", id);
        Optional<ResumoImpostoFacturaDTO> resumoImpostoFacturaDTO = resumoImpostoFacturaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resumoImpostoFacturaDTO);
    }

    /**
     * {@code DELETE  /resumo-imposto-facturas/:id} : delete the "id" resumoImpostoFactura.
     *
     * @param id the id of the resumoImpostoFacturaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resumo-imposto-facturas/{id}")
    public ResponseEntity<Void> deleteResumoImpostoFactura(@PathVariable Long id) {
        log.debug("REST request to delete ResumoImpostoFactura : {}", id);
        resumoImpostoFacturaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
