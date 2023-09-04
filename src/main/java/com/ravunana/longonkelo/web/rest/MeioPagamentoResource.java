package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.MeioPagamentoRepository;
import com.ravunana.longonkelo.service.MeioPagamentoQueryService;
import com.ravunana.longonkelo.service.MeioPagamentoService;
import com.ravunana.longonkelo.service.criteria.MeioPagamentoCriteria;
import com.ravunana.longonkelo.service.dto.MeioPagamentoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.MeioPagamento}.
 */
@RestController
@RequestMapping("/api")
public class MeioPagamentoResource {

    private final Logger log = LoggerFactory.getLogger(MeioPagamentoResource.class);

    private static final String ENTITY_NAME = "meioPagamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeioPagamentoService meioPagamentoService;

    private final MeioPagamentoRepository meioPagamentoRepository;

    private final MeioPagamentoQueryService meioPagamentoQueryService;

    public MeioPagamentoResource(
        MeioPagamentoService meioPagamentoService,
        MeioPagamentoRepository meioPagamentoRepository,
        MeioPagamentoQueryService meioPagamentoQueryService
    ) {
        this.meioPagamentoService = meioPagamentoService;
        this.meioPagamentoRepository = meioPagamentoRepository;
        this.meioPagamentoQueryService = meioPagamentoQueryService;
    }

    /**
     * {@code POST  /meio-pagamentos} : Create a new meioPagamento.
     *
     * @param meioPagamentoDTO the meioPagamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meioPagamentoDTO, or with status {@code 400 (Bad Request)} if the meioPagamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meio-pagamentos")
    public ResponseEntity<MeioPagamentoDTO> createMeioPagamento(@Valid @RequestBody MeioPagamentoDTO meioPagamentoDTO)
        throws URISyntaxException {
        log.debug("REST request to save MeioPagamento : {}", meioPagamentoDTO);
        if (meioPagamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new meioPagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeioPagamentoDTO result = meioPagamentoService.save(meioPagamentoDTO);
        return ResponseEntity
            .created(new URI("/api/meio-pagamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meio-pagamentos/:id} : Updates an existing meioPagamento.
     *
     * @param id the id of the meioPagamentoDTO to save.
     * @param meioPagamentoDTO the meioPagamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meioPagamentoDTO,
     * or with status {@code 400 (Bad Request)} if the meioPagamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meioPagamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meio-pagamentos/{id}")
    public ResponseEntity<MeioPagamentoDTO> updateMeioPagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MeioPagamentoDTO meioPagamentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MeioPagamento : {}, {}", id, meioPagamentoDTO);
        if (meioPagamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, meioPagamentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!meioPagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MeioPagamentoDTO result = meioPagamentoService.update(meioPagamentoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meioPagamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /meio-pagamentos/:id} : Partial updates given fields of an existing meioPagamento, field will ignore if it is null
     *
     * @param id the id of the meioPagamentoDTO to save.
     * @param meioPagamentoDTO the meioPagamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meioPagamentoDTO,
     * or with status {@code 400 (Bad Request)} if the meioPagamentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the meioPagamentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the meioPagamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/meio-pagamentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MeioPagamentoDTO> partialUpdateMeioPagamento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MeioPagamentoDTO meioPagamentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MeioPagamento partially : {}, {}", id, meioPagamentoDTO);
        if (meioPagamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, meioPagamentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!meioPagamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MeioPagamentoDTO> result = meioPagamentoService.partialUpdate(meioPagamentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meioPagamentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /meio-pagamentos} : get all the meioPagamentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meioPagamentos in body.
     */
    @GetMapping("/meio-pagamentos")
    public ResponseEntity<List<MeioPagamentoDTO>> getAllMeioPagamentos(
        MeioPagamentoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MeioPagamentos by criteria: {}", criteria);
        Page<MeioPagamentoDTO> page = meioPagamentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /meio-pagamentos/count} : count all the meioPagamentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/meio-pagamentos/count")
    public ResponseEntity<Long> countMeioPagamentos(MeioPagamentoCriteria criteria) {
        log.debug("REST request to count MeioPagamentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(meioPagamentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /meio-pagamentos/:id} : get the "id" meioPagamento.
     *
     * @param id the id of the meioPagamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meioPagamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meio-pagamentos/{id}")
    public ResponseEntity<MeioPagamentoDTO> getMeioPagamento(@PathVariable Long id) {
        log.debug("REST request to get MeioPagamento : {}", id);
        Optional<MeioPagamentoDTO> meioPagamentoDTO = meioPagamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(meioPagamentoDTO);
    }

    /**
     * {@code DELETE  /meio-pagamentos/:id} : delete the "id" meioPagamento.
     *
     * @param id the id of the meioPagamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meio-pagamentos/{id}")
    public ResponseEntity<Void> deleteMeioPagamento(@PathVariable Long id) {
        log.debug("REST request to delete MeioPagamento : {}", id);
        meioPagamentoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
