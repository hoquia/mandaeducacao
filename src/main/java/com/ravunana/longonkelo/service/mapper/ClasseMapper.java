package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Classe;
import com.ravunana.longonkelo.domain.NivelEnsino;
import com.ravunana.longonkelo.service.dto.ClasseDTO;
import com.ravunana.longonkelo.service.dto.NivelEnsinoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Classe} and its DTO {@link ClasseDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClasseMapper extends EntityMapper<ClasseDTO, Classe> {
    @Mapping(target = "nivesEnsinos", source = "nivesEnsinos", qualifiedByName = "nivelEnsinoNomeSet")
    ClasseDTO toDto(Classe s);

    @Mapping(target = "removeNivesEnsino", ignore = true)
    Classe toEntity(ClasseDTO classeDTO);

    @Named("nivelEnsinoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    NivelEnsinoDTO toDtoNivelEnsinoNome(NivelEnsino nivelEnsino);

    @Named("nivelEnsinoNomeSet")
    default Set<NivelEnsinoDTO> toDtoNivelEnsinoNomeSet(Set<NivelEnsino> nivelEnsino) {
        return nivelEnsino.stream().map(this::toDtoNivelEnsinoNome).collect(Collectors.toSet());
    }
}
