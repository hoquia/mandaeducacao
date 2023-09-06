package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Emolumento;
import com.ravunana.longonkelo.domain.Factura;
import com.ravunana.longonkelo.domain.ItemFactura;
import com.ravunana.longonkelo.service.dto.EmolumentoDTO;
import com.ravunana.longonkelo.service.dto.FacturaDTO;
import com.ravunana.longonkelo.service.dto.ItemFacturaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ItemFactura} and its DTO {@link ItemFacturaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ItemFacturaMapper extends EntityMapper<ItemFacturaDTO, ItemFactura> {
    @Mapping(target = "factura", source = "factura")
    @Mapping(target = "emolumento", source = "emolumento")
    ItemFacturaDTO toDto(ItemFactura s);

    @Named("facturaNumero")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numero", source = "numero")
    FacturaDTO toDtoFacturaNumero(Factura factura);

    @Named("emolumentoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    EmolumentoDTO toDtoEmolumentoNome(Emolumento emolumento);
}
