package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Turno;
import com.ravunana.longonkelo.service.dto.TurnoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Turno} and its DTO {@link TurnoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TurnoMapper extends EntityMapper<TurnoDTO, Turno> {}
