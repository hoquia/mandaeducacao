package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.SequenciaDocumento;
import com.ravunana.longonkelo.domain.SerieDocumento;
import com.ravunana.longonkelo.service.dto.SequenciaDocumentoDTO;
import com.ravunana.longonkelo.service.dto.SerieDocumentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SequenciaDocumento} and its DTO {@link SequenciaDocumentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface SequenciaDocumentoMapper extends EntityMapper<SequenciaDocumentoDTO, SequenciaDocumento> {
    @Mapping(target = "serie", source = "serie", qualifiedByName = "serieDocumentoSerie")
    SequenciaDocumentoDTO toDto(SequenciaDocumento s);

    @Named("serieDocumentoSerie")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "serie", source = "serie")
    SerieDocumentoDTO toDtoSerieDocumentoSerie(SerieDocumento serieDocumento);
}
