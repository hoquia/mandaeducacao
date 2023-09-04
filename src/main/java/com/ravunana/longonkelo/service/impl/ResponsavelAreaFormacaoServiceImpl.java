package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.ResponsavelAreaFormacao;
import com.ravunana.longonkelo.repository.ResponsavelAreaFormacaoRepository;
import com.ravunana.longonkelo.service.ResponsavelAreaFormacaoService;
import com.ravunana.longonkelo.service.dto.ResponsavelAreaFormacaoDTO;
import com.ravunana.longonkelo.service.mapper.ResponsavelAreaFormacaoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ResponsavelAreaFormacao}.
 */
@Service
@Transactional
public class ResponsavelAreaFormacaoServiceImpl implements ResponsavelAreaFormacaoService {

    private final Logger log = LoggerFactory.getLogger(ResponsavelAreaFormacaoServiceImpl.class);

    private final ResponsavelAreaFormacaoRepository responsavelAreaFormacaoRepository;

    private final ResponsavelAreaFormacaoMapper responsavelAreaFormacaoMapper;

    public ResponsavelAreaFormacaoServiceImpl(
        ResponsavelAreaFormacaoRepository responsavelAreaFormacaoRepository,
        ResponsavelAreaFormacaoMapper responsavelAreaFormacaoMapper
    ) {
        this.responsavelAreaFormacaoRepository = responsavelAreaFormacaoRepository;
        this.responsavelAreaFormacaoMapper = responsavelAreaFormacaoMapper;
    }

    @Override
    public ResponsavelAreaFormacaoDTO save(ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO) {
        log.debug("Request to save ResponsavelAreaFormacao : {}", responsavelAreaFormacaoDTO);
        ResponsavelAreaFormacao responsavelAreaFormacao = responsavelAreaFormacaoMapper.toEntity(responsavelAreaFormacaoDTO);
        responsavelAreaFormacao = responsavelAreaFormacaoRepository.save(responsavelAreaFormacao);
        return responsavelAreaFormacaoMapper.toDto(responsavelAreaFormacao);
    }

    @Override
    public ResponsavelAreaFormacaoDTO update(ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO) {
        log.debug("Request to update ResponsavelAreaFormacao : {}", responsavelAreaFormacaoDTO);
        ResponsavelAreaFormacao responsavelAreaFormacao = responsavelAreaFormacaoMapper.toEntity(responsavelAreaFormacaoDTO);
        responsavelAreaFormacao = responsavelAreaFormacaoRepository.save(responsavelAreaFormacao);
        return responsavelAreaFormacaoMapper.toDto(responsavelAreaFormacao);
    }

    @Override
    public Optional<ResponsavelAreaFormacaoDTO> partialUpdate(ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO) {
        log.debug("Request to partially update ResponsavelAreaFormacao : {}", responsavelAreaFormacaoDTO);

        return responsavelAreaFormacaoRepository
            .findById(responsavelAreaFormacaoDTO.getId())
            .map(existingResponsavelAreaFormacao -> {
                responsavelAreaFormacaoMapper.partialUpdate(existingResponsavelAreaFormacao, responsavelAreaFormacaoDTO);

                return existingResponsavelAreaFormacao;
            })
            .map(responsavelAreaFormacaoRepository::save)
            .map(responsavelAreaFormacaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsavelAreaFormacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResponsavelAreaFormacaos");
        return responsavelAreaFormacaoRepository.findAll(pageable).map(responsavelAreaFormacaoMapper::toDto);
    }

    public Page<ResponsavelAreaFormacaoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return responsavelAreaFormacaoRepository.findAllWithEagerRelationships(pageable).map(responsavelAreaFormacaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResponsavelAreaFormacaoDTO> findOne(Long id) {
        log.debug("Request to get ResponsavelAreaFormacao : {}", id);
        return responsavelAreaFormacaoRepository.findOneWithEagerRelationships(id).map(responsavelAreaFormacaoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResponsavelAreaFormacao : {}", id);
        responsavelAreaFormacaoRepository.deleteById(id);
    }
}
