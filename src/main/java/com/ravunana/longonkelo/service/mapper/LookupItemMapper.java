package com.ravunana.longonkelo.service.mapper;

import com.ravunana.longonkelo.domain.Lookup;
import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.service.dto.LookupDTO;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LookupItem} and its DTO {@link LookupItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface LookupItemMapper extends EntityMapper<LookupItemDTO, LookupItem> {
    @Mapping(target = "lookup", source = "lookup", qualifiedByName = "lookupNome")
    LookupItemDTO toDto(LookupItem s);

    @Named("lookupNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    LookupDTO toDtoLookupNome(Lookup lookup);
}
