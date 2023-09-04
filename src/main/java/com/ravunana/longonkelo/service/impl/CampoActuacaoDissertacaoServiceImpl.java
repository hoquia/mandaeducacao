package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.CampoActuacaoDissertacao;
import com.ravunana.longonkelo.repository.CampoActuacaoDissertacaoRepository;
import com.ravunana.longonkelo.service.CampoActuacaoDissertacaoService;
import com.ravunana.longonkelo.service.dto.CampoActuacaoDissertacaoDTO;
import com.ravunana.longonkelo.service.mapper.CampoActuacaoDissertacaoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CampoActuacaoDissertacao}.
 */
@Service
@Transactional
public class CampoActuacaoDissertacaoServiceImpl implements CampoActuacaoDissertacaoService {

    private final Logger log = LoggerFactory.getLogger(CampoActuacaoDissertacaoServiceImpl.class);

    private final CampoActuacaoDissertacaoRepository campoActuacaoDissertacaoRepository;

    private final CampoActuacaoDissertacaoMapper campoActuacaoDissertacaoMapper;

    public CampoActuacaoDissertacaoServiceImpl(
        CampoActuacaoDissertacaoRepository campoActuacaoDissertacaoRepository,
        CampoActuacaoDissertacaoMapper campoActuacaoDissertacaoMapper
    ) {
        this.campoActuacaoDissertacaoRepository = campoActuacaoDissertacaoRepository;
        this.campoActuacaoDissertacaoMapper = campoActuacaoDissertacaoMapper;
    }

    @Override
    public CampoActuacaoDissertacaoDTO save(CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO) {
        log.debug("Request to save CampoActuacaoDissertacao : {}", campoActuacaoDissertacaoDTO);
        CampoActuacaoDissertacao campoActuacaoDissertacao = campoActuacaoDissertacaoMapper.toEntity(campoActuacaoDissertacaoDTO);
        campoActuacaoDissertacao = campoActuacaoDissertacaoRepository.save(campoActuacaoDissertacao);
        return campoActuacaoDissertacaoMapper.toDto(campoActuacaoDissertacao);
    }

    @Override
    public CampoActuacaoDissertacaoDTO update(CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO) {
        log.debug("Request to update CampoActuacaoDissertacao : {}", campoActuacaoDissertacaoDTO);
        CampoActuacaoDissertacao campoActuacaoDissertacao = campoActuacaoDissertacaoMapper.toEntity(campoActuacaoDissertacaoDTO);
        campoActuacaoDissertacao = campoActuacaoDissertacaoRepository.save(campoActuacaoDissertacao);
        return campoActuacaoDissertacaoMapper.toDto(campoActuacaoDissertacao);
    }

    @Override
    public Optional<CampoActuacaoDissertacaoDTO> partialUpdate(CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO) {
        log.debug("Request to partially update CampoActuacaoDissertacao : {}", campoActuacaoDissertacaoDTO);

        return campoActuacaoDissertacaoRepository
            .findById(campoActuacaoDissertacaoDTO.getId())
            .map(existingCampoActuacaoDissertacao -> {
                campoActuacaoDissertacaoMapper.partialUpdate(existingCampoActuacaoDissertacao, campoActuacaoDissertacaoDTO);

                return existingCampoActuacaoDissertacao;
            })
            .map(campoActuacaoDissertacaoRepository::save)
            .map(campoActuacaoDissertacaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CampoActuacaoDissertacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CampoActuacaoDissertacaos");
        return campoActuacaoDissertacaoRepository.findAll(pageable).map(campoActuacaoDissertacaoMapper::toDto);
    }

    public Page<CampoActuacaoDissertacaoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return campoActuacaoDissertacaoRepository.findAllWithEagerRelationships(pageable).map(campoActuacaoDissertacaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CampoActuacaoDissertacaoDTO> findOne(Long id) {
        log.debug("Request to get CampoActuacaoDissertacao : {}", id);
        return campoActuacaoDissertacaoRepository.findOneWithEagerRelationships(id).map(campoActuacaoDissertacaoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CampoActuacaoDissertacao : {}", id);
        campoActuacaoDissertacaoRepository.deleteById(id);
    }
}
