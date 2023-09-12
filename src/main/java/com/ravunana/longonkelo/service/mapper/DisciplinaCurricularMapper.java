package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Disciplina;
import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.PlanoCurricular;
import com.ravunana.longonkelo.service.dto.DisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.dto.DisciplinaDTO;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import com.ravunana.longonkelo.service.dto.PlanoCurricularDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DisciplinaCurricular} and its DTO {@link DisciplinaCurricularDTO}.
 */
@Mapper(componentModel = "spring")
public interface DisciplinaCurricularMapper extends EntityMapper<DisciplinaCurricularDTO, DisciplinaCurricular> {
    @Mapping(target = "componente", source = "componente", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "regime", source = "regime", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "planosCurriculars", source = "planosCurriculars")
    @Mapping(target = "disciplina", source = "disciplina", qualifiedByName = "disciplinaNome")
    @Mapping(target = "referencia", source = "referencia", qualifiedByName = "disciplinaCurricularDescricao")
    DisciplinaCurricularDTO toDto(DisciplinaCurricular s);

    @Mapping(target = "removePlanosCurricular", ignore = true)
    DisciplinaCurricular toEntity(DisciplinaCurricularDTO disciplinaCurricularDTO);

    @Named("disciplinaCurricularDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    DisciplinaCurricularDTO toDtoDisciplinaCurricularDescricao(DisciplinaCurricular disciplinaCurricular);

    @Named("lookupItemDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    LookupItemDTO toDtoLookupItemDescricao(LookupItem lookupItem);

    @Named("planoCurricularDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    PlanoCurricularDTO toDtoPlanoCurricularDescricao(PlanoCurricular planoCurricular);

    @Named("planoCurricularDescricaoSet")
    default Set<PlanoCurricularDTO> toDtoPlanoCurricularDescricaoSet(Set<PlanoCurricular> planoCurricular) {
        return planoCurricular.stream().map(this::toDtoPlanoCurricularDescricao).collect(Collectors.toSet());
    }

    @Named("disciplinaNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DisciplinaDTO toDtoDisciplinaNome(Disciplina disciplina);
}
