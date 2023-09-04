package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.EstadoDissertacao;
import com.ravunana.longonkelo.repository.EstadoDissertacaoRepository;
import com.ravunana.longonkelo.service.EstadoDissertacaoService;
import com.ravunana.longonkelo.service.dto.EstadoDissertacaoDTO;
import com.ravunana.longonkelo.service.mapper.EstadoDissertacaoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EstadoDissertacao}.
 */
@Service
@Transactional
public class EstadoDissertacaoServiceImpl implements EstadoDissertacaoService {

    private final Logger log = LoggerFactory.getLogger(EstadoDissertacaoServiceImpl.class);

    private final EstadoDissertacaoRepository estadoDissertacaoRepository;

    private final EstadoDissertacaoMapper estadoDissertacaoMapper;

    public EstadoDissertacaoServiceImpl(
        EstadoDissertacaoRepository estadoDissertacaoRepository,
        EstadoDissertacaoMapper estadoDissertacaoMapper
    ) {
        this.estadoDissertacaoRepository = estadoDissertacaoRepository;
        this.estadoDissertacaoMapper = estadoDissertacaoMapper;
    }

    @Override
    public EstadoDissertacaoDTO save(EstadoDissertacaoDTO estadoDissertacaoDTO) {
        log.debug("Request to save EstadoDissertacao : {}", estadoDissertacaoDTO);
        EstadoDissertacao estadoDissertacao = estadoDissertacaoMapper.toEntity(estadoDissertacaoDTO);
        estadoDissertacao = estadoDissertacaoRepository.save(estadoDissertacao);
        return estadoDissertacaoMapper.toDto(estadoDissertacao);
    }

    @Override
    public EstadoDissertacaoDTO update(EstadoDissertacaoDTO estadoDissertacaoDTO) {
        log.debug("Request to update EstadoDissertacao : {}", estadoDissertacaoDTO);
        EstadoDissertacao estadoDissertacao = estadoDissertacaoMapper.toEntity(estadoDissertacaoDTO);
        estadoDissertacao = estadoDissertacaoRepository.save(estadoDissertacao);
        return estadoDissertacaoMapper.toDto(estadoDissertacao);
    }

    @Override
    public Optional<EstadoDissertacaoDTO> partialUpdate(EstadoDissertacaoDTO estadoDissertacaoDTO) {
        log.debug("Request to partially update EstadoDissertacao : {}", estadoDissertacaoDTO);

        return estadoDissertacaoRepository
            .findById(estadoDissertacaoDTO.getId())
            .map(existingEstadoDissertacao -> {
                estadoDissertacaoMapper.partialUpdate(existingEstadoDissertacao, estadoDissertacaoDTO);

                return existingEstadoDissertacao;
            })
            .map(estadoDissertacaoRepository::save)
            .map(estadoDissertacaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstadoDissertacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstadoDissertacaos");
        return estadoDissertacaoRepository.findAll(pageable).map(estadoDissertacaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoDissertacaoDTO> findOne(Long id) {
        log.debug("Request to get EstadoDissertacao : {}", id);
        return estadoDissertacaoRepository.findById(id).map(estadoDissertacaoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstadoDissertacao : {}", id);
        estadoDissertacaoRepository.deleteById(id);
    }
}
