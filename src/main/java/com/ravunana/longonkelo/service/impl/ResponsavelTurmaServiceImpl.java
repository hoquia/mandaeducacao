package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.ResponsavelTurma;
import com.ravunana.longonkelo.repository.ResponsavelTurmaRepository;
import com.ravunana.longonkelo.service.ResponsavelTurmaService;
import com.ravunana.longonkelo.service.dto.ResponsavelTurmaDTO;
import com.ravunana.longonkelo.service.mapper.ResponsavelTurmaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ResponsavelTurma}.
 */
@Service
@Transactional
public class ResponsavelTurmaServiceImpl implements ResponsavelTurmaService {

    private final Logger log = LoggerFactory.getLogger(ResponsavelTurmaServiceImpl.class);

    private final ResponsavelTurmaRepository responsavelTurmaRepository;

    private final ResponsavelTurmaMapper responsavelTurmaMapper;

    public ResponsavelTurmaServiceImpl(
        ResponsavelTurmaRepository responsavelTurmaRepository,
        ResponsavelTurmaMapper responsavelTurmaMapper
    ) {
        this.responsavelTurmaRepository = responsavelTurmaRepository;
        this.responsavelTurmaMapper = responsavelTurmaMapper;
    }

    @Override
    public ResponsavelTurmaDTO save(ResponsavelTurmaDTO responsavelTurmaDTO) {
        log.debug("Request to save ResponsavelTurma : {}", responsavelTurmaDTO);
        ResponsavelTurma responsavelTurma = responsavelTurmaMapper.toEntity(responsavelTurmaDTO);
        responsavelTurma = responsavelTurmaRepository.save(responsavelTurma);
        return responsavelTurmaMapper.toDto(responsavelTurma);
    }

    @Override
    public ResponsavelTurmaDTO update(ResponsavelTurmaDTO responsavelTurmaDTO) {
        log.debug("Request to update ResponsavelTurma : {}", responsavelTurmaDTO);
        ResponsavelTurma responsavelTurma = responsavelTurmaMapper.toEntity(responsavelTurmaDTO);
        responsavelTurma = responsavelTurmaRepository.save(responsavelTurma);
        return responsavelTurmaMapper.toDto(responsavelTurma);
    }

    @Override
    public Optional<ResponsavelTurmaDTO> partialUpdate(ResponsavelTurmaDTO responsavelTurmaDTO) {
        log.debug("Request to partially update ResponsavelTurma : {}", responsavelTurmaDTO);

        return responsavelTurmaRepository
            .findById(responsavelTurmaDTO.getId())
            .map(existingResponsavelTurma -> {
                responsavelTurmaMapper.partialUpdate(existingResponsavelTurma, responsavelTurmaDTO);

                return existingResponsavelTurma;
            })
            .map(responsavelTurmaRepository::save)
            .map(responsavelTurmaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsavelTurmaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResponsavelTurmas");
        return responsavelTurmaRepository.findAll(pageable).map(responsavelTurmaMapper::toDto);
    }

    public Page<ResponsavelTurmaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return responsavelTurmaRepository.findAllWithEagerRelationships(pageable).map(responsavelTurmaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResponsavelTurmaDTO> findOne(Long id) {
        log.debug("Request to get ResponsavelTurma : {}", id);
        return responsavelTurmaRepository.findOneWithEagerRelationships(id).map(responsavelTurmaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResponsavelTurma : {}", id);
        responsavelTurmaRepository.deleteById(id);
    }
}
