package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.PlanoMulta;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.PlanoMultaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoMulta} and its DTO {@link PlanoMultaDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlanoMultaMapper extends EntityMapper<PlanoMultaDTO, PlanoMulta> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    PlanoMultaDTO toDto(PlanoMulta s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
