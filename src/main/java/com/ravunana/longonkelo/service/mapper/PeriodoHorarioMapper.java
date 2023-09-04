package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.PeriodoHorario;
import com.ravunana.longonkelo.domain.Turno;
import com.ravunana.longonkelo.service.dto.PeriodoHorarioDTO;
import com.ravunana.longonkelo.service.dto.TurnoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PeriodoHorario} and its DTO {@link PeriodoHorarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface PeriodoHorarioMapper extends EntityMapper<PeriodoHorarioDTO, PeriodoHorario> {
    @Mapping(target = "turno", source = "turno", qualifiedByName = "turnoNome")
    PeriodoHorarioDTO toDto(PeriodoHorario s);

    @Named("turnoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    TurnoDTO toDtoTurnoNome(Turno turno);
}
