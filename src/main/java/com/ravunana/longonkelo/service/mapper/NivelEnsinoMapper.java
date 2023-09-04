package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.NivelEnsino;
import com.ravunana.longonkelo.service.dto.NivelEnsinoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NivelEnsino} and its DTO {@link NivelEnsinoDTO}.
 */
@Mapper(componentModel = "spring")
public interface NivelEnsinoMapper extends EntityMapper<NivelEnsinoDTO, NivelEnsino> {
    @Mapping(target = "referencia", source = "referencia", qualifiedByName = "nivelEnsinoNome")
    NivelEnsinoDTO toDto(NivelEnsino s);

    @Named("nivelEnsinoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    NivelEnsinoDTO toDtoNivelEnsinoNome(NivelEnsino nivelEnsino);
}
