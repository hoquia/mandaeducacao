package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.MedidaDisciplinar;
import com.ravunana.longonkelo.service.dto.MedidaDisciplinarDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MedidaDisciplinar} and its DTO {@link MedidaDisciplinarDTO}.
 */
@Mapper(componentModel = "spring")
public interface MedidaDisciplinarMapper extends EntityMapper<MedidaDisciplinarDTO, MedidaDisciplinar> {}
