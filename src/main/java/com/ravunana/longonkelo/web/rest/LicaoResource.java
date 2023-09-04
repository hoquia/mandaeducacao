package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.LicaoRepository;
import com.ravunana.longonkelo.service.LicaoQueryService;
import com.ravunana.longonkelo.service.LicaoService;
import com.ravunana.longonkelo.service.criteria.LicaoCriteria;
import com.ravunana.longonkelo.service.dto.LicaoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.Licao}.
 */
@RestController
@RequestMapping("/api")
public class LicaoResource {

    private final Logger log = LoggerFactory.getLogger(LicaoResource.class);

    private static final String ENTITY_NAME = "licao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LicaoService licaoService;

    private final LicaoRepository licaoRepository;

    private final LicaoQueryService licaoQueryService;

    public LicaoResource(LicaoService licaoService, LicaoRepository licaoRepository, LicaoQueryService licaoQueryService) {
        this.licaoService = licaoService;
        this.licaoRepository = licaoRepository;
        this.licaoQueryService = licaoQueryService;
    }

    /**
     * {@code POST  /licaos} : Create a new licao.
     *
     * @param licaoDTO the licaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new licaoDTO, or with status {@code 400 (Bad Request)} if the licao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/licaos")
    public ResponseEntity<LicaoDTO> createLicao(@Valid @RequestBody LicaoDTO licaoDTO) throws URISyntaxException {
        log.debug("REST request to save Licao : {}", licaoDTO);
        if (licaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new licao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LicaoDTO result = licaoService.save(licaoDTO);
        return ResponseEntity
            .created(new URI("/api/licaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /licaos/:id} : Updates an existing licao.
     *
     * @param id the id of the licaoDTO to save.
     * @param licaoDTO the licaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licaoDTO,
     * or with status {@code 400 (Bad Request)} if the licaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the licaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/licaos/{id}")
    public ResponseEntity<LicaoDTO> updateLicao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LicaoDTO licaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Licao : {}, {}", id, licaoDTO);
        if (licaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, licaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!licaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LicaoDTO result = licaoService.update(licaoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, licaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /licaos/:id} : Partial updates given fields of an existing licao, field will ignore if it is null
     *
     * @param id the id of the licaoDTO to save.
     * @param licaoDTO the licaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licaoDTO,
     * or with status {@code 400 (Bad Request)} if the licaoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the licaoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the licaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/licaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LicaoDTO> partialUpdateLicao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LicaoDTO licaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Licao partially : {}, {}", id, licaoDTO);
        if (licaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, licaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!licaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LicaoDTO> result = licaoService.partialUpdate(licaoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, licaoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /licaos} : get all the licaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of licaos in body.
     */
    @GetMapping("/licaos")
    public ResponseEntity<List<LicaoDTO>> getAllLicaos(
        LicaoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Licaos by criteria: {}", criteria);
        Page<LicaoDTO> page = licaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /licaos/count} : count all the licaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/licaos/count")
    public ResponseEntity<Long> countLicaos(LicaoCriteria criteria) {
        log.debug("REST request to count Licaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(licaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /licaos/:id} : get the "id" licao.
     *
     * @param id the id of the licaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the licaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/licaos/{id}")
    public ResponseEntity<LicaoDTO> getLicao(@PathVariable Long id) {
        log.debug("REST request to get Licao : {}", id);
        Optional<LicaoDTO> licaoDTO = licaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(licaoDTO);
    }

    /**
     * {@code DELETE  /licaos/:id} : delete the "id" licao.
     *
     * @param id the id of the licaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/licaos/{id}")
    public ResponseEntity<Void> deleteLicao(@PathVariable Long id) {
        log.debug("REST request to delete Licao : {}", id);
        licaoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
