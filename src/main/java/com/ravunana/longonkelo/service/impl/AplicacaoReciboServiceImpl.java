package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.config.Constants;
import com.ravunana.longonkelo.config.LongonkeloException;
import com.ravunana.longonkelo.domain.AplicacaoRecibo;
import com.ravunana.longonkelo.domain.enumeration.EstadoDocumentoComercial;
import com.ravunana.longonkelo.domain.enumeration.EstadoItemFactura;
import com.ravunana.longonkelo.repository.AplicacaoReciboRepository;
import com.ravunana.longonkelo.service.AplicacaoReciboService;
import com.ravunana.longonkelo.service.dto.AplicacaoReciboDTO;
import com.ravunana.longonkelo.service.mapper.AplicacaoReciboMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    private final TransacaoServiceImpl transacaoService;
    private final ItemFacturaServiceImpl itemFacturaService;
    private final BigDecimal PERCENTAGEM_CALCULO_MULTA_JUROS = new BigDecimal(100);
    private final ReciboServiceImpl reciboService;

    public AplicacaoReciboServiceImpl(
        AplicacaoReciboRepository aplicacaoReciboRepository,
        AplicacaoReciboMapper aplicacaoReciboMapper,
        FacturaServiceImpl facturaService,
        ItemFacturaServiceImpl itemFacturaService,
        TransacaoServiceImpl transacaoService,
        ItemFacturaServiceImpl itemFacturaService1,
        ReciboServiceImpl reciboService
    ) {
        this.aplicacaoReciboRepository = aplicacaoReciboRepository;
        this.aplicacaoReciboMapper = aplicacaoReciboMapper;
        this.transacaoService = transacaoService;
        this.itemFacturaService = itemFacturaService1;
        this.reciboService = reciboService;
    }

    @Override
    public AplicacaoReciboDTO save(AplicacaoReciboDTO aplicacaoReciboDTO) {
        log.debug("Request to save AplicacaoRecibo : {}", aplicacaoReciboDTO);

        var factura = aplicacaoReciboDTO.getFactura();
        var itemRecibo = aplicacaoReciboDTO.getItemFactura();
        var emolumento = itemRecibo.getEmolumento();
        var transacao = aplicacaoReciboDTO.getRecibo().getTransacao();

        var planoMulta = emolumento.getPlanoMulta();
        var dataActual = Constants.DATE_TIME;
        var dataPagametoMontante = transacao.getData();

        var dataExpiracaoRecibo = transacao.getData().getDayOfMonth();
        var totalFactura = factura.getTotalPagar();
        var totalPago = itemRecibo.getPrecoTotal();
        BigDecimal totalMulta = BigDecimal.ZERO;
        BigDecimal totalJuro = BigDecimal.ZERO;
        var trasacaMontante = transacao.getMontante();
        var precoUnitarioEmolumento = emolumento.getPreco();

        //Verificando se o emolumento ja foi pago  TODO:EDMAR_ZUNGO, VERIFICAR O ANO DE PAGAMENTO DO EMOLUMENTO
        if (itemRecibo.getEstado().equals(EstadoItemFactura.PAGO)) {
            throw new LongonkeloException("O Emolumento selecionado " + emolumento.getNome() + "já foi pago");
        }

        // Validando se existe plano de multa ou nao
        if (planoMulta != null) {
            var dataAplicacaoMulta = planoMulta.getDiaAplicacaoMulta();
            var dataAplicacaoJuros = planoMulta.getDiaAplicacaoJuro();

            var taxaAplicacaoMulta = planoMulta.getTaxaMulta();
            var taxaAplicacaoJuros = planoMulta.getTaxaJuro();

            if (taxaAplicacaoMulta == null) {
                taxaAplicacaoMulta = new BigDecimal(0);
            }

            if (taxaAplicacaoJuros == null) {
                taxaAplicacaoJuros = new BigDecimal(0);
            }

            if (dataAplicacaoMulta != null) {
                //                TODO:EDMAR_ZUNGO, REFACTORAR COLOCAR A CONDICAO SUBCEQUENTE NA MESMA LINHA QUE A BAIXO
                if (dataExpiracaoRecibo >= dataAplicacaoMulta) {
                    if (emolumento.getPlanoMulta().getIsTaxaMultaPercentual()) {
                        totalMulta = precoUnitarioEmolumento.multiply(taxaAplicacaoMulta.divide(PERCENTAGEM_CALCULO_MULTA_JUROS));
                        precoUnitarioEmolumento.add(totalMulta);
                    }

                    precoUnitarioEmolumento.add(taxaAplicacaoMulta);
                    //                    //Comparacao para saldo insuficiente
                    //                    if (trasacaMontante.compareTo(totalPago.add(taxaAplicacaoMulta).add(taxaAplicacaoJuros)) < 0) {
                    //                        throw new RuntimeException("Saldo insuficiente!");
                    //                    }
                }
            }

            if (dataAplicacaoJuros != null) {
                //                TODO:EDMAR_ZUNGO, REFACTORAR COLOCAR A CONDICAO SUBCEQUENTE NA MESMA LINHA QUE A BAIXO
                if (dataPagametoMontante.getDayOfMonth() > dataAplicacaoJuros) {
                    if (emolumento.getPlanoMulta().getIsTaxaJuroPercentual()) {
                        totalJuro = precoUnitarioEmolumento.multiply(taxaAplicacaoJuros.divide(PERCENTAGEM_CALCULO_MULTA_JUROS));
                        precoUnitarioEmolumento.add(totalJuro);
                    }

                    precoUnitarioEmolumento.add(taxaAplicacaoJuros);
                    //Comparacao para saldo insuficiente
                    //                    if (trasacaMontante.compareTo(totalPago.add(taxaAplicacaoMulta).add(taxaAplicacaoJuros)) < 0) {
                    //                        throw new RuntimeException("Saldo insuficiente!");
                    //                    }
                }
            }
        }

        //        var aplicaRecibo = getDadosWithFactura(aplicacaoReciboDTO);

        aplicacaoReciboDTO.setTimestamp(dataActual);
        aplicacaoReciboDTO.setTotalFactura(totalFactura);
        aplicacaoReciboDTO.setTotalPago(totalPago);

        //Setando a diferenca da aplicacao recibo
        aplicacaoReciboDTO.setTotalDiferenca(totalFactura.subtract(totalPago));

        AplicacaoRecibo aplicacaoRecibo = aplicacaoReciboMapper.toEntity(aplicacaoReciboDTO);
        aplicacaoRecibo = aplicacaoReciboRepository.save(aplicacaoRecibo);

        //Diminuindo o valor da transacao ao salvar
        var transacaResult = transacaoService.findOne(transacao.getId());
        if (transacaResult.isPresent()) {
            var transacaoResultDTO = transacaResult.get();
            transacaoResultDTO.setMontante(transacaoResultDTO.getMontante().subtract(totalPago));
            transacaoService.partialUpdate(transacaoResultDTO);
        }

        //Setando o estado ao salvar
        var itemReciboResult = itemFacturaService.findOne(itemRecibo.getId());
        if (itemReciboResult.isPresent()) {
            var itemReciboResultDTO = itemReciboResult.get();
            itemReciboResultDTO.setEstado(EstadoItemFactura.PAGO);
            itemFacturaService.partialUpdate(itemReciboResultDTO);
        }

        var reciboResult = reciboService.findOne(aplicacaoRecibo.getId());

        if (reciboResult.isPresent()) {
            var recibo = reciboResult.get();

            recibo.setTotalJuro(recibo.getTotalJuro().add(totalJuro));
            recibo.setTotalPago(recibo.getTotalPago().add(totalPago));
            recibo.setTotalPagar(recibo.getTotalPagar().add(totalPago));
            recibo.setTotalTroco(recibo.getTotalTroco().add(recibo.getTotalPagar().subtract(recibo.getTotalPago())));
            recibo.setTotalFalta(recibo.getTotalTroco());

            if (recibo.getTotalPagar().equals(recibo.getTotalPago())) {
                recibo.setEstado(EstadoDocumentoComercial.N);
            }

            reciboService.partialUpdate(recibo);
        }

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

    public List<AplicacaoReciboDTO> getAplicacaoReciboWithRecibo(Long reciboID) {
        var aplicacaoRecibo = aplicacaoReciboRepository
            .findAll()
            .stream()
            .filter(ar -> ar.getRecibo().getId().equals(reciboID))
            .collect(Collectors.toList());
        return aplicacaoReciboMapper.toDto(aplicacaoRecibo);
    }
}
