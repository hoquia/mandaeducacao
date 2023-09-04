package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.AreaFormacao;
import com.ravunana.longonkelo.domain.NivelEnsino;
import com.ravunana.longonkelo.service.dto.AreaFormacaoDTO;
import com.ravunana.longonkelo.service.dto.NivelEnsinoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AreaFormacao} and its DTO {@link AreaFormacaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AreaFormacaoMapper extends EntityMapper<AreaFormacaoDTO, AreaFormacao> {
    @Mapping(target = "nivelEnsino", source = "nivelEnsino", qualifiedByName = "nivelEnsinoNome")
    AreaFormacaoDTO toDto(AreaFormacao s);

    @Named("nivelEnsinoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    NivelEnsinoDTO toDtoNivelEnsinoNome(NivelEnsino nivelEnsino);
}
