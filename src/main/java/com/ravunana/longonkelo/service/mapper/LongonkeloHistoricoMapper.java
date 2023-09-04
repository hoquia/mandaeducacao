package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.LongonkeloHistorico;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.LongonkeloHistoricoDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LongonkeloHistorico} and its DTO {@link LongonkeloHistoricoDTO}.
 */
@Mapper(componentModel = "spring")
public interface LongonkeloHistoricoMapper extends EntityMapper<LongonkeloHistoricoDTO, LongonkeloHistorico> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    LongonkeloHistoricoDTO toDto(LongonkeloHistorico s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
