package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.HistoricoSaude;
import com.ravunana.longonkelo.repository.HistoricoSaudeRepository;
import com.ravunana.longonkelo.service.HistoricoSaudeService;
import com.ravunana.longonkelo.service.dto.HistoricoSaudeDTO;
import com.ravunana.longonkelo.service.mapper.HistoricoSaudeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HistoricoSaude}.
 */
@Service
@Transactional
public class HistoricoSaudeServiceImpl implements HistoricoSaudeService {

    private final Logger log = LoggerFactory.getLogger(HistoricoSaudeServiceImpl.class);

    private final HistoricoSaudeRepository historicoSaudeRepository;

    private final HistoricoSaudeMapper historicoSaudeMapper;

    public HistoricoSaudeServiceImpl(HistoricoSaudeRepository historicoSaudeRepository, HistoricoSaudeMapper historicoSaudeMapper) {
        this.historicoSaudeRepository = historicoSaudeRepository;
        this.historicoSaudeMapper = historicoSaudeMapper;
    }

    @Override
    public HistoricoSaudeDTO save(HistoricoSaudeDTO historicoSaudeDTO) {
        log.debug("Request to save HistoricoSaude : {}", historicoSaudeDTO);
        HistoricoSaude historicoSaude = historicoSaudeMapper.toEntity(historicoSaudeDTO);
        historicoSaude = historicoSaudeRepository.save(historicoSaude);
        return historicoSaudeMapper.toDto(historicoSaude);
    }

    @Override
    public HistoricoSaudeDTO update(HistoricoSaudeDTO historicoSaudeDTO) {
        log.debug("Request to update HistoricoSaude : {}", historicoSaudeDTO);
        HistoricoSaude historicoSaude = historicoSaudeMapper.toEntity(historicoSaudeDTO);
        historicoSaude = historicoSaudeRepository.save(historicoSaude);
        return historicoSaudeMapper.toDto(historicoSaude);
    }

    @Override
    public Optional<HistoricoSaudeDTO> partialUpdate(HistoricoSaudeDTO historicoSaudeDTO) {
        log.debug("Request to partially update HistoricoSaude : {}", historicoSaudeDTO);

        return historicoSaudeRepository
            .findById(historicoSaudeDTO.getId())
            .map(existingHistoricoSaude -> {
                historicoSaudeMapper.partialUpdate(existingHistoricoSaude, historicoSaudeDTO);

                return existingHistoricoSaude;
            })
            .map(historicoSaudeRepository::save)
            .map(historicoSaudeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HistoricoSaudeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HistoricoSaudes");
        return historicoSaudeRepository.findAll(pageable).map(historicoSaudeMapper::toDto);
    }

    public Page<HistoricoSaudeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return historicoSaudeRepository.findAllWithEagerRelationships(pageable).map(historicoSaudeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HistoricoSaudeDTO> findOne(Long id) {
        log.debug("Request to get HistoricoSaude : {}", id);
        return historicoSaudeRepository.findOneWithEagerRelationships(id).map(historicoSaudeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HistoricoSaude : {}", id);
        historicoSaudeRepository.deleteById(id);
    }
}
