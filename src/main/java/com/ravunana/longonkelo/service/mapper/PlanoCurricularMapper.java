package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Classe;
import com.ravunana.longonkelo.domain.Curso;
import com.ravunana.longonkelo.domain.PlanoCurricular;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.ClasseDTO;
import com.ravunana.longonkelo.service.dto.CursoDTO;
import com.ravunana.longonkelo.service.dto.PlanoCurricularDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoCurricular} and its DTO {@link PlanoCurricularDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlanoCurricularMapper extends EntityMapper<PlanoCurricularDTO, PlanoCurricular> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "classe", source = "classe", qualifiedByName = "classeDescricao")
    @Mapping(target = "curso", source = "curso", qualifiedByName = "cursoNome")
    PlanoCurricularDTO toDto(PlanoCurricular s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("classeDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    ClasseDTO toDtoClasseDescricao(Classe classe);

    @Named("cursoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    CursoDTO toDtoCursoNome(Curso curso);
}
