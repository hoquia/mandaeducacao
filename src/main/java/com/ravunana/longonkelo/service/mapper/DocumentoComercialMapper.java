package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.DocumentoComercial;
import com.ravunana.longonkelo.service.dto.DocumentoComercialDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentoComercial} and its DTO {@link DocumentoComercialDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentoComercialMapper extends EntityMapper<DocumentoComercialDTO, DocumentoComercial> {
    @Mapping(target = "transformaEm", source = "transformaEm", qualifiedByName = "documentoComercialDescricao")
    DocumentoComercialDTO toDto(DocumentoComercial s);

    @Named("documentoComercialDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    DocumentoComercialDTO toDtoDocumentoComercialDescricao(DocumentoComercial documentoComercial);
}
