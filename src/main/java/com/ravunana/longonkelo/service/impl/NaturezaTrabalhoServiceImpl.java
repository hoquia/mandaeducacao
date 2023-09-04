package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.NaturezaTrabalho;
import com.ravunana.longonkelo.repository.NaturezaTrabalhoRepository;
import com.ravunana.longonkelo.service.NaturezaTrabalhoService;
import com.ravunana.longonkelo.service.dto.NaturezaTrabalhoDTO;
import com.ravunana.longonkelo.service.mapper.NaturezaTrabalhoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NaturezaTrabalho}.
 */
@Service
@Transactional
public class NaturezaTrabalhoServiceImpl implements NaturezaTrabalhoService {

    private final Logger log = LoggerFactory.getLogger(NaturezaTrabalhoServiceImpl.class);

    private final NaturezaTrabalhoRepository naturezaTrabalhoRepository;

    private final NaturezaTrabalhoMapper naturezaTrabalhoMapper;

    public NaturezaTrabalhoServiceImpl(
        NaturezaTrabalhoRepository naturezaTrabalhoRepository,
        NaturezaTrabalhoMapper naturezaTrabalhoMapper
    ) {
        this.naturezaTrabalhoRepository = naturezaTrabalhoRepository;
        this.naturezaTrabalhoMapper = naturezaTrabalhoMapper;
    }

    @Override
    public NaturezaTrabalhoDTO save(NaturezaTrabalhoDTO naturezaTrabalhoDTO) {
        log.debug("Request to save NaturezaTrabalho : {}", naturezaTrabalhoDTO);
        NaturezaTrabalho naturezaTrabalho = naturezaTrabalhoMapper.toEntity(naturezaTrabalhoDTO);
        naturezaTrabalho = naturezaTrabalhoRepository.save(naturezaTrabalho);
        return naturezaTrabalhoMapper.toDto(naturezaTrabalho);
    }

    @Override
    public NaturezaTrabalhoDTO update(NaturezaTrabalhoDTO naturezaTrabalhoDTO) {
        log.debug("Request to update NaturezaTrabalho : {}", naturezaTrabalhoDTO);
        NaturezaTrabalho naturezaTrabalho = naturezaTrabalhoMapper.toEntity(naturezaTrabalhoDTO);
        naturezaTrabalho = naturezaTrabalhoRepository.save(naturezaTrabalho);
        return naturezaTrabalhoMapper.toDto(naturezaTrabalho);
    }

    @Override
    public Optional<NaturezaTrabalhoDTO> partialUpdate(NaturezaTrabalhoDTO naturezaTrabalhoDTO) {
        log.debug("Request to partially update NaturezaTrabalho : {}", naturezaTrabalhoDTO);

        return naturezaTrabalhoRepository
            .findById(naturezaTrabalhoDTO.getId())
            .map(existingNaturezaTrabalho -> {
                naturezaTrabalhoMapper.partialUpdate(existingNaturezaTrabalho, naturezaTrabalhoDTO);

                return existingNaturezaTrabalho;
            })
            .map(naturezaTrabalhoRepository::save)
            .map(naturezaTrabalhoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NaturezaTrabalhoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NaturezaTrabalhos");
        return naturezaTrabalhoRepository.findAll(pageable).map(naturezaTrabalhoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NaturezaTrabalhoDTO> findOne(Long id) {
        log.debug("Request to get NaturezaTrabalho : {}", id);
        return naturezaTrabalhoRepository.findById(id).map(naturezaTrabalhoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NaturezaTrabalho : {}", id);
        naturezaTrabalhoRepository.deleteById(id);
    }
}
