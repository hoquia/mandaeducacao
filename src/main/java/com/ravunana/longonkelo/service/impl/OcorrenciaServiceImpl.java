package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.config.SendSmsInfobip;
import com.ravunana.longonkelo.domain.Ocorrencia;
import com.ravunana.longonkelo.repository.OcorrenciaRepository;
import com.ravunana.longonkelo.service.OcorrenciaService;
import com.ravunana.longonkelo.service.dto.OcorrenciaDTO;
import com.ravunana.longonkelo.service.mapper.OcorrenciaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ocorrencia}.
 */
@Service
@Transactional
public class OcorrenciaServiceImpl implements OcorrenciaService {

    private final Logger log = LoggerFactory.getLogger(OcorrenciaServiceImpl.class);

    private final OcorrenciaRepository ocorrenciaRepository;

    private final OcorrenciaMapper ocorrenciaMapper;
    private final SendSmsInfobip smsInfobip;

    public OcorrenciaServiceImpl(OcorrenciaRepository ocorrenciaRepository, OcorrenciaMapper ocorrenciaMapper, SendSmsInfobip smsInfobip) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.ocorrenciaMapper = ocorrenciaMapper;
        this.smsInfobip = smsInfobip;
    }

    @Override
    public OcorrenciaDTO save(OcorrenciaDTO ocorrenciaDTO) {
        log.debug("Request to save Ocorrencia : {}", ocorrenciaDTO);

        String descricao = ocorrenciaDTO.getDescricao();

        Ocorrencia ocorrencia = ocorrenciaMapper.toEntity(ocorrenciaDTO);
        String telefone = ocorrencia.getMatricula().getDiscente().getTelefoneParente();
        ocorrencia = ocorrenciaRepository.save(ocorrencia);

        smsInfobip.send( telefone, descricao );

        return ocorrenciaMapper.toDto(ocorrencia);
    }

    @Override
    public OcorrenciaDTO update(OcorrenciaDTO ocorrenciaDTO) {
        log.debug("Request to update Ocorrencia : {}", ocorrenciaDTO);
        Ocorrencia ocorrencia = ocorrenciaMapper.toEntity(ocorrenciaDTO);
        ocorrencia = ocorrenciaRepository.save(ocorrencia);
        return ocorrenciaMapper.toDto(ocorrencia);
    }

    @Override
    public Optional<OcorrenciaDTO> partialUpdate(OcorrenciaDTO ocorrenciaDTO) {
        log.debug("Request to partially update Ocorrencia : {}", ocorrenciaDTO);

        return ocorrenciaRepository
            .findById(ocorrenciaDTO.getId())
            .map(existingOcorrencia -> {
                ocorrenciaMapper.partialUpdate(existingOcorrencia, ocorrenciaDTO);

                return existingOcorrencia;
            })
            .map(ocorrenciaRepository::save)
            .map(ocorrenciaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OcorrenciaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ocorrencias");
        return ocorrenciaRepository.findAll(pageable).map(ocorrenciaMapper::toDto);
    }

    public Page<OcorrenciaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ocorrenciaRepository.findAllWithEagerRelationships(pageable).map(ocorrenciaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OcorrenciaDTO> findOne(Long id) {
        log.debug("Request to get Ocorrencia : {}", id);
        return ocorrenciaRepository.findOneWithEagerRelationships(id).map(ocorrenciaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ocorrencia : {}", id);
        ocorrenciaRepository.deleteById(id);
    }
}
