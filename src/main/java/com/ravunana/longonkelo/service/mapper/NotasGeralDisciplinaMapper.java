package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.NotasGeralDisciplina;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.DisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.dto.DocenteDTO;
import com.ravunana.longonkelo.service.dto.EstadoDisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.dto.MatriculaDTO;
import com.ravunana.longonkelo.service.dto.NotasGeralDisciplinaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NotasGeralDisciplina} and its DTO {@link NotasGeralDisciplinaDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotasGeralDisciplinaMapper extends EntityMapper<NotasGeralDisciplinaDTO, NotasGeralDisciplina> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "docente", source = "docente", qualifiedByName = "docenteNome")
    @Mapping(target = "disciplinaCurricular", source = "disciplinaCurricular", qualifiedByName = "disciplinaCurricularDescricao")
    @Mapping(target = "matricula", source = "matricula", qualifiedByName = "matriculaNumeroMatricula")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "estadoDisciplinaCurricularDescricao")
    NotasGeralDisciplinaDTO toDto(NotasGeralDisciplina s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

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
