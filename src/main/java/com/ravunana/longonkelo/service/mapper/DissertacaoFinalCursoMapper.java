package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.AreaFormacao;
import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.domain.DissertacaoFinalCurso;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.EstadoDissertacao;
import com.ravunana.longonkelo.domain.NaturezaTrabalho;
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.domain.User;
import com.ravunana.longonkelo.service.dto.AreaFormacaoDTO;
import com.ravunana.longonkelo.service.dto.DiscenteDTO;
import com.ravunana.longonkelo.service.dto.DissertacaoFinalCursoDTO;
import com.ravunana.longonkelo.service.dto.DocenteDTO;
import com.ravunana.longonkelo.service.dto.EstadoDissertacaoDTO;
import com.ravunana.longonkelo.service.dto.NaturezaTrabalhoDTO;
import com.ravunana.longonkelo.service.dto.TurmaDTO;
import com.ravunana.longonkelo.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DissertacaoFinalCurso} and its DTO {@link DissertacaoFinalCursoDTO}.
 */
@Mapper(componentModel = "spring")
public interface DissertacaoFinalCursoMapper extends EntityMapper<DissertacaoFinalCursoDTO, DissertacaoFinalCurso> {
    @Mapping(target = "utilizador", source = "utilizador", qualifiedByName = "userLogin")
    @Mapping(target = "turma", source = "turma", qualifiedByName = "turmaDescricao")
    @Mapping(target = "orientador", source = "orientador", qualifiedByName = "docenteNome")
    @Mapping(target = "especialidade", source = "especialidade", qualifiedByName = "areaFormacaoNome")
    @Mapping(target = "discente", source = "discente", qualifiedByName = "discenteNome")
    @Mapping(target = "estado", source = "estado", qualifiedByName = "estadoDissertacaoNome")
    @Mapping(target = "natureza", source = "natureza", qualifiedByName = "naturezaTrabalhoNome")
    DissertacaoFinalCursoDTO toDto(DissertacaoFinalCurso s);

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

    @Named("docenteNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DocenteDTO toDtoDocenteNome(Docente docente);

    @Named("areaFormacaoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    AreaFormacaoDTO toDtoAreaFormacaoNome(AreaFormacao areaFormacao);

    @Named("discenteNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DiscenteDTO toDtoDiscenteNome(Discente discente);

    @Named("estadoDissertacaoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    EstadoDissertacaoDTO toDtoEstadoDissertacaoNome(EstadoDissertacao estadoDissertacao);

    @Named("naturezaTrabalhoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    NaturezaTrabalhoDTO toDtoNaturezaTrabalhoNome(NaturezaTrabalho naturezaTrabalho);
}
