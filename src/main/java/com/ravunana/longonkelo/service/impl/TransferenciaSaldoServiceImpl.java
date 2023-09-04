package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.TransferenciaSaldo;
import com.ravunana.longonkelo.repository.TransferenciaSaldoRepository;
import com.ravunana.longonkelo.service.TransferenciaSaldoService;
import com.ravunana.longonkelo.service.dto.TransferenciaSaldoDTO;
import com.ravunana.longonkelo.service.mapper.TransferenciaSaldoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TransferenciaSaldo}.
 */
@Service
@Transactional
public class TransferenciaSaldoServiceImpl implements TransferenciaSaldoService {

    private final Logger log = LoggerFactory.getLogger(TransferenciaSaldoServiceImpl.class);

    private final TransferenciaSaldoRepository transferenciaSaldoRepository;

    private final TransferenciaSaldoMapper transferenciaSaldoMapper;

    public TransferenciaSaldoServiceImpl(
        TransferenciaSaldoRepository transferenciaSaldoRepository,
        TransferenciaSaldoMapper transferenciaSaldoMapper
    ) {
        this.transferenciaSaldoRepository = transferenciaSaldoRepository;
        this.transferenciaSaldoMapper = transferenciaSaldoMapper;
    }

    @Override
    public TransferenciaSaldoDTO save(TransferenciaSaldoDTO transferenciaSaldoDTO) {
        log.debug("Request to save TransferenciaSaldo : {}", transferenciaSaldoDTO);
        TransferenciaSaldo transferenciaSaldo = transferenciaSaldoMapper.toEntity(transferenciaSaldoDTO);
        transferenciaSaldo = transferenciaSaldoRepository.save(transferenciaSaldo);
        return transferenciaSaldoMapper.toDto(transferenciaSaldo);
    }

    @Override
    public TransferenciaSaldoDTO update(TransferenciaSaldoDTO transferenciaSaldoDTO) {
        log.debug("Request to update TransferenciaSaldo : {}", transferenciaSaldoDTO);
        TransferenciaSaldo transferenciaSaldo = transferenciaSaldoMapper.toEntity(transferenciaSaldoDTO);
        transferenciaSaldo = transferenciaSaldoRepository.save(transferenciaSaldo);
        return transferenciaSaldoMapper.toDto(transferenciaSaldo);
    }

    @Override
    public Optional<TransferenciaSaldoDTO> partialUpdate(TransferenciaSaldoDTO transferenciaSaldoDTO) {
        log.debug("Request to partially update TransferenciaSaldo : {}", transferenciaSaldoDTO);

        return transferenciaSaldoRepository
            .findById(transferenciaSaldoDTO.getId())
            .map(existingTransferenciaSaldo -> {
                transferenciaSaldoMapper.partialUpdate(existingTransferenciaSaldo, transferenciaSaldoDTO);

                return existingTransferenciaSaldo;
            })
            .map(transferenciaSaldoRepository::save)
            .map(transferenciaSaldoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransferenciaSaldoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransferenciaSaldos");
        return transferenciaSaldoRepository.findAll(pageable).map(transferenciaSaldoMapper::toDto);
    }

    public Page<TransferenciaSaldoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return transferenciaSaldoRepository.findAllWithEagerRelationships(pageable).map(transferenciaSaldoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransferenciaSaldoDTO> findOne(Long id) {
        log.debug("Request to get TransferenciaSaldo : {}", id);
        return transferenciaSaldoRepository.findOneWithEagerRelationships(id).map(transferenciaSaldoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransferenciaSaldo : {}", id);
        transferenciaSaldoRepository.deleteById(id);
    }
}
