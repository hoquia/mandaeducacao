package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.EncarregadoEducacao;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.service.dto.DiscenteDTO;
import com.ravunana.longonkelo.service.dto.EncarregadoEducacaoDTO;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Discente} and its DTO {@link DiscenteDTO}.
 */
@Mapper(componentModel = "spring")
public interface DiscenteMapper extends EntityMapper<DiscenteDTO, Discente> {
    @Mapping(target = "nacionalidade", source = "nacionalidade", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "naturalidade", source = "naturalidade", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "tipoDocumento", source = "tipoDocumento", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "profissao", source = "profissao", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "grupoSanguinio", source = "grupoSanguinio", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "necessidadeEspecial", source = "necessidadeEspecial", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "encarregadoEducacao", source = "encarregadoEducacao", qualifiedByName = "encarregadoEducacaoNome")
    DiscenteDTO toDto(Discente s);

    @Named("lookupItemDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    LookupItemDTO toDtoLookupItemDescricao(LookupItem lookupItem);

    @Named("encarregadoEducacaoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    EncarregadoEducacaoDTO toDtoEncarregadoEducacaoNome(EncarregadoEducacao encarregadoEducacao);
}
