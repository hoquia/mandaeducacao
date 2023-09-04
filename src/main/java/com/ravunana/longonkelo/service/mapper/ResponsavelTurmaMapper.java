package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.ResponsavelTurma;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.ResponsavelTurmaDTO;
import com.ravunana.longonkelo.service.dto.TurmaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResponsavelTurma} and its DTO {@link ResponsavelTurmaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResponsavelTurmaMapper extends EntityMapper<ResponsavelTurmaDTO, ResponsavelTurma> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "turma", source = "turma", qualifiedByName = "turmaDescricao")
    ResponsavelTurmaDTO toDto(ResponsavelTurma s);

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
}
