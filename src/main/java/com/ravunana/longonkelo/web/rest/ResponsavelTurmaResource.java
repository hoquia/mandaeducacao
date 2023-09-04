package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.ResponsavelTurmaRepository;
import com.ravunana.longonkelo.service.ResponsavelTurmaQueryService;
import com.ravunana.longonkelo.service.ResponsavelTurmaService;
import com.ravunana.longonkelo.service.criteria.ResponsavelTurmaCriteria;
import com.ravunana.longonkelo.service.dto.ResponsavelTurmaDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.ResponsavelTurma}.
 */
@RestController
@RequestMapping("/api")
public class ResponsavelTurmaResource {

    private final Logger log = LoggerFactory.getLogger(ResponsavelTurmaResource.class);

    private static final String ENTITY_NAME = "responsavelTurma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsavelTurmaService responsavelTurmaService;

    private final ResponsavelTurmaRepository responsavelTurmaRepository;

    private final ResponsavelTurmaQueryService responsavelTurmaQueryService;

    public ResponsavelTurmaResource(
        ResponsavelTurmaService responsavelTurmaService,
        ResponsavelTurmaRepository responsavelTurmaRepository,
        ResponsavelTurmaQueryService responsavelTurmaQueryService
    ) {
        this.responsavelTurmaService = responsavelTurmaService;
        this.responsavelTurmaRepository = responsavelTurmaRepository;
        this.responsavelTurmaQueryService = responsavelTurmaQueryService;
    }

    /**
     * {@code POST  /responsavel-turmas} : Create a new responsavelTurma.
     *
     * @param responsavelTurmaDTO the responsavelTurmaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responsavelTurmaDTO, or with status {@code 400 (Bad Request)} if the responsavelTurma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/responsavel-turmas")
    public ResponseEntity<ResponsavelTurmaDTO> createResponsavelTurma(@Valid @RequestBody ResponsavelTurmaDTO responsavelTurmaDTO)
        throws URISyntaxException {
        log.debug("REST request to save ResponsavelTurma : {}", responsavelTurmaDTO);
        if (responsavelTurmaDTO.getId() != null) {
            throw new BadRequestAlertException("A new responsavelTurma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponsavelTurmaDTO result = responsavelTurmaService.save(responsavelTurmaDTO);
        return ResponseEntity
            .created(new URI("/api/responsavel-turmas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /responsavel-turmas/:id} : Updates an existing responsavelTurma.
     *
     * @param id the id of the responsavelTurmaDTO to save.
     * @param responsavelTurmaDTO the responsavelTurmaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavelTurmaDTO,
     * or with status {@code 400 (Bad Request)} if the responsavelTurmaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responsavelTurmaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/responsavel-turmas/{id}")
    public ResponseEntity<ResponsavelTurmaDTO> updateResponsavelTurma(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResponsavelTurmaDTO responsavelTurmaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ResponsavelTurma : {}, {}", id, responsavelTurmaDTO);
        if (responsavelTurmaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsavelTurmaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsavelTurmaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResponsavelTurmaDTO result = responsavelTurmaService.update(responsavelTurmaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsavelTurmaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /responsavel-turmas/:id} : Partial updates given fields of an existing responsavelTurma, field will ignore if it is null
     *
     * @param id the id of the responsavelTurmaDTO to save.
     * @param responsavelTurmaDTO the responsavelTurmaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavelTurmaDTO,
     * or with status {@code 400 (Bad Request)} if the responsavelTurmaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the responsavelTurmaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the responsavelTurmaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/responsavel-turmas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResponsavelTurmaDTO> partialUpdateResponsavelTurma(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResponsavelTurmaDTO responsavelTurmaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResponsavelTurma partially : {}, {}", id, responsavelTurmaDTO);
        if (responsavelTurmaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsavelTurmaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsavelTurmaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResponsavelTurmaDTO> result = responsavelTurmaService.partialUpdate(responsavelTurmaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsavelTurmaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /responsavel-turmas} : get all the responsavelTurmas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responsavelTurmas in body.
     */
    @GetMapping("/responsavel-turmas")
    public ResponseEntity<List<ResponsavelTurmaDTO>> getAllResponsavelTurmas(
        ResponsavelTurmaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ResponsavelTurmas by criteria: {}", criteria);
        Page<ResponsavelTurmaDTO> page = responsavelTurmaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /responsavel-turmas/count} : count all the responsavelTurmas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/responsavel-turmas/count")
    public ResponseEntity<Long> countResponsavelTurmas(ResponsavelTurmaCriteria criteria) {
        log.debug("REST request to count ResponsavelTurmas by criteria: {}", criteria);
        return ResponseEntity.ok().body(responsavelTurmaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /responsavel-turmas/:id} : get the "id" responsavelTurma.
     *
     * @param id the id of the responsavelTurmaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responsavelTurmaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/responsavel-turmas/{id}")
    public ResponseEntity<ResponsavelTurmaDTO> getResponsavelTurma(@PathVariable Long id) {
        log.debug("REST request to get ResponsavelTurma : {}", id);
        Optional<ResponsavelTurmaDTO> responsavelTurmaDTO = responsavelTurmaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsavelTurmaDTO);
    }

    /**
     * {@code DELETE  /responsavel-turmas/:id} : delete the "id" responsavelTurma.
     *
     * @param id the id of the responsavelTurmaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/responsavel-turmas/{id}")
    public ResponseEntity<Void> deleteResponsavelTurma(@PathVariable Long id) {
        log.debug("REST request to delete ResponsavelTurma : {}", id);
        responsavelTurmaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
