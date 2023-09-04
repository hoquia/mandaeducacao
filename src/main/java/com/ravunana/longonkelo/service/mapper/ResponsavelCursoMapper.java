package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Curso;
import com.ravunana.longonkelo.domain.ResponsavelCurso;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.CursoDTO;
import com.ravunana.longonkelo.service.dto.ResponsavelCursoDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResponsavelCurso} and its DTO {@link ResponsavelCursoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResponsavelCursoMapper extends EntityMapper<ResponsavelCursoDTO, ResponsavelCurso> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "curso", source = "curso", qualifiedByName = "cursoNome")
    ResponsavelCursoDTO toDto(ResponsavelCurso s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("cursoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    CursoDTO toDtoCursoNome(Curso curso);
}
