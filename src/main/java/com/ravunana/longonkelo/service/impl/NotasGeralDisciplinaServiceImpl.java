package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.NotasGeralDisciplina;
import com.ravunana.longonkelo.repository.NotasGeralDisciplinaRepository;
import com.ravunana.longonkelo.service.NotasGeralDisciplinaService;
import com.ravunana.longonkelo.service.dto.NotasGeralDisciplinaDTO;
import com.ravunana.longonkelo.service.mapper.NotasGeralDisciplinaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NotasGeralDisciplina}.
 */
@Service
@Transactional
public class NotasGeralDisciplinaServiceImpl implements NotasGeralDisciplinaService {

    private final Logger log = LoggerFactory.getLogger(NotasGeralDisciplinaServiceImpl.class);

    private final NotasGeralDisciplinaRepository notasGeralDisciplinaRepository;

    private final NotasGeralDisciplinaMapper notasGeralDisciplinaMapper;

    public NotasGeralDisciplinaServiceImpl(
        NotasGeralDisciplinaRepository notasGeralDisciplinaRepository,
        NotasGeralDisciplinaMapper notasGeralDisciplinaMapper
    ) {
        this.notasGeralDisciplinaRepository = notasGeralDisciplinaRepository;
        this.notasGeralDisciplinaMapper = notasGeralDisciplinaMapper;
    }

    @Override
    public NotasGeralDisciplinaDTO save(NotasGeralDisciplinaDTO notasGeralDisciplinaDTO) {
        log.debug("Request to save NotasGeralDisciplina : {}", notasGeralDisciplinaDTO);
        NotasGeralDisciplina notasGeralDisciplina = notasGeralDisciplinaMapper.toEntity(notasGeralDisciplinaDTO);
        notasGeralDisciplina = notasGeralDisciplinaRepository.save(notasGeralDisciplina);
        return notasGeralDisciplinaMapper.toDto(notasGeralDisciplina);
    }

    @Override
    public NotasGeralDisciplinaDTO update(NotasGeralDisciplinaDTO notasGeralDisciplinaDTO) {
        log.debug("Request to update NotasGeralDisciplina : {}", notasGeralDisciplinaDTO);
        NotasGeralDisciplina notasGeralDisciplina = notasGeralDisciplinaMapper.toEntity(notasGeralDisciplinaDTO);
        notasGeralDisciplina = notasGeralDisciplinaRepository.save(notasGeralDisciplina);
        return notasGeralDisciplinaMapper.toDto(notasGeralDisciplina);
    }

    @Override
    public Optional<NotasGeralDisciplinaDTO> partialUpdate(NotasGeralDisciplinaDTO notasGeralDisciplinaDTO) {
        log.debug("Request to partially update NotasGeralDisciplina : {}", notasGeralDisciplinaDTO);

        return notasGeralDisciplinaRepository
            .findById(notasGeralDisciplinaDTO.getId())
            .map(existingNotasGeralDisciplina -> {
                notasGeralDisciplinaMapper.partialUpdate(existingNotasGeralDisciplina, notasGeralDisciplinaDTO);

                return existingNotasGeralDisciplina;
            })
            .map(notasGeralDisciplinaRepository::save)
            .map(notasGeralDisciplinaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotasGeralDisciplinaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NotasGeralDisciplinas");
        return notasGeralDisciplinaRepository.findAll(pageable).map(notasGeralDisciplinaMapper::toDto);
    }

    public Page<NotasGeralDisciplinaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return notasGeralDisciplinaRepository.findAllWithEagerRelationships(pageable).map(notasGeralDisciplinaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotasGeralDisciplinaDTO> findOne(Long id) {
        log.debug("Request to get NotasGeralDisciplina : {}", id);
        return notasGeralDisciplinaRepository.findOneWithEagerRelationships(id).map(notasGeralDisciplinaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NotasGeralDisciplina : {}", id);
        notasGeralDisciplinaRepository.deleteById(id);
    }
}
