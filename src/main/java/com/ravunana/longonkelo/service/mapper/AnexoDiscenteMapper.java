package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.AnexoDiscente;
import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.service.dto.AnexoDiscenteDTO;
import com.ravunana.longonkelo.service.dto.DiscenteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnexoDiscente} and its DTO {@link AnexoDiscenteDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnexoDiscenteMapper extends EntityMapper<AnexoDiscenteDTO, AnexoDiscente> {
    @Mapping(target = "discente", source = "discente", qualifiedByName = "discenteNome")
    AnexoDiscenteDTO toDto(AnexoDiscente s);

    @Named("discenteNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DiscenteDTO toDtoDiscenteNome(Discente discente);
}
