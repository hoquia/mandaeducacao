package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.DocumentoComercial;
import com.ravunana.longonkelo.domain.SerieDocumento;
import com.ravunana.longonkelo.service.dto.DocumentoComercialDTO;
import com.ravunana.longonkelo.service.dto.SerieDocumentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SerieDocumento} and its DTO {@link SerieDocumentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface SerieDocumentoMapper extends EntityMapper<SerieDocumentoDTO, SerieDocumento> {
    @Mapping(target = "tipoDocumento", source = "tipoDocumento", qualifiedByName = "documentoComercialSiglaFiscal")
    SerieDocumentoDTO toDto(SerieDocumento s);

    @Named("documentoComercialSiglaFiscal")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "siglaFiscal", source = "siglaFiscal")
    DocumentoComercialDTO toDtoDocumentoComercialSiglaFiscal(DocumentoComercial documentoComercial);
}
