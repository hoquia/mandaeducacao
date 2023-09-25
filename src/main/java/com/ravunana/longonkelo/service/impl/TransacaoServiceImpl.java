package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.Transacao;
import com.ravunana.longonkelo.domain.enumeration.EstadoPagamento;
import com.ravunana.longonkelo.repository.TransacaoRepository;
import com.ravunana.longonkelo.service.TransacaoService;
import com.ravunana.longonkelo.service.dto.ReciboDTO;
import com.ravunana.longonkelo.service.dto.TransacaoDTO;
import com.ravunana.longonkelo.service.mapper.TransacaoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Transacao}.
 */
@Service
@Transactional
public class TransacaoServiceImpl implements TransacaoService {

    private final Logger log = LoggerFactory.getLogger(TransacaoServiceImpl.class);

    private final TransacaoRepository transacaoRepository;

    private final TransacaoMapper transacaoMapper;
    private final ReciboServiceImpl reciboService;
    private final DocumentoComercialServiceImpl documentoComercialService;

    public TransacaoServiceImpl(
        TransacaoRepository transacaoRepository,
        TransacaoMapper transacaoMapper,
        ReciboServiceImpl reciboService,
        DocumentoComercialServiceImpl documentoComercialService
    ) {
        this.transacaoRepository = transacaoRepository;
        this.transacaoMapper = transacaoMapper;
        this.reciboService = reciboService;
        this.documentoComercialService = documentoComercialService;
    }

    @Override
    public TransacaoDTO save(TransacaoDTO transacaoDTO) {
        log.debug("Request to save Transacao : {}", transacaoDTO);
        Transacao transacao = transacaoMapper.toEntity(transacaoDTO);
        transacao = transacaoRepository.save(transacao);

        if (transacao.getEstado().equals(EstadoPagamento.VALIDO)) {
            var recibo = new ReciboDTO();
            recibo.setMatricula(transacaoDTO.getMatricula());
            recibo.setTransacao(transacaoMapper.toDto(transacao));
            var docRecibo = documentoComercialService.getDocumentoComercialReciboPadrao();
            recibo.setDocumentoComercial(docRecibo);

            reciboService.save(recibo);
        }

        return transacaoMapper.toDto(transacao);
    }

    @Override
    public TransacaoDTO update(TransacaoDTO transacaoDTO) {
        log.debug("Request to update Transacao : {}", transacaoDTO);
        Transacao transacao = transacaoMapper.toEntity(transacaoDTO);
        transacao = transacaoRepository.save(transacao);
        return transacaoMapper.toDto(transacao);
    }

    @Override
    public Optional<TransacaoDTO> partialUpdate(TransacaoDTO transacaoDTO) {
        log.debug("Request to partially update Transacao : {}", transacaoDTO);

        return transacaoRepository
            .findById(transacaoDTO.getId())
            .map(existingTransacao -> {
                transacaoMapper.partialUpdate(existingTransacao, transacaoDTO);

                return existingTransacao;
            })
            .map(transacaoRepository::save)
            .map(transacaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Transacaos");
        return transacaoRepository.findAll(pageable).map(transacaoMapper::toDto);
    }

    public Page<TransacaoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return transacaoRepository.findAllWithEagerRelationships(pageable).map(transacaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransacaoDTO> findOne(Long id) {
        log.debug("Request to get Transacao : {}", id);
        return transacaoRepository.findOneWithEagerRelationships(id).map(transacaoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transacao : {}", id);
        transacaoRepository.deleteById(id);
    }

    public TransacaoDTO getUltimaTransacaoMatricula(Long matriculaID) {
        var result = transacaoRepository.findAll().stream().filter(x -> x.getMatricula().getId().equals(matriculaID)).findFirst();

        return transacaoMapper.toDto(result.get());
    }
}
