package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface DisciplinaCurricularRepositoryWithBagRelationships {
    Optional<DisciplinaCurricular> fetchBagRelationships(Optional<DisciplinaCurricular> disciplinaCurricular);

    List<DisciplinaCurricular> fetchBagRelationships(List<DisciplinaCurricular> disciplinaCurriculars);

    Page<DisciplinaCurricular> fetchBagRelationships(Page<DisciplinaCurricular> disciplinaCurriculars);
}
