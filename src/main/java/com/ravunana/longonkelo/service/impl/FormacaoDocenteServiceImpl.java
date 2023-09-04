package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.FormacaoDocente;
import com.ravunana.longonkelo.repository.FormacaoDocenteRepository;
import com.ravunana.longonkelo.service.FormacaoDocenteService;
import com.ravunana.longonkelo.service.dto.FormacaoDocenteDTO;
import com.ravunana.longonkelo.service.mapper.FormacaoDocenteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FormacaoDocente}.
 */
@Service
@Transactional
public class FormacaoDocenteServiceImpl implements FormacaoDocenteService {

    private final Logger log = LoggerFactory.getLogger(FormacaoDocenteServiceImpl.class);

    private final FormacaoDocenteRepository formacaoDocenteRepository;

    private final FormacaoDocenteMapper formacaoDocenteMapper;

    public FormacaoDocenteServiceImpl(FormacaoDocenteRepository formacaoDocenteRepository, FormacaoDocenteMapper formacaoDocenteMapper) {
        this.formacaoDocenteRepository = formacaoDocenteRepository;
        this.formacaoDocenteMapper = formacaoDocenteMapper;
    }

    @Override
    public FormacaoDocenteDTO save(FormacaoDocenteDTO formacaoDocenteDTO) {
        log.debug("Request to save FormacaoDocente : {}", formacaoDocenteDTO);
        FormacaoDocente formacaoDocente = formacaoDocenteMapper.toEntity(formacaoDocenteDTO);
        formacaoDocente = formacaoDocenteRepository.save(formacaoDocente);
        return formacaoDocenteMapper.toDto(formacaoDocente);
    }

    @Override
    public FormacaoDocenteDTO update(FormacaoDocenteDTO formacaoDocenteDTO) {
        log.debug("Request to update FormacaoDocente : {}", formacaoDocenteDTO);
        FormacaoDocente formacaoDocente = formacaoDocenteMapper.toEntity(formacaoDocenteDTO);
        formacaoDocente = formacaoDocenteRepository.save(formacaoDocente);
        return formacaoDocenteMapper.toDto(formacaoDocente);
    }

    @Override
    public Optional<FormacaoDocenteDTO> partialUpdate(FormacaoDocenteDTO formacaoDocenteDTO) {
        log.debug("Request to partially update FormacaoDocente : {}", formacaoDocenteDTO);

        return formacaoDocenteRepository
            .findById(formacaoDocenteDTO.getId())
            .map(existingFormacaoDocente -> {
                formacaoDocenteMapper.partialUpdate(existingFormacaoDocente, formacaoDocenteDTO);

                return existingFormacaoDocente;
            })
            .map(formacaoDocenteRepository::save)
            .map(formacaoDocenteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormacaoDocenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FormacaoDocentes");
        return formacaoDocenteRepository.findAll(pageable).map(formacaoDocenteMapper::toDto);
    }

    public Page<FormacaoDocenteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return formacaoDocenteRepository.findAllWithEagerRelationships(pageable).map(formacaoDocenteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormacaoDocenteDTO> findOne(Long id) {
        log.debug("Request to get FormacaoDocente : {}", id);
        return formacaoDocenteRepository.findOneWithEagerRelationships(id).map(formacaoDocenteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormacaoDocente : {}", id);
        formacaoDocenteRepository.deleteById(id);
    }
}
