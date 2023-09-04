package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.AnoLectivo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface AnoLectivoRepositoryWithBagRelationships {
    Optional<AnoLectivo> fetchBagRelationships(Optional<AnoLectivo> anoLectivo);

    List<AnoLectivo> fetchBagRelationships(List<AnoLectivo> anoLectivos);

    Page<AnoLectivo> fetchBagRelationships(Page<AnoLectivo> anoLectivos);
}
