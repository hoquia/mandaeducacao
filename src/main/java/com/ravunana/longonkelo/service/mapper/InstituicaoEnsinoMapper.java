package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.InstituicaoEnsino;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.service.dto.InstituicaoEnsinoDTO;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InstituicaoEnsino} and its DTO {@link InstituicaoEnsinoDTO}.
 */
@Mapper(componentModel = "spring")
public interface InstituicaoEnsinoMapper extends EntityMapper<InstituicaoEnsinoDTO, InstituicaoEnsino> {
    @Mapping(target = "categoriaInstituicao", source = "categoriaInstituicao", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "unidadePagadora", source = "unidadePagadora", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "tipoVinculo", source = "tipoVinculo", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "tipoInstalacao", source = "tipoInstalacao", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "sede", source = "sede", qualifiedByName = "instituicaoEnsinoUnidadeOrganica")
    InstituicaoEnsinoDTO toDto(InstituicaoEnsino s);

    @Named("instituicaoEnsinoUnidadeOrganica")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "unidadeOrganica", source = "unidadeOrganica")
    InstituicaoEnsinoDTO toDtoInstituicaoEnsinoUnidadeOrganica(InstituicaoEnsino instituicaoEnsino);

    @Named("lookupItemDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    LookupItemDTO toDtoLookupItemDescricao(LookupItem lookupItem);
}
