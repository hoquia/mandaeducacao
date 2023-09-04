package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.Disciplina;
import com.ravunana.longonkelo.repository.DisciplinaRepository;
import com.ravunana.longonkelo.service.DisciplinaService;
import com.ravunana.longonkelo.service.dto.DisciplinaDTO;
import com.ravunana.longonkelo.service.mapper.DisciplinaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Disciplina}.
 */
@Service
@Transactional
public class DisciplinaServiceImpl implements DisciplinaService {

    private final Logger log = LoggerFactory.getLogger(DisciplinaServiceImpl.class);

    private final DisciplinaRepository disciplinaRepository;

    private final DisciplinaMapper disciplinaMapper;

    public DisciplinaServiceImpl(DisciplinaRepository disciplinaRepository, DisciplinaMapper disciplinaMapper) {
        this.disciplinaRepository = disciplinaRepository;
        this.disciplinaMapper = disciplinaMapper;
    }

    @Override
    public DisciplinaDTO save(DisciplinaDTO disciplinaDTO) {
        log.debug("Request to save Disciplina : {}", disciplinaDTO);
        Disciplina disciplina = disciplinaMapper.toEntity(disciplinaDTO);
        disciplina = disciplinaRepository.save(disciplina);
        return disciplinaMapper.toDto(disciplina);
    }

    @Override
    public DisciplinaDTO update(DisciplinaDTO disciplinaDTO) {
        log.debug("Request to update Disciplina : {}", disciplinaDTO);
        Disciplina disciplina = disciplinaMapper.toEntity(disciplinaDTO);
        disciplina = disciplinaRepository.save(disciplina);
        return disciplinaMapper.toDto(disciplina);
    }

    @Override
    public Optional<DisciplinaDTO> partialUpdate(DisciplinaDTO disciplinaDTO) {
        log.debug("Request to partially update Disciplina : {}", disciplinaDTO);

        return disciplinaRepository
            .findById(disciplinaDTO.getId())
            .map(existingDisciplina -> {
                disciplinaMapper.partialUpdate(existingDisciplina, disciplinaDTO);

                return existingDisciplina;
            })
            .map(disciplinaRepository::save)
            .map(disciplinaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DisciplinaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Disciplinas");
        return disciplinaRepository.findAll(pageable).map(disciplinaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DisciplinaDTO> findOne(Long id) {
        log.debug("Request to get Disciplina : {}", id);
        return disciplinaRepository.findById(id).map(disciplinaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Disciplina : {}", id);
        disciplinaRepository.deleteById(id);
    }
}
