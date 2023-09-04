package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.AreaFormacaoRepository;
import com.ravunana.longonkelo.service.AreaFormacaoService;
import com.ravunana.longonkelo.service.dto.AreaFormacaoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.AreaFormacao}.
 */
@RestController
@RequestMapping("/api")
public class AreaFormacaoResource {

    private final Logger log = LoggerFactory.getLogger(AreaFormacaoResource.class);

    private static final String ENTITY_NAME = "areaFormacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AreaFormacaoService areaFormacaoService;

    private final AreaFormacaoRepository areaFormacaoRepository;

    public AreaFormacaoResource(AreaFormacaoService areaFormacaoService, AreaFormacaoRepository areaFormacaoRepository) {
        this.areaFormacaoService = areaFormacaoService;
        this.areaFormacaoRepository = areaFormacaoRepository;
    }

    /**
     * {@code POST  /area-formacaos} : Create a new areaFormacao.
     *
     * @param areaFormacaoDTO the areaFormacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new areaFormacaoDTO, or with status {@code 400 (Bad Request)} if the areaFormacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/area-formacaos")
    public ResponseEntity<AreaFormacaoDTO> createAreaFormacao(@Valid @RequestBody AreaFormacaoDTO areaFormacaoDTO)
        throws URISyntaxException {
        log.debug("REST request to save AreaFormacao : {}", areaFormacaoDTO);
        if (areaFormacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new areaFormacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AreaFormacaoDTO result = areaFormacaoService.save(areaFormacaoDTO);
        return ResponseEntity
            .created(new URI("/api/area-formacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /area-formacaos/:id} : Updates an existing areaFormacao.
     *
     * @param id the id of the areaFormacaoDTO to save.
     * @param areaFormacaoDTO the areaFormacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaFormacaoDTO,
     * or with status {@code 400 (Bad Request)} if the areaFormacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the areaFormacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/area-formacaos/{id}")
    public ResponseEntity<AreaFormacaoDTO> updateAreaFormacao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AreaFormacaoDTO areaFormacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AreaFormacao : {}, {}", id, areaFormacaoDTO);
        if (areaFormacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaFormacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaFormacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AreaFormacaoDTO result = areaFormacaoService.update(areaFormacaoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaFormacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /area-formacaos/:id} : Partial updates given fields of an existing areaFormacao, field will ignore if it is null
     *
     * @param id the id of the areaFormacaoDTO to save.
     * @param areaFormacaoDTO the areaFormacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaFormacaoDTO,
     * or with status {@code 400 (Bad Request)} if the areaFormacaoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the areaFormacaoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the areaFormacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/area-formacaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AreaFormacaoDTO> partialUpdateAreaFormacao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AreaFormacaoDTO areaFormacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AreaFormacao partially : {}, {}", id, areaFormacaoDTO);
        if (areaFormacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaFormacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaFormacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AreaFormacaoDTO> result = areaFormacaoService.partialUpdate(areaFormacaoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaFormacaoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /area-formacaos} : get all the areaFormacaos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of areaFormacaos in body.
     */
    @GetMapping("/area-formacaos")
    public ResponseEntity<List<AreaFormacaoDTO>> getAllAreaFormacaos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of AreaFormacaos");
        Page<AreaFormacaoDTO> page;
        if (eagerload) {
            page = areaFormacaoService.findAllWithEagerRelationships(pageable);
        } else {
            page = areaFormacaoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /area-formacaos/:id} : get the "id" areaFormacao.
     *
     * @param id the id of the areaFormacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the areaFormacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/area-formacaos/{id}")
    public ResponseEntity<AreaFormacaoDTO> getAreaFormacao(@PathVariable Long id) {
        log.debug("REST request to get AreaFormacao : {}", id);
        Optional<AreaFormacaoDTO> areaFormacaoDTO = areaFormacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(areaFormacaoDTO);
    }

    /**
     * {@code DELETE  /area-formacaos/:id} : delete the "id" areaFormacao.
     *
     * @param id the id of the areaFormacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/area-formacaos/{id}")
    public ResponseEntity<Void> deleteAreaFormacao(@PathVariable Long id) {
        log.debug("REST request to delete AreaFormacao : {}", id);
        areaFormacaoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
