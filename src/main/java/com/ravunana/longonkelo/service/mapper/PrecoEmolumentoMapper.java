package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.AreaFormacao;
import com.ravunana.longonkelo.domain.Classe;
import com.ravunana.longonkelo.domain.Curso;
import com.ravunana.longonkelo.domain.Emolumento;
import com.ravunana.longonkelo.domain.PlanoMulta;
import com.ravunana.longonkelo.domain.PrecoEmolumento;
import com.ravunana.longonkelo.domain.Turno;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.AreaFormacaoDTO;
import com.ravunana.longonkelo.service.dto.ClasseDTO;
import com.ravunana.longonkelo.service.dto.CursoDTO;
import com.ravunana.longonkelo.service.dto.EmolumentoDTO;
import com.ravunana.longonkelo.service.dto.PlanoMultaDTO;
import com.ravunana.longonkelo.service.dto.PrecoEmolumentoDTO;
import com.ravunana.longonkelo.service.dto.TurnoDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrecoEmolumento} and its DTO {@link PrecoEmolumentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PrecoEmolumentoMapper extends EntityMapper<PrecoEmolumentoDTO, PrecoEmolumento> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "emolumento", source = "emolumento", qualifiedByName = "emolumentoNome")
    @Mapping(target = "areaFormacao", source = "areaFormacao", qualifiedByName = "areaFormacaoNome")
    @Mapping(target = "curso", source = "curso", qualifiedByName = "cursoNome")
    @Mapping(target = "classe", source = "classe", qualifiedByName = "classeDescricao")
    @Mapping(target = "turno", source = "turno", qualifiedByName = "turnoNome")
    @Mapping(target = "planoMulta", source = "planoMulta", qualifiedByName = "planoMultaDescricao")
    PrecoEmolumentoDTO toDto(PrecoEmolumento s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("emolumentoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    EmolumentoDTO toDtoEmolumentoNome(Emolumento emolumento);

    @Named("areaFormacaoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    AreaFormacaoDTO toDtoAreaFormacaoNome(AreaFormacao areaFormacao);

    @Named("cursoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    CursoDTO toDtoCursoNome(Curso curso);

    @Named("classeDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    ClasseDTO toDtoClasseDescricao(Classe classe);

    @Named("turnoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    TurnoDTO toDtoTurnoNome(Turno turno);

    @Named("planoMultaDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    PlanoMultaDTO toDtoPlanoMultaDescricao(PlanoMulta planoMulta);
}
