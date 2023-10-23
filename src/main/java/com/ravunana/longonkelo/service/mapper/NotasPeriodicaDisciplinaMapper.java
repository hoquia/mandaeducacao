package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.DisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.dto.DocenteDTO;
import com.ravunana.longonkelo.service.dto.EstadoDisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.dto.MatriculaDTO;
import com.ravunana.longonkelo.service.dto.NotasPeriodicaDisciplinaDTO;
import com.ravunana.longonkelo.service.dto.TurmaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NotasPeriodicaDisciplina} and its DTO {@link NotasPeriodicaDisciplinaDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotasPeriodicaDisciplinaMapper extends EntityMapper<NotasPeriodicaDisciplinaDTO, NotasPeriodicaDisciplina> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "turma", source = "turma", qualifiedByName = "turmaDescricao")
    @Mapping(target = "docente", source = "docente", qualifiedByName = "docenteNome")
    @Mapping(target = "disciplinaCurricular", source = "disciplinaCurricular")
    @Mapping(target = "matricula", source = "matricula")
    @Mapping(target = "estado", source = "estado")
    NotasPeriodicaDisciplinaDTO toDto(NotasPeriodicaDisciplina s);

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

    @Named("matriculaNumeroMatricula")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeroMatricula", source = "numeroMatricula")
    MatriculaDTO toDtoMatriculaNumeroMatricula(Matricula matricula);

    @Named("estadoDisciplinaCurricularDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    EstadoDisciplinaCurricularDTO toDtoEstadoDisciplinaCurricularDescricao(EstadoDisciplinaCurricular estadoDisciplinaCurricular);
}
