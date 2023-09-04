package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.AreaFormacao;
import com.ravunana.longonkelo.domain.Curso;
import com.ravunana.longonkelo.service.dto.AreaFormacaoDTO;
import com.ravunana.longonkelo.service.dto.CursoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Curso} and its DTO {@link CursoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CursoMapper extends EntityMapper<CursoDTO, Curso> {
    @Mapping(target = "areaFormacao", source = "areaFormacao", qualifiedByName = "areaFormacaoNome")
    CursoDTO toDto(Curso s);

    @Named("areaFormacaoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    AreaFormacaoDTO toDtoAreaFormacaoNome(AreaFormacao areaFormacao);
}
