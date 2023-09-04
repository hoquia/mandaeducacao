package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.CategoriaEmolumento;
import com.ravunana.longonkelo.domain.PlanoDesconto;
import com.ravunana.longonkelo.service.dto.CategoriaEmolumentoDTO;
import com.ravunana.longonkelo.service.dto.PlanoDescontoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoDesconto} and its DTO {@link PlanoDescontoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlanoDescontoMapper extends EntityMapper<PlanoDescontoDTO, PlanoDesconto> {
    @Mapping(target = "categoriasEmolumentos", source = "categoriasEmolumentos", qualifiedByName = "categoriaEmolumentoNomeSet")
    PlanoDescontoDTO toDto(PlanoDesconto s);

    @Mapping(target = "removeCategoriasEmolumento", ignore = true)
    PlanoDesconto toEntity(PlanoDescontoDTO planoDescontoDTO);

    @Named("categoriaEmolumentoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    CategoriaEmolumentoDTO toDtoCategoriaEmolumentoNome(CategoriaEmolumento categoriaEmolumento);

    @Named("categoriaEmolumentoNomeSet")
    default Set<CategoriaEmolumentoDTO> toDtoCategoriaEmolumentoNomeSet(Set<CategoriaEmolumento> categoriaEmolumento) {
        return categoriaEmolumento.stream().map(this::toDtoCategoriaEmolumentoNome).collect(Collectors.toSet());
    }
}
