package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.AplicacaoRecibo;
import com.ravunana.longonkelo.repository.AplicacaoReciboRepository;
import com.ravunana.longonkelo.service.AplicacaoReciboService;
import com.ravunana.longonkelo.service.dto.AplicacaoReciboDTO;
import com.ravunana.longonkelo.service.mapper.AplicacaoReciboMapper;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
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

    public AplicacaoReciboServiceImpl(
        AplicacaoReciboRepository aplicacaoReciboRepository,
        AplicacaoReciboMapper aplicacaoReciboMapper,
        FacturaServiceImpl facturaService,
        ItemFacturaServiceImpl itemFacturaService
    ) {
        this.aplicacaoReciboRepository = aplicacaoReciboRepository;
        this.aplicacaoReciboMapper = aplicacaoReciboMapper;
    }

    @Override
    public AplicacaoReciboDTO save(AplicacaoReciboDTO aplicacaoReciboDTO) {
        log.debug("Request to save AplicacaoRecibo : {}", aplicacaoReciboDTO);

        var factura = aplicacaoReciboDTO.getFactura();
        var itemRecibo = aplicacaoReciboDTO.getItemFactura();
        var emolumento = itemRecibo.getEmolumento();
        var transacao = aplicacaoReciboDTO.getRecibo().getTransacao();

        var dataActual = ZonedDateTime.now();
        var totalFactura = factura.getTotalPagar();
        var totalPago = itemRecibo.getPrecoTotal();
        var trasacaMontante = transacao.getMontante();
        var dataPagametoMontante = transacao.getData();
        var dataAplicacaoMulta = emolumento.getPlanoMulta().getDiaAplicacaoMulta();
        var dataAplicacaoJuros = emolumento.getPlanoMulta().getDiaAplicacaoJuro();
        var precoUnitarioEmolumento = emolumento.getPreco();
        var taxaAplicacaoMulta = emolumento.getPlanoMulta().getTaxaMulta();
        var taxaAplicacaoJuros = emolumento.getPlanoMulta().getTaxaJuro();

        if (trasacaMontante.compareTo(totalPago) < 0) {
            throw new RuntimeException("Saldo insuficiente!");
        }

        if (taxaAplicacaoMulta == null) {
            taxaAplicacaoMulta = new BigDecimal(0);
        }

        if (taxaAplicacaoJuros == null) {
            taxaAplicacaoJuros = new BigDecimal(0);
        }

        if (dataPagametoMontante.getDayOfMonth() > dataAplicacaoMulta) {
            if (emolumento.getPlanoMulta().getIsTaxaMultaPercentual()) {
                var multa = precoUnitarioEmolumento.multiply(taxaAplicacaoMulta.divide(new BigDecimal(100)));
                precoUnitarioEmolumento.add(multa);
            }

            precoUnitarioEmolumento.add(taxaAplicacaoMulta);
        }

        if (dataPagametoMontante.getDayOfMonth() > dataAplicacaoJuros) {
            if (emolumento.getPlanoMulta().getIsTaxaJuroPercentual()) {
                var juros = precoUnitarioEmolumento.multiply(taxaAplicacaoJuros.divide(new BigDecimal(100)));
                precoUnitarioEmolumento.add(juros);
            }

            precoUnitarioEmolumento.add(taxaAplicacaoJuros);
        }

        //        var aplicaRecibo = getDadosWithFactura(aplicacaoReciboDTO);

        aplicacaoReciboDTO.setTimestamp(dataActual);
        aplicacaoReciboDTO.setTotalFactura(totalFactura);
        aplicacaoReciboDTO.setTotalPago(totalPago);

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
    //    private AplicacaoReciboDTO getDadosWithFactura(AplicacaoReciboDTO aplicacaoReciboDTO){
    //        AplicacaoReciboDTO aplicacaoRecDTO = new AplicacaoReciboDTO();
    //
    //
    //        aplicacaoRecDTO.setFactura(factura);
    //        aplicacaoRecDTO.setItemFactura(itemFactura);
    //        aplicacaoRecDTO.setTotalPago(totalPago);
    //        aplicacaoRecDTO.setTotalFactura(totalFactura);
    //
    //        return aplicacaoRecDTO;
    //    }

}
