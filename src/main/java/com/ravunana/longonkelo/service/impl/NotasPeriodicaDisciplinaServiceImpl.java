package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina;
import com.ravunana.longonkelo.repository.NotasPeriodicaDisciplinaRepository;
import com.ravunana.longonkelo.service.NotasPeriodicaDisciplinaService;
import com.ravunana.longonkelo.service.dto.NotasPeriodicaDisciplinaDTO;
import com.ravunana.longonkelo.service.mapper.NotasPeriodicaDisciplinaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NotasPeriodicaDisciplina}.
 */
@Service
@Transactional
public class NotasPeriodicaDisciplinaServiceImpl implements NotasPeriodicaDisciplinaService {

    private final Logger log = LoggerFactory.getLogger(NotasPeriodicaDisciplinaServiceImpl.class);

    private final NotasPeriodicaDisciplinaRepository notasPeriodicaDisciplinaRepository;

    private final NotasPeriodicaDisciplinaMapper notasPeriodicaDisciplinaMapper;

    public NotasPeriodicaDisciplinaServiceImpl(
        NotasPeriodicaDisciplinaRepository notasPeriodicaDisciplinaRepository,
        NotasPeriodicaDisciplinaMapper notasPeriodicaDisciplinaMapper
    ) {
        this.notasPeriodicaDisciplinaRepository = notasPeriodicaDisciplinaRepository;
        this.notasPeriodicaDisciplinaMapper = notasPeriodicaDisciplinaMapper;
    }

    @Override
    public NotasPeriodicaDisciplinaDTO save(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO) {
        log.debug("Request to save NotasPeriodicaDisciplina : {}", notasPeriodicaDisciplinaDTO);
        NotasPeriodicaDisciplina notasPeriodicaDisciplina = notasPeriodicaDisciplinaMapper.toEntity(notasPeriodicaDisciplinaDTO);
        notasPeriodicaDisciplina = notasPeriodicaDisciplinaRepository.save(notasPeriodicaDisciplina);
        return notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina);
    }

    @Override
    public NotasPeriodicaDisciplinaDTO update(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO) {
        log.debug("Request to update NotasPeriodicaDisciplina : {}", notasPeriodicaDisciplinaDTO);
        NotasPeriodicaDisciplina notasPeriodicaDisciplina = notasPeriodicaDisciplinaMapper.toEntity(notasPeriodicaDisciplinaDTO);
        notasPeriodicaDisciplina = notasPeriodicaDisciplinaRepository.save(notasPeriodicaDisciplina);
        return notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina);
    }

    @Override
    public Optional<NotasPeriodicaDisciplinaDTO> partialUpdate(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO) {
        log.debug("Request to partially update NotasPeriodicaDisciplina : {}", notasPeriodicaDisciplinaDTO);

        return notasPeriodicaDisciplinaRepository
            .findById(notasPeriodicaDisciplinaDTO.getId())
            .map(existingNotasPeriodicaDisciplina -> {
                notasPeriodicaDisciplinaMapper.partialUpdate(existingNotasPeriodicaDisciplina, notasPeriodicaDisciplinaDTO);

                return existingNotasPeriodicaDisciplina;
            })
            .map(notasPeriodicaDisciplinaRepository::save)
            .map(notasPeriodicaDisciplinaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotasPeriodicaDisciplinaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NotasPeriodicaDisciplinas");
        return notasPeriodicaDisciplinaRepository.findAll(pageable).map(notasPeriodicaDisciplinaMapper::toDto);
    }

    public Page<NotasPeriodicaDisciplinaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return notasPeriodicaDisciplinaRepository.findAllWithEagerRelationships(pageable).map(notasPeriodicaDisciplinaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotasPeriodicaDisciplinaDTO> findOne(Long id) {
        log.debug("Request to get NotasPeriodicaDisciplina : {}", id);
        return notasPeriodicaDisciplinaRepository.findOneWithEagerRelationships(id).map(notasPeriodicaDisciplinaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NotasPeriodicaDisciplina : {}", id);
        notasPeriodicaDisciplinaRepository.deleteById(id);
    }
}
