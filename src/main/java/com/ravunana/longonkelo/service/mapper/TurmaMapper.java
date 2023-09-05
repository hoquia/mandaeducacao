package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.PlanoCurricular;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.Turno;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.PlanoCurricularDTO;
import com.ravunana.longonkelo.service.dto.TurmaDTO;
import com.ravunana.longonkelo.service.dto.TurnoDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Turma} and its DTO {@link TurmaDTO}.
 */
@Mapper(componentModel = "spring")
public interface TurmaMapper extends EntityMapper<TurmaDTO, Turma> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "referencia", source = "referencia")
    @Mapping(target = "planoCurricular", source = "planoCurricular")
    @Mapping(target = "turno", source = "turno")
    TurmaDTO toDto(Turma s);

    @Named("turmaDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    TurmaDTO toDtoTurmaDescricao(Turma turma);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("planoCurricularDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    PlanoCurricularDTO toDtoPlanoCurricularDescricao(PlanoCurricular planoCurricular);

    @Named("turnoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    TurnoDTO toDtoTurnoNome(Turno turno);
}
