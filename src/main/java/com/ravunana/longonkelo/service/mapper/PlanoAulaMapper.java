package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.PlanoAula;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.DisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.dto.DocenteDTO;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import com.ravunana.longonkelo.service.dto.PlanoAulaDTO;
import com.ravunana.longonkelo.service.dto.TurmaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoAula} and its DTO {@link PlanoAulaDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlanoAulaMapper extends EntityMapper<PlanoAulaDTO, PlanoAula> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "unidadeTematica", source = "unidadeTematica")
    @Mapping(target = "subUnidadeTematica", source = "subUnidadeTematica")
    @Mapping(target = "turma", source = "turma")
    @Mapping(target = "docente", source = "docente", qualifiedByName = "docenteNome")
    @Mapping(target = "disciplinaCurricular", source = "disciplinaCurricular")
    PlanoAulaDTO toDto(PlanoAula s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("lookupItemDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    LookupItemDTO toDtoLookupItemDescricao(LookupItem lookupItem);

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
}
