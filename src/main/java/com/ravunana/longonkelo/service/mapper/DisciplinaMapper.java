package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Disciplina;
import com.ravunana.longonkelo.service.dto.DisciplinaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Disciplina} and its DTO {@link DisciplinaDTO}.
 */
@Mapper(componentModel = "spring")
public interface DisciplinaMapper extends EntityMapper<DisciplinaDTO, Disciplina> {}
