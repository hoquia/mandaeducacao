package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.CategoriaEmolumento;
import com.ravunana.longonkelo.domain.PlanoMulta;
import com.ravunana.longonkelo.service.dto.CategoriaEmolumentoDTO;
import com.ravunana.longonkelo.service.dto.PlanoMultaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoriaEmolumento} and its DTO {@link CategoriaEmolumentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoriaEmolumentoMapper extends EntityMapper<CategoriaEmolumentoDTO, CategoriaEmolumento> {
    @Mapping(target = "planoMulta", source = "planoMulta", qualifiedByName = "planoMultaDescricao")
    CategoriaEmolumentoDTO toDto(CategoriaEmolumento s);

    @Named("planoMultaDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    PlanoMultaDTO toDtoPlanoMultaDescricao(PlanoMulta planoMulta);
}
