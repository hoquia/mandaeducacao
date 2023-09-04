package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.PeriodoLancamentoNota;
import com.ravunana.longonkelo.repository.PeriodoLancamentoNotaRepository;
import com.ravunana.longonkelo.service.PeriodoLancamentoNotaService;
import com.ravunana.longonkelo.service.dto.PeriodoLancamentoNotaDTO;
import com.ravunana.longonkelo.service.mapper.PeriodoLancamentoNotaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PeriodoLancamentoNota}.
 */
@Service
@Transactional
public class PeriodoLancamentoNotaServiceImpl implements PeriodoLancamentoNotaService {

    private final Logger log = LoggerFactory.getLogger(PeriodoLancamentoNotaServiceImpl.class);

    private final PeriodoLancamentoNotaRepository periodoLancamentoNotaRepository;

    private final PeriodoLancamentoNotaMapper periodoLancamentoNotaMapper;

    public PeriodoLancamentoNotaServiceImpl(
        PeriodoLancamentoNotaRepository periodoLancamentoNotaRepository,
        PeriodoLancamentoNotaMapper periodoLancamentoNotaMapper
    ) {
        this.periodoLancamentoNotaRepository = periodoLancamentoNotaRepository;
        this.periodoLancamentoNotaMapper = periodoLancamentoNotaMapper;
    }

    @Override
    public PeriodoLancamentoNotaDTO save(PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO) {
        log.debug("Request to save PeriodoLancamentoNota : {}", periodoLancamentoNotaDTO);
        PeriodoLancamentoNota periodoLancamentoNota = periodoLancamentoNotaMapper.toEntity(periodoLancamentoNotaDTO);
        periodoLancamentoNota = periodoLancamentoNotaRepository.save(periodoLancamentoNota);
        return periodoLancamentoNotaMapper.toDto(periodoLancamentoNota);
    }

    @Override
    public PeriodoLancamentoNotaDTO update(PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO) {
        log.debug("Request to update PeriodoLancamentoNota : {}", periodoLancamentoNotaDTO);
        PeriodoLancamentoNota periodoLancamentoNota = periodoLancamentoNotaMapper.toEntity(periodoLancamentoNotaDTO);
        periodoLancamentoNota = periodoLancamentoNotaRepository.save(periodoLancamentoNota);
        return periodoLancamentoNotaMapper.toDto(periodoLancamentoNota);
    }

    @Override
    public Optional<PeriodoLancamentoNotaDTO> partialUpdate(PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO) {
        log.debug("Request to partially update PeriodoLancamentoNota : {}", periodoLancamentoNotaDTO);

        return periodoLancamentoNotaRepository
            .findById(periodoLancamentoNotaDTO.getId())
            .map(existingPeriodoLancamentoNota -> {
                periodoLancamentoNotaMapper.partialUpdate(existingPeriodoLancamentoNota, periodoLancamentoNotaDTO);

                return existingPeriodoLancamentoNota;
            })
            .map(periodoLancamentoNotaRepository::save)
            .map(periodoLancamentoNotaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PeriodoLancamentoNotaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PeriodoLancamentoNotas");
        return periodoLancamentoNotaRepository.findAll(pageable).map(periodoLancamentoNotaMapper::toDto);
    }

    public Page<PeriodoLancamentoNotaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return periodoLancamentoNotaRepository.findAllWithEagerRelationships(pageable).map(periodoLancamentoNotaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PeriodoLancamentoNotaDTO> findOne(Long id) {
        log.debug("Request to get PeriodoLancamentoNota : {}", id);
        return periodoLancamentoNotaRepository.findOneWithEagerRelationships(id).map(periodoLancamentoNotaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PeriodoLancamentoNota : {}", id);
        periodoLancamentoNotaRepository.deleteById(id);
    }
}
