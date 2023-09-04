package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.NaturezaTrabalho;
import com.ravunana.longonkelo.service.dto.NaturezaTrabalhoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NaturezaTrabalho} and its DTO {@link NaturezaTrabalhoDTO}.
 */
@Mapper(componentModel = "spring")
public interface NaturezaTrabalhoMapper extends EntityMapper<NaturezaTrabalhoDTO, NaturezaTrabalho> {}
