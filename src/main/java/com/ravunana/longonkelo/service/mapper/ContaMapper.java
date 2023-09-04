package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Conta;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.service.dto.ContaDTO;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Conta} and its DTO {@link ContaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContaMapper extends EntityMapper<ContaDTO, Conta> {
    @Mapping(target = "moeda", source = "moeda", qualifiedByName = "lookupItemDescricao")
    ContaDTO toDto(Conta s);

    @Named("lookupItemDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    LookupItemDTO toDtoLookupItemDescricao(LookupItem lookupItem);
}
