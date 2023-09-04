package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Factura;
import com.ravunana.longonkelo.domain.ResumoImpostoFactura;
import com.ravunana.longonkelo.service.dto.FacturaDTO;
import com.ravunana.longonkelo.service.dto.ResumoImpostoFacturaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResumoImpostoFactura} and its DTO {@link ResumoImpostoFacturaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResumoImpostoFacturaMapper extends EntityMapper<ResumoImpostoFacturaDTO, ResumoImpostoFactura> {
    @Mapping(target = "factura", source = "factura", qualifiedByName = "facturaNumero")
    ResumoImpostoFacturaDTO toDto(ResumoImpostoFactura s);

    @Named("facturaNumero")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numero", source = "numero")
    FacturaDTO toDtoFacturaNumero(Factura factura);
}
