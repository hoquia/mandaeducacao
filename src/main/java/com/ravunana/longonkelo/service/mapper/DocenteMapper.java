package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.domain.ResponsavelAreaFormacao;
import com.ravunana.longonkelo.domain.ResponsavelCurso;
import com.ravunana.longonkelo.domain.ResponsavelDisciplina;
import com.ravunana.longonkelo.domain.ResponsavelTurma;
import com.ravunana.longonkelo.domain.ResponsavelTurno;
import com.ravunana.longonkelo.service.dto.DocenteDTO;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import com.ravunana.longonkelo.service.dto.ResponsavelAreaFormacaoDTO;
import com.ravunana.longonkelo.service.dto.ResponsavelCursoDTO;
import com.ravunana.longonkelo.service.dto.ResponsavelDisciplinaDTO;
import com.ravunana.longonkelo.service.dto.ResponsavelTurmaDTO;
import com.ravunana.longonkelo.service.dto.ResponsavelTurnoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Docente} and its DTO {@link DocenteDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocenteMapper extends EntityMapper<DocenteDTO, Docente> {
    @Mapping(target = "nacionalidade", source = "nacionalidade", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "naturalidade", source = "naturalidade", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "tipoDocumento", source = "tipoDocumento", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "grauAcademico", source = "grauAcademico", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "categoriaProfissional", source = "categoriaProfissional", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "unidadeOrganica", source = "unidadeOrganica", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "estadoCivil", source = "estadoCivil", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "responsavelTurno", source = "responsavelTurno", qualifiedByName = "responsavelTurnoId")
    @Mapping(target = "responsavelAreaFormacao", source = "responsavelAreaFormacao", qualifiedByName = "responsavelAreaFormacaoId")
    @Mapping(target = "responsavelCurso", source = "responsavelCurso", qualifiedByName = "responsavelCursoId")
    @Mapping(target = "responsavelDisciplina", source = "responsavelDisciplina", qualifiedByName = "responsavelDisciplinaId")
    @Mapping(target = "responsavelTurma", source = "responsavelTurma", qualifiedByName = "responsavelTurmaId")
    DocenteDTO toDto(Docente s);

    @Named("lookupItemDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    LookupItemDTO toDtoLookupItemDescricao(LookupItem lookupItem);

    @Named("responsavelTurnoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResponsavelTurnoDTO toDtoResponsavelTurnoId(ResponsavelTurno responsavelTurno);

    @Named("responsavelAreaFormacaoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResponsavelAreaFormacaoDTO toDtoResponsavelAreaFormacaoId(ResponsavelAreaFormacao responsavelAreaFormacao);

    @Named("responsavelCursoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResponsavelCursoDTO toDtoResponsavelCursoId(ResponsavelCurso responsavelCurso);

    @Named("responsavelDisciplinaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResponsavelDisciplinaDTO toDtoResponsavelDisciplinaId(ResponsavelDisciplina responsavelDisciplina);

    @Named("responsavelTurmaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResponsavelTurmaDTO toDtoResponsavelTurmaId(ResponsavelTurma responsavelTurma);
}
