package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.ResponsavelDisciplinaRepository;
import com.ravunana.longonkelo.service.ResponsavelDisciplinaQueryService;
import com.ravunana.longonkelo.service.ResponsavelDisciplinaService;
import com.ravunana.longonkelo.service.criteria.ResponsavelDisciplinaCriteria;
import com.ravunana.longonkelo.service.dto.ResponsavelDisciplinaDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.ResponsavelDisciplina}.
 */
@RestController
@RequestMapping("/api")
public class ResponsavelDisciplinaResource {

    private final Logger log = LoggerFactory.getLogger(ResponsavelDisciplinaResource.class);

    private static final String ENTITY_NAME = "responsavelDisciplina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsavelDisciplinaService responsavelDisciplinaService;

    private final ResponsavelDisciplinaRepository responsavelDisciplinaRepository;

    private final ResponsavelDisciplinaQueryService responsavelDisciplinaQueryService;

    public ResponsavelDisciplinaResource(
        ResponsavelDisciplinaService responsavelDisciplinaService,
        ResponsavelDisciplinaRepository responsavelDisciplinaRepository,
        ResponsavelDisciplinaQueryService responsavelDisciplinaQueryService
    ) {
        this.responsavelDisciplinaService = responsavelDisciplinaService;
        this.responsavelDisciplinaRepository = responsavelDisciplinaRepository;
        this.responsavelDisciplinaQueryService = responsavelDisciplinaQueryService;
    }

    /**
     * {@code POST  /responsavel-disciplinas} : Create a new responsavelDisciplina.
     *
     * @param responsavelDisciplinaDTO the responsavelDisciplinaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responsavelDisciplinaDTO, or with status {@code 400 (Bad Request)} if the responsavelDisciplina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/responsavel-disciplinas")
    public ResponseEntity<ResponsavelDisciplinaDTO> createResponsavelDisciplina(
        @Valid @RequestBody ResponsavelDisciplinaDTO responsavelDisciplinaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ResponsavelDisciplina : {}", responsavelDisciplinaDTO);
        if (responsavelDisciplinaDTO.getId() != null) {
            throw new BadRequestAlertException("A new responsavelDisciplina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponsavelDisciplinaDTO result = responsavelDisciplinaService.save(responsavelDisciplinaDTO);
        return ResponseEntity
            .created(new URI("/api/responsavel-disciplinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /responsavel-disciplinas/:id} : Updates an existing responsavelDisciplina.
     *
     * @param id the id of the responsavelDisciplinaDTO to save.
     * @param responsavelDisciplinaDTO the responsavelDisciplinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavelDisciplinaDTO,
     * or with status {@code 400 (Bad Request)} if the responsavelDisciplinaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responsavelDisciplinaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/responsavel-disciplinas/{id}")
    public ResponseEntity<ResponsavelDisciplinaDTO> updateResponsavelDisciplina(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResponsavelDisciplinaDTO responsavelDisciplinaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ResponsavelDisciplina : {}, {}", id, responsavelDisciplinaDTO);
        if (responsavelDisciplinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsavelDisciplinaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsavelDisciplinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResponsavelDisciplinaDTO result = responsavelDisciplinaService.update(responsavelDisciplinaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsavelDisciplinaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /responsavel-disciplinas/:id} : Partial updates given fields of an existing responsavelDisciplina, field will ignore if it is null
     *
     * @param id the id of the responsavelDisciplinaDTO to save.
     * @param responsavelDisciplinaDTO the responsavelDisciplinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavelDisciplinaDTO,
     * or with status {@code 400 (Bad Request)} if the responsavelDisciplinaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the responsavelDisciplinaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the responsavelDisciplinaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/responsavel-disciplinas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResponsavelDisciplinaDTO> partialUpdateResponsavelDisciplina(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResponsavelDisciplinaDTO responsavelDisciplinaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResponsavelDisciplina partially : {}, {}", id, responsavelDisciplinaDTO);
        if (responsavelDisciplinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsavelDisciplinaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsavelDisciplinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResponsavelDisciplinaDTO> result = responsavelDisciplinaService.partialUpdate(responsavelDisciplinaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsavelDisciplinaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /responsavel-disciplinas} : get all the responsavelDisciplinas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responsavelDisciplinas in body.
     */
    @GetMapping("/responsavel-disciplinas")
    public ResponseEntity<List<ResponsavelDisciplinaDTO>> getAllResponsavelDisciplinas(
        ResponsavelDisciplinaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ResponsavelDisciplinas by criteria: {}", criteria);
        Page<ResponsavelDisciplinaDTO> page = responsavelDisciplinaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /responsavel-disciplinas/count} : count all the responsavelDisciplinas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/responsavel-disciplinas/count")
    public ResponseEntity<Long> countResponsavelDisciplinas(ResponsavelDisciplinaCriteria criteria) {
        log.debug("REST request to count ResponsavelDisciplinas by criteria: {}", criteria);
        return ResponseEntity.ok().body(responsavelDisciplinaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /responsavel-disciplinas/:id} : get the "id" responsavelDisciplina.
     *
     * @param id the id of the responsavelDisciplinaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responsavelDisciplinaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/responsavel-disciplinas/{id}")
    public ResponseEntity<ResponsavelDisciplinaDTO> getResponsavelDisciplina(@PathVariable Long id) {
        log.debug("REST request to get ResponsavelDisciplina : {}", id);
        Optional<ResponsavelDisciplinaDTO> responsavelDisciplinaDTO = responsavelDisciplinaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsavelDisciplinaDTO);
    }

    /**
     * {@code DELETE  /responsavel-disciplinas/:id} : delete the "id" responsavelDisciplina.
     *
     * @param id the id of the responsavelDisciplinaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/responsavel-disciplinas/{id}")
    public ResponseEntity<Void> deleteResponsavelDisciplina(@PathVariable Long id) {
        log.debug("REST request to delete ResponsavelDisciplina : {}", id);
        responsavelDisciplinaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
