package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.AplicacaoRecibo;
import com.ravunana.longonkelo.domain.Factura;
import com.ravunana.longonkelo.domain.ItemFactura;
import com.ravunana.longonkelo.domain.Recibo;
import com.ravunana.longonkelo.service.dto.AplicacaoReciboDTO;
import com.ravunana.longonkelo.service.dto.FacturaDTO;
import com.ravunana.longonkelo.service.dto.ItemFacturaDTO;
import com.ravunana.longonkelo.service.dto.ReciboDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AplicacaoRecibo} and its DTO {@link AplicacaoReciboDTO}.
 */
@Mapper(componentModel = "spring")
public interface AplicacaoReciboMapper extends EntityMapper<AplicacaoReciboDTO, AplicacaoRecibo> {
    @Mapping(target = "itemFactura", source = "itemFactura")
    @Mapping(target = "factura", source = "factura")
    @Mapping(target = "recibo", source = "recibo")
    AplicacaoReciboDTO toDto(AplicacaoRecibo s);

    @Named("itemFacturaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ItemFacturaDTO toDtoItemFacturaId(ItemFactura itemFactura);

    @Named("facturaNumero")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numero", source = "numero")
    FacturaDTO toDtoFacturaNumero(Factura factura);

    @Named("reciboNumero")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numero", source = "numero")
    ReciboDTO toDtoReciboNumero(Recibo recibo);
}
