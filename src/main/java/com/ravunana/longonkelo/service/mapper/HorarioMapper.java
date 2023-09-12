package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.Horario;
import com.ravunana.longonkelo.domain.PeriodoHorario;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.DisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.dto.DocenteDTO;
import com.ravunana.longonkelo.service.dto.HorarioDTO;
import com.ravunana.longonkelo.service.dto.PeriodoHorarioDTO;
import com.ravunana.longonkelo.service.dto.TurmaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Horario} and its DTO {@link HorarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface HorarioMapper extends EntityMapper<HorarioDTO, Horario> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "turma", source = "turma")
    @Mapping(target = "referencia", source = "referencia", qualifiedByName = "horarioId")
    @Mapping(target = "periodo", source = "periodo")
    @Mapping(target = "docente", source = "docente")
    @Mapping(target = "disciplinaCurricular", source = "disciplinaCurricular")
    HorarioDTO toDto(Horario s);

    @Named("horarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HorarioDTO toDtoHorarioId(Horario horario);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("turmaDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    TurmaDTO toDtoTurmaDescricao(Turma turma);

    @Named("periodoHorarioDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    PeriodoHorarioDTO toDtoPeriodoHorarioDescricao(PeriodoHorario periodoHorario);

    @Named("docenteNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DocenteDTO toDtoDocenteNome(Docente docente);

    @Named("disciplinaCurricularDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    DisciplinaCurricularDTO toDtoDisciplinaCurricularDescricao(DisciplinaCurricular disciplinaCurricular);
}
