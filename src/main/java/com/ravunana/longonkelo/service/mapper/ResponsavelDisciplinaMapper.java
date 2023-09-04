package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Disciplina;
import com.ravunana.longonkelo.domain.ResponsavelDisciplina;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.DisciplinaDTO;
import com.ravunana.longonkelo.service.dto.ResponsavelDisciplinaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResponsavelDisciplina} and its DTO {@link ResponsavelDisciplinaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResponsavelDisciplinaMapper extends EntityMapper<ResponsavelDisciplinaDTO, ResponsavelDisciplina> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "disciplina", source = "disciplina", qualifiedByName = "disciplinaNome")
    ResponsavelDisciplinaDTO toDto(ResponsavelDisciplina s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("disciplinaNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DisciplinaDTO toDtoDisciplinaNome(Disciplina disciplina);
}
