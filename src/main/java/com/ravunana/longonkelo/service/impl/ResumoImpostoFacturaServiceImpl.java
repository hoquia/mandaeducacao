package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.ResumoImpostoFactura;
import com.ravunana.longonkelo.repository.ResumoImpostoFacturaRepository;
import com.ravunana.longonkelo.service.ResumoImpostoFacturaService;
import com.ravunana.longonkelo.service.dto.ResumoImpostoFacturaDTO;
import com.ravunana.longonkelo.service.mapper.ResumoImpostoFacturaMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ResumoImpostoFactura}.
 */
@Service
@Transactional
public class ResumoImpostoFacturaServiceImpl implements ResumoImpostoFacturaService {

    private final Logger log = LoggerFactory.getLogger(ResumoImpostoFacturaServiceImpl.class);

    private final ResumoImpostoFacturaRepository resumoImpostoFacturaRepository;

    private final ResumoImpostoFacturaMapper resumoImpostoFacturaMapper;

    public ResumoImpostoFacturaServiceImpl(
        ResumoImpostoFacturaRepository resumoImpostoFacturaRepository,
        ResumoImpostoFacturaMapper resumoImpostoFacturaMapper
    ) {
        this.resumoImpostoFacturaRepository = resumoImpostoFacturaRepository;
        this.resumoImpostoFacturaMapper = resumoImpostoFacturaMapper;
    }

    @Override
    public ResumoImpostoFacturaDTO save(ResumoImpostoFacturaDTO resumoImpostoFacturaDTO) {
        log.debug("Request to save ResumoImpostoFactura : {}", resumoImpostoFacturaDTO);
        ResumoImpostoFactura resumoImpostoFactura = resumoImpostoFacturaMapper.toEntity(resumoImpostoFacturaDTO);
        resumoImpostoFactura = resumoImpostoFacturaRepository.save(resumoImpostoFactura);
        return resumoImpostoFacturaMapper.toDto(resumoImpostoFactura);
    }

    @Override
    public ResumoImpostoFacturaDTO update(ResumoImpostoFacturaDTO resumoImpostoFacturaDTO) {
        log.debug("Request to update ResumoImpostoFactura : {}", resumoImpostoFacturaDTO);
        ResumoImpostoFactura resumoImpostoFactura = resumoImpostoFacturaMapper.toEntity(resumoImpostoFacturaDTO);
        resumoImpostoFactura = resumoImpostoFacturaRepository.save(resumoImpostoFactura);
        return resumoImpostoFacturaMapper.toDto(resumoImpostoFactura);
    }

    @Override
    public Optional<ResumoImpostoFacturaDTO> partialUpdate(ResumoImpostoFacturaDTO resumoImpostoFacturaDTO) {
        log.debug("Request to partially update ResumoImpostoFactura : {}", resumoImpostoFacturaDTO);

        return resumoImpostoFacturaRepository
            .findById(resumoImpostoFacturaDTO.getId())
            .map(existingResumoImpostoFactura -> {
                resumoImpostoFacturaMapper.partialUpdate(existingResumoImpostoFactura, resumoImpostoFacturaDTO);

                return existingResumoImpostoFactura;
            })
            .map(resumoImpostoFacturaRepository::save)
            .map(resumoImpostoFacturaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResumoImpostoFacturaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResumoImpostoFacturas");
        return resumoImpostoFacturaRepository.findAll(pageable).map(resumoImpostoFacturaMapper::toDto);
    }

    public Page<ResumoImpostoFacturaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return resumoImpostoFacturaRepository.findAllWithEagerRelationships(pageable).map(resumoImpostoFacturaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResumoImpostoFacturaDTO> findOne(Long id) {
        log.debug("Request to get ResumoImpostoFactura : {}", id);
        return resumoImpostoFacturaRepository.findOneWithEagerRelationships(id).map(resumoImpostoFacturaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResumoImpostoFactura : {}", id);
        resumoImpostoFacturaRepository.deleteById(id);
    }

    @Override
    public List<ResumoImpostoFacturaDTO> getResumoImpostoFactura(Long facturaID) {
        var result = resumoImpostoFacturaRepository
            .findAll()
            .stream()
            .filter(x -> x.getFactura().getId().equals(facturaID))
            .collect(Collectors.toList());

        return resumoImpostoFacturaMapper.toDto(result);
    }
}
