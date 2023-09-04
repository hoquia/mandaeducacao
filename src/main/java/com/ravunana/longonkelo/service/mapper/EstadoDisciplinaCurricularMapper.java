package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular;
import com.ravunana.longonkelo.service.dto.DisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.dto.EstadoDisciplinaCurricularDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstadoDisciplinaCurricular} and its DTO {@link EstadoDisciplinaCurricularDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstadoDisciplinaCurricularMapper extends EntityMapper<EstadoDisciplinaCurricularDTO, EstadoDisciplinaCurricular> {
    @Mapping(target = "disciplinasCurriculars", source = "disciplinasCurriculars", qualifiedByName = "disciplinaCurricularIdSet")
    @Mapping(target = "referencia", source = "referencia", qualifiedByName = "estadoDisciplinaCurricularId")
    EstadoDisciplinaCurricularDTO toDto(EstadoDisciplinaCurricular s);

    @Mapping(target = "removeDisciplinasCurriculars", ignore = true)
    EstadoDisciplinaCurricular toEntity(EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO);

    @Named("estadoDisciplinaCurricularId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EstadoDisciplinaCurricularDTO toDtoEstadoDisciplinaCurricularId(EstadoDisciplinaCurricular estadoDisciplinaCurricular);

    @Named("disciplinaCurricularId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DisciplinaCurricularDTO toDtoDisciplinaCurricularId(DisciplinaCurricular disciplinaCurricular);

    @Named("disciplinaCurricularIdSet")
    default Set<DisciplinaCurricularDTO> toDtoDisciplinaCurricularIdSet(Set<DisciplinaCurricular> disciplinaCurricular) {
        return disciplinaCurricular.stream().map(this::toDtoDisciplinaCurricularId).collect(Collectors.toSet());
    }
}
