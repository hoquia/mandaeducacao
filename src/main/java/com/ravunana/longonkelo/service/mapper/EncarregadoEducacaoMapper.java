package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.EncarregadoEducacao;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.service.dto.EncarregadoEducacaoDTO;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EncarregadoEducacao} and its DTO {@link EncarregadoEducacaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EncarregadoEducacaoMapper extends EntityMapper<EncarregadoEducacaoDTO, EncarregadoEducacao> {
    @Mapping(target = "grauParentesco", source = "grauParentesco", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "tipoDocumento", source = "tipoDocumento", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "profissao", source = "profissao", qualifiedByName = "lookupItemDescricao")
    EncarregadoEducacaoDTO toDto(EncarregadoEducacao s);

    @Named("lookupItemDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    LookupItemDTO toDtoLookupItemDescricao(LookupItem lookupItem);
}
