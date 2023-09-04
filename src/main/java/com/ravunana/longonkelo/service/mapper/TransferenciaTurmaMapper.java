package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.TransferenciaTurma;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import com.ravunana.longonkelo.service.dto.MatriculaDTO;
import com.ravunana.longonkelo.service.dto.TransferenciaTurmaDTO;
import com.ravunana.longonkelo.service.dto.TurmaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransferenciaTurma} and its DTO {@link TransferenciaTurmaDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransferenciaTurmaMapper extends EntityMapper<TransferenciaTurmaDTO, TransferenciaTurma> {
    @Mapping(target = "de", source = "de", qualifiedByName = "turmaDescricao")
    @Mapping(target = "para", source = "para", qualifiedByName = "turmaDescricao")
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "motivoTransferencia", source = "motivoTransferencia", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "matricula", source = "matricula", qualifiedByName = "matriculaNumeroMatricula")
    TransferenciaTurmaDTO toDto(TransferenciaTurma s);

    @Named("turmaDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    TurmaDTO toDtoTurmaDescricao(Turma turma);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("lookupItemDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    LookupItemDTO toDtoLookupItemDescricao(LookupItem lookupItem);

    @Named("matriculaNumeroMatricula")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeroMatricula", source = "numeroMatricula")
    MatriculaDTO toDtoMatriculaNumeroMatricula(Matricula matricula);
}
