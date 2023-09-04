package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Horario;
import com.ravunana.longonkelo.domain.Licao;
import com.ravunana.longonkelo.domain.PlanoAula;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.HorarioDTO;
import com.ravunana.longonkelo.service.dto.LicaoDTO;
import com.ravunana.longonkelo.service.dto.PlanoAulaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Licao} and its DTO {@link LicaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface LicaoMapper extends EntityMapper<LicaoDTO, Licao> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "planoAula", source = "planoAula", qualifiedByName = "planoAulaAssunto")
    @Mapping(target = "horario", source = "horario", qualifiedByName = "horarioId")
    LicaoDTO toDto(Licao s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("planoAulaAssunto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "assunto", source = "assunto")
    PlanoAulaDTO toDtoPlanoAulaAssunto(PlanoAula planoAula);

    @Named("horarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HorarioDTO toDtoHorarioId(Horario horario);
}
