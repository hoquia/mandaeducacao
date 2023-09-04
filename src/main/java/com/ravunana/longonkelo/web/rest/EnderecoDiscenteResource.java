package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.EnderecoDiscenteRepository;
import com.ravunana.longonkelo.service.EnderecoDiscenteQueryService;
import com.ravunana.longonkelo.service.EnderecoDiscenteService;
import com.ravunana.longonkelo.service.criteria.EnderecoDiscenteCriteria;
import com.ravunana.longonkelo.service.dto.EnderecoDiscenteDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.EnderecoDiscente}.
 */
@RestController
@RequestMapping("/api")
public class EnderecoDiscenteResource {

    private final Logger log = LoggerFactory.getLogger(EnderecoDiscenteResource.class);

    private static final String ENTITY_NAME = "enderecoDiscente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnderecoDiscenteService enderecoDiscenteService;

    private final EnderecoDiscenteRepository enderecoDiscenteRepository;

    private final EnderecoDiscenteQueryService enderecoDiscenteQueryService;

    public EnderecoDiscenteResource(
        EnderecoDiscenteService enderecoDiscenteService,
        EnderecoDiscenteRepository enderecoDiscenteRepository,
        EnderecoDiscenteQueryService enderecoDiscenteQueryService
    ) {
        this.enderecoDiscenteService = enderecoDiscenteService;
        this.enderecoDiscenteRepository = enderecoDiscenteRepository;
        this.enderecoDiscenteQueryService = enderecoDiscenteQueryService;
    }

    /**
     * {@code POST  /endereco-discentes} : Create a new enderecoDiscente.
     *
     * @param enderecoDiscenteDTO the enderecoDiscenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enderecoDiscenteDTO, or with status {@code 400 (Bad Request)} if the enderecoDiscente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/endereco-discentes")
    public ResponseEntity<EnderecoDiscenteDTO> createEnderecoDiscente(@Valid @RequestBody EnderecoDiscenteDTO enderecoDiscenteDTO)
        throws URISyntaxException {
        log.debug("REST request to save EnderecoDiscente : {}", enderecoDiscenteDTO);
        if (enderecoDiscenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new enderecoDiscente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnderecoDiscenteDTO result = enderecoDiscenteService.save(enderecoDiscenteDTO);
        return ResponseEntity
            .created(new URI("/api/endereco-discentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /endereco-discentes/:id} : Updates an existing enderecoDiscente.
     *
     * @param id the id of the enderecoDiscenteDTO to save.
     * @param enderecoDiscenteDTO the enderecoDiscenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enderecoDiscenteDTO,
     * or with status {@code 400 (Bad Request)} if the enderecoDiscenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enderecoDiscenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/endereco-discentes/{id}")
    public ResponseEntity<EnderecoDiscenteDTO> updateEnderecoDiscente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EnderecoDiscenteDTO enderecoDiscenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EnderecoDiscente : {}, {}", id, enderecoDiscenteDTO);
        if (enderecoDiscenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enderecoDiscenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enderecoDiscenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EnderecoDiscenteDTO result = enderecoDiscenteService.update(enderecoDiscenteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enderecoDiscenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /endereco-discentes/:id} : Partial updates given fields of an existing enderecoDiscente, field will ignore if it is null
     *
     * @param id the id of the enderecoDiscenteDTO to save.
     * @param enderecoDiscenteDTO the enderecoDiscenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enderecoDiscenteDTO,
     * or with status {@code 400 (Bad Request)} if the enderecoDiscenteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the enderecoDiscenteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the enderecoDiscenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/endereco-discentes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EnderecoDiscenteDTO> partialUpdateEnderecoDiscente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EnderecoDiscenteDTO enderecoDiscenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EnderecoDiscente partially : {}, {}", id, enderecoDiscenteDTO);
        if (enderecoDiscenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enderecoDiscenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enderecoDiscenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EnderecoDiscenteDTO> result = enderecoDiscenteService.partialUpdate(enderecoDiscenteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enderecoDiscenteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /endereco-discentes} : get all the enderecoDiscentes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enderecoDiscentes in body.
     */
    @GetMapping("/endereco-discentes")
    public ResponseEntity<List<EnderecoDiscenteDTO>> getAllEnderecoDiscentes(
        EnderecoDiscenteCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EnderecoDiscentes by criteria: {}", criteria);
        Page<EnderecoDiscenteDTO> page = enderecoDiscenteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /endereco-discentes/count} : count all the enderecoDiscentes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/endereco-discentes/count")
    public ResponseEntity<Long> countEnderecoDiscentes(EnderecoDiscenteCriteria criteria) {
        log.debug("REST request to count EnderecoDiscentes by criteria: {}", criteria);
        return ResponseEntity.ok().body(enderecoDiscenteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /endereco-discentes/:id} : get the "id" enderecoDiscente.
     *
     * @param id the id of the enderecoDiscenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enderecoDiscenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/endereco-discentes/{id}")
    public ResponseEntity<EnderecoDiscenteDTO> getEnderecoDiscente(@PathVariable Long id) {
        log.debug("REST request to get EnderecoDiscente : {}", id);
        Optional<EnderecoDiscenteDTO> enderecoDiscenteDTO = enderecoDiscenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enderecoDiscenteDTO);
    }

    /**
     * {@code DELETE  /endereco-discentes/:id} : delete the "id" enderecoDiscente.
     *
     * @param id the id of the enderecoDiscenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/endereco-discentes/{id}")
    public ResponseEntity<Void> deleteEnderecoDiscente(@PathVariable Long id) {
        log.debug("REST request to delete EnderecoDiscente : {}", id);
        enderecoDiscenteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
