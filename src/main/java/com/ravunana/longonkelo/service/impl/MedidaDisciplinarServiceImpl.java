package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.MedidaDisciplinar;
import com.ravunana.longonkelo.repository.MedidaDisciplinarRepository;
import com.ravunana.longonkelo.service.MedidaDisciplinarService;
import com.ravunana.longonkelo.service.dto.MedidaDisciplinarDTO;
import com.ravunana.longonkelo.service.mapper.MedidaDisciplinarMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MedidaDisciplinar}.
 */
@Service
@Transactional
public class MedidaDisciplinarServiceImpl implements MedidaDisciplinarService {

    private final Logger log = LoggerFactory.getLogger(MedidaDisciplinarServiceImpl.class);

    private final MedidaDisciplinarRepository medidaDisciplinarRepository;

    private final MedidaDisciplinarMapper medidaDisciplinarMapper;

    public MedidaDisciplinarServiceImpl(
        MedidaDisciplinarRepository medidaDisciplinarRepository,
        MedidaDisciplinarMapper medidaDisciplinarMapper
    ) {
        this.medidaDisciplinarRepository = medidaDisciplinarRepository;
        this.medidaDisciplinarMapper = medidaDisciplinarMapper;
    }

    @Override
    public MedidaDisciplinarDTO save(MedidaDisciplinarDTO medidaDisciplinarDTO) {
        log.debug("Request to save MedidaDisciplinar : {}", medidaDisciplinarDTO);
        MedidaDisciplinar medidaDisciplinar = medidaDisciplinarMapper.toEntity(medidaDisciplinarDTO);
        medidaDisciplinar = medidaDisciplinarRepository.save(medidaDisciplinar);
        return medidaDisciplinarMapper.toDto(medidaDisciplinar);
    }

    @Override
    public MedidaDisciplinarDTO update(MedidaDisciplinarDTO medidaDisciplinarDTO) {
        log.debug("Request to update MedidaDisciplinar : {}", medidaDisciplinarDTO);
        MedidaDisciplinar medidaDisciplinar = medidaDisciplinarMapper.toEntity(medidaDisciplinarDTO);
        medidaDisciplinar = medidaDisciplinarRepository.save(medidaDisciplinar);
        return medidaDisciplinarMapper.toDto(medidaDisciplinar);
    }

    @Override
    public Optional<MedidaDisciplinarDTO> partialUpdate(MedidaDisciplinarDTO medidaDisciplinarDTO) {
        log.debug("Request to partially update MedidaDisciplinar : {}", medidaDisciplinarDTO);

        return medidaDisciplinarRepository
            .findById(medidaDisciplinarDTO.getId())
            .map(existingMedidaDisciplinar -> {
                medidaDisciplinarMapper.partialUpdate(existingMedidaDisciplinar, medidaDisciplinarDTO);

                return existingMedidaDisciplinar;
            })
            .map(medidaDisciplinarRepository::save)
            .map(medidaDisciplinarMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedidaDisciplinarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MedidaDisciplinars");
        return medidaDisciplinarRepository.findAll(pageable).map(medidaDisciplinarMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedidaDisciplinarDTO> findOne(Long id) {
        log.debug("Request to get MedidaDisciplinar : {}", id);
        return medidaDisciplinarRepository.findById(id).map(medidaDisciplinarMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedidaDisciplinar : {}", id);
        medidaDisciplinarRepository.deleteById(id);
    }
}
