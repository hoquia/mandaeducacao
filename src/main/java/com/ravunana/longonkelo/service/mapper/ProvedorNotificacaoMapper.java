package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.InstituicaoEnsino;
import com.ravunana.longonkelo.domain.ProvedorNotificacao;
import com.ravunana.longonkelo.service.dto.InstituicaoEnsinoDTO;
import com.ravunana.longonkelo.service.dto.ProvedorNotificacaoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProvedorNotificacao} and its DTO {@link ProvedorNotificacaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProvedorNotificacaoMapper extends EntityMapper<ProvedorNotificacaoDTO, ProvedorNotificacao> {
    @Mapping(target = "instituicao", source = "instituicao", qualifiedByName = "instituicaoEnsinoUnidadeOrganica")
    ProvedorNotificacaoDTO toDto(ProvedorNotificacao s);

    @Named("instituicaoEnsinoUnidadeOrganica")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "unidadeOrganica", source = "unidadeOrganica")
    InstituicaoEnsinoDTO toDtoInstituicaoEnsinoUnidadeOrganica(InstituicaoEnsino instituicaoEnsino);
}
