package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.ProcessoSelectivoMatricula;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.DiscenteDTO;
import com.ravunana.longonkelo.service.dto.ProcessoSelectivoMatriculaDTO;
import com.ravunana.longonkelo.service.dto.TurmaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProcessoSelectivoMatricula} and its DTO {@link ProcessoSelectivoMatriculaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProcessoSelectivoMatriculaMapper extends EntityMapper<ProcessoSelectivoMatriculaDTO, ProcessoSelectivoMatricula> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "turma", source = "turma", qualifiedByName = "turmaDescricao")
    @Mapping(target = "discente", source = "discente", qualifiedByName = "discenteNome")
    ProcessoSelectivoMatriculaDTO toDto(ProcessoSelectivoMatricula s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("turmaDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    TurmaDTO toDtoTurmaDescricao(Turma turma);

    @Named("discenteNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DiscenteDTO toDtoDiscenteNome(Discente discente);
}
