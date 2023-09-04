package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.CategoriaOcorrencia;
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.domain.MedidaDisciplinar;
import com.ravunana.longonkelo.service.dto.CategoriaOcorrenciaDTO;
import com.ravunana.longonkelo.service.dto.DocenteDTO;
import com.ravunana.longonkelo.service.dto.MedidaDisciplinarDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoriaOcorrencia} and its DTO {@link CategoriaOcorrenciaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoriaOcorrenciaMapper extends EntityMapper<CategoriaOcorrenciaDTO, CategoriaOcorrencia> {
    @Mapping(target = "encaminhar", source = "encaminhar", qualifiedByName = "docenteNome")
    @Mapping(target = "referencia", source = "referencia", qualifiedByName = "categoriaOcorrenciaId")
    @Mapping(target = "medidaDisciplinar", source = "medidaDisciplinar", qualifiedByName = "medidaDisciplinarDescricao")
    CategoriaOcorrenciaDTO toDto(CategoriaOcorrencia s);

    @Named("categoriaOcorrenciaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoriaOcorrenciaDTO toDtoCategoriaOcorrenciaId(CategoriaOcorrencia categoriaOcorrencia);

    @Named("docenteNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DocenteDTO toDtoDocenteNome(Docente docente);

    @Named("medidaDisciplinarDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    MedidaDisciplinarDTO toDtoMedidaDisciplinarDescricao(MedidaDisciplinar medidaDisciplinar);
}
