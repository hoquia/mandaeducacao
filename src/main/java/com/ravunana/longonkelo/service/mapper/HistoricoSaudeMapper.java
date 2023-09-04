package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.HistoricoSaude;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.DiscenteDTO;
import com.ravunana.longonkelo.service.dto.HistoricoSaudeDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HistoricoSaude} and its DTO {@link HistoricoSaudeDTO}.
 */
@Mapper(componentModel = "spring")
public interface HistoricoSaudeMapper extends EntityMapper<HistoricoSaudeDTO, HistoricoSaude> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "discente", source = "discente", qualifiedByName = "discenteNome")
    HistoricoSaudeDTO toDto(HistoricoSaude s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("discenteNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DiscenteDTO toDtoDiscenteNome(Discente discente);
}
