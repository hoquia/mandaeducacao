package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular;
import com.ravunana.longonkelo.domain.ResumoAcademico;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.DiscenteDTO;
import com.ravunana.longonkelo.service.dto.EstadoDisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.dto.ResumoAcademicoDTO;
import com.ravunana.longonkelo.service.dto.TurmaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResumoAcademico} and its DTO {@link ResumoAcademicoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResumoAcademicoMapper extends EntityMapper<ResumoAcademicoDTO, ResumoAcademico> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "ultimaTurmaMatriculada", source = "ultimaTurmaMatriculada", qualifiedByName = "turmaDescricao")
    @Mapping(target = "discente", source = "discente", qualifiedByName = "discenteNome")
    @Mapping(target = "situacao", source = "situacao", qualifiedByName = "estadoDisciplinaCurricularDescricao")
    ResumoAcademicoDTO toDto(ResumoAcademico s);

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

    @Named("discenteNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DiscenteDTO toDtoDiscenteNome(Discente discente);

    @Named("estadoDisciplinaCurricularDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    EstadoDisciplinaCurricularDTO toDtoEstadoDisciplinaCurricularDescricao(EstadoDisciplinaCurricular estadoDisciplinaCurricular);
}
