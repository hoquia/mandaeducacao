package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.ReciboRepository;
import com.ravunana.longonkelo.service.ReciboQueryService;
import com.ravunana.longonkelo.service.ReciboService;
import com.ravunana.longonkelo.service.criteria.ReciboCriteria;
import com.ravunana.longonkelo.service.dto.ReciboDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.Recibo}.
 */
@RestController
@RequestMapping("/api")
public class ReciboResource {

    private final Logger log = LoggerFactory.getLogger(ReciboResource.class);

    private static final String ENTITY_NAME = "recibo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReciboService reciboService;

    private final ReciboRepository reciboRepository;

    private final ReciboQueryService reciboQueryService;
    private final ReciboPagamentoReport reciboPagamentoReport;

    public ReciboResource(
        ReciboService reciboService,
        ReciboRepository reciboRepository,
        ReciboQueryService reciboQueryService,
        ReciboPagamentoReport reciboPagamentoReport
    ) {
        this.reciboService = reciboService;
        this.reciboRepository = reciboRepository;
        this.reciboQueryService = reciboQueryService;
        this.reciboPagamentoReport = reciboPagamentoReport;
    }

    /**
     * {@code POST  /recibos} : Create a new recibo.
     *
     * @param reciboDTO the reciboDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reciboDTO, or with status {@code 400 (Bad Request)} if the recibo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recibos")
    public ResponseEntity<ReciboDTO> createRecibo(@Valid @RequestBody ReciboDTO reciboDTO) throws URISyntaxException {
        log.debug("REST request to save Recibo : {}", reciboDTO);
        if (reciboDTO.getId() != null) {
            throw new BadRequestAlertException("A new recibo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReciboDTO result = reciboService.save(reciboDTO);
        return ResponseEntity
            .created(new URI("/api/recibos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recibos/:id} : Updates an existing recibo.
     *
     * @param id the id of the reciboDTO to save.
     * @param reciboDTO the reciboDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reciboDTO,
     * or with status {@code 400 (Bad Request)} if the reciboDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reciboDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recibos/{id}")
    public ResponseEntity<ReciboDTO> updateRecibo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ReciboDTO reciboDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Recibo : {}, {}", id, reciboDTO);
        if (reciboDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reciboDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reciboRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReciboDTO result = reciboService.update(reciboDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reciboDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /recibos/:id} : Partial updates given fields of an existing recibo, field will ignore if it is null
     *
     * @param id the id of the reciboDTO to save.
     * @param reciboDTO the reciboDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reciboDTO,
     * or with status {@code 400 (Bad Request)} if the reciboDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reciboDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reciboDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recibos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReciboDTO> partialUpdateRecibo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ReciboDTO reciboDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Recibo partially : {}, {}", id, reciboDTO);
        if (reciboDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reciboDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reciboRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReciboDTO> result = reciboService.partialUpdate(reciboDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reciboDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /recibos} : get all the recibos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recibos in body.
     */
    @GetMapping("/recibos")
    public ResponseEntity<List<ReciboDTO>> getAllRecibos(
        ReciboCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Recibos by criteria: {}", criteria);
        Page<ReciboDTO> page = reciboQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recibos/count} : count all the recibos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/recibos/count")
    public ResponseEntity<Long> countRecibos(ReciboCriteria criteria) {
        log.debug("REST request to count Recibos by criteria: {}", criteria);
        return ResponseEntity.ok().body(reciboQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /recibos/:id} : get the "id" recibo.
     *
     * @param id the id of the reciboDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reciboDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recibos/{id}")
    public ResponseEntity<ReciboDTO> getRecibo(@PathVariable Long id) {
        log.debug("REST request to get Recibo : {}", id);
        Optional<ReciboDTO> reciboDTO = reciboService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reciboDTO);
    }

    /**
     * {@code DELETE  /recibos/:id} : delete the "id" recibo.
     *
     * @param id the id of the reciboDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recibos/{id}")
    public ResponseEntity<Void> deleteRecibo(@PathVariable Long id) {
        log.debug("REST request to delete Recibo : {}", id);
        reciboService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/recibos/recibo-pagamento/{reciboID}")
    public ResponseEntity<Resource> getReciboSalario(@PathVariable Long reciboID) throws IOException {
        var filePath = reciboPagamentoReport.gerarReciboPdf(reciboID);
        File file = new File(filePath);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.APPLICATION_PDF).body(resource);
    }
}
