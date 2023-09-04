package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.ProvedorNotificacao;
import com.ravunana.longonkelo.repository.ProvedorNotificacaoRepository;
import com.ravunana.longonkelo.service.ProvedorNotificacaoService;
import com.ravunana.longonkelo.service.dto.ProvedorNotificacaoDTO;
import com.ravunana.longonkelo.service.mapper.ProvedorNotificacaoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProvedorNotificacao}.
 */
@Service
@Transactional
public class ProvedorNotificacaoServiceImpl implements ProvedorNotificacaoService {

    private final Logger log = LoggerFactory.getLogger(ProvedorNotificacaoServiceImpl.class);

    private final ProvedorNotificacaoRepository provedorNotificacaoRepository;

    private final ProvedorNotificacaoMapper provedorNotificacaoMapper;

    public ProvedorNotificacaoServiceImpl(
        ProvedorNotificacaoRepository provedorNotificacaoRepository,
        ProvedorNotificacaoMapper provedorNotificacaoMapper
    ) {
        this.provedorNotificacaoRepository = provedorNotificacaoRepository;
        this.provedorNotificacaoMapper = provedorNotificacaoMapper;
    }

    @Override
    public ProvedorNotificacaoDTO save(ProvedorNotificacaoDTO provedorNotificacaoDTO) {
        log.debug("Request to save ProvedorNotificacao : {}", provedorNotificacaoDTO);
        ProvedorNotificacao provedorNotificacao = provedorNotificacaoMapper.toEntity(provedorNotificacaoDTO);
        provedorNotificacao = provedorNotificacaoRepository.save(provedorNotificacao);
        return provedorNotificacaoMapper.toDto(provedorNotificacao);
    }

    @Override
    public ProvedorNotificacaoDTO update(ProvedorNotificacaoDTO provedorNotificacaoDTO) {
        log.debug("Request to update ProvedorNotificacao : {}", provedorNotificacaoDTO);
        ProvedorNotificacao provedorNotificacao = provedorNotificacaoMapper.toEntity(provedorNotificacaoDTO);
        provedorNotificacao = provedorNotificacaoRepository.save(provedorNotificacao);
        return provedorNotificacaoMapper.toDto(provedorNotificacao);
    }

    @Override
    public Optional<ProvedorNotificacaoDTO> partialUpdate(ProvedorNotificacaoDTO provedorNotificacaoDTO) {
        log.debug("Request to partially update ProvedorNotificacao : {}", provedorNotificacaoDTO);

        return provedorNotificacaoRepository
            .findById(provedorNotificacaoDTO.getId())
            .map(existingProvedorNotificacao -> {
                provedorNotificacaoMapper.partialUpdate(existingProvedorNotificacao, provedorNotificacaoDTO);

                return existingProvedorNotificacao;
            })
            .map(provedorNotificacaoRepository::save)
            .map(provedorNotificacaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProvedorNotificacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProvedorNotificacaos");
        return provedorNotificacaoRepository.findAll(pageable).map(provedorNotificacaoMapper::toDto);
    }

    public Page<ProvedorNotificacaoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return provedorNotificacaoRepository.findAllWithEagerRelationships(pageable).map(provedorNotificacaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProvedorNotificacaoDTO> findOne(Long id) {
        log.debug("Request to get ProvedorNotificacao : {}", id);
        return provedorNotificacaoRepository.findOneWithEagerRelationships(id).map(provedorNotificacaoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProvedorNotificacao : {}", id);
        provedorNotificacaoRepository.deleteById(id);
    }
}
