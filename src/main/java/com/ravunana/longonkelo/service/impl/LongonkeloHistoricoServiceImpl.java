package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.LongonkeloHistorico;
import com.ravunana.longonkelo.repository.LongonkeloHistoricoRepository;
import com.ravunana.longonkelo.service.LongonkeloHistoricoService;
import com.ravunana.longonkelo.service.dto.LongonkeloHistoricoDTO;
import com.ravunana.longonkelo.service.mapper.LongonkeloHistoricoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LongonkeloHistorico}.
 */
@Service
@Transactional
public class LongonkeloHistoricoServiceImpl implements LongonkeloHistoricoService {

    private final Logger log = LoggerFactory.getLogger(LongonkeloHistoricoServiceImpl.class);

    private final LongonkeloHistoricoRepository longonkeloHistoricoRepository;

    private final LongonkeloHistoricoMapper longonkeloHistoricoMapper;

    public LongonkeloHistoricoServiceImpl(
        LongonkeloHistoricoRepository longonkeloHistoricoRepository,
        LongonkeloHistoricoMapper longonkeloHistoricoMapper
    ) {
        this.longonkeloHistoricoRepository = longonkeloHistoricoRepository;
        this.longonkeloHistoricoMapper = longonkeloHistoricoMapper;
    }

    @Override
    public LongonkeloHistoricoDTO save(LongonkeloHistoricoDTO longonkeloHistoricoDTO) {
        log.debug("Request to save LongonkeloHistorico : {}", longonkeloHistoricoDTO);
        LongonkeloHistorico longonkeloHistorico = longonkeloHistoricoMapper.toEntity(longonkeloHistoricoDTO);
        longonkeloHistorico = longonkeloHistoricoRepository.save(longonkeloHistorico);
        return longonkeloHistoricoMapper.toDto(longonkeloHistorico);
    }

    @Override
    public LongonkeloHistoricoDTO update(LongonkeloHistoricoDTO longonkeloHistoricoDTO) {
        log.debug("Request to update LongonkeloHistorico : {}", longonkeloHistoricoDTO);
        LongonkeloHistorico longonkeloHistorico = longonkeloHistoricoMapper.toEntity(longonkeloHistoricoDTO);
        longonkeloHistorico = longonkeloHistoricoRepository.save(longonkeloHistorico);
        return longonkeloHistoricoMapper.toDto(longonkeloHistorico);
    }

    @Override
    public Optional<LongonkeloHistoricoDTO> partialUpdate(LongonkeloHistoricoDTO longonkeloHistoricoDTO) {
        log.debug("Request to partially update LongonkeloHistorico : {}", longonkeloHistoricoDTO);

        return longonkeloHistoricoRepository
            .findById(longonkeloHistoricoDTO.getId())
            .map(existingLongonkeloHistorico -> {
                longonkeloHistoricoMapper.partialUpdate(existingLongonkeloHistorico, longonkeloHistoricoDTO);

                return existingLongonkeloHistorico;
            })
            .map(longonkeloHistoricoRepository::save)
            .map(longonkeloHistoricoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LongonkeloHistoricoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LongonkeloHistoricos");
        return longonkeloHistoricoRepository.findAll(pageable).map(longonkeloHistoricoMapper::toDto);
    }

    public Page<LongonkeloHistoricoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return longonkeloHistoricoRepository.findAllWithEagerRelationships(pageable).map(longonkeloHistoricoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LongonkeloHistoricoDTO> findOne(Long id) {
        log.debug("Request to get LongonkeloHistorico : {}", id);
        return longonkeloHistoricoRepository.findOneWithEagerRelationships(id).map(longonkeloHistoricoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LongonkeloHistorico : {}", id);
        longonkeloHistoricoRepository.deleteById(id);
    }
}
