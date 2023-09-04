package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.EncarregadoEducacao;
import com.ravunana.longonkelo.repository.EncarregadoEducacaoRepository;
import com.ravunana.longonkelo.service.EncarregadoEducacaoService;
import com.ravunana.longonkelo.service.dto.EncarregadoEducacaoDTO;
import com.ravunana.longonkelo.service.mapper.EncarregadoEducacaoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EncarregadoEducacao}.
 */
@Service
@Transactional
public class EncarregadoEducacaoServiceImpl implements EncarregadoEducacaoService {

    private final Logger log = LoggerFactory.getLogger(EncarregadoEducacaoServiceImpl.class);

    private final EncarregadoEducacaoRepository encarregadoEducacaoRepository;

    private final EncarregadoEducacaoMapper encarregadoEducacaoMapper;

    public EncarregadoEducacaoServiceImpl(
        EncarregadoEducacaoRepository encarregadoEducacaoRepository,
        EncarregadoEducacaoMapper encarregadoEducacaoMapper
    ) {
        this.encarregadoEducacaoRepository = encarregadoEducacaoRepository;
        this.encarregadoEducacaoMapper = encarregadoEducacaoMapper;
    }

    @Override
    public EncarregadoEducacaoDTO save(EncarregadoEducacaoDTO encarregadoEducacaoDTO) {
        log.debug("Request to save EncarregadoEducacao : {}", encarregadoEducacaoDTO);
        EncarregadoEducacao encarregadoEducacao = encarregadoEducacaoMapper.toEntity(encarregadoEducacaoDTO);
        encarregadoEducacao = encarregadoEducacaoRepository.save(encarregadoEducacao);
        return encarregadoEducacaoMapper.toDto(encarregadoEducacao);
    }

    @Override
    public EncarregadoEducacaoDTO update(EncarregadoEducacaoDTO encarregadoEducacaoDTO) {
        log.debug("Request to update EncarregadoEducacao : {}", encarregadoEducacaoDTO);
        EncarregadoEducacao encarregadoEducacao = encarregadoEducacaoMapper.toEntity(encarregadoEducacaoDTO);
        encarregadoEducacao = encarregadoEducacaoRepository.save(encarregadoEducacao);
        return encarregadoEducacaoMapper.toDto(encarregadoEducacao);
    }

    @Override
    public Optional<EncarregadoEducacaoDTO> partialUpdate(EncarregadoEducacaoDTO encarregadoEducacaoDTO) {
        log.debug("Request to partially update EncarregadoEducacao : {}", encarregadoEducacaoDTO);

        return encarregadoEducacaoRepository
            .findById(encarregadoEducacaoDTO.getId())
            .map(existingEncarregadoEducacao -> {
                encarregadoEducacaoMapper.partialUpdate(existingEncarregadoEducacao, encarregadoEducacaoDTO);

                return existingEncarregadoEducacao;
            })
            .map(encarregadoEducacaoRepository::save)
            .map(encarregadoEducacaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EncarregadoEducacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EncarregadoEducacaos");
        return encarregadoEducacaoRepository.findAll(pageable).map(encarregadoEducacaoMapper::toDto);
    }

    public Page<EncarregadoEducacaoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return encarregadoEducacaoRepository.findAllWithEagerRelationships(pageable).map(encarregadoEducacaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EncarregadoEducacaoDTO> findOne(Long id) {
        log.debug("Request to get EncarregadoEducacao : {}", id);
        return encarregadoEducacaoRepository.findOneWithEagerRelationships(id).map(encarregadoEducacaoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EncarregadoEducacao : {}", id);
        encarregadoEducacaoRepository.deleteById(id);
    }
}
