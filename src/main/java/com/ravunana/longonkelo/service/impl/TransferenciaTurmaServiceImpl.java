package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.TransferenciaTurma;
import com.ravunana.longonkelo.repository.TransferenciaTurmaRepository;
import com.ravunana.longonkelo.service.TransferenciaTurmaService;
import com.ravunana.longonkelo.service.dto.TransferenciaTurmaDTO;
import com.ravunana.longonkelo.service.mapper.TransferenciaTurmaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TransferenciaTurma}.
 */
@Service
@Transactional
public class TransferenciaTurmaServiceImpl implements TransferenciaTurmaService {

    private final Logger log = LoggerFactory.getLogger(TransferenciaTurmaServiceImpl.class);

    private final TransferenciaTurmaRepository transferenciaTurmaRepository;

    private final TransferenciaTurmaMapper transferenciaTurmaMapper;

    public TransferenciaTurmaServiceImpl(
        TransferenciaTurmaRepository transferenciaTurmaRepository,
        TransferenciaTurmaMapper transferenciaTurmaMapper
    ) {
        this.transferenciaTurmaRepository = transferenciaTurmaRepository;
        this.transferenciaTurmaMapper = transferenciaTurmaMapper;
    }

    @Override
    public TransferenciaTurmaDTO save(TransferenciaTurmaDTO transferenciaTurmaDTO) {
        log.debug("Request to save TransferenciaTurma : {}", transferenciaTurmaDTO);
        TransferenciaTurma transferenciaTurma = transferenciaTurmaMapper.toEntity(transferenciaTurmaDTO);
        transferenciaTurma = transferenciaTurmaRepository.save(transferenciaTurma);
        return transferenciaTurmaMapper.toDto(transferenciaTurma);
    }

    @Override
    public TransferenciaTurmaDTO update(TransferenciaTurmaDTO transferenciaTurmaDTO) {
        log.debug("Request to update TransferenciaTurma : {}", transferenciaTurmaDTO);
        TransferenciaTurma transferenciaTurma = transferenciaTurmaMapper.toEntity(transferenciaTurmaDTO);
        transferenciaTurma = transferenciaTurmaRepository.save(transferenciaTurma);
        return transferenciaTurmaMapper.toDto(transferenciaTurma);
    }

    @Override
    public Optional<TransferenciaTurmaDTO> partialUpdate(TransferenciaTurmaDTO transferenciaTurmaDTO) {
        log.debug("Request to partially update TransferenciaTurma : {}", transferenciaTurmaDTO);

        return transferenciaTurmaRepository
            .findById(transferenciaTurmaDTO.getId())
            .map(existingTransferenciaTurma -> {
                transferenciaTurmaMapper.partialUpdate(existingTransferenciaTurma, transferenciaTurmaDTO);

                return existingTransferenciaTurma;
            })
            .map(transferenciaTurmaRepository::save)
            .map(transferenciaTurmaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransferenciaTurmaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransferenciaTurmas");
        return transferenciaTurmaRepository.findAll(pageable).map(transferenciaTurmaMapper::toDto);
    }

    public Page<TransferenciaTurmaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return transferenciaTurmaRepository.findAllWithEagerRelationships(pageable).map(transferenciaTurmaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransferenciaTurmaDTO> findOne(Long id) {
        log.debug("Request to get TransferenciaTurma : {}", id);
        return transferenciaTurmaRepository.findOneWithEagerRelationships(id).map(transferenciaTurmaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransferenciaTurma : {}", id);
        transferenciaTurmaRepository.deleteById(id);
    }
}
