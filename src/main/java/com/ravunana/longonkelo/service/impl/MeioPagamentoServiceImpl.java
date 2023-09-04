package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.MeioPagamento;
import com.ravunana.longonkelo.repository.MeioPagamentoRepository;
import com.ravunana.longonkelo.service.MeioPagamentoService;
import com.ravunana.longonkelo.service.dto.MeioPagamentoDTO;
import com.ravunana.longonkelo.service.mapper.MeioPagamentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MeioPagamento}.
 */
@Service
@Transactional
public class MeioPagamentoServiceImpl implements MeioPagamentoService {

    private final Logger log = LoggerFactory.getLogger(MeioPagamentoServiceImpl.class);

    private final MeioPagamentoRepository meioPagamentoRepository;

    private final MeioPagamentoMapper meioPagamentoMapper;

    public MeioPagamentoServiceImpl(MeioPagamentoRepository meioPagamentoRepository, MeioPagamentoMapper meioPagamentoMapper) {
        this.meioPagamentoRepository = meioPagamentoRepository;
        this.meioPagamentoMapper = meioPagamentoMapper;
    }

    @Override
    public MeioPagamentoDTO save(MeioPagamentoDTO meioPagamentoDTO) {
        log.debug("Request to save MeioPagamento : {}", meioPagamentoDTO);
        MeioPagamento meioPagamento = meioPagamentoMapper.toEntity(meioPagamentoDTO);
        meioPagamento = meioPagamentoRepository.save(meioPagamento);
        return meioPagamentoMapper.toDto(meioPagamento);
    }

    @Override
    public MeioPagamentoDTO update(MeioPagamentoDTO meioPagamentoDTO) {
        log.debug("Request to update MeioPagamento : {}", meioPagamentoDTO);
        MeioPagamento meioPagamento = meioPagamentoMapper.toEntity(meioPagamentoDTO);
        meioPagamento = meioPagamentoRepository.save(meioPagamento);
        return meioPagamentoMapper.toDto(meioPagamento);
    }

    @Override
    public Optional<MeioPagamentoDTO> partialUpdate(MeioPagamentoDTO meioPagamentoDTO) {
        log.debug("Request to partially update MeioPagamento : {}", meioPagamentoDTO);

        return meioPagamentoRepository
            .findById(meioPagamentoDTO.getId())
            .map(existingMeioPagamento -> {
                meioPagamentoMapper.partialUpdate(existingMeioPagamento, meioPagamentoDTO);

                return existingMeioPagamento;
            })
            .map(meioPagamentoRepository::save)
            .map(meioPagamentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MeioPagamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MeioPagamentos");
        return meioPagamentoRepository.findAll(pageable).map(meioPagamentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MeioPagamentoDTO> findOne(Long id) {
        log.debug("Request to get MeioPagamento : {}", id);
        return meioPagamentoRepository.findById(id).map(meioPagamentoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MeioPagamento : {}", id);
        meioPagamentoRepository.deleteById(id);
    }
}
