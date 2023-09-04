package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.DetalhePlanoAula;
import com.ravunana.longonkelo.domain.PlanoAula;
import com.ravunana.longonkelo.service.dto.DetalhePlanoAulaDTO;
import com.ravunana.longonkelo.service.dto.PlanoAulaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetalhePlanoAula} and its DTO {@link DetalhePlanoAulaDTO}.
 */
@Mapper(componentModel = "spring")
public interface DetalhePlanoAulaMapper extends EntityMapper<DetalhePlanoAulaDTO, DetalhePlanoAula> {
    @Mapping(target = "planoAula", source = "planoAula", qualifiedByName = "planoAulaAssunto")
    DetalhePlanoAulaDTO toDto(DetalhePlanoAula s);

    @Named("planoAulaAssunto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "assunto", source = "assunto")
    PlanoAulaDTO toDtoPlanoAulaAssunto(PlanoAula planoAula);
}
