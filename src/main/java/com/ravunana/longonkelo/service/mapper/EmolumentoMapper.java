package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.CategoriaEmolumento;
import com.ravunana.longonkelo.domain.Emolumento;
import com.ravunana.longonkelo.domain.Imposto;
import com.ravunana.longonkelo.domain.PlanoMulta;
import com.ravunana.longonkelo.service.dto.CategoriaEmolumentoDTO;
import com.ravunana.longonkelo.service.dto.EmolumentoDTO;
import com.ravunana.longonkelo.service.dto.ImpostoDTO;
import com.ravunana.longonkelo.service.dto.PlanoMultaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Emolumento} and its DTO {@link EmolumentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmolumentoMapper extends EntityMapper<EmolumentoDTO, Emolumento> {
    @Mapping(target = "categoria", source = "categoria", qualifiedByName = "categoriaEmolumentoNome")
    @Mapping(target = "imposto", source = "imposto", qualifiedByName = "impostoDescricao")
    @Mapping(target = "referencia", source = "referencia")
    @Mapping(target = "planoMulta", source = "planoMulta", qualifiedByName = "planoMultaDescricao")
    EmolumentoDTO toDto(Emolumento s);

    @Named("emolumentoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    EmolumentoDTO toDtoEmolumentoNome(Emolumento emolumento);

    @Named("categoriaEmolumentoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    CategoriaEmolumentoDTO toDtoCategoriaEmolumentoNome(CategoriaEmolumento categoriaEmolumento);

    @Named("impostoDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    ImpostoDTO toDtoImpostoDescricao(Imposto imposto);

    @Named("planoMultaDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    PlanoMultaDTO toDtoPlanoMultaDescricao(PlanoMulta planoMulta);
}
