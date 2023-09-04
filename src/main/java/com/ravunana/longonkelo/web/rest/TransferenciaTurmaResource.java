package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.TransferenciaTurmaRepository;
import com.ravunana.longonkelo.service.TransferenciaTurmaQueryService;
import com.ravunana.longonkelo.service.TransferenciaTurmaService;
import com.ravunana.longonkelo.service.criteria.TransferenciaTurmaCriteria;
import com.ravunana.longonkelo.service.dto.TransferenciaTurmaDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.TransferenciaTurma}.
 */
@RestController
@RequestMapping("/api")
public class TransferenciaTurmaResource {

    private final Logger log = LoggerFactory.getLogger(TransferenciaTurmaResource.class);

    private static final String ENTITY_NAME = "transferenciaTurma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransferenciaTurmaService transferenciaTurmaService;

    private final TransferenciaTurmaRepository transferenciaTurmaRepository;

    private final TransferenciaTurmaQueryService transferenciaTurmaQueryService;

    public TransferenciaTurmaResource(
        TransferenciaTurmaService transferenciaTurmaService,
        TransferenciaTurmaRepository transferenciaTurmaRepository,
        TransferenciaTurmaQueryService transferenciaTurmaQueryService
    ) {
        this.transferenciaTurmaService = transferenciaTurmaService;
        this.transferenciaTurmaRepository = transferenciaTurmaRepository;
        this.transferenciaTurmaQueryService = transferenciaTurmaQueryService;
    }

    /**
     * {@code POST  /transferencia-turmas} : Create a new transferenciaTurma.
     *
     * @param transferenciaTurmaDTO the transferenciaTurmaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transferenciaTurmaDTO, or with status {@code 400 (Bad Request)} if the transferenciaTurma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transferencia-turmas")
    public ResponseEntity<TransferenciaTurmaDTO> createTransferenciaTurma(@Valid @RequestBody TransferenciaTurmaDTO transferenciaTurmaDTO)
        throws URISyntaxException {
        log.debug("REST request to save TransferenciaTurma : {}", transferenciaTurmaDTO);
        if (transferenciaTurmaDTO.getId() != null) {
            throw new BadRequestAlertException("A new transferenciaTurma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransferenciaTurmaDTO result = transferenciaTurmaService.save(transferenciaTurmaDTO);
        return ResponseEntity
            .created(new URI("/api/transferencia-turmas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transferencia-turmas/:id} : Updates an existing transferenciaTurma.
     *
     * @param id the id of the transferenciaTurmaDTO to save.
     * @param transferenciaTurmaDTO the transferenciaTurmaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferenciaTurmaDTO,
     * or with status {@code 400 (Bad Request)} if the transferenciaTurmaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transferenciaTurmaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transferencia-turmas/{id}")
    public ResponseEntity<TransferenciaTurmaDTO> updateTransferenciaTurma(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TransferenciaTurmaDTO transferenciaTurmaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TransferenciaTurma : {}, {}", id, transferenciaTurmaDTO);
        if (transferenciaTurmaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferenciaTurmaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferenciaTurmaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransferenciaTurmaDTO result = transferenciaTurmaService.update(transferenciaTurmaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferenciaTurmaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transferencia-turmas/:id} : Partial updates given fields of an existing transferenciaTurma, field will ignore if it is null
     *
     * @param id the id of the transferenciaTurmaDTO to save.
     * @param transferenciaTurmaDTO the transferenciaTurmaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferenciaTurmaDTO,
     * or with status {@code 400 (Bad Request)} if the transferenciaTurmaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transferenciaTurmaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transferenciaTurmaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transferencia-turmas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransferenciaTurmaDTO> partialUpdateTransferenciaTurma(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TransferenciaTurmaDTO transferenciaTurmaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransferenciaTurma partially : {}, {}", id, transferenciaTurmaDTO);
        if (transferenciaTurmaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferenciaTurmaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferenciaTurmaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransferenciaTurmaDTO> result = transferenciaTurmaService.partialUpdate(transferenciaTurmaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferenciaTurmaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transferencia-turmas} : get all the transferenciaTurmas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transferenciaTurmas in body.
     */
    @GetMapping("/transferencia-turmas")
    public ResponseEntity<List<TransferenciaTurmaDTO>> getAllTransferenciaTurmas(
        TransferenciaTurmaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TransferenciaTurmas by criteria: {}", criteria);
        Page<TransferenciaTurmaDTO> page = transferenciaTurmaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transferencia-turmas/count} : count all the transferenciaTurmas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transferencia-turmas/count")
    public ResponseEntity<Long> countTransferenciaTurmas(TransferenciaTurmaCriteria criteria) {
        log.debug("REST request to count TransferenciaTurmas by criteria: {}", criteria);
        return ResponseEntity.ok().body(transferenciaTurmaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transferencia-turmas/:id} : get the "id" transferenciaTurma.
     *
     * @param id the id of the transferenciaTurmaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transferenciaTurmaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transferencia-turmas/{id}")
    public ResponseEntity<TransferenciaTurmaDTO> getTransferenciaTurma(@PathVariable Long id) {
        log.debug("REST request to get TransferenciaTurma : {}", id);
        Optional<TransferenciaTurmaDTO> transferenciaTurmaDTO = transferenciaTurmaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transferenciaTurmaDTO);
    }

    /**
     * {@code DELETE  /transferencia-turmas/:id} : delete the "id" transferenciaTurma.
     *
     * @param id the id of the transferenciaTurmaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transferencia-turmas/{id}")
    public ResponseEntity<Void> deleteTransferenciaTurma(@PathVariable Long id) {
        log.debug("REST request to delete TransferenciaTurma : {}", id);
        transferenciaTurmaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
