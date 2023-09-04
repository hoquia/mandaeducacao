package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.EnderecoDiscente;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.service.dto.DiscenteDTO;
import com.ravunana.longonkelo.service.dto.EnderecoDiscenteDTO;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EnderecoDiscente} and its DTO {@link EnderecoDiscenteDTO}.
 */
@Mapper(componentModel = "spring")
public interface EnderecoDiscenteMapper extends EntityMapper<EnderecoDiscenteDTO, EnderecoDiscente> {
    @Mapping(target = "pais", source = "pais", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "provincia", source = "provincia", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "municipio", source = "municipio", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "discente", source = "discente", qualifiedByName = "discenteNome")
    EnderecoDiscenteDTO toDto(EnderecoDiscente s);

    @Named("lookupItemDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    LookupItemDTO toDtoLookupItemDescricao(LookupItem lookupItem);

    @Named("discenteNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DiscenteDTO toDtoDiscenteNome(Discente discente);
}
