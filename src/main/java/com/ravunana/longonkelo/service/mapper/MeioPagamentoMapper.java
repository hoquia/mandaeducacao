package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.MeioPagamento;
import com.ravunana.longonkelo.service.dto.MeioPagamentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MeioPagamento} and its DTO {@link MeioPagamentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface MeioPagamentoMapper extends EntityMapper<MeioPagamentoDTO, MeioPagamento> {}
