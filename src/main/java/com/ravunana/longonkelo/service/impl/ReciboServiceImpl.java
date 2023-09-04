package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.Recibo;
import com.ravunana.longonkelo.repository.ReciboRepository;
import com.ravunana.longonkelo.service.ReciboService;
import com.ravunana.longonkelo.service.dto.ReciboDTO;
import com.ravunana.longonkelo.service.mapper.ReciboMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Recibo}.
 */
@Service
@Transactional
public class ReciboServiceImpl implements ReciboService {

    private final Logger log = LoggerFactory.getLogger(ReciboServiceImpl.class);

    private final ReciboRepository reciboRepository;

    private final ReciboMapper reciboMapper;

    public ReciboServiceImpl(ReciboRepository reciboRepository, ReciboMapper reciboMapper) {
        this.reciboRepository = reciboRepository;
        this.reciboMapper = reciboMapper;
    }

    @Override
    public ReciboDTO save(ReciboDTO reciboDTO) {
        log.debug("Request to save Recibo : {}", reciboDTO);
        Recibo recibo = reciboMapper.toEntity(reciboDTO);
        recibo = reciboRepository.save(recibo);
        return reciboMapper.toDto(recibo);
    }

    @Override
    public ReciboDTO update(ReciboDTO reciboDTO) {
        log.debug("Request to update Recibo : {}", reciboDTO);
        Recibo recibo = reciboMapper.toEntity(reciboDTO);
        recibo = reciboRepository.save(recibo);
        return reciboMapper.toDto(recibo);
    }

    @Override
    public Optional<ReciboDTO> partialUpdate(ReciboDTO reciboDTO) {
        log.debug("Request to partially update Recibo : {}", reciboDTO);

        return reciboRepository
            .findById(reciboDTO.getId())
            .map(existingRecibo -> {
                reciboMapper.partialUpdate(existingRecibo, reciboDTO);

                return existingRecibo;
            })
            .map(reciboRepository::save)
            .map(reciboMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReciboDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recibos");
        return reciboRepository.findAll(pageable).map(reciboMapper::toDto);
    }

    public Page<ReciboDTO> findAllWithEagerRelationships(Pageable pageable) {
        return reciboRepository.findAllWithEagerRelationships(pageable).map(reciboMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReciboDTO> findOne(Long id) {
        log.debug("Request to get Recibo : {}", id);
        return reciboRepository.findOneWithEagerRelationships(id).map(reciboMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Recibo : {}", id);
        reciboRepository.deleteById(id);
    }
}
