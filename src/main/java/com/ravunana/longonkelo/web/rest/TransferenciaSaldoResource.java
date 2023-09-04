package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.TransferenciaSaldoRepository;
import com.ravunana.longonkelo.service.TransferenciaSaldoQueryService;
import com.ravunana.longonkelo.service.TransferenciaSaldoService;
import com.ravunana.longonkelo.service.criteria.TransferenciaSaldoCriteria;
import com.ravunana.longonkelo.service.dto.TransferenciaSaldoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.TransferenciaSaldo}.
 */
@RestController
@RequestMapping("/api")
public class TransferenciaSaldoResource {

    private final Logger log = LoggerFactory.getLogger(TransferenciaSaldoResource.class);

    private static final String ENTITY_NAME = "transferenciaSaldo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransferenciaSaldoService transferenciaSaldoService;

    private final TransferenciaSaldoRepository transferenciaSaldoRepository;

    private final TransferenciaSaldoQueryService transferenciaSaldoQueryService;

    public TransferenciaSaldoResource(
        TransferenciaSaldoService transferenciaSaldoService,
        TransferenciaSaldoRepository transferenciaSaldoRepository,
        TransferenciaSaldoQueryService transferenciaSaldoQueryService
    ) {
        this.transferenciaSaldoService = transferenciaSaldoService;
        this.transferenciaSaldoRepository = transferenciaSaldoRepository;
        this.transferenciaSaldoQueryService = transferenciaSaldoQueryService;
    }

    /**
     * {@code POST  /transferencia-saldos} : Create a new transferenciaSaldo.
     *
     * @param transferenciaSaldoDTO the transferenciaSaldoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transferenciaSaldoDTO, or with status {@code 400 (Bad Request)} if the transferenciaSaldo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transferencia-saldos")
    public ResponseEntity<TransferenciaSaldoDTO> createTransferenciaSaldo(@Valid @RequestBody TransferenciaSaldoDTO transferenciaSaldoDTO)
        throws URISyntaxException {
        log.debug("REST request to save TransferenciaSaldo : {}", transferenciaSaldoDTO);
        if (transferenciaSaldoDTO.getId() != null) {
            throw new BadRequestAlertException("A new transferenciaSaldo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransferenciaSaldoDTO result = transferenciaSaldoService.save(transferenciaSaldoDTO);
        return ResponseEntity
            .created(new URI("/api/transferencia-saldos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transferencia-saldos/:id} : Updates an existing transferenciaSaldo.
     *
     * @param id the id of the transferenciaSaldoDTO to save.
     * @param transferenciaSaldoDTO the transferenciaSaldoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferenciaSaldoDTO,
     * or with status {@code 400 (Bad Request)} if the transferenciaSaldoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transferenciaSaldoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transferencia-saldos/{id}")
    public ResponseEntity<TransferenciaSaldoDTO> updateTransferenciaSaldo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TransferenciaSaldoDTO transferenciaSaldoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TransferenciaSaldo : {}, {}", id, transferenciaSaldoDTO);
        if (transferenciaSaldoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferenciaSaldoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferenciaSaldoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransferenciaSaldoDTO result = transferenciaSaldoService.update(transferenciaSaldoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferenciaSaldoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transferencia-saldos/:id} : Partial updates given fields of an existing transferenciaSaldo, field will ignore if it is null
     *
     * @param id the id of the transferenciaSaldoDTO to save.
     * @param transferenciaSaldoDTO the transferenciaSaldoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferenciaSaldoDTO,
     * or with status {@code 400 (Bad Request)} if the transferenciaSaldoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transferenciaSaldoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transferenciaSaldoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transferencia-saldos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransferenciaSaldoDTO> partialUpdateTransferenciaSaldo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TransferenciaSaldoDTO transferenciaSaldoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransferenciaSaldo partially : {}, {}", id, transferenciaSaldoDTO);
        if (transferenciaSaldoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferenciaSaldoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferenciaSaldoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransferenciaSaldoDTO> result = transferenciaSaldoService.partialUpdate(transferenciaSaldoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferenciaSaldoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transferencia-saldos} : get all the transferenciaSaldos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transferenciaSaldos in body.
     */
    @GetMapping("/transferencia-saldos")
    public ResponseEntity<List<TransferenciaSaldoDTO>> getAllTransferenciaSaldos(
        TransferenciaSaldoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TransferenciaSaldos by criteria: {}", criteria);
        Page<TransferenciaSaldoDTO> page = transferenciaSaldoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transferencia-saldos/count} : count all the transferenciaSaldos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transferencia-saldos/count")
    public ResponseEntity<Long> countTransferenciaSaldos(TransferenciaSaldoCriteria criteria) {
        log.debug("REST request to count TransferenciaSaldos by criteria: {}", criteria);
        return ResponseEntity.ok().body(transferenciaSaldoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transferencia-saldos/:id} : get the "id" transferenciaSaldo.
     *
     * @param id the id of the transferenciaSaldoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transferenciaSaldoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transferencia-saldos/{id}")
    public ResponseEntity<TransferenciaSaldoDTO> getTransferenciaSaldo(@PathVariable Long id) {
        log.debug("REST request to get TransferenciaSaldo : {}", id);
        Optional<TransferenciaSaldoDTO> transferenciaSaldoDTO = transferenciaSaldoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transferenciaSaldoDTO);
    }

    /**
     * {@code DELETE  /transferencia-saldos/:id} : delete the "id" transferenciaSaldo.
     *
     * @param id the id of the transferenciaSaldoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transferencia-saldos/{id}")
    public ResponseEntity<Void> deleteTransferenciaSaldo(@PathVariable Long id) {
        log.debug("REST request to delete TransferenciaSaldo : {}", id);
        transferenciaSaldoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
