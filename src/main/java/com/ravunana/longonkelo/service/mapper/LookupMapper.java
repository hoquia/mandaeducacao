package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Lookup;
import com.ravunana.longonkelo.service.dto.LookupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lookup} and its DTO {@link LookupDTO}.
 */
@Mapper(componentModel = "spring")
public interface LookupMapper extends EntityMapper<LookupDTO, Lookup> {}
