package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.ResponsavelDisciplina;
import com.ravunana.longonkelo.repository.ResponsavelDisciplinaRepository;
import com.ravunana.longonkelo.service.ResponsavelDisciplinaService;
import com.ravunana.longonkelo.service.dto.ResponsavelDisciplinaDTO;
import com.ravunana.longonkelo.service.mapper.ResponsavelDisciplinaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ResponsavelDisciplina}.
 */
@Service
@Transactional
public class ResponsavelDisciplinaServiceImpl implements ResponsavelDisciplinaService {

    private final Logger log = LoggerFactory.getLogger(ResponsavelDisciplinaServiceImpl.class);

    private final ResponsavelDisciplinaRepository responsavelDisciplinaRepository;

    private final ResponsavelDisciplinaMapper responsavelDisciplinaMapper;

    public ResponsavelDisciplinaServiceImpl(
        ResponsavelDisciplinaRepository responsavelDisciplinaRepository,
        ResponsavelDisciplinaMapper responsavelDisciplinaMapper
    ) {
        this.responsavelDisciplinaRepository = responsavelDisciplinaRepository;
        this.responsavelDisciplinaMapper = responsavelDisciplinaMapper;
    }

    @Override
    public ResponsavelDisciplinaDTO save(ResponsavelDisciplinaDTO responsavelDisciplinaDTO) {
        log.debug("Request to save ResponsavelDisciplina : {}", responsavelDisciplinaDTO);
        ResponsavelDisciplina responsavelDisciplina = responsavelDisciplinaMapper.toEntity(responsavelDisciplinaDTO);
        responsavelDisciplina = responsavelDisciplinaRepository.save(responsavelDisciplina);
        return responsavelDisciplinaMapper.toDto(responsavelDisciplina);
    }

    @Override
    public ResponsavelDisciplinaDTO update(ResponsavelDisciplinaDTO responsavelDisciplinaDTO) {
        log.debug("Request to update ResponsavelDisciplina : {}", responsavelDisciplinaDTO);
        ResponsavelDisciplina responsavelDisciplina = responsavelDisciplinaMapper.toEntity(responsavelDisciplinaDTO);
        responsavelDisciplina = responsavelDisciplinaRepository.save(responsavelDisciplina);
        return responsavelDisciplinaMapper.toDto(responsavelDisciplina);
    }

    @Override
    public Optional<ResponsavelDisciplinaDTO> partialUpdate(ResponsavelDisciplinaDTO responsavelDisciplinaDTO) {
        log.debug("Request to partially update ResponsavelDisciplina : {}", responsavelDisciplinaDTO);

        return responsavelDisciplinaRepository
            .findById(responsavelDisciplinaDTO.getId())
            .map(existingResponsavelDisciplina -> {
                responsavelDisciplinaMapper.partialUpdate(existingResponsavelDisciplina, responsavelDisciplinaDTO);

                return existingResponsavelDisciplina;
            })
            .map(responsavelDisciplinaRepository::save)
            .map(responsavelDisciplinaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsavelDisciplinaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResponsavelDisciplinas");
        return responsavelDisciplinaRepository.findAll(pageable).map(responsavelDisciplinaMapper::toDto);
    }

    public Page<ResponsavelDisciplinaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return responsavelDisciplinaRepository.findAllWithEagerRelationships(pageable).map(responsavelDisciplinaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResponsavelDisciplinaDTO> findOne(Long id) {
        log.debug("Request to get ResponsavelDisciplina : {}", id);
        return responsavelDisciplinaRepository.findOneWithEagerRelationships(id).map(responsavelDisciplinaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResponsavelDisciplina : {}", id);
        responsavelDisciplinaRepository.deleteById(id);
    }
}
