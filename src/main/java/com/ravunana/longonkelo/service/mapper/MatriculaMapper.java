package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.EncarregadoEducacao;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.PlanoDesconto;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.DiscenteDTO;
import com.ravunana.longonkelo.service.dto.EncarregadoEducacaoDTO;
import com.ravunana.longonkelo.service.dto.MatriculaDTO;
import com.ravunana.longonkelo.service.dto.PlanoDescontoDTO;
import com.ravunana.longonkelo.service.dto.TurmaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Matricula} and its DTO {@link MatriculaDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatriculaMapper extends EntityMapper<MatriculaDTO, Matricula> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "categoriasMatriculas", source = "categoriasMatriculas", qualifiedByName = "planoDescontoNomeSet")
    @Mapping(target = "turma", source = "turma")
    @Mapping(target = "responsavelFinanceiro", source = "responsavelFinanceiro", qualifiedByName = "encarregadoEducacaoNome")
    @Mapping(target = "discente", source = "discente")
    @Mapping(target = "referencia", source = "referencia", qualifiedByName = "matriculaId")
    MatriculaDTO toDto(Matricula s);

    @Mapping(target = "removeCategoriasMatriculas", ignore = true)
    Matricula toEntity(MatriculaDTO matriculaDTO);

    @Named("matriculaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatriculaDTO toDtoMatriculaId(Matricula matricula);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("planoDescontoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PlanoDescontoDTO toDtoPlanoDescontoNome(PlanoDesconto planoDesconto);

    @Named("planoDescontoNomeSet")
    default Set<PlanoDescontoDTO> toDtoPlanoDescontoNomeSet(Set<PlanoDesconto> planoDesconto) {
        return planoDesconto.stream().map(this::toDtoPlanoDescontoNome).collect(Collectors.toSet());
    }

    @Named("turmaDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    TurmaDTO toDtoTurmaDescricao(Turma turma);

    @Named("encarregadoEducacaoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    EncarregadoEducacaoDTO toDtoEncarregadoEducacaoNome(EncarregadoEducacao encarregadoEducacao);

    @Named("discenteNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DiscenteDTO toDtoDiscenteNome(Discente discente);
}
