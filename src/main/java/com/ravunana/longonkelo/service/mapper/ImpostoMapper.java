package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Imposto;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.service.dto.ImpostoDTO;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Imposto} and its DTO {@link ImpostoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ImpostoMapper extends EntityMapper<ImpostoDTO, Imposto> {
    @Mapping(target = "tipoImposto", source = "tipoImposto", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "codigoImposto", source = "codigoImposto", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "motivoIsencaoCodigo", source = "motivoIsencaoCodigo", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "motivoIsencaoDescricao", source = "motivoIsencaoDescricao", qualifiedByName = "lookupItemDescricao")
    ImpostoDTO toDto(Imposto s);

    @Named("lookupItemDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    LookupItemDTO toDtoLookupItemDescricao(LookupItem lookupItem);
}
