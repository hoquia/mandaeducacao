package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular;
import com.ravunana.longonkelo.domain.enumeration.CategoriaClassificacao;
import com.ravunana.longonkelo.repository.EstadoDisciplinaCurricularRepository;
import com.ravunana.longonkelo.service.EstadoDisciplinaCurricularService;
import com.ravunana.longonkelo.service.dto.EstadoDisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.mapper.EstadoDisciplinaCurricularMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EstadoDisciplinaCurricular}.
 */
@Service
@Transactional
public class EstadoDisciplinaCurricularServiceImpl implements EstadoDisciplinaCurricularService {

    private final Logger log = LoggerFactory.getLogger(EstadoDisciplinaCurricularServiceImpl.class);

    private final EstadoDisciplinaCurricularRepository estadoDisciplinaCurricularRepository;

    private final EstadoDisciplinaCurricularMapper estadoDisciplinaCurricularMapper;

    public EstadoDisciplinaCurricularServiceImpl(
        EstadoDisciplinaCurricularRepository estadoDisciplinaCurricularRepository,
        EstadoDisciplinaCurricularMapper estadoDisciplinaCurricularMapper
    ) {
        this.estadoDisciplinaCurricularRepository = estadoDisciplinaCurricularRepository;
        this.estadoDisciplinaCurricularMapper = estadoDisciplinaCurricularMapper;
    }

    @Override
    public EstadoDisciplinaCurricularDTO save(EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO) {
        log.debug("Request to save EstadoDisciplinaCurricular : {}", estadoDisciplinaCurricularDTO);
        EstadoDisciplinaCurricular estadoDisciplinaCurricular = estadoDisciplinaCurricularMapper.toEntity(estadoDisciplinaCurricularDTO);
        estadoDisciplinaCurricular = estadoDisciplinaCurricularRepository.save(estadoDisciplinaCurricular);
        return estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricular);
    }

    @Override
    public EstadoDisciplinaCurricularDTO update(EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO) {
        log.debug("Request to update EstadoDisciplinaCurricular : {}", estadoDisciplinaCurricularDTO);
        EstadoDisciplinaCurricular estadoDisciplinaCurricular = estadoDisciplinaCurricularMapper.toEntity(estadoDisciplinaCurricularDTO);
        estadoDisciplinaCurricular = estadoDisciplinaCurricularRepository.save(estadoDisciplinaCurricular);
        return estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricular);
    }

    @Override
    public Optional<EstadoDisciplinaCurricularDTO> partialUpdate(EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO) {
        log.debug("Request to partially update EstadoDisciplinaCurricular : {}", estadoDisciplinaCurricularDTO);

        return estadoDisciplinaCurricularRepository
            .findById(estadoDisciplinaCurricularDTO.getId())
            .map(existingEstadoDisciplinaCurricular -> {
                estadoDisciplinaCurricularMapper.partialUpdate(existingEstadoDisciplinaCurricular, estadoDisciplinaCurricularDTO);

                return existingEstadoDisciplinaCurricular;
            })
            .map(estadoDisciplinaCurricularRepository::save)
            .map(estadoDisciplinaCurricularMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstadoDisciplinaCurricularDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstadoDisciplinaCurriculars");
        return estadoDisciplinaCurricularRepository.findAll(pageable).map(estadoDisciplinaCurricularMapper::toDto);
    }

    public Page<EstadoDisciplinaCurricularDTO> findAllWithEagerRelationships(Pageable pageable) {
        return estadoDisciplinaCurricularRepository.findAllWithEagerRelationships(pageable).map(estadoDisciplinaCurricularMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoDisciplinaCurricularDTO> findOne(Long id) {
        log.debug("Request to get EstadoDisciplinaCurricular : {}", id);
        return estadoDisciplinaCurricularRepository.findOneWithEagerRelationships(id).map(estadoDisciplinaCurricularMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstadoDisciplinaCurricular : {}", id);
        estadoDisciplinaCurricularRepository.deleteById(id);
    }

    public List<EstadoDisciplinaCurricularDTO> findAll() {
        var listaEstados = estadoDisciplinaCurricularRepository.findAll().stream().collect(Collectors.toList());
        return estadoDisciplinaCurricularMapper.toDto(listaEstados);
    }

    public void registrarEstadosDefault() {
        List<EstadoDisciplinaCurricularDTO> estadosDefault = new ArrayList<>();

        EstadoDisciplinaCurricularDTO aprovado = new EstadoDisciplinaCurricularDTO();
        aprovado.setClassificacao(CategoriaClassificacao.APROVADO);
        aprovado.setCodigo("APROVADO");
        aprovado.setDescricao("APROVADO");
        aprovado.setCor("Verde");
        aprovado.setValor(10D);

        estadosDefault.add(aprovado);

        EstadoDisciplinaCurricularDTO reprovado = new EstadoDisciplinaCurricularDTO();
        reprovado.setClassificacao(CategoriaClassificacao.REPROVADO);
        reprovado.setCodigo("REPROVADO");
        reprovado.setDescricao("REPROVADO");
        reprovado.setCor("Vermelho");
        reprovado.setValor(9D);
        estadosDefault.add(reprovado);

        for (var estado : estadosDefault) {
            if (estadoDisciplinaCurricularRepository.findByClassificacao(estado.getClassificacao()).isPresent()) {
                continue;
            }

            save(estado);
        }
    }
}
