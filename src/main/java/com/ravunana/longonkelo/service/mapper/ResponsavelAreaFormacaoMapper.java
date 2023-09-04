package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.AreaFormacao;
import com.ravunana.longonkelo.domain.ResponsavelAreaFormacao;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.AreaFormacaoDTO;
import com.ravunana.longonkelo.service.dto.ResponsavelAreaFormacaoDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResponsavelAreaFormacao} and its DTO {@link ResponsavelAreaFormacaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResponsavelAreaFormacaoMapper extends EntityMapper<ResponsavelAreaFormacaoDTO, ResponsavelAreaFormacao> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "areaFormacao", source = "areaFormacao", qualifiedByName = "areaFormacaoNome")
    ResponsavelAreaFormacaoDTO toDto(ResponsavelAreaFormacao s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("areaFormacaoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    AreaFormacaoDTO toDtoAreaFormacaoNome(AreaFormacao areaFormacao);
}
