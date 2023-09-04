package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.AplicacaoRecibo;
import com.ravunana.longonkelo.repository.AplicacaoReciboRepository;
import com.ravunana.longonkelo.service.AplicacaoReciboService;
import com.ravunana.longonkelo.service.dto.AplicacaoReciboDTO;
import com.ravunana.longonkelo.service.mapper.AplicacaoReciboMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AplicacaoRecibo}.
 */
@Service
@Transactional
public class AplicacaoReciboServiceImpl implements AplicacaoReciboService {

    private final Logger log = LoggerFactory.getLogger(AplicacaoReciboServiceImpl.class);

    private final AplicacaoReciboRepository aplicacaoReciboRepository;

    private final AplicacaoReciboMapper aplicacaoReciboMapper;

    public AplicacaoReciboServiceImpl(AplicacaoReciboRepository aplicacaoReciboRepository, AplicacaoReciboMapper aplicacaoReciboMapper) {
        this.aplicacaoReciboRepository = aplicacaoReciboRepository;
        this.aplicacaoReciboMapper = aplicacaoReciboMapper;
    }

    @Override
    public AplicacaoReciboDTO save(AplicacaoReciboDTO aplicacaoReciboDTO) {
        log.debug("Request to save AplicacaoRecibo : {}", aplicacaoReciboDTO);
        AplicacaoRecibo aplicacaoRecibo = aplicacaoReciboMapper.toEntity(aplicacaoReciboDTO);
        aplicacaoRecibo = aplicacaoReciboRepository.save(aplicacaoRecibo);
        return aplicacaoReciboMapper.toDto(aplicacaoRecibo);
    }

    @Override
    public AplicacaoReciboDTO update(AplicacaoReciboDTO aplicacaoReciboDTO) {
        log.debug("Request to update AplicacaoRecibo : {}", aplicacaoReciboDTO);
        AplicacaoRecibo aplicacaoRecibo = aplicacaoReciboMapper.toEntity(aplicacaoReciboDTO);
        aplicacaoRecibo = aplicacaoReciboRepository.save(aplicacaoRecibo);
        return aplicacaoReciboMapper.toDto(aplicacaoRecibo);
    }

    @Override
    public Optional<AplicacaoReciboDTO> partialUpdate(AplicacaoReciboDTO aplicacaoReciboDTO) {
        log.debug("Request to partially update AplicacaoRecibo : {}", aplicacaoReciboDTO);

        return aplicacaoReciboRepository
            .findById(aplicacaoReciboDTO.getId())
            .map(existingAplicacaoRecibo -> {
                aplicacaoReciboMapper.partialUpdate(existingAplicacaoRecibo, aplicacaoReciboDTO);

                return existingAplicacaoRecibo;
            })
            .map(aplicacaoReciboRepository::save)
            .map(aplicacaoReciboMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AplicacaoReciboDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AplicacaoRecibos");
        return aplicacaoReciboRepository.findAll(pageable).map(aplicacaoReciboMapper::toDto);
    }

    public Page<AplicacaoReciboDTO> findAllWithEagerRelationships(Pageable pageable) {
        return aplicacaoReciboRepository.findAllWithEagerRelationships(pageable).map(aplicacaoReciboMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AplicacaoReciboDTO> findOne(Long id) {
        log.debug("Request to get AplicacaoRecibo : {}", id);
        return aplicacaoReciboRepository.findOneWithEagerRelationships(id).map(aplicacaoReciboMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AplicacaoRecibo : {}", id);
        aplicacaoReciboRepository.deleteById(id);
    }
}
