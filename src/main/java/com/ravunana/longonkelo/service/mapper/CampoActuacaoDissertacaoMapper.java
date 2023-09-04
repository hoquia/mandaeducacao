package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.CampoActuacaoDissertacao;
import com.ravunana.longonkelo.domain.Curso;
import com.ravunana.longonkelo.service.dto.CampoActuacaoDissertacaoDTO;
import com.ravunana.longonkelo.service.dto.CursoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CampoActuacaoDissertacao} and its DTO {@link CampoActuacaoDissertacaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CampoActuacaoDissertacaoMapper extends EntityMapper<CampoActuacaoDissertacaoDTO, CampoActuacaoDissertacao> {
    @Mapping(target = "cursos", source = "cursos", qualifiedByName = "cursoNomeSet")
    CampoActuacaoDissertacaoDTO toDto(CampoActuacaoDissertacao s);

    @Mapping(target = "removeCursos", ignore = true)
    CampoActuacaoDissertacao toEntity(CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO);

    @Named("cursoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    CursoDTO toDtoCursoNome(Curso curso);

    @Named("cursoNomeSet")
    default Set<CursoDTO> toDtoCursoNomeSet(Set<Curso> curso) {
        return curso.stream().map(this::toDtoCursoNome).collect(Collectors.toSet());
    }
}
