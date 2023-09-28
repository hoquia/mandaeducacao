package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.config.Constants;
import com.ravunana.longonkelo.domain.Recibo;
import com.ravunana.longonkelo.domain.enumeration.DocumentoFiscal;
import com.ravunana.longonkelo.domain.enumeration.EstadoDocumentoComercial;
import com.ravunana.longonkelo.repository.ReciboRepository;
import com.ravunana.longonkelo.service.ReciboService;
import com.ravunana.longonkelo.service.dto.ReciboDTO;
import com.ravunana.longonkelo.service.mapper.ReciboMapper;
import java.math.BigDecimal;
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
    private final DocumentoComercialServiceImpl documentoComercialService;
    private final AnoLectivoServiceImpl anoLectivoService;

    public ReciboServiceImpl(
        ReciboRepository reciboRepository,
        ReciboMapper reciboMapper,
        DocumentoComercialServiceImpl documentoComercialService,
        AnoLectivoServiceImpl anoLectivoService
    ) {
        this.reciboRepository = reciboRepository;
        this.reciboMapper = reciboMapper;
        this.documentoComercialService = documentoComercialService;
        this.anoLectivoService = anoLectivoService;
    }

    @Override
    public ReciboDTO save(ReciboDTO reciboDTO) {
        log.debug("Request to save Recibo : {}", reciboDTO);
        var anoLectivo = anoLectivoService.getAnoLectivoActual();

        var serieDocumentoComercial = documentoComercialService.getSerieDocumentoComercialActivoByTipoFiscal(
            anoLectivo.getAno(),
            DocumentoFiscal.RC
        );
        var sequenciaResult = documentoComercialService.getSequenciaDocumento(serieDocumentoComercial.getId());
        Long sequencia = 1L;

        if (sequenciaResult.isPresent()) {
            sequencia = sequenciaResult.get().getSequencia();
            sequencia++;
        }

        sequencia =
            reciboRepository
                .findAll()
                .stream()
                .filter(x -> x.getDocumentoComercial().getId().equals(reciboDTO.getDocumentoComercial().getId()))
                .count();
        sequencia++;

        reciboDTO.setTimestamp(Constants.DATE_TIME);
        reciboDTO.setIsFiscalizado(false);
        reciboDTO.setIsNovo(true);
        reciboDTO.setNumero(
            serieDocumentoComercial.getTipoDocumento().getSiglaInterna() + " " + serieDocumentoComercial.getSerie() + "/" + sequencia
        );
        reciboDTO.setData(Constants.DATE_TIME.toLocalDate());
        reciboDTO.setTotalSemImposto(BigDecimal.ZERO);
        reciboDTO.setTotalComImposto(BigDecimal.ZERO);
        reciboDTO.setTotalDescontoComercial(BigDecimal.ZERO);
        reciboDTO.setTotalDescontoFinanceiro(BigDecimal.ZERO);
        reciboDTO.setTotalIVA(BigDecimal.ZERO);
        reciboDTO.setTotalRetencao(BigDecimal.ZERO);
        reciboDTO.setTotalJuro(BigDecimal.ZERO);
        reciboDTO.setCambio(BigDecimal.ZERO);
        reciboDTO.setTotalMoedaEstrangeira(BigDecimal.ZERO);
        reciboDTO.setTotalPagar(BigDecimal.ZERO);
        reciboDTO.setTotalPago(BigDecimal.ZERO);
        reciboDTO.setTotalFalta(BigDecimal.ZERO);
        reciboDTO.setTotalTroco(BigDecimal.ZERO);
        reciboDTO.setOrigem("P");
        reciboDTO.setEstado(EstadoDocumentoComercial.P);

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
