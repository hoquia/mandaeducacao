package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.EstadoDissertacao;
import com.ravunana.longonkelo.service.dto.EstadoDissertacaoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstadoDissertacao} and its DTO {@link EstadoDissertacaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstadoDissertacaoMapper extends EntityMapper<EstadoDissertacaoDTO, EstadoDissertacao> {}
