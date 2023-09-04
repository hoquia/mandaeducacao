package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface EstadoDisciplinaCurricularRepositoryWithBagRelationships {
    Optional<EstadoDisciplinaCurricular> fetchBagRelationships(Optional<EstadoDisciplinaCurricular> estadoDisciplinaCurricular);

    List<EstadoDisciplinaCurricular> fetchBagRelationships(List<EstadoDisciplinaCurricular> estadoDisciplinaCurriculars);

    Page<EstadoDisciplinaCurricular> fetchBagRelationships(Page<EstadoDisciplinaCurricular> estadoDisciplinaCurriculars);
}
