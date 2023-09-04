package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.FormacaoDocente;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.service.dto.DocenteDTO;
import com.ravunana.longonkelo.service.dto.FormacaoDocenteDTO;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FormacaoDocente} and its DTO {@link FormacaoDocenteDTO}.
 */
@Mapper(componentModel = "spring")
public interface FormacaoDocenteMapper extends EntityMapper<FormacaoDocenteDTO, FormacaoDocente> {
    @Mapping(target = "grauAcademico", source = "grauAcademico", qualifiedByName = "lookupItemDescricao")
    @Mapping(target = "docente", source = "docente", qualifiedByName = "docenteNome")
    FormacaoDocenteDTO toDto(FormacaoDocente s);

    @Named("lookupItemDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    LookupItemDTO toDtoLookupItemDescricao(LookupItem lookupItem);

    @Named("docenteNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DocenteDTO toDtoDocenteNome(Docente docente);
}
