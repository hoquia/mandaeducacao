package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Classe;
import com.ravunana.longonkelo.domain.PeriodoLancamentoNota;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.ClasseDTO;
import com.ravunana.longonkelo.service.dto.PeriodoLancamentoNotaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PeriodoLancamentoNota} and its DTO {@link PeriodoLancamentoNotaDTO}.
 */
@Mapper(componentModel = "spring")
public interface PeriodoLancamentoNotaMapper extends EntityMapper<PeriodoLancamentoNotaDTO, PeriodoLancamentoNota> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "classes", source = "classes", qualifiedByName = "classeDescricaoSet")
    PeriodoLancamentoNotaDTO toDto(PeriodoLancamentoNota s);

    @Mapping(target = "removeClasse", ignore = true)
    PeriodoLancamentoNota toEntity(PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("classeDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    ClasseDTO toDtoClasseDescricao(Classe classe);

    @Named("classeDescricaoSet")
    default Set<ClasseDTO> toDtoClasseDescricaoSet(Set<Classe> classe) {
        return classe.stream().map(this::toDtoClasseDescricao).collect(Collectors.toSet());
    }
}
