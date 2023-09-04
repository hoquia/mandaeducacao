package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.ResponsavelTurno;
import com.ravunana.longonkelo.domain.Turno;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.ResponsavelTurnoDTO;
import com.ravunana.longonkelo.service.dto.TurnoDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResponsavelTurno} and its DTO {@link ResponsavelTurnoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResponsavelTurnoMapper extends EntityMapper<ResponsavelTurnoDTO, ResponsavelTurno> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "turno", source = "turno", qualifiedByName = "turnoNome")
    ResponsavelTurnoDTO toDto(ResponsavelTurno s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("turnoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    TurnoDTO toDtoTurnoNome(Turno turno);
}
